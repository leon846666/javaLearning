package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.enums.BizCodeEnum;
import org.example.enums.CouponCategoryEnum;
import org.example.enums.CouponPublishEnum;
import org.example.enums.CouponStateEnum;
import org.example.exception.BizException;
import org.example.interceptor.LoginInterceptor;
import org.example.mapper.CouponMapper;
import org.example.mapper.CouponRecordMapper;
import org.example.model.CouponDO;
import org.example.model.CouponRecordDO;
import org.example.model.LoginUser;
import org.example.request.NewUserCouponRequest;
import org.example.service.CouponService;
import org.example.utils.CommonUtil;
import org.example.utils.JsonData;
import org.example.utils.RedisTemplateUtil;
import org.example.vo.CouponVO;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yangLiu
 * @since 2023-03-05
 */
@Service
@Slf4j
@AllArgsConstructor
public class CouponServiceImpl implements CouponService {


    private CouponMapper couponMapper;
    private CouponRecordMapper recordMapper;
    private RedisTemplateUtil redisTemplate;
    private RedissonClient redissonClient;

    @Override
    public Map<String, Object> pageCouponActivity(int page, int size) {

        Page<CouponDO> pageInfo = new Page<>(page, size);

        IPage<CouponDO> couponDOIPage = couponMapper.selectPage(pageInfo, new QueryWrapper<CouponDO>()
                .eq("publish", CouponPublishEnum.PUBLISH)
                .eq("category", CouponCategoryEnum.PROMOTION)
                .orderByDesc("create_time"));


        Map<String, Object> pageMap = new HashMap<>(3);
        //总条数
        pageMap.put("total_record", couponDOIPage.getTotal());
        //总页数
        pageMap.put("total_page", couponDOIPage.getPages());

        pageMap.put("current_data", couponDOIPage.getRecords().stream().map(obj -> beanProcess(obj)).collect(Collectors.toList()));


        return pageMap;
    }

    /**
     * @Description: 1. 判断优惠卷是否存在
     * 2. 判断是否可以领取，时间，库存，超过现在
     * 3. 扣减库存
     * 4. 保存领卷记录
     * @Author: Yang
     * @Date: 2023/3/7 11:43
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public JsonData addCoupon(long couponId, CouponCategoryEnum promotion) {
        LoginUser loginUser = LoginInterceptor.threadLocal.get();
//        /**
//         * 原生分布式锁 开始
//         * 1、原子加锁 设置过期时间，防止宕机死锁
//         * 2、原子解锁：需要判断是不是自己的锁
//         */
//        String uuid = CommonUtil.generateUUID();
//        String lockKey = "lock:coupon:" + couponId;
//        Boolean nativeLock = redisTemplate.setIfAbsent(lockKey, uuid, 30);
//        if (nativeLock) {
//            //加锁成功
//            log.info("加锁：{}", nativeLock);
//            try {
//                //执行业务  TODO
//            } finally {
//                String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
//
//                Integer result = redisTemplate.execute(new DefaultRedisScript<>(script, Integer.class), Arrays.asList(lockKey), uuid);
//                log.info("解锁：{}", result);
//            }
//
//        } else {
//            //加锁失败，睡眠100毫秒，自旋重试
//            try {
//                TimeUnit.MILLISECONDS.sleep(100L);
//            } catch (InterruptedException e) {
//            }
//            return addCoupon(couponId, CouponCategoryEnum.PROMOTION);
//        }
        //原生分布式锁 结束
        String uuid = CommonUtil.generateUUID();
        String lockKey = "lock:coupon:" + couponId;
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        log.info("领卷接口加锁成功 {}", Thread.currentThread().getId());
        try {


            CouponDO couponDO = couponMapper.selectOne(new QueryWrapper<CouponDO>()
                    .eq("id", couponId)
                    .eq("category", promotion.name())
                    .eq("publish", CouponPublishEnum.PUBLISH));
            if (null == couponDO) {
                throw new BizException(BizCodeEnum.COUPON_NO_EXITS);
            }
            checkCoupon(couponDO, loginUser.getId());

            // create coupon record
            CouponRecordDO couponRecordDO = new CouponRecordDO();
            BeanUtils.copyProperties(couponDO, couponRecordDO);
            couponRecordDO.setCreateTime(new Date());
            couponRecordDO.setUseState(CouponStateEnum.NEW.name());
            couponRecordDO.setUserId(loginUser.getId());
            couponRecordDO.setCouponId(couponId);
            couponRecordDO.setId(null);

            // stock todo
            int rows = couponMapper.reduceStock(couponId);

            log.info("库存减少 :{}", rows);
            if (rows == 1) {
                // 库存记录扣减成功才保存记录
                recordMapper.insert(couponRecordDO);
            } else {
                log.warn("发放优惠卷失败 ： {}", BizCodeEnum.COUPON_NO_STOCK);
                throw new BizException(BizCodeEnum.COUPON_NO_STOCK);
            }
        } finally {
            lock.unlock();
            log.info("领卷接口 解锁{}", Thread.currentThread().getId());
        }


        return JsonData.buildSuccess();
    }

    /**
     * 用户微服务调用时，没传递token，
     *
     * @Author: Yang
     * @Date: 2023/3/17 17:54
     */
    @Override
    public JsonData initNewMember(NewUserCouponRequest newUserCouponRequest) {
        LoginUser loginUser = new LoginUser();
        loginUser.setName(newUserCouponRequest.getName());
        loginUser.setId(newUserCouponRequest.getUserId());
        LoginInterceptor.threadLocal.set(loginUser);

        List<CouponDO> couponDOList = couponMapper.selectList(new QueryWrapper<CouponDO>()
                .eq("category", CouponCategoryEnum.NEW_USER));

        couponDOList.forEach(item -> {
            this.addCoupon(item.getId(), CouponCategoryEnum.NEW_USER);
        });

        return JsonData.buildSuccess();
    }

    private void checkCoupon(CouponDO couponDO, long id) {
        Integer stock = couponDO.getStock();
        if (stock <= 0) {
            throw new BizException(BizCodeEnum.COUPON_NO_STOCK);
        }
        if (!couponDO.getPublish().equals(CouponPublishEnum.PUBLISH.name())) {
            throw new BizException(BizCodeEnum.COUPON_GET_FAIL);
        }
        long endTime = couponDO.getEndTime().getTime();
        long startTime = couponDO.getStartTime().getTime();

        long currentTime = CommonUtil.getCurrentTimeStamp();
        if (currentTime > endTime || currentTime < startTime) {
            throw new BizException(BizCodeEnum.COUPON_OUT_OF_TIME);
        }
        Integer integer = recordMapper.selectCount(new QueryWrapper<CouponRecordDO>()
                .eq("coupon_id", couponDO.getId())
                .eq("user_id", id));
        if (integer >= couponDO.getUserLimit()) {
            throw new BizException(BizCodeEnum.COUPON_OUT_OF_LIMIT);
        }

    }

    private CouponVO beanProcess(CouponDO couponDO) {
        CouponVO couponVO = new CouponVO();
        BeanUtils.copyProperties(couponDO, couponVO);
        return couponVO;
    }
}
