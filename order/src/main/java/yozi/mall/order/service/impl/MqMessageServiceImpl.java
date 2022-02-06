package yozi.mall.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import yozi.mall.common.utils.PageUtils;
import yozi.mall.common.utils.Query;
import yozi.mall.order.dao.MqMessageDao;
import yozi.mall.order.entity.MqMessageEntity;
import yozi.mall.order.service.MqMessageService;

import java.util.Map;

@Service("mqMessageService")
public class MqMessageServiceImpl extends ServiceImpl<MqMessageDao, MqMessageEntity> implements MqMessageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MqMessageEntity> page = this.page(
                new Query<MqMessageEntity>().getPage(params),
                new QueryWrapper<MqMessageEntity>()
        );

        return new PageUtils(page);
    }

    // @RabbitListener(queues = {"hello-queue"})
    // void receiveMsg(Message message,
    //                 RefundInfoEntity content,
    //                 Channel channel) {
    //     System.out.println(content);
    //     // deliveryTag在channel內按順序自增
    //     long deliveryTag = message.getMessageProperties().getDeliveryTag();
    //     // 手動簽收，後面的false = 非批量模式
    //     try {
    //         channel.basicAck(deliveryTag, false);
    //         System.out.println("簽收，單據流水號=" + deliveryTag);
    //     } catch (IOException e) {
    //         // 簽收失敗，例如網路異常
    //         e.printStackTrace();
    //     }
    // }
}