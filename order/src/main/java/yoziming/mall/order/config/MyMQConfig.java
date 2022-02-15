package yoziming.mall.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/* 容器中的Queue、Exchange、Binding 會自動創建（在RabbitMQ）不存在的情況下
 * 創建隊列，交換機，延遲隊列，綁定關係 的configuration
 * 不會重複創建覆蓋(一旦創好，不能更新)
 * 1、第一次使用隊列【監聽】的時候才會創建
 * 2、Broker沒有隊列、交換機才會創建
 */
@Configuration
public class MyMQConfig {

    // 延遲隊列
    @Bean
    public Queue orderDelayQueue() {
    /*
        Queue(String name,  隊列名字
        boolean durable,  是否持久化
        boolean exclusive,  是否排他
        boolean autoDelete, 是否自動刪除
        Map<String, Object> arguments) 屬性【TTL、死信路由、死信路由鍵】
     */
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", "order-event-exchange");// 死信路由
        arguments.put("x-dead-letter-routing-key", "order.release.order");// 死信路由鍵
        arguments.put("x-message-ttl", 300000); // 消息過期時間 30分鐘
        Queue queue = new Queue("order.delay.queue", true, false, false, arguments);

        return queue;
    }

    // 死信隊列
    @Bean
    public Queue orderReleaseQueue() {

        return new Queue("order.release.order.queue", true, false, false);
    }

    // 普通路由【死信路由】
    @Bean
    public Exchange orderEventExchange() {
        /*
         *   String name,
         *   boolean durable,
         *   boolean autoDelete,
         *   Map<String, Object> arguments
         * */
        return new TopicExchange("order-event-exchange", true, false);

    }

    // 交換機與延遲隊列的綁定
    @Bean
    public Binding orderCreateBinding() {
        /*
         * String destination, 目的地（隊列名或者交換機名字）
         * DestinationType destinationType, 目的地類型（Queue、Exhcange）
         * String exchange,
         * String routingKey,
         * Map<String, Object> arguments
         * */
        return new Binding("order.delay.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.create.order",
                null);
    }

    // 死信路由與普通隊列的綁定
    @Bean
    public Binding orderReleaseBinding() {

        return new Binding("order.release.order.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.release.order",
                null);
    }

    // 訂單釋放直接和庫存釋放進行綁定
    @Bean
    public Binding orderReleaseOtherBinding() {

        return new Binding("stock.release.stock.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.release.other.#",
                null);
    }

    /**
     * 商品秒殺隊列
     * 作用：削峰，創建訂單
     */
    @Bean
    public Queue orderSecKillOrderQueue() {
        Queue queue = new Queue("order.seckill.order.queue", true, false, false);
        return queue;
    }

    @Bean
    public Binding orderSecKillOrderQueueBinding() {
        //String destination, DestinationType destinationType, String exchange, String routingKey,
        // 			Map<String, Object> arguments
        Binding binding = new Binding(
                "order.seckill.order.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.seckill.order",
                null);

        return binding;
    }
}
