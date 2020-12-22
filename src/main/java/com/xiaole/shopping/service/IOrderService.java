package com.xiaole.shopping.service;

import com.xiaole.shopping.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaole.shopping.entity.User;
import com.xiaole.shopping.vo.OrderVO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 小乐
 * @since 2020-12-13
 */
public interface IOrderService extends IService<Orders> {
    boolean save(Orders orders, User user,String address,String remark);
    List<OrderVO> findAllOrederVOByUserId(Integer id);
}
