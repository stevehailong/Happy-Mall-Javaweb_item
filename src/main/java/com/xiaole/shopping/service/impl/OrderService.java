package com.xiaole.shopping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaole.shopping.entity.*;
import com.xiaole.shopping.mapper.*;
import com.xiaole.shopping.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaole.shopping.vo.OrderDetailVO;
import com.xiaole.shopping.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 小乐
 * @since 2020-12-13
 */
@Service
public class OrderService extends ServiceImpl<OrderMapper, Orders> implements IOrderService {
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private ProductMapper productMapper;

    @Override
    public boolean save(Orders orders, User user, String address, String remark) {
        if(address == "" && orders.getUserAddress() == null){
            return false;
        }
        //判断是新地址还是老地址
        if (orders.getUserAddress().equals("newAddress")) {
            //如果是新地址，更新属性值
            UserAddress userAddress = new UserAddress();
            userAddress.setAddress(address);
            userAddress.setRemark(remark);
            userAddress.setUserId(user.getId());
            //将旧地址不设为默认
            QueryWrapper wrapper = new QueryWrapper<>();
            wrapper.eq("isdefault", 1);
            wrapper.eq("user_id", user.getId());
            UserAddress old = userAddressMapper.selectOne(wrapper);
            if (old != null) {
                old.setIsdefault(0);
                userAddressMapper.updateById(old);
            }
            userAddress.setIsdefault(1);
            //插入到数据库
            userAddressMapper.insert(userAddress);
            //更新userAddress
            orders.setUserAddress(address);
        }
        orders.setLoginName(user.getLoginName());
        orders.setUserId(user.getId());
        String seriNumber = null;
        try {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < 32; i++) {
                stringBuffer.append(Integer.toHexString(new Random().nextInt(16)));
            }
            seriNumber = stringBuffer.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        orders.setSerialnumber(seriNumber);
        orderMapper.insert(orders);
        //存储ordersdetail，将实体cart属性复制给OrderDetail
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", user.getId());
        List<Cart> cartList = cartMapper.selectList(wrapper);
        for (Cart cart : cartList) {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(cart, orderDetail);
            orderDetail.setId(null);
            //避免id冲突，重新设置id
            orderDetail.setOrderId(orders.getId());
            orderDetailMapper.insert(orderDetail);
        }

        //清空购物车
        QueryWrapper wrapper1 = new QueryWrapper();
        wrapper1.eq("user_id", user.getId());
        cartMapper.delete(wrapper1);
        return true;
    }

    @Override
    public List<OrderVO> findAllOrederVOByUserId(Integer id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id", id);
        List<Orders> ordersList = orderMapper.selectList(wrapper);
        List<OrderVO> orderVOList = ordersList.stream()
                .map(e -> new OrderVO(
                        e.getId(),
                        e.getLoginName(),
                        e.getSerialnumber(),
                        e.getUserAddress(),
                        e.getCost()
                )).collect(Collectors.toList());
        for (OrderVO ordervo : orderVOList
        ) {
            wrapper = new QueryWrapper();
            wrapper.eq("order_id", ordervo.getId());
            List<OrderDetail> orderDetailList = orderDetailMapper.selectList(wrapper);
            ArrayList<OrderDetailVO> orderDetailVOList = new ArrayList<>();
            for (OrderDetail orderDetail : orderDetailList
            ) {
                OrderDetailVO orderDetailVO = new OrderDetailVO();
                Product product = productMapper.selectById(orderDetail.getProductId());
                BeanUtils.copyProperties(product, orderDetailVO);
                BeanUtils.copyProperties(orderDetail, orderDetailVO);
                orderDetailVOList.add(orderDetailVO);
            }
            ordervo.setOrderDetailVOList(orderDetailVOList);
        }
        return orderVOList;
    }
}
