package com.xiaole.shopping.controller;


import com.xiaole.shopping.entity.User;
import com.xiaole.shopping.service.impl.CartService;
import com.xiaole.shopping.service.impl.ProductCategoryService;
import com.xiaole.shopping.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
@RequestMapping("/productCategory")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService service;
    @Autowired
    private CartService cartService;

    @GetMapping("/list")
    public ModelAndView list(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("main");
        modelAndView.addObject("list", service.getAllProductCategoryVO());
        User user =(User)session.getAttribute("user");
        if(user == null){
            modelAndView.addObject("cartList",new ArrayList<>());
        }else {
        modelAndView.addObject("cartList",cartService.findAllCartVOById(user.getId()));
        }
        return modelAndView;
    }
}

