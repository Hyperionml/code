package com.hyperionml.mapper;

import com.hyperionml.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao {

    @Select("select * from mybatis.eehomework5")
    public List<User> save();
}
