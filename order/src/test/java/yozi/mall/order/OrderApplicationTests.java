package yozi.mall.order;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
class OrderApplicationTests {

    @Autowired
    AmqpAdmin amqpAdmin;

    @Test
    void createExchange() {
        // 名稱, 持久, 自動刪
        DirectExchange directExchange = new DirectExchange("hello-exchange", true, false);
        amqpAdmin.declareExchange(directExchange);
        log.info("Exchange[{}]創建成功", "hello-exchange");
    }

    @Test
    void createQueue() {
        // import org.springframework.amqp.core.Queue;
        // 第三個參數 排他，若true表示隊列只能被聲明的連接綁定，其他人綁不上
        amqpAdmin.declareQueue(new Queue("hello-queue", true, false, false));
        log.info("Queue創建[{}]成功", "hello-queue");
    }

    @Test
    void createBinding() {
        // String destination目的地，隊列name或 交換機name
        // Binding.DestinationType destinationType目的地類型 queue還是exchange
        // String exchange 交換機
        // String routingKey 路由鍵
        // @Nullable Map<String, Object> arguments 自定義參數
        amqpAdmin.declareBinding(new Binding("hello-queue", Binding.DestinationType.QUEUE, "hello-exchange",
                "hello.java", null));
        log.info("Binding創建[{}]成功", "hello-binding");
    }

    @Autowired
    RabbitTemplate rabbitTemplate;

    // @Test
    // void send() {
    //     for (int i = 0; i < 5; i++) {
    //         RefundInfoEntity refundInfoEntity = new RefundInfoEntity();
    //         refundInfoEntity.setRefundContent("test" + i);
    //         rabbitTemplate.convertAndSend("hello-exchange", "hello.java", refundInfoEntity);
    //     }
    // }
}
