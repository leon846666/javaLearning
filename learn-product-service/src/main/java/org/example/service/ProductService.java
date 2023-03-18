package org.example.service;


import org.example.vo.ProductVO;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yangLiu
 * @since 2023-03-18
 */
public interface ProductService  {

    Map<String, Object> page(int page, int size);

    ProductVO findDetailById(long productId);
}
