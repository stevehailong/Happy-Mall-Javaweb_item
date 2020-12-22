package com.xiaole.shopping.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaole.shopping.entity.User;
import com.xiaole.shopping.service.impl.CartService;
import com.xiaole.shopping.service.impl.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小乐
 * @since 2020-12-13
 */
@Controller
@RequestMapping("/userAddress")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;
    @Autowired
    private CartService cartService;

    @GetMapping("/list")
    public ModelAndView list(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userAddressList");
        User user = (User)session.getAttribute("user");
        //购物车数据获取
        modelAndView.addObject("cartList",cartService.findAllCartVOById(user.getId()));
        //根据user_id获取地址数据
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",user.getId());
        modelAndView.addObject("addressList",userAddressService.list(wrapper));
        return modelAndView;
    }

    @GetMapping("/deleteById/{id}")
    public ModelAndView userAddress(HttpSession session,@PathVariable("id") Integer id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userAddressList");
        User user = (User)session.getAttribute("user");
        //购物车数据获取
        modelAndView.addObject("cartList", cartService.findAllCartVOById(user.getId()));
        //根据user_id获取地址数据
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_id",user.getId());
        modelAndView.addObject("addressList",userAddressService.list(wrapper));
        //根据user_id获取地址数据
        userAddressService.deletbyId(id);
        modelAndView.addObject("addressList",userAddressService.list(wrapper));
        return modelAndView;
    }
}

