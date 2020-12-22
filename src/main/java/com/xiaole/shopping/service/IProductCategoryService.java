package com.xiaole.shopping.service;

import com.xiaole.shopping.entity.ProductCategory;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaole.shopping.vo.ProductCategoryVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 小乐
 * @since 2020-12-13
 */
public interface IProductCategoryService extends IService<ProductCategory> {
    public List<ProductCategoryVO> getAllProductCategoryVO();
}
