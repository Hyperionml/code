package com.hyperionml;

import com.hyperionml.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Homework1ApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void testList() {
        System.out.println(userMapper.list());
    }

    @Test
    void testSelectAll(){
        System.out.println(userMapper.selectAll());
    }

}
