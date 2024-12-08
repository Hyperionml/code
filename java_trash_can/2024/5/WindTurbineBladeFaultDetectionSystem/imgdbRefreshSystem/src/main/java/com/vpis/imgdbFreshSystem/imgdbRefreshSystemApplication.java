package com.vpis.imgdbFreshSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class imgdbRefreshSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(imgdbRefreshSystemApplication.class, args);
    }

}
