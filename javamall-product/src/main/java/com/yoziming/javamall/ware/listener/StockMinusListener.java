package com.yoziming.javamall.ware.listener;

import com.rabbitmq.client.Channel;
import com.yoziming.common.to.mq.OrderTo;
import com.yoziming.javamall.ware.service.WareSkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author: yoziming
 * @Date: 2022/3/30 20:05
 */
@Slf4j
@Service
@RabbitListener(queues = "stock.minus.stock.queue")
public class StockMinusListener {

    @Autowired
    WareSkuService wareSkuService;

    @RabbitHandler
    public void handleStockLockedRelease(OrderTo to, Message message, Channel channel) throws IOException {
        log.info("收減去庫存的消息...");
        try {
            wareSkuService.minusWare(to);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }

    }
}
