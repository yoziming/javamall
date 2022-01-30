package yozi.mall.order.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yozi.mall.order.service.OrderService;

@Slf4j
@Component
@RabbitListener(queues = "order.seckill.order.queue")
public class OrderSeckillListener {

    @Autowired
    private OrderService orderService;

    // @RabbitHandler
    // public void listener(SeckillOrderTo orderTo, Channel channel, Message message) throws IOException {
    //     log.info("準備創建秒殺單的詳細信息...");
    //     try {
    //         orderService.createSeckillOrder(orderTo);
    //         channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    //     } catch (Exception e) {
    //         channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
    //     }
    // }
}
