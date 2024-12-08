package com.hyperionml;

import com.hyperionml.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AnnotationTest {

    @Autowired
    private UserController userController;

    @Test
    public void test(){
        System.out.println(userController.save());
    }
}
