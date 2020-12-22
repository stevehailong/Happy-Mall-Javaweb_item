package com.xiaole.shopping.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaole.shopping.Enums.GenderEnums;
import com.xiaole.shopping.entity.User;
import com.xiaole.shopping.service.impl.CartService;
import com.xiaole.shopping.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;


    @PostMapping("/register")
    public String register(User user, Model model) {
        boolean result = false;
        try {
            if(user.getGenderCode() == 1){
                user.setGender(GenderEnums.MAN);
            }
            if(user.getGenderCode() == 0){
                user.setGender(GenderEnums.WOMAN);
            }

            result = userService.save(user);
        } catch (Exception e) {
            model.addAttribute("error", user.getLoginName() + "已存在！请重新输入！");
            return "register";
        }
        if (result) return "login";
        else return "register";
    }
    /**
     * 登录功能
     *
     * @author 小乐
     * @since 2020-12-13
     */
    @PostMapping("/login")
    public String login(String loginName, String passWord, HttpSession session) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("login_name", loginName);
        wrapper.eq("password", passWord);
        User user = userService.getOne(wrapper);
        if (user == null) {
            return "login";
        } else {
            session.setAttribute("user", user);
            return "redirect:/productCategory/list";
        }
    }

    /**
     * 注销操作
     */
    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "login";
    }

    /**
     * 用户信息
     */
    @GetMapping("/userInfo")
    public ModelAndView userInfo(HttpSession session) {
        User user = (User) session.getAttribute("user");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("userInfo");
        modelAndView.addObject("cartList", cartService.findAllCartVOById(user.getId()));
        return modelAndView;
    }
}

