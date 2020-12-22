package com.xiaole.shopping.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaole.shopping.entity.Cart;
import com.xiaole.shopping.entity.User;
import com.xiaole.shopping.service.impl.CartService;
import com.xiaole.shopping.service.impl.UserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 小乐
 * @since 2020-12-13
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/add/{productId}/{price}/{quantity}")
    public String add(@PathVariable("productId") Integer productId,
                      @PathVariable("price") Float price,
                      @PathVariable("quantity") Integer quantity,
                      HttpSession httpSession) {
        Cart cart = new Cart();
        cart.setProductId(productId);
        cart.setQuantity(quantity);
        cart.setCost(price * quantity);
        //UserId从session里面去取
        User user = (User) httpSession.getAttribute("user");
        cart.setUserId(user.getId());
        try {
            if (cartService.save(cart)) {
                return "redirect:/cart/findAllCart";
            }
        } catch (Exception e) {
            //库存不足简单处理，跳到首页
            return "redirect:/productCategory/list";
        }
        return null;
    }

    @GetMapping("/findAllCart")
    public ModelAndView findAllCart(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement1");
        User user = (User) session.getAttribute("user");
        if (user == null) {
            modelAndView.addObject("cartList", new ArrayList<>());
        } else {
            modelAndView.addObject("cartList", cartService.findAllCartVOById(user.getId()));
        }
        return modelAndView;
    }

    @GetMapping("/deletById/{id}")
    public String deleById(@PathVariable("id") Integer id) {
        cartService.removeById(id);
        return "redirect:/cart/findAllCart";
    }

    @GetMapping("/settlement2")
    public ModelAndView settlement2(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("settlement2");
        User user = (User) session.getAttribute("user");
        if (user == null) {
            modelAndView.addObject("cartList", new ArrayList<>());
        } else {
            modelAndView.addObject("cartList", cartService.findAllCartVOById(user.getId()));
            QueryWrapper wrapper = new QueryWrapper();
            wrapper.eq("user_id",user.getId());
            modelAndView.addObject("addressList",userAddressService.list(wrapper));
        }
        return modelAndView;
    }

    @PostMapping("/update/{id}/{quantity}/{cost}")
    @ResponseBody
    public String updateCart(
            @PathVariable("id") Integer id,
            @PathVariable("quantity") Integer quantity,
            @PathVariable("cost") Float cost
            ) {
        Cart cart = cartService.getById(id);
        cart.setQuantity(quantity);
        cart.setCost(cost);
        if (cartService.updateById(cart)) {
            return "success";
        } else {
            return "failed";
        }
    }
}

