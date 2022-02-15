/*
Navicat MySQL Data Transfer

Source Server         : 192.168.56.10_3306
Source Server Version : 50727
Source Host           : 192.168.56.10:3306
Source Database       : gulimall_ums

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-03-11 17:37:18
*/
DROP DATABASE IF EXISTS `gulimall_ums`;

CREATE DATABASE IF NOT EXISTS `gulimall_ums` DEFAULT CHARACTER SET utf8mb4;

USE `gulimall_ums`;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ums_growth_change_history
-- ----------------------------
DROP TABLE IF EXISTS `ums_growth_change_history`;
CREATE TABLE `ums_growth_change_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) DEFAULT NULL COMMENT 'member_id',
  `create_time` datetime DEFAULT NULL COMMENT 'create_time',
  `change_count` int(11) DEFAULT NULL COMMENT '改變的值（正負計數）',
  `note` varchar(0) DEFAULT NULL COMMENT '備註',
  `source_type` tinyint(4) DEFAULT NULL COMMENT '積分來源[0-購物，1-管理員修改]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成長值變化歷史記錄';

-- ----------------------------
-- Records of ums_growth_change_history
-- ----------------------------

-- ----------------------------
-- Table structure for ums_integration_change_history
-- ----------------------------
DROP TABLE IF EXISTS `ums_integration_change_history`;
CREATE TABLE `ums_integration_change_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) DEFAULT NULL COMMENT 'member_id',
  `create_time` datetime DEFAULT NULL COMMENT 'create_time',
  `change_count` int(11) DEFAULT NULL COMMENT '變化的值',
  `note` varchar(255) DEFAULT NULL COMMENT '備註',
  `source_tyoe` tinyint(4) DEFAULT NULL COMMENT '來源[0->購物；1->管理員修改;2->活動]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='積分變化歷史記錄';

-- ----------------------------
-- Records of ums_integration_change_history
-- ----------------------------

-- ----------------------------
-- Table structure for ums_member
-- ----------------------------
DROP TABLE IF EXISTS `ums_member`;
CREATE TABLE `ums_member` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `level_id` bigint(20) DEFAULT NULL COMMENT '會員等級id',
  `username` char(64) DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) DEFAULT NULL COMMENT '密碼',
  `nickname` varchar(64) DEFAULT NULL COMMENT '暱稱',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手機號碼',
  `email` varchar(64) DEFAULT NULL COMMENT '郵箱',
  `header` varchar(500) DEFAULT NULL COMMENT '頭像',
  `gender` tinyint(4) DEFAULT NULL COMMENT '性別',
  `birth` date DEFAULT NULL COMMENT '生日',
  `city` varchar(500) DEFAULT NULL COMMENT '所在城市',
  `job` varchar(255) DEFAULT NULL COMMENT '職業',
  `sign` varchar(255) DEFAULT NULL COMMENT '個性簽名',
  `source_type` tinyint(4) DEFAULT NULL COMMENT '用户來源',
  `integration` int(11) DEFAULT NULL COMMENT '積分',
  `growth` int(11) DEFAULT NULL COMMENT '成長值',
  `status` tinyint(4) DEFAULT NULL COMMENT '啓用狀態',
  `create_time` datetime DEFAULT NULL COMMENT '註冊時間',
  `social_uid` varchar(255) DEFAULT NULL COMMENT '社交用户的唯一id',
  `access_token` varchar(255) DEFAULT NULL COMMENT '訪問令牌',
  `expires_in` varchar(255) DEFAULT NULL COMMENT '訪問令牌的時間',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='會員';

-- ----------------------------
-- Records of ums_member
-- ----------------------------

-- ----------------------------
-- Table structure for ums_member_collect_spu
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_collect_spu`;
CREATE TABLE `ums_member_collect_spu` (
  `id` bigint(20) NOT NULL COMMENT 'id',
  `member_id` bigint(20) DEFAULT NULL COMMENT '會員id',
  `spu_id` bigint(20) DEFAULT NULL COMMENT 'spu_id',
  `spu_name` varchar(500) DEFAULT NULL COMMENT 'spu_name',
  `spu_img` varchar(500) DEFAULT NULL COMMENT 'spu_img',
  `create_time` datetime DEFAULT NULL COMMENT 'create_time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='會員收藏的商品';

-- ----------------------------
-- Records of ums_member_collect_spu
-- ----------------------------

-- ----------------------------
-- Table structure for ums_member_collect_subject
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_collect_subject`;
CREATE TABLE `ums_member_collect_subject` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `subject_id` bigint(20) DEFAULT NULL COMMENT 'subject_id',
  `subject_name` varchar(255) DEFAULT NULL COMMENT 'subject_name',
  `subject_img` varchar(500) DEFAULT NULL COMMENT 'subject_img',
  `subject_urll` varchar(500) DEFAULT NULL COMMENT '活動url',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='會員收藏的專題活動';

-- ----------------------------
-- Records of ums_member_collect_subject
-- ----------------------------

-- ----------------------------
-- Table structure for ums_member_level
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_level`;
CREATE TABLE `ums_member_level` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) DEFAULT NULL COMMENT '等級名稱',
  `growth_point` int(11) DEFAULT NULL COMMENT '等級需要的成長值',
  `default_status` tinyint(4) DEFAULT NULL COMMENT '是否為默認等級[0->不是；1->是]',
  `free_freight_point` decimal(18,4) DEFAULT NULL COMMENT '免運費標準',
  `comment_growth_point` int(11) DEFAULT NULL COMMENT '每次評價獲取的成長值',
  `priviledge_free_freight` tinyint(4) DEFAULT NULL COMMENT '是否有免郵特權',
  `priviledge_member_price` tinyint(4) DEFAULT NULL COMMENT '是否有會員價格特權',
  `priviledge_birthday` tinyint(4) DEFAULT NULL COMMENT '是否有生日特權',
  `note` varchar(255) DEFAULT NULL COMMENT '備註',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='會員等級';

-- ----------------------------
-- Records of ums_member_level
-- ----------------------------

-- ----------------------------
-- Table structure for ums_member_login_log
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_login_log`;
CREATE TABLE `ums_member_login_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) DEFAULT NULL COMMENT 'member_id',
  `create_time` datetime DEFAULT NULL COMMENT '創建時間',
  `ip` varchar(64) DEFAULT NULL COMMENT 'ip',
  `city` varchar(64) DEFAULT NULL COMMENT 'city',
  `login_type` tinyint(1) DEFAULT NULL COMMENT '登錄類型[1-web，2-app]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='會員登錄記錄';

-- ----------------------------
-- Records of ums_member_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for ums_member_receive_address
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_receive_address`;
CREATE TABLE `ums_member_receive_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) DEFAULT NULL COMMENT 'member_id',
  `name` varchar(255) DEFAULT NULL COMMENT '收貨人姓名',
  `phone` varchar(64) DEFAULT NULL COMMENT '電話',
  `post_code` varchar(64) DEFAULT NULL COMMENT '郵政編碼',
  `province` varchar(100) DEFAULT NULL COMMENT '省份/直轄市',
  `city` varchar(100) DEFAULT NULL COMMENT '城市',
  `region` varchar(100) DEFAULT NULL COMMENT '區',
  `detail_address` varchar(255) DEFAULT NULL COMMENT '詳細地址(街道)',
  `areacode` varchar(15) DEFAULT NULL COMMENT '省市區代碼',
  `default_status` tinyint(1) DEFAULT NULL COMMENT '是否默認',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='會員收貨地址';

-- ----------------------------
-- Records of ums_member_receive_address
-- ----------------------------

-- ----------------------------
-- Table structure for ums_member_statistics_info
-- ----------------------------
DROP TABLE IF EXISTS `ums_member_statistics_info`;
CREATE TABLE `ums_member_statistics_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) DEFAULT NULL COMMENT '會員id',
  `consume_amount` decimal(18,4) DEFAULT NULL COMMENT '累計消費金額',
  `coupon_amount` decimal(18,4) DEFAULT NULL COMMENT '累計優惠金額',
  `order_count` int(11) DEFAULT NULL COMMENT '訂單數量',
  `coupon_count` int(11) DEFAULT NULL COMMENT '優惠券數量',
  `comment_count` int(11) DEFAULT NULL COMMENT '評價數',
  `return_order_count` int(11) DEFAULT NULL COMMENT '退貨數量',
  `login_count` int(11) DEFAULT NULL COMMENT '登錄次數',
  `attend_count` int(11) DEFAULT NULL COMMENT '關注數量',
  `fans_count` int(11) DEFAULT NULL COMMENT '粉絲數量',
  `collect_product_count` int(11) DEFAULT NULL COMMENT '收藏的商品數量',
  `collect_subject_count` int(11) DEFAULT NULL COMMENT '收藏的專題活動數量',
  `collect_comment_count` int(11) DEFAULT NULL COMMENT '收藏的評論數量',
  `invite_friend_count` int(11) DEFAULT NULL COMMENT '邀請的朋友數量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='會員統計信息';

-- ----------------------------
-- Records of ums_member_statistics_info
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
