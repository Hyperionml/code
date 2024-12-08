package com.hyperionml.controller;

import com.hyperionml.pojo.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ProductController {

    @RequestMapping("/getProduct")
    public void getProduct(@RequestBody Product product){
        String proId = product.getProId();
        String proName = product.getProName();
        System.out.println("获取到了id为" + proId + "名称为" + proName + "的商品");
    }

    @RequestMapping("/getProductList")
    public void getProductList(@RequestBody List<Product> productList){
        for (Product product : productList) {
            String proId = product.getProId();
            String proName = product.getProName();
            System.out.println("获取到了id为" + proId + "名称为" + proName + "的商品");
        }
    }

    @RequestMapping("/showModelAndView")
    public ModelAndView showModelAndView(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("username", "heima");
        modelAndView.setViewName("register");
        return modelAndView;
    }

}
