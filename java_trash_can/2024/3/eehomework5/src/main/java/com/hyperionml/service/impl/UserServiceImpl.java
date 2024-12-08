package com.hyperionml.service.impl;

import com.hyperionml.entity.User;
import com.hyperionml.mapper.UserDao;
import com.hyperionml.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public List<User> save() {
        return userDao.save();
    }
}
