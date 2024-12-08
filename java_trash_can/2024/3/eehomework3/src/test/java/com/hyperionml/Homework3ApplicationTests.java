package com.hyperionml;

import com.hyperionml.mapper.OrdersMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Homework3ApplicationTests {

    @Autowired
    OrdersMapper ordersMapper;

    @Test
    void testFindOrderWithProduct() {
        System.out.println(ordersMapper.findOrderWithProduct(1));
    }

}
