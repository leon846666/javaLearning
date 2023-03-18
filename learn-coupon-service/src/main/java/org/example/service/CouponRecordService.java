package org.example.service;

import org.example.vo.CouponRecordVO;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yangLiu
 * @since 2023-03-05
 */
public interface CouponRecordService {

    Map<String,Object> page(int page, int size);

    CouponRecordVO findById(long recordId);

}
