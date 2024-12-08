package com.hyperionml;

import com.hyperionml.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Eehomework2ApplicationTests {

    @Autowired
    StudentMapper studentMapper;

    @Test
    void testSelect() {
        System.out.println(studentMapper.select("张三", "数学"));
        System.out.println("---------------------------------");
        System.out.println(studentMapper.select("", ""));
    }

    @Test
    void testSelectBeforeId(){
        System.out.println(studentMapper.listBeforeId(5));
    }

}
