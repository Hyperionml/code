package cn.itcast.mq.spring;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.SuccessCallback;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAmqpTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage2SimpleQueue() throws InterruptedException {
        String message = "hello, spring amqp!";
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        correlationData.getFuture().addCallback(result -> {
            if(result.isAck()){
                log.debug("消息成功投递到交换机！消息id：{}", correlationData.getId());
            }else {
                log.error("消息投递到交换机失败！消息id：{}", correlationData.getId());
            }
        }, ex -> {
            log.error("消息发送失败！", ex);

        });
        rabbitTemplate.convertAndSend("amq.topic", "a.simple.test", message, correlationData);
    }

    @Test
    public void testDurableMessage() {
        // 1.准备消息
        Message message = MessageBuilder.withBody("hello, spring".getBytes(StandardCharsets.UTF_8))
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .build();
        // 2.发送
        rabbitTemplate.convertAndSend("simple.queue", message);
    }

    @Test
    public void testTTLMessage() {
        // 1.准备消息
        Message message = MessageBuilder.withBody("hello, ttlMessage".getBytes(StandardCharsets.UTF_8))
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .setExpiration("5000")
                .build();
        // 2.发送
        rabbitTemplate.convertAndSend("ttl.direct", "ttl", message);
        // 3.记录
        log.info("消息已经成功发送！");
    }

    @Test
    public void testSendDelayMessage() throws InterruptedException {
        Message message = MessageBuilder.withBody("hello, ttl message".getBytes(StandardCharsets.UTF_8))
                .setDeliveryMode(MessageDeliveryMode.PERSISTENT)
                .setHeader("x-delay", 5000)
                .build();
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        // 3.发
        rabbitTemplate.convertAndSend("delay.direct", "a.simple.test", message, correlationData);

        log.info("发送消息成功");
    }

    @Test
    public void testLazyQueue() {
        for (int i = 0; i < 100000; i++) {
            Message message = MessageBuilder.withBody("hello, spring".getBytes(StandardCharsets.UTF_8))
                    .setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT)
                    .build();
            // 3.发
            rabbitTemplate.convertAndSend("lazy.queue",message);
        }
    }

    @Test
    public void testNormalQueue() {
        for (int i = 0; i < 100000; i++) {
            Message message = MessageBuilder.withBody("hello, spring".getBytes(StandardCharsets.UTF_8))
                    .setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT)
                    .build();
            // 3.发
            rabbitTemplate.convertAndSend("normal.queue",message);
        }
    }
}
