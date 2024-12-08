package com.hyperionml.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chapter10")
public class TestController {

    @GetMapping("/firstController")
    public String firstController(){
        System.out.println("访问到FirstController!");
        return "success";
    }
}
