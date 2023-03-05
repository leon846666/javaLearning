package net.javaLearning.service.impl;

import net.javaLearning.model.CouponDO;
import net.javaLearning.mapper.CouponMapper;
import net.javaLearning.service.CouponService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangLiu
 * @since 2023-03-05
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, CouponDO> implements CouponService {

}
