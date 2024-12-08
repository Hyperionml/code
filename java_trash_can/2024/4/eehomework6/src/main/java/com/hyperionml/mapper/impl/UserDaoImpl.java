package com.hyperionml.mapper.impl;

import com.hyperionml.anno.Log;
import com.hyperionml.mapper.UserDao;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public class UserDaoImpl implements UserDao {
    @Override
    @Log
    public int insert() {
        System.out.println("增方法");
        return 0;
    }

    @Override
    @Log
    public int delete() {
        System.out.println("删方法");
        return 0;
    }

    @Override
    @Log
    public int update() {
        System.out.println("改方法");
        return 0;
    }

    @Override
    @Log
    public void select() {
        System.out.println("查方法");
    }
}
