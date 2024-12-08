package com.hyperionml;

import com.hyperionml.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Eehomework7ApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {

        userService.zhuanZhang("wangwu", "zhangsan", 100);

    }

}
