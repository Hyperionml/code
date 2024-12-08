package com.hyperionml.service;

import com.hyperionml.pojo.User;
import com.hyperionml.vo.UserVO;

import java.util.List;

public interface UserService {
//    List<User> list();

    int deleteById(Integer id);

    void addUser(User user);

    User selectById(Integer id);

    void updateById(User user);

    List<User> page(Integer page, Integer pageSize, String name, Integer gender);
}
