package com.hyperionml.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Update("update spring.account set balance = balance + #{money} where username = #{name}")
    int addMoney(String name, Integer money);

    @Update("update spring.account set balance = balance - #{money} where username = #{name}")
    int deleteMoney(String name, Integer money);
}
