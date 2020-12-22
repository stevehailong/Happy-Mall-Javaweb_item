package com.xiaole.shopping.service;

import com.xiaole.shopping.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;

import java.awt.*;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 小乐
 * @since 2020-12-13
 */
public interface IProductService extends IService<Product> {
     List<Product> findCategoryById(String type,Integer categoryId);
}
