package com.xiaole.shopping.service.impl;

import com.xiaole.shopping.entity.Product;
import com.xiaole.shopping.mapper.ProductMapper;
import com.xiaole.shopping.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小乐
 * @since 2020-12-13
 */
@Service
public class ProductService extends ServiceImpl<ProductMapper, Product> implements IProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<Product> findCategoryById(String type,Integer categoryId) {
        //通过id去查询商品
        Map<String,Object> map=new HashMap<>();//遍历必须是Object类型
        map.put("categorylevel"+type+"_id",categoryId);
        return productMapper.selectByMap(map);
    }
}
