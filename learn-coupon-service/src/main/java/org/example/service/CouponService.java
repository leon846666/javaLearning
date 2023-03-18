package org.example.service;

import org.example.enums.CouponCategoryEnum;
import org.example.request.NewUserCouponRequest;
import org.example.utils.JsonData;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangLiu
 * @since 2023-03-05
 */
public interface CouponService {

    Map<String, Object> pageCouponActivity(int page, int size);

    JsonData addCoupon(long couponId, CouponCategoryEnum promotion);

    JsonData initNewMember(NewUserCouponRequest newUserCouponRequest);
}
