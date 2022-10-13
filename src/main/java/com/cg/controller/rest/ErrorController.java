package com.cg.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/errors")
public class ErrorController {

    @GetMapping()
    public ModelAndView showErrorPage(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/error-401");

        return modelAndView;
    }
}
