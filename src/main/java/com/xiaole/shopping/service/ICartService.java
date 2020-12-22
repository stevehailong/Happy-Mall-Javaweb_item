package com.xiaole.shopping.service;

import com.xiaole.shopping.entity.Cart;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaole.shopping.vo.CartVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 小乐
 * @since 2020-12-13
 */
public interface ICartService extends IService<Cart> {
        public List<CartVO> findAllCartVOById(Integer id);
}
