package yoziming.mall.ware.listener;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yoziming.mall.common.to.OrderTo;
import yoziming.mall.common.to.mq.StockLockedTo;
import yoziming.mall.ware.service.WareSkuService;

import java.io.IOException;

/**
 * 監聽死信隊列，解鎖庫存
 **/
@Slf4j
@RabbitListener(queues = "stock.release.stock.queue")
@Service
public class StockReleaseListener {

    @Autowired
    private WareSkuService wareSkuService;

    /**
     * 這個是監聽死信消息
     * 1、庫存自動解鎖
     * 下訂單成功，庫存鎖定成功，接下來的業務調用失敗，導致訂單回滾。之前鎖定的庫存就要自動解鎖
     * 2、訂單失敗
     * 庫存鎖定失敗
     * 只要解鎖庫存的消息失敗，一定要告訴服務解鎖失敗
     */
    @RabbitHandler
    public void handleStockLockedRelease(StockLockedTo to, Message message, Channel channel) throws IOException {
        System.out.println("******收到解鎖庫存的延時信息******，準備解鎖" + to.getDetailTo().getId());
        try {
            //當前消息是否被第二次及以後（重新）派發過來了
            // Boolean redelivered = message.getMessageProperties().getRedelivered();
            //解鎖庫存
            wareSkuService.unlockStock(to);
            // 手動刪除消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 解鎖失敗 將消息重新放回隊列，讓別人消費
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }

    /**
     * 客戶取消訂單，監聽到消息
     */
    @RabbitHandler
    public void handleOrderCloseRelease(OrderTo orderTo, Message message, Channel channel) throws IOException {
        System.out.println("******收到訂單關閉，準備解鎖庫存的信息******訂單號：" + orderTo.getOrderSn());
        try {
            wareSkuService.unlockStock(orderTo);
            // 手動刪除消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 解鎖失敗 將消息重新放回隊列，讓別人消費
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
