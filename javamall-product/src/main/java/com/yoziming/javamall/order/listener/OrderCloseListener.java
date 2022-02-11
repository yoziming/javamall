package com.yoziming.javamall.order.listener;

import com.rabbitmq.client.Channel;
import com.yoziming.javamall.order.entity.OrderEntity;
import com.yoziming.javamall.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author: yoziming
 * @Date: 2022/3/1 10:00
 * @Description:
 */
@Slf4j
@RabbitListener(queues = "order.release.order.queue")
@Service
public class OrderCloseListener {

    @Autowired
    OrderService orderService;

    @RabbitHandler
    public void listener(OrderEntity entity, Channel channel, Message message) throws IOException {
        log.info("收到過期的訂單消息，準備關閉訂單:{}", entity.getOrderSn());
        try {
            orderService.closeOrder(entity);
            //手動接消息
            //手動調用支付寶收單
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
