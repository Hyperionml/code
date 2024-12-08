package com.hyperionml;

import com.hyperionml.controller.UserController;
import com.hyperionml.pojo.User;
import com.hyperionml.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BooksManagementSystemApplicationTests {

    @Autowired
    private UserController userController;

    @Test
    void contextLoads() {
    }

    /*@Test
    void listTest(){
        userController.list();
    }*/

    @Test
    void insertTest(){
        User user = new User();
        user.setName("小明");
        user.setUserName("xiaoming");
        user.setGender(2);
        userController.addUser(user);
    }

    @Test
    void deleteTest(){
        int id = 3;
        userController.deleteById(id);
    }

}
