package org.example.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.example.mapper.BannerMapper;
import org.example.model.BannerDO;
import org.example.service.BannerService;
import org.example.utils.JsonData;
import org.example.vo.BannerVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yangLiu
 * @since 2023-03-18
 */
@Service
@AllArgsConstructor
public class BannerServiceImpl implements BannerService {

   private BannerMapper bannerMapper;

    @Override
    public JsonData list() {

        QueryWrapper<BannerDO> queryWrapper = new QueryWrapper<>();
        List<BannerDO> bannerDOS = bannerMapper.selectList(queryWrapper);
        List<BannerVO> bannerVOS = new ArrayList();
        bannerDOS.forEach( bannerDO -> {
            BannerVO bannerVO = new BannerVO();
            BeanUtils.copyProperties(bannerDO,bannerVO);
            bannerVOS.add(bannerVO);
        });

        return JsonData.buildSuccess(bannerVOS);
    }
}
