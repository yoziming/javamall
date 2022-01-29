package yozi.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.stereotype.Service;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.common.utils.Query;
import yozi.mall.order.dao.OrderItemDao;
import yozi.mall.order.entity.OrderItemEntity;
import yozi.mall.order.entity.OrderReturnReasonEntity;
import yozi.mall.order.service.OrderItemService;

import java.util.Map;

@Service("orderItemService")
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OrderItemEntity> implements OrderItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderItemEntity> page = this.page(
                new Query<OrderItemEntity>().getPage(params),
                new QueryWrapper<OrderItemEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * queues：聲明需要監聽的隊列
     * channel：當前傳輸數據的通道
     */
    //@RabbitListener(queues = {"hello-java-queue"})
    public void revieveMessage(Message message,
                               OrderReturnReasonEntity content) {
        //拿到主體內容
        byte[] body = message.getBody();
        //拿到的消息頭屬性信息
        MessageProperties messageProperties = message.getMessageProperties();
        System.out.println("接受到的消息...內容" + message + "===內容：" + content);

    }
}