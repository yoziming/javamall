package com.yoziming.javamall.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: yoziming
 * @Date: 2022/2/26 15:54
 * @Description:
 */
@Configuration
public class MyRabbitConfig {
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Exchange stockEventExchange() {
        return new TopicExchange("stock-event-exchange", true, false);
    }

    /**
     * 解鎖庫存隊列
     *
     * @return
     */
    @Bean
    public Queue stockReleaseStockQueue() {
        return new Queue("stock.release.stock.queue", true, false, false);
    }

    /**
     * 延時隊列
     *
     * @return
     */
    @Bean
    public Queue stockDelayQueue() {
        Map<String, Object> arguments = new HashMap<>();
        // 當隊列消息長度大於最大長度、或者過期的等，將從隊列中刪除的消息推送到指定的交換機中去而不是丟棄掉
        arguments.put("x-dead-letter-exchange", "stock-event-exchange");
        // 將刪除的消息推送到指定交換機的指定路由鍵的隊列中去, Feature=DLK
        arguments.put("x-dead-letter-routing-key", "stock.release");
        // 設置隊列中的所有消息的生存周期, 也可以在發布消息的時候單獨為某個消息指定剩餘生存時間,單位毫秒, 類似於redis中的ttl，生存時間到了，消息會被從隊里中刪除
        arguments.put("x-message-ttl", 4 * 60 * 1000); //4分鐘

        return new Queue("stock.delay.queue", true, false, false, arguments);
    }

    @Bean
    public Binding stockReleaseBinding() {
        return new Binding("stock.release.stock.queue",
                Binding.DestinationType.QUEUE,
                "stock-event-exchange",
                "stock.release.#",
                null);
    }

    @Bean
    public Binding stockLockedBinding() {
        return new Binding("stock.delay.queue",
                Binding.DestinationType.QUEUE,
                "stock-event-exchange",
                "stock.locked",
                null);
    }

    @Bean
    public Queue minusStockQueue() {
        return new Queue("stock.minus.stock.queue", true, false, false);
    }

    @Bean
    public Binding minusStockBinding() {
        return new Binding("stock.minus.stock.queue",
                Binding.DestinationType.QUEUE,
                "stock-event-exchange",
                "stock.minus",
                null);
    }

    //===========order=============

    /**
     * 容器中的Binding,Queue,Exchange都會自動創建（RabbitMQ沒有的情況）
     *
     * @return
     */
    @Bean
    public Queue orderDelayQueue() {
        Map<String, Object> arguments = new HashMap<>();
        // 當隊列消息長度大於最大長度、或者過期的等，將從隊列中刪除的消息推送到指定的交換機中去而不是丟棄掉
        arguments.put("x-dead-letter-exchange", "order-event-exchange");
        // 將刪除的消息推送到指定交換機的指定路由鍵的隊列中去, Feature=DLK
        arguments.put("x-dead-letter-routing-key", "order.release.order");
        // 設置隊列中的所有消息的生存周期, 也可以在發布消息的時候單獨為某個消息指定剩餘生存時間,單位毫秒, 類似於redis中的ttl，生存時間到了，消息會被從隊里中刪除
        arguments.put("x-message-ttl", 5 * 60 * 1000);

        return new Queue("order.delay.queue", true, false, false, arguments);
    }

    @Bean
    public Queue orderReleaseQueue() {
        return new Queue("order.release.order.queue", true, false, false);
    }

    @Bean
    public Exchange orderEventExchange() {
        return new TopicExchange("order-event-exchange", true, false);
    }

    @Bean
    public Binding orderCreateOrderBinding() {
        return new Binding("order.delay.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.create.order",
                null);
    }

    @Bean
    public Binding orderReleaseOrderBinding() {
        return new Binding("order.release.order.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.release.order",
                null);
    }

    /**
     * 訂單釋放直接和庫存釋放直接綁定
     *
     * @return
     */
    @Bean
    public Binding orderReleaseOtherBinding() {
        return new Binding("stock.release.stock.queue",
                Binding.DestinationType.QUEUE,
                "order-event-exchange",
                "order.release.other.#",
                null);
    }

}
