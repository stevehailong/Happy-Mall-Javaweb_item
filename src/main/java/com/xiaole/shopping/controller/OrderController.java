package com.xiaole.shopping.controller;


import com.sun.org.apache.xpath.internal.operations.Mod;
import com.xiaole.shopping.entity.Orders;
import com.xiaole.shopping.entity.User;
import com.xiaole.shopping.service.impl.CartService;
import com.xiaole.shopping.service.impl.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 小乐
 * @since 2020-12-13
 */
@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @PostMapping("/settlement3")
    public ModelAndView settlement3(
            Orders order,
            HttpSession session,
            String address,
            String remark,
            Model model) {
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement3");
        modelAndView.addObject("cartList",cartService.findAllCartVOById(user.getId()));
        modelAndView.addObject("orders",order);
        boolean save = orderService.save(order, user, address, remark);
        if(!save){
            modelAndView.setViewName("settlement2");
            model.addAttribute("error","地址不能为空，请重新填写！");
        }
        return modelAndView;
    }

    @GetMapping("/list")
    public ModelAndView list(HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("orderList");
        User user = (User) session.getAttribute("user");
        modelAndView.addObject("ordersList",orderService.findAllOrederVOByUserId(user.getId()));
        //购物车数据
        modelAndView.addObject("cartList",cartService.findAllCartVOById(user.getId()));
        return modelAndView;
    }
}

