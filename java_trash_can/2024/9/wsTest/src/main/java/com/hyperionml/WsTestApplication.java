package com.hyperionml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@SpringBootApplication
public class WsTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WsTestApplication.class, args);
    }

}
