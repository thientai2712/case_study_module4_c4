package com.cg.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("carts")
public class CartController {

    @GetMapping
    public ModelAndView showCart(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/cart/home");

        return modelAndView;
    }
}
