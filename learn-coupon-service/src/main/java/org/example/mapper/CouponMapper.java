package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.example.model.CouponDO;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yangLiu
 * @since 2023-03-05
 */
public interface CouponMapper extends BaseMapper<CouponDO> {

    int reduceStock(@Param("couponId") long couponId);
}
