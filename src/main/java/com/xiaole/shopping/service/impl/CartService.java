package com.xiaole.shopping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaole.shopping.entity.Cart;
import com.xiaole.shopping.entity.Product;
import com.xiaole.shopping.Enums.ResultEnum;
import com.xiaole.shopping.exception.stockException;
import com.xiaole.shopping.mapper.CartMapper;
import com.xiaole.shopping.mapper.ProductMapper;
import com.xiaole.shopping.service.ICartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaole.shopping.vo.CartVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 小乐
 * @since 2020-12-13
 */
@Service
@Slf4j
public class CartService extends ServiceImpl<CartMapper, Cart> implements ICartService {

    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public boolean save(Cart entity) {
        //扣库存
        Product product = productMapper.selectById(entity.getProductId());
        Integer stock = product.getStock() - entity.getQuantity();
        if (stock < 0) {
            //添加日志打印
            log.error("【添加购物车】库存不足！Stock={}",stock);
            throw new stockException(ResultEnum.STOCK_ERROR9);
        }
        product.setStock(stock);
        productMapper.updateById(product);
        if (cartMapper.insert(entity) == 1) {
            return true;
        }
        return false;
    }

    @Override
    public List<CartVO> findAllCartVOById(Integer id) {
        List<CartVO> cartVOList =new ArrayList<>();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",id);
        List<Cart> cartList = cartMapper.selectList(wrapper);
        for (Cart cart:cartList
             ) {
            CartVO cartVO = new CartVO();
            BeanUtils.copyProperties(cart,cartVO);
            Product product=productMapper.selectById(cart.getProductId());
            BeanUtils.copyProperties(product,cartVO);
            BeanUtils.copyProperties(cart,cartVO); //cart赋值一定放在最后，原因ID冲突
            cartVOList.add(cartVO);
        }
        return cartVOList;
    }
}
