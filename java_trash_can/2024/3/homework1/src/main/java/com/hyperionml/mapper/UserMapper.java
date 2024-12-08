package com.hyperionml.mapper;

import com.hyperionml.pojo.Student;
import com.hyperionml.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from mybatis.users")
    public List<User> list();

    @Select("select * from mybatis.t_student")
    public List<Student> selectAll();
}
