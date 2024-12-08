package com.hyperionml.controller;

import com.hyperionml.entity.User;
import com.hyperionml.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    public List<User> save(){
        return userService.save();
    }
}
