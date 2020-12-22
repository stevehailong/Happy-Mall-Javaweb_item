package com.xiaole.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RedirectHandler {
    @GetMapping("/{url}")
    public String redirect(@PathVariable("url") String url) {
        return url;
    }


    @GetMapping("/")
    public String main(){
        return "redirect:/productCategory/list";  //必须加redirect，否则当作html解析
    }
}
