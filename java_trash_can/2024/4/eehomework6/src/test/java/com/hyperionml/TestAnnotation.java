package com.hyperionml;

import com.hyperionml.mapper.impl.UserDaoImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestAnnotation {

    @Autowired
    private UserDaoImpl userDaoImpl;

    @Test
    void test() {
        userDaoImpl.insert();
        System.out.println("-----------------------");
        userDaoImpl.delete();
        System.out.println("-----------------------");
        userDaoImpl.update();
        System.out.println("-----------------------");
        userDaoImpl.select();
    }
}
