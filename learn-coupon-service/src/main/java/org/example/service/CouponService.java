package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.model.CouponDO;

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
}
