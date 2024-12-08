package com.hyperionml;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.hyperionml.mapper")
public class OssSpringTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(OssSpringTestApplication.class, args);
    }

}
