package com.hyperionml.mapper.impl;

import com.hyperionml.entity.User;
import com.hyperionml.mapper.UserDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public class UserDaoImpl implements UserDao {
    @Override
    public List<User> save() {
        return null;
    }
}
