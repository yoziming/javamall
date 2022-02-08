package yoziming.mall.order.web;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import yoziming.mall.order.entity.OrderEntity;

import java.util.Date;
import java.util.UUID;

@Controller
public class HelloController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @ResponseBody
    @GetMapping(value = "/test/createOrder")
    public String createOrderTest() {

        //訂單下單成功
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderSn(UUID.randomUUID().toString());
        orderEntity.setModifyTime(new Date());

        //給MQ發送消息
        rabbitTemplate.convertAndSend("order-event-exchange", "order.create.order", orderEntity);

        return "ok";
    }

    @GetMapping(value = "/{page}.html")
    public String listPage(@PathVariable("page") String page) {

        return page;
    }

}
