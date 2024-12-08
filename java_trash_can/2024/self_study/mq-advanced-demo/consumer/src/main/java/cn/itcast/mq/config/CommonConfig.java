package cn.itcast.mq.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class CommonConfig {
    //其实他们本来就是默认持久化的
    @Bean
    public DirectExchange simpleDirect(){
        return new DirectExchange("simple.direct", true, false);
    }

    @Bean
    public Queue simpleQueue(){
        return QueueBuilder.durable("simple.queue").build();
    }
}
