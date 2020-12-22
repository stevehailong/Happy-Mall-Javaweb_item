package com.xiaole.shopping.controller;


import com.xiaole.shopping.entity.User;
import com.xiaole.shopping.service.impl.CartService;
import com.xiaole.shopping.service.impl.ProductCategoryService;
import com.xiaole.shopping.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小乐
 * @since 2020-12-13
 */
@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService service;
    @Autowired
    private ProductCategoryService ProductCategoryService;
    @Autowired
    private CartService cartService;

    @GetMapping("list/{type}/{id}")
    public ModelAndView list(@PathVariable("type") String type, @PathVariable("id") Integer id, HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productList");
        //通过id去查询商品
        Map<String,Object> map=new HashMap<>();//遍历必须是Object类型
        map.put("categorylevelthree_id",id);
        modelAndView.addObject("productList",service.findCategoryById(type,id));
        modelAndView.addObject("list", ProductCategoryService.getAllProductCategoryVO());
        User user =(User)session.getAttribute("user");
        if(user == null){
            modelAndView.addObject("cartList",new ArrayList<>());
        }else {
            modelAndView.addObject("cartList",cartService.findAllCartVOById(user.getId()));
        }
        return modelAndView;
    }

    @GetMapping("/findById/{id}")
    public ModelAndView findById(@PathVariable("id")Integer id,HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("productDetail");
        modelAndView.addObject("product",service.getById(id));
        modelAndView.addObject("list", ProductCategoryService.getAllProductCategoryVO());
        User user =(User)session.getAttribute("user");
        if(user == null){
            modelAndView.addObject("cartList",new ArrayList<>());
        }else {
            modelAndView.addObject("cartList",cartService.findAllCartVOById(user.getId()));
        }
        return modelAndView;
    }
}

