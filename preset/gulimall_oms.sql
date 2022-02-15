/*
Navicat MySQL Data Transfer

Source Server         : 192.168.56.10_3306
Source Server Version : 50727
Source Host           : 192.168.56.10:3306
Source Database       : gulimall_oms

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-03-11 17:36:38
*/
DROP DATABASE IF EXISTS `gulimall_oms`;

CREATE DATABASE IF NOT EXISTS `gulimall_oms` DEFAULT CHARACTER SET utf8mb4;

USE `gulimall_oms`;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for mq_message
-- ----------------------------
DROP TABLE IF EXISTS `mq_message`;
CREATE TABLE `mq_message` (
  `message_id` char(32) NOT NULL,
  `content` text,
  `to_exchane` varchar(255) DEFAULT NULL,
  `routing_key` varchar(255) DEFAULT NULL,
  `class_type` varchar(255) DEFAULT NULL,
  `message_status` int(1) DEFAULT '0' COMMENT '0-新建 1-已發送 2-錯誤抵達 3-已抵達',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of mq_message
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order
-- ----------------------------
DROP TABLE IF EXISTS `oms_order`;
CREATE TABLE `oms_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) DEFAULT NULL COMMENT 'member_id',
  `order_sn` char(64) DEFAULT NULL COMMENT '訂單號',
  `coupon_id` bigint(20) DEFAULT NULL COMMENT '使用的優惠券',
  `create_time` datetime DEFAULT NULL COMMENT 'create_time',
  `member_username` varchar(200) DEFAULT NULL COMMENT '用户名',
  `total_amount` decimal(18,4) DEFAULT NULL COMMENT '訂單總額',
  `pay_amount` decimal(18,4) DEFAULT NULL COMMENT '應付總額',
  `freight_amount` decimal(18,4) DEFAULT NULL COMMENT '運費金額',
  `promotion_amount` decimal(18,4) DEFAULT NULL COMMENT '促銷優化金額（促銷價、滿減、階梯價）',
  `integration_amount` decimal(18,4) DEFAULT NULL COMMENT '積分抵扣金額',
  `coupon_amount` decimal(18,4) DEFAULT NULL COMMENT '優惠券抵扣金額',
  `discount_amount` decimal(18,4) DEFAULT NULL COMMENT '後台調整訂單使用的折扣金額',
  `pay_type` tinyint(4) DEFAULT NULL COMMENT '支付方式【1->支付寶；2->微信；3->銀聯； 4->貨到付款；】',
  `source_type` tinyint(4) DEFAULT NULL COMMENT '訂單來源[0->PC訂單；1->app訂單]',
  `status` tinyint(4) DEFAULT NULL COMMENT '訂單狀態【0->待付款；1->待發貨；2->已發貨；3->已完成；4->已關閉；5->無效訂單】',
  `delivery_company` varchar(64) DEFAULT NULL COMMENT '物流公司(配送方式)',
  `delivery_sn` varchar(64) DEFAULT NULL COMMENT '物流單號',
  `auto_confirm_day` int(11) DEFAULT NULL COMMENT '自動確認時間（天）',
  `integration` int(11) DEFAULT NULL COMMENT '可以獲得的積分',
  `growth` int(11) DEFAULT NULL COMMENT '可以獲得的成長值',
  `bill_type` tinyint(4) DEFAULT NULL COMMENT '發票類型[0->不開發票；1->電子發票；2->紙質發票]',
  `bill_header` varchar(255) DEFAULT NULL COMMENT '發票抬頭',
  `bill_content` varchar(255) DEFAULT NULL COMMENT '發票內容',
  `bill_receiver_phone` varchar(32) DEFAULT NULL COMMENT '收票人電話',
  `bill_receiver_email` varchar(64) DEFAULT NULL COMMENT '收票人郵箱',
  `receiver_name` varchar(100) DEFAULT NULL COMMENT '收貨人姓名',
  `receiver_phone` varchar(32) DEFAULT NULL COMMENT '收貨人電話',
  `receiver_post_code` varchar(32) DEFAULT NULL COMMENT '收貨人郵編',
  `receiver_province` varchar(32) DEFAULT NULL COMMENT '省份/直轄市',
  `receiver_city` varchar(32) DEFAULT NULL COMMENT '城市',
  `receiver_region` varchar(32) DEFAULT NULL COMMENT '區',
  `receiver_detail_address` varchar(200) DEFAULT NULL COMMENT '詳細地址',
  `note` varchar(500) DEFAULT NULL COMMENT '訂單備註',
  `confirm_status` tinyint(4) DEFAULT NULL COMMENT '確認收貨狀態[0->未確認；1->已確認]',
  `delete_status` tinyint(4) DEFAULT NULL COMMENT '刪除狀態【0->未刪除；1->已刪除】',
  `use_integration` int(11) DEFAULT NULL COMMENT '下單時使用的積分',
  `payment_time` datetime DEFAULT NULL COMMENT '支付時間',
  `delivery_time` datetime DEFAULT NULL COMMENT '發貨時間',
  `receive_time` datetime DEFAULT NULL COMMENT '確認收貨時間',
  `comment_time` datetime DEFAULT NULL COMMENT '評價時間',
  `modify_time` datetime DEFAULT NULL COMMENT '修改時間',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_sn` (`order_sn`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='訂單';

-- ----------------------------
-- Records of oms_order
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_item
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_item`;
CREATE TABLE `oms_order_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) DEFAULT NULL COMMENT 'order_id',
  `order_sn` char(64) DEFAULT NULL COMMENT 'order_sn',
  `spu_id` bigint(20) DEFAULT NULL COMMENT 'spu_id',
  `spu_name` varchar(255) DEFAULT NULL COMMENT 'spu_name',
  `spu_pic` varchar(500) DEFAULT NULL COMMENT 'spu_pic',
  `spu_brand` varchar(200) DEFAULT NULL COMMENT '品牌',
  `category_id` bigint(20) DEFAULT NULL COMMENT '商品分類id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT '商品sku編號',
  `sku_name` varchar(255) DEFAULT NULL COMMENT '商品sku名字',
  `sku_pic` varchar(500) DEFAULT NULL COMMENT '商品sku圖片',
  `sku_price` decimal(18,4) DEFAULT NULL COMMENT '商品sku價格',
  `sku_quantity` int(11) DEFAULT NULL COMMENT '商品購買的數量',
  `sku_attrs_vals` varchar(500) DEFAULT NULL COMMENT '商品銷售屬性組合（JSON）',
  `promotion_amount` decimal(18,4) DEFAULT NULL COMMENT '商品促銷分解金額',
  `coupon_amount` decimal(18,4) DEFAULT NULL COMMENT '優惠券優惠分解金額',
  `integration_amount` decimal(18,4) DEFAULT NULL COMMENT '積分優惠分解金額',
  `real_amount` decimal(18,4) DEFAULT NULL COMMENT '該商品經過優惠後的分解金額',
  `gift_integration` int(11) DEFAULT NULL COMMENT '贈送積分',
  `gift_growth` int(11) DEFAULT NULL COMMENT '贈送成長值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='訂單項信息';

-- ----------------------------
-- Records of oms_order_item
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_operate_history
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_operate_history`;
CREATE TABLE `oms_order_operate_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) DEFAULT NULL COMMENT '訂單id',
  `operate_man` varchar(100) DEFAULT NULL COMMENT '操作人[用户；系統；後台管理員]',
  `create_time` datetime DEFAULT NULL COMMENT '操作時間',
  `order_status` tinyint(4) DEFAULT NULL COMMENT '訂單狀態【0->待付款；1->待發貨；2->已發貨；3->已完成；4->已關閉；5->無效訂單】',
  `note` varchar(500) DEFAULT NULL COMMENT '備註',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='訂單操作歷史記錄';

-- ----------------------------
-- Records of oms_order_operate_history
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_return_apply
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_return_apply`;
CREATE TABLE `oms_order_return_apply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) DEFAULT NULL COMMENT 'order_id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT '退貨商品id',
  `order_sn` char(32) DEFAULT NULL COMMENT '訂單編號',
  `create_time` datetime DEFAULT NULL COMMENT '申請時間',
  `member_username` varchar(64) DEFAULT NULL COMMENT '會員用户名',
  `return_amount` decimal(18,4) DEFAULT NULL COMMENT '退款金額',
  `return_name` varchar(100) DEFAULT NULL COMMENT '退貨人姓名',
  `return_phone` varchar(20) DEFAULT NULL COMMENT '退貨人電話',
  `status` tinyint(1) DEFAULT NULL COMMENT '申請狀態[0->待處理；1->退貨中；2->已完成；3->已拒絕]',
  `handle_time` datetime DEFAULT NULL COMMENT '處理時間',
  `sku_img` varchar(500) DEFAULT NULL COMMENT '商品圖片',
  `sku_name` varchar(200) DEFAULT NULL COMMENT '商品名稱',
  `sku_brand` varchar(200) DEFAULT NULL COMMENT '商品品牌',
  `sku_attrs_vals` varchar(500) DEFAULT NULL COMMENT '商品銷售屬性(JSON)',
  `sku_count` int(11) DEFAULT NULL COMMENT '退貨數量',
  `sku_price` decimal(18,4) DEFAULT NULL COMMENT '商品單價',
  `sku_real_price` decimal(18,4) DEFAULT NULL COMMENT '商品實際支付單價',
  `reason` varchar(200) DEFAULT NULL COMMENT '原因',
  `description述` varchar(500) DEFAULT NULL COMMENT '描述',
  `desc_pics` varchar(2000) DEFAULT NULL COMMENT '憑證圖片，以逗號隔開',
  `handle_note` varchar(500) DEFAULT NULL COMMENT '處理備註',
  `handle_man` varchar(200) DEFAULT NULL COMMENT '處理人員',
  `receive_man` varchar(100) DEFAULT NULL COMMENT '收貨人',
  `receive_time` datetime DEFAULT NULL COMMENT '收貨時間',
  `receive_note` varchar(500) DEFAULT NULL COMMENT '收貨備註',
  `receive_phone` varchar(20) DEFAULT NULL COMMENT '收貨電話',
  `company_address` varchar(500) DEFAULT NULL COMMENT '公司收貨地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='訂單退貨申請';

-- ----------------------------
-- Records of oms_order_return_apply
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_return_reason
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_return_reason`;
CREATE TABLE `oms_order_return_reason` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) DEFAULT NULL COMMENT '退貨原因名',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `status` tinyint(1) DEFAULT NULL COMMENT '啓用狀態',
  `create_time` datetime DEFAULT NULL COMMENT 'create_time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退貨原因';

-- ----------------------------
-- Records of oms_order_return_reason
-- ----------------------------

-- ----------------------------
-- Table structure for oms_order_setting
-- ----------------------------
DROP TABLE IF EXISTS `oms_order_setting`;
CREATE TABLE `oms_order_setting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `flash_order_overtime` int(11) DEFAULT NULL COMMENT '秒殺訂單超時關閉時間(分)',
  `normal_order_overtime` int(11) DEFAULT NULL COMMENT '正常訂單超時時間(分)',
  `confirm_overtime` int(11) DEFAULT NULL COMMENT '發貨後自動確認收貨時間（天）',
  `finish_overtime` int(11) DEFAULT NULL COMMENT '自動完成交易時間，不能申請退貨（天）',
  `comment_overtime` int(11) DEFAULT NULL COMMENT '訂單完成後自動好評時間（天）',
  `member_level` tinyint(2) DEFAULT NULL COMMENT '會員等級【0-不限會員等級，全部通用；其他-對應的其他會員等級】',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='訂單配置信息';

-- ----------------------------
-- Records of oms_order_setting
-- ----------------------------

-- ----------------------------
-- Table structure for oms_payment_info
-- ----------------------------
DROP TABLE IF EXISTS `oms_payment_info`;
CREATE TABLE `oms_payment_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_sn` char(64) DEFAULT NULL COMMENT '訂單號（對外業務號）',
  `order_id` bigint(20) DEFAULT NULL COMMENT '訂單id',
  `alipay_trade_no` varchar(50) DEFAULT NULL COMMENT '支付寶交易流水號',
  `total_amount` decimal(18,4) DEFAULT NULL COMMENT '支付總金額',
  `subject` varchar(200) DEFAULT NULL COMMENT '交易內容',
  `payment_status` varchar(20) DEFAULT NULL COMMENT '支付狀態',
  `create_time` datetime DEFAULT NULL COMMENT '創建時間',
  `confirm_time` datetime DEFAULT NULL COMMENT '確認時間',
  `callback_content` varchar(4000) DEFAULT NULL COMMENT '回調內容',
  `callback_time` datetime DEFAULT NULL COMMENT '回調時間',
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_sn` (`order_sn`) USING BTREE,
  UNIQUE KEY `alipay_trade_no` (`alipay_trade_no`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='支付信息表';

-- ----------------------------
-- Records of oms_payment_info
-- ----------------------------

-- ----------------------------
-- Table structure for oms_refund_info
-- ----------------------------
DROP TABLE IF EXISTS `oms_refund_info`;
CREATE TABLE `oms_refund_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_return_id` bigint(20) DEFAULT NULL COMMENT '退款的訂單',
  `refund` decimal(18,4) DEFAULT NULL COMMENT '退款金額',
  `refund_sn` varchar(64) DEFAULT NULL COMMENT '退款交易流水號',
  `refund_status` tinyint(1) DEFAULT NULL COMMENT '退款狀態',
  `refund_channel` tinyint(4) DEFAULT NULL COMMENT '退款渠道[1-支付寶，2-微信，3-銀聯，4-匯款]',
  `refund_content` varchar(5000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退款信息';

-- ----------------------------
-- Records of oms_refund_info
-- ----------------------------

-- ----------------------------
-- Table structure for undo_log
-- ----------------------------
DROP TABLE IF EXISTS `undo_log`;
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  `ext` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of undo_log
-- ----------------------------
