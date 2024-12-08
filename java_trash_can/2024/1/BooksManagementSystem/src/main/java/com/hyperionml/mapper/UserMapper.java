package com.hyperionml.mapper;

import com.hyperionml.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    //@Select("select * from user")
    List<User> list(String name, Integer gender);

    @Delete("delete from user where id = #{id}")
    int deleteById(Integer id);

    @Update("insert into user (name, user_name, password, gender, root) values (#{name}, #{userName}, #{password}, #{gender}, #{root})")
    void addUser(User user);

    @Select("select * from user where id = #{id}")
    User selectById(Integer id);

    void updateById(User user);

    /**
     * 分页查询
     * @param start
     * @param pageSize
     * @return
     */
    @Select("select * from user limit #{start}, #{pageSize}")
    public List<User> page(Integer start, Integer pageSize);
}
