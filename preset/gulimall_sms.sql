/*
Navicat MySQL Data Transfer

Source Server         : 192.168.56.10_3306
Source Server Version : 50727
Source Host           : 192.168.56.10:3306
Source Database       : gulimall_sms

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-03-11 17:37:07
*/
DROP DATABASE IF EXISTS `gulimall_sms`;

CREATE DATABASE IF NOT EXISTS `gulimall_sms` DEFAULT CHARACTER SET utf8mb4;

USE `gulimall_sms`;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sms_coupon
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon`;
CREATE TABLE `sms_coupon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `coupon_type` tinyint(1) DEFAULT NULL COMMENT '優惠卷類型[0->全場贈券；1->會員贈券；2->購物贈券；3->註冊贈券]',
  `coupon_img` varchar(2000) DEFAULT NULL COMMENT '優惠券圖片',
  `coupon_name` varchar(100) DEFAULT NULL COMMENT '優惠卷名字',
  `num` int(11) DEFAULT NULL COMMENT '數量',
  `amount` decimal(18,4) DEFAULT NULL COMMENT '金額',
  `per_limit` int(11) DEFAULT NULL COMMENT '每人限領張數',
  `min_point` decimal(18,4) DEFAULT NULL COMMENT '使用門檻',
  `start_time` datetime DEFAULT NULL COMMENT '開始時間',
  `end_time` datetime DEFAULT NULL COMMENT '結束時間',
  `use_type` tinyint(1) DEFAULT NULL COMMENT '使用類型[0->全場通用；1->指定分類；2->指定商品]',
  `note` varchar(200) DEFAULT NULL COMMENT '備註',
  `publish_count` int(11) DEFAULT NULL COMMENT '發行數量',
  `use_count` int(11) DEFAULT NULL COMMENT '已使用數量',
  `receive_count` int(11) DEFAULT NULL COMMENT '領取數量',
  `enable_start_time` datetime DEFAULT NULL COMMENT '可以領取的開始日期',
  `enable_end_time` datetime DEFAULT NULL COMMENT '可以領取的結束日期',
  `code` varchar(64) DEFAULT NULL COMMENT '優惠碼',
  `member_level` tinyint(1) DEFAULT NULL COMMENT '可以領取的會員等級[0->不限等級，其他-對應等級]',
  `publish` tinyint(1) DEFAULT NULL COMMENT '發佈狀態[0-未發佈，1-已發佈]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='優惠券信息';

-- ----------------------------
-- Records of sms_coupon
-- ----------------------------

-- ----------------------------
-- Table structure for sms_coupon_history
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_history`;
CREATE TABLE `sms_coupon_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `coupon_id` bigint(20) DEFAULT NULL COMMENT '優惠券id',
  `member_id` bigint(20) DEFAULT NULL COMMENT '會員id',
  `member_nick_name` varchar(64) DEFAULT NULL COMMENT '會員名字',
  `get_type` tinyint(1) DEFAULT NULL COMMENT '獲取方式[0->後台贈送；1->主動領取]',
  `create_time` datetime DEFAULT NULL COMMENT '創建時間',
  `use_type` tinyint(1) DEFAULT NULL COMMENT '使用狀態[0->未使用；1->已使用；2->已過期]',
  `use_time` datetime DEFAULT NULL COMMENT '使用時間',
  `order_id` bigint(20) DEFAULT NULL COMMENT '訂單id',
  `order_sn` bigint(20) DEFAULT NULL COMMENT '訂單號',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='優惠券領取歷史記錄';

-- ----------------------------
-- Records of sms_coupon_history
-- ----------------------------

-- ----------------------------
-- Table structure for sms_coupon_spu_category_relation
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_spu_category_relation`;
CREATE TABLE `sms_coupon_spu_category_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `coupon_id` bigint(20) DEFAULT NULL COMMENT '優惠券id',
  `category_id` bigint(20) DEFAULT NULL COMMENT '產品分類id',
  `category_name` varchar(64) DEFAULT NULL COMMENT '產品分類名稱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='優惠券分類關聯';

-- ----------------------------
-- Records of sms_coupon_spu_category_relation
-- ----------------------------

-- ----------------------------
-- Table structure for sms_coupon_spu_relation
-- ----------------------------
DROP TABLE IF EXISTS `sms_coupon_spu_relation`;
CREATE TABLE `sms_coupon_spu_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `coupon_id` bigint(20) DEFAULT NULL COMMENT '優惠券id',
  `spu_id` bigint(20) DEFAULT NULL COMMENT 'spu_id',
  `spu_name` varchar(255) DEFAULT NULL COMMENT 'spu_name',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='優惠券與產品關聯';

-- ----------------------------
-- Records of sms_coupon_spu_relation
-- ----------------------------

-- ----------------------------
-- Table structure for sms_home_adv
-- ----------------------------
DROP TABLE IF EXISTS `sms_home_adv`;
CREATE TABLE `sms_home_adv` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) DEFAULT NULL COMMENT '名字',
  `pic` varchar(500) DEFAULT NULL COMMENT '圖片地址',
  `start_time` datetime DEFAULT NULL COMMENT '開始時間',
  `end_time` datetime DEFAULT NULL COMMENT '結束時間',
  `status` tinyint(1) DEFAULT NULL COMMENT '狀態',
  `click_count` int(11) DEFAULT NULL COMMENT '點擊數',
  `url` varchar(500) DEFAULT NULL COMMENT '廣告詳情連接地址',
  `note` varchar(500) DEFAULT NULL COMMENT '備註',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `publisher_id` bigint(20) DEFAULT NULL COMMENT '發佈者',
  `auth_id` bigint(20) DEFAULT NULL COMMENT '審核者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='首頁輪播廣告';

-- ----------------------------
-- Records of sms_home_adv
-- ----------------------------

-- ----------------------------
-- Table structure for sms_home_subject
-- ----------------------------
DROP TABLE IF EXISTS `sms_home_subject`;
CREATE TABLE `sms_home_subject` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) DEFAULT NULL COMMENT '專題名字',
  `title` varchar(255) DEFAULT NULL COMMENT '專題標題',
  `sub_title` varchar(255) DEFAULT NULL COMMENT '專題副標題',
  `status` tinyint(1) DEFAULT NULL COMMENT '顯示狀態',
  `url` varchar(500) DEFAULT NULL COMMENT '詳情連接',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `img` varchar(500) DEFAULT NULL COMMENT '專題圖片地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='首頁專題表【jd首頁下面很多專題，每個專題鏈接新的頁面，展示專題商品信息】';

-- ----------------------------
-- Records of sms_home_subject
-- ----------------------------

-- ----------------------------
-- Table structure for sms_home_subject_spu
-- ----------------------------
DROP TABLE IF EXISTS `sms_home_subject_spu`;
CREATE TABLE `sms_home_subject_spu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) DEFAULT NULL COMMENT '專題名字',
  `subject_id` bigint(20) DEFAULT NULL COMMENT '專題id',
  `spu_id` bigint(20) DEFAULT NULL COMMENT 'spu_id',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='專題商品';

-- ----------------------------
-- Records of sms_home_subject_spu
-- ----------------------------

-- ----------------------------
-- Table structure for sms_member_price
-- ----------------------------
DROP TABLE IF EXISTS `sms_member_price`;
CREATE TABLE `sms_member_price` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT 'sku_id',
  `member_level_id` bigint(20) DEFAULT NULL COMMENT '會員等級id',
  `member_level_name` varchar(100) DEFAULT NULL COMMENT '會員等級名',
  `member_price` decimal(18,4) DEFAULT NULL COMMENT '會員對應價格',
  `add_other` tinyint(1) DEFAULT NULL COMMENT '可否疊加其他優惠[0-不可疊加優惠，1-可疊加]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品會員價格';

-- ----------------------------
-- Records of sms_member_price
-- ----------------------------

-- ----------------------------
-- Table structure for sms_seckill_promotion
-- ----------------------------
DROP TABLE IF EXISTS `sms_seckill_promotion`;
CREATE TABLE `sms_seckill_promotion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(255) DEFAULT NULL COMMENT '活動標題',
  `start_time` datetime DEFAULT NULL COMMENT '開始日期',
  `end_time` datetime DEFAULT NULL COMMENT '結束日期',
  `status` tinyint(4) DEFAULT NULL COMMENT '上下線狀態',
  `create_time` datetime DEFAULT NULL COMMENT '創建時間',
  `user_id` bigint(20) DEFAULT NULL COMMENT '創建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒殺活動';

-- ----------------------------
-- Records of sms_seckill_promotion
-- ----------------------------

-- ----------------------------
-- Table structure for sms_seckill_session
-- ----------------------------
DROP TABLE IF EXISTS `sms_seckill_session`;
CREATE TABLE `sms_seckill_session` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(200) DEFAULT NULL COMMENT '場次名稱',
  `start_time` datetime DEFAULT NULL COMMENT '每日開始時間',
  `end_time` datetime DEFAULT NULL COMMENT '每日結束時間',
  `status` tinyint(1) DEFAULT NULL COMMENT '啓用狀態',
  `create_time` datetime DEFAULT NULL COMMENT '創建時間',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒殺活動場次';

-- ----------------------------
-- Records of sms_seckill_session
-- ----------------------------

-- ----------------------------
-- Table structure for sms_seckill_sku_notice
-- ----------------------------
DROP TABLE IF EXISTS `sms_seckill_sku_notice`;
CREATE TABLE `sms_seckill_sku_notice` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) DEFAULT NULL COMMENT 'member_id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT 'sku_id',
  `session_id` bigint(20) DEFAULT NULL COMMENT '活動場次id',
  `subcribe_time` datetime DEFAULT NULL COMMENT '訂閲時間',
  `send_time` datetime DEFAULT NULL COMMENT '發送時間',
  `notice_type` tinyint(1) DEFAULT NULL COMMENT '通知方式[0-短信，1-郵件]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒殺商品通知訂閲';

-- ----------------------------
-- Records of sms_seckill_sku_notice
-- ----------------------------

-- ----------------------------
-- Table structure for sms_seckill_sku_relation
-- ----------------------------
DROP TABLE IF EXISTS `sms_seckill_sku_relation`;
CREATE TABLE `sms_seckill_sku_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `promotion_id` bigint(20) DEFAULT NULL COMMENT '活動id',
  `promotion_session_id` bigint(20) DEFAULT NULL COMMENT '活動場次id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `seckill_price` decimal(10,4) DEFAULT NULL COMMENT '秒殺價格',
  `seckill_count` int(11) DEFAULT NULL COMMENT '秒殺總量',
  `seckill_limit` int(11) DEFAULT NULL COMMENT '每人限購數量',
  `seckill_sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒殺活動商品關聯';

-- ----------------------------
-- Records of sms_seckill_sku_relation
-- ----------------------------

-- ----------------------------
-- Table structure for sms_sku_full_reduction
-- ----------------------------
DROP TABLE IF EXISTS `sms_sku_full_reduction`;
CREATE TABLE `sms_sku_full_reduction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT 'spu_id',
  `full_price` decimal(18,4) DEFAULT NULL COMMENT '滿多少',
  `reduce_price` decimal(18,4) DEFAULT NULL COMMENT '減多少',
  `add_other` tinyint(1) DEFAULT NULL COMMENT '是否參與其他優惠',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品滿減信息';

-- ----------------------------
-- Records of sms_sku_full_reduction
-- ----------------------------

-- ----------------------------
-- Table structure for sms_sku_ladder
-- ----------------------------
DROP TABLE IF EXISTS `sms_sku_ladder`;
CREATE TABLE `sms_sku_ladder` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT 'spu_id',
  `full_count` int(11) DEFAULT NULL COMMENT '滿幾件',
  `discount` decimal(4,2) DEFAULT NULL COMMENT '打幾折',
  `price` decimal(18,4) DEFAULT NULL COMMENT '折後價',
  `add_other` tinyint(1) DEFAULT NULL COMMENT '是否疊加其他優惠[0-不可疊加，1-可疊加]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品階梯價格';

-- ----------------------------
-- Records of sms_sku_ladder
-- ----------------------------

-- ----------------------------
-- Table structure for sms_spu_bounds
-- ----------------------------
DROP TABLE IF EXISTS `sms_spu_bounds`;
CREATE TABLE `sms_spu_bounds` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `spu_id` bigint(20) DEFAULT NULL,
  `grow_bounds` decimal(18,4) DEFAULT NULL COMMENT '成長積分',
  `buy_bounds` decimal(18,4) DEFAULT NULL COMMENT '購物積分',
  `work` tinyint(1) DEFAULT NULL COMMENT '優惠生效情況[1111（四個狀態位，從右到左）;0 - 無優惠，成長積分是否贈送;1 - 無優惠，購物積分是否贈送;2 - 有優惠，成長積分是否贈送;3 - 有優惠，購物積分是否贈送【狀態位0：不贈送，1：贈送】]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品spu積分設置';

-- ----------------------------
-- Records of sms_spu_bounds
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
