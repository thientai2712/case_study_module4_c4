package com.cg.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/homes")
public class PageController {

    @GetMapping
    public ModelAndView showPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/product/home");


        return modelAndView;
    }
}
