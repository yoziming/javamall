/*
Navicat MySQL Data Transfer

Source Server         : 192.168.56.10_3306
Source Server Version : 50727
Source Host           : 192.168.56.10:3306
Source Database       : gulimall_pms

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-03-11 17:36:52
*/
DROP DATABASE IF EXISTS `gulimall_pms`;

CREATE DATABASE IF NOT EXISTS `gulimall_pms` DEFAULT CHARACTER SET utf8mb4;

USE `gulimall_pms`;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for pms_attr
-- ----------------------------
DROP TABLE IF EXISTS `pms_attr`;
CREATE TABLE `pms_attr` (
  `attr_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '屬性id',
  `attr_name` char(30) DEFAULT NULL COMMENT '屬性名',
  `search_type` tinyint(4) DEFAULT NULL COMMENT '是否需要檢索[0-不需要，1-需要]',
  `value_type` tinyint(4) DEFAULT NULL COMMENT '值類型[0-為單個值，1-可以選擇多個值]',
  `icon` varchar(255) DEFAULT NULL COMMENT '屬性圖標',
  `value_select` char(255) DEFAULT NULL COMMENT '可選值列表[用逗號分隔]',
  `attr_type` tinyint(4) DEFAULT NULL COMMENT '屬性類型[0-銷售屬性，1-基本屬性',
  `enable` bigint(20) DEFAULT NULL COMMENT '啓用狀態[0 - 禁用，1 - 啓用]',
  `catalog_id` bigint(20) DEFAULT NULL COMMENT '所屬分類',
  `show_desc` tinyint(4) DEFAULT NULL COMMENT '快速展示【是否展示在介紹上；0-否 1-是】，在sku中仍然可以調整',
  PRIMARY KEY (`attr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COMMENT='商品屬性';

-- ----------------------------
-- Records of pms_attr
-- ----------------------------
INSERT INTO `pms_attr` VALUES ('7', '入網型號', '0', '0', 'xxx', 'A2217;C3J;以官網信息為準', '1', '1', '225', '0');
INSERT INTO `pms_attr` VALUES ('8', '上市年份', '0', '0', 'xxx', '2018;2019', '1', '1', '225', '0');
INSERT INTO `pms_attr` VALUES ('9', '顏色', '0', '0', 'xxx', '黑色;白色;藍色', '0', '1', '225', '0');
INSERT INTO `pms_attr` VALUES ('10', '內存', '0', '0', 'xxx', '4GB;6GB;8GB;12GB', '0', '1', '225', '0');
INSERT INTO `pms_attr` VALUES ('11', '機身顏色', '0', '0', 'xxx', '黑色;白色', '1', '1', '225', '1');
INSERT INTO `pms_attr` VALUES ('12', '版本', '0', '0', 'xxx', '', '0', '1', '225', '0');
INSERT INTO `pms_attr` VALUES ('13', '機身長度（mm）', '0', '0', 'xx', '158.3;135.9', '1', '1', '225', '0');
INSERT INTO `pms_attr` VALUES ('14', '機身材質工藝', '0', '1', 'xxx', '以官網信息為準;陶瓷;玻璃', '1', '1', '225', '0');
INSERT INTO `pms_attr` VALUES ('15', 'CPU品牌', '1', '0', 'xxx', '高通(Qualcomm);海思（Hisilicon）;以官網信息為準', '1', '1', '225', '1');
INSERT INTO `pms_attr` VALUES ('16', 'CPU型號', '1', '0', 'xxx', '驍龍665;驍龍845;驍龍855;驍龍730;HUAWEI Kirin 980;HUAWEI Kirin 970', '1', '1', '225', '0');

-- ----------------------------
-- Table structure for pms_attr_attrgroup_relation
-- ----------------------------
DROP TABLE IF EXISTS `pms_attr_attrgroup_relation`;
CREATE TABLE `pms_attr_attrgroup_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `attr_id` bigint(20) DEFAULT NULL COMMENT '屬性id',
  `attr_group_id` bigint(20) DEFAULT NULL COMMENT '屬性分組id',
  `attr_sort` int(11) DEFAULT NULL COMMENT '屬性組內排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COMMENT='屬性&屬性分組關聯';

-- ----------------------------
-- Records of pms_attr_attrgroup_relation
-- ----------------------------
INSERT INTO `pms_attr_attrgroup_relation` VALUES ('23', '7', '1', null);
INSERT INTO `pms_attr_attrgroup_relation` VALUES ('24', '8', '1', null);
INSERT INTO `pms_attr_attrgroup_relation` VALUES ('26', '11', '2', null);
INSERT INTO `pms_attr_attrgroup_relation` VALUES ('27', '13', '2', null);
INSERT INTO `pms_attr_attrgroup_relation` VALUES ('28', '14', '2', null);
INSERT INTO `pms_attr_attrgroup_relation` VALUES ('29', '15', '7', null);
INSERT INTO `pms_attr_attrgroup_relation` VALUES ('30', '16', '7', null);

-- ----------------------------
-- Table structure for pms_attr_group
-- ----------------------------
DROP TABLE IF EXISTS `pms_attr_group`;
CREATE TABLE `pms_attr_group` (
  `attr_group_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分組id',
  `attr_group_name` char(20) DEFAULT NULL COMMENT '組名',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `descript` varchar(255) DEFAULT NULL COMMENT '描述',
  `icon` varchar(255) DEFAULT NULL COMMENT '組圖標',
  `catalog_id` bigint(20) DEFAULT NULL COMMENT '所屬分類id',
  PRIMARY KEY (`attr_group_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='屬性分組';

-- ----------------------------
-- Records of pms_attr_group
-- ----------------------------
INSERT INTO `pms_attr_group` VALUES ('1', '主體', '0', '主體', 'dd', '225');
INSERT INTO `pms_attr_group` VALUES ('2', '基本信息', '0', '基本信息', 'xx', '225');
INSERT INTO `pms_attr_group` VALUES ('4', '屏幕', '0', '屏幕', 'xx', '233');
INSERT INTO `pms_attr_group` VALUES ('7', '主芯片', '0', '主芯片', 'xx', '225');

-- ----------------------------
-- Table structure for pms_brand
-- ----------------------------
DROP TABLE IF EXISTS `pms_brand`;
CREATE TABLE `pms_brand` (
  `brand_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '品牌id',
  `name` char(50) DEFAULT NULL COMMENT '品牌名',
  `logo` varchar(2000) DEFAULT NULL COMMENT '品牌logo地址',
  `descript` longtext COMMENT '介紹',
  `show_status` tinyint(4) DEFAULT NULL COMMENT '顯示狀態[0-不顯示；1-顯示]',
  `first_letter` char(1) DEFAULT NULL COMMENT '檢索首字母',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`brand_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COMMENT='品牌';

-- ----------------------------
-- Records of pms_brand
-- ----------------------------
INSERT INTO `pms_brand` VALUES ('9', '華為', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-18/de2426bd-a689-41d0-865a-d45d1afa7cde_huawei.png', '華為', '1', 'H', '1');
INSERT INTO `pms_brand` VALUES ('10', '小米', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-18/1f9e6968-cf92-462e-869a-4c2331a4113f_xiaomi.png', '小米', '1', 'M', '1');
INSERT INTO `pms_brand` VALUES ('11', 'oppo', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-18/5c8303f2-8b0c-4a5b-89a6-86513133d758_oppo.png', 'oppo', '1', 'O', '1');
INSERT INTO `pms_brand` VALUES ('12', 'Apple', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-18/819bb0b1-3ed8-4072-8304-78811a289781_apple.png', '蘋果', '1', 'A', '1');

-- ----------------------------
-- Table structure for pms_category
-- ----------------------------
DROP TABLE IF EXISTS `pms_category`;
CREATE TABLE `pms_category` (
  `cat_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分類id',
  `name` char(50) DEFAULT NULL COMMENT '分類名稱',
  `parent_cid` bigint(20) DEFAULT NULL COMMENT '父分類id',
  `cat_level` int(11) DEFAULT NULL COMMENT '層級',
  `show_status` tinyint(4) DEFAULT NULL COMMENT '是否顯示[0-不顯示，1顯示]',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `icon` char(255) DEFAULT NULL COMMENT '圖標地址',
  `product_unit` char(50) DEFAULT NULL COMMENT '計量單位',
  `product_count` int(11) DEFAULT NULL COMMENT '商品數量',
  PRIMARY KEY (`cat_id`),
  KEY `parent_cid` (`parent_cid`)
) ENGINE=InnoDB AUTO_INCREMENT=1433 DEFAULT CHARSET=utf8mb4 COMMENT='商品三級分類';

-- ----------------------------
-- Records of pms_category
-- ----------------------------
INSERT INTO `pms_category` VALUES ('1', '圖書、音像、電子書刊', '0', '1', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('2', '手機', '0', '1', '1', '0', '111q', null, '0');
INSERT INTO `pms_category` VALUES ('3', '家用電器', '0', '1', '1', '0', 'aaa', null, '0');
INSERT INTO `pms_category` VALUES ('4', '數碼', '0', '1', '1', '0', 'aaa', null, '0');
INSERT INTO `pms_category` VALUES ('5', '家居家裝', '0', '1', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('6', '電腦辦公', '0', '1', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('7', '廚具', '0', '1', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('8', '個護化妝', '0', '1', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('9', '服飾內衣', '0', '1', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('10', '鐘錶', '0', '1', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('11', '鞋靴', '0', '1', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('12', '母嬰', '0', '1', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('13', '禮品箱包', '0', '1', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('14', '食品飲料、保健食品', '0', '1', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('15', '珠寶', '0', '1', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('16', '汽車用品', '0', '1', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('17', '運動健康', '0', '1', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('18', '玩具樂器', '0', '1', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('19', '彩票、旅行、充值、票務', '0', '1', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('20', '生鮮', '0', '1', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('21', '整車', '0', '1', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('22', '電子書刊', '1', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('23', '音像', '1', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('24', '英文原版', '1', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('25', '文藝', '1', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('26', '少兒', '1', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('27', '人文社科', '1', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('28', '經管勵志', '1', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('29', '生活', '1', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('30', '科技', '1', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('31', '教育', '1', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('32', '港台圖書', '1', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('33', '其他', '1', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('34', '手機通訊', '2', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('35', '運營商', '2', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('36', '手機配件', '2', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('37', '大 家 電', '3', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('38', '廚衞大電', '3', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('39', '廚房小電', '3', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('40', '生活電器', '3', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('41', '個護健康', '3', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('42', '五金家裝', '3', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('43', '攝影攝像', '4', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('44', '數碼配件', '4', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('45', '智能設備', '4', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('46', '影音娛樂', '4', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('47', '電子教育', '4', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('48', '虛擬商品', '4', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('49', '家紡', '5', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('50', '燈具', '5', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('51', '生活日用', '5', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('52', '家裝軟飾', '5', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('53', '寵物生活', '5', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('54', '電腦整機', '6', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('55', '電腦配件', '6', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('56', '外設產品', '6', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('57', '遊戲設備', '6', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('58', '網絡產品', '6', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('59', '辦公設備', '6', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('60', '文具/耗材', '6', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('61', '服務產品', '6', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('62', '烹飪鍋具', '7', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('63', '刀剪菜板', '7', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('64', '廚房配件', '7', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('65', '水具酒具', '7', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('66', '餐具', '7', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('67', '酒店用品', '7', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('68', '茶具/咖啡具', '7', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('69', '清潔用品', '8', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('70', '面部護膚', '8', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('71', '身體護理', '8', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('72', '口腔護理', '8', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('73', '女性護理', '8', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('74', '洗髮護髮', '8', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('75', '香水彩妝', '8', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('76', '女裝', '9', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('77', '男裝', '9', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('78', '內衣', '9', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('79', '洗衣服務', '9', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('80', '服飾配件', '9', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('81', '鐘錶', '10', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('82', '流行男鞋', '11', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('83', '時尚女鞋', '11', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('84', '奶粉', '12', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('85', '營養輔食', '12', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('86', '尿褲濕巾', '12', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('87', '餵養用品', '12', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('88', '洗護用品', '12', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('89', '童車童牀', '12', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('90', '寢居服飾', '12', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('91', '媽媽專區', '12', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('92', '童裝童鞋', '12', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('93', '安全座椅', '12', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('94', '潮流女包', '13', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('95', '精品男包', '13', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('96', '功能箱包', '13', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('97', '禮品', '13', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('98', '奢侈品', '13', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('99', '婚慶', '13', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('100', '進口食品', '14', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('101', '地方特產', '14', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('102', '休閒食品', '14', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('103', '糧油調味', '14', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('104', '飲料衝調', '14', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('105', '食品禮券', '14', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('106', '茗茶', '14', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('107', '時尚飾品', '15', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('108', '黃金', '15', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('109', 'K金飾品', '15', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('110', '金銀投資', '15', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('111', '銀飾', '15', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('112', '鑽石', '15', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('113', '翡翠玉石', '15', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('114', '水晶瑪瑙', '15', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('115', '彩寶', '15', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('116', '鉑金', '15', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('117', '木手串/把件', '15', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('118', '珍珠', '15', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('119', '維修保養', '16', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('120', '車載電器', '16', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('121', '美容清洗', '16', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('122', '汽車裝飾', '16', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('123', '安全自駕', '16', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('124', '汽車服務', '16', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('125', '賽事改裝', '16', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('126', '運動鞋包', '17', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('127', '運動服飾', '17', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('128', '騎行運動', '17', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('129', '垂釣用品', '17', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('130', '游泳用品', '17', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('131', '户外鞋服', '17', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('132', '户外裝備', '17', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('133', '健身訓練', '17', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('134', '體育用品', '17', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('135', '適用年齡', '18', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('136', '遙控/電動', '18', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('137', '毛絨布藝', '18', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('138', '娃娃玩具', '18', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('139', '模型玩具', '18', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('140', '健身玩具', '18', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('141', '動漫玩具', '18', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('142', '益智玩具', '18', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('143', '積木拼插', '18', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('144', 'DIY玩具', '18', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('145', '創意減壓', '18', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('146', '樂器', '18', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('147', '彩票', '19', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('148', '機票', '19', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('149', '酒店', '19', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('150', '旅行', '19', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('151', '充值', '19', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('152', '遊戲', '19', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('153', '票務', '19', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('154', '產地直供', '20', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('155', '水果', '20', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('156', '豬牛羊肉', '20', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('157', '海鮮水產', '20', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('158', '禽肉蛋品', '20', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('159', '冷凍食品', '20', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('160', '熟食臘味', '20', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('161', '飲品甜品', '20', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('162', '蔬菜', '20', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('163', '全新整車', '21', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('164', '二手車', '21', '2', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('165', '電子書', '22', '3', '1', '1', null, null, '0');
INSERT INTO `pms_category` VALUES ('166', '網絡原創', '22', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('167', '數字雜誌', '22', '3', '1', '2', null, null, '0');
INSERT INTO `pms_category` VALUES ('168', '多媒體圖書', '22', '3', '1', '3', null, null, '0');
INSERT INTO `pms_category` VALUES ('169', '音樂', '23', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('170', '影視', '23', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('171', '教育音像', '23', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('172', '少兒', '24', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('173', '商務投資', '24', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('174', '英語學習與考試', '24', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('175', '文學', '24', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('176', '傳記', '24', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('177', '勵志', '24', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('178', '小説', '25', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('179', '文學', '25', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('180', '青春文學', '25', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('181', '傳記', '25', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('182', '藝術', '25', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('183', '少兒', '26', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('184', '0-2歲', '26', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('185', '3-6歲', '26', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('186', '7-10歲', '26', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('187', '11-14歲', '26', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('188', '歷史', '27', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('189', '哲學', '27', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('190', '國學', '27', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('191', '政治/軍事', '27', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('192', '法律', '27', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('193', '人文社科', '27', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('194', '心理學', '27', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('195', '文化', '27', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('196', '社會科學', '27', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('197', '經濟', '28', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('198', '金融與投資', '28', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('199', '管理', '28', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('200', '勵志與成功', '28', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('201', '生活', '29', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('202', '健身與保健', '29', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('203', '家庭與育兒', '29', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('204', '旅遊', '29', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('205', '烹飪美食', '29', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('206', '工業技術', '30', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('207', '科普讀物', '30', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('208', '建築', '30', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('209', '醫學', '30', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('210', '科學與自然', '30', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('211', '計算機與互聯網', '30', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('212', '電子通信', '30', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('213', '中小學教輔', '31', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('214', '教育與考試', '31', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('215', '外語學習', '31', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('216', '大中專教材', '31', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('217', '字典詞典', '31', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('218', '藝術/設計/收藏', '32', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('219', '經濟管理', '32', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('220', '文化/學術', '32', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('221', '少兒', '32', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('222', '工具書', '33', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('223', '雜誌/期刊', '33', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('224', '套裝書', '33', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('225', '手機', '34', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('226', '對講機', '34', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('227', '合約機', '35', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('228', '選號中心', '35', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('229', '裝寬帶', '35', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('230', '辦套餐', '35', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('231', '移動電源', '36', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('232', '電池/移動電源', '36', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('233', '藍牙耳機', '36', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('234', '充電器/數據線', '36', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('235', '蘋果周邊', '36', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('236', '手機耳機', '36', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('237', '手機貼膜', '36', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('238', '手機存儲卡', '36', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('239', '充電器', '36', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('240', '數據線', '36', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('241', '手機保護套', '36', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('242', '車載配件', '36', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('243', 'iPhone 配件', '36', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('244', '手機電池', '36', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('245', '創意配件', '36', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('246', '便攜/無線音響', '36', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('247', '手機飾品', '36', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('248', '拍照配件', '36', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('249', '手機支架', '36', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('250', '平板電視', '37', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('251', '空調', '37', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('252', '冰箱', '37', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('253', '洗衣機', '37', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('254', '家庭影院', '37', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('255', 'DVD/電視盒子', '37', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('256', '迷你音響', '37', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('257', '冷櫃/冰吧', '37', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('258', '家電配件', '37', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('259', '功放', '37', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('260', '迴音壁/Soundbar', '37', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('261', 'Hi-Fi專區', '37', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('262', '電視盒子', '37', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('263', '酒櫃', '37', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('264', '燃氣灶', '38', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('265', '油煙機', '38', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('266', '熱水器', '38', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('267', '消毒櫃', '38', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('268', '洗碗機', '38', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('269', '料理機', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('270', '榨汁機', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('271', '電飯煲', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('272', '電壓力鍋', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('273', '豆漿機', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('274', '咖啡機', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('275', '微波爐', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('276', '電烤箱', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('277', '電磁爐', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('278', '麪包機', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('279', '煮蛋器', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('280', '酸奶機', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('281', '電燉鍋', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('282', '電水壺/熱水瓶', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('283', '電餅鐺', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('284', '多用途鍋', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('285', '電燒烤爐', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('286', '果蔬解毒機', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('287', '其它廚房電器', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('288', '養生壺/煎藥壺', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('289', '電熱飯盒', '39', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('290', '取暖電器', '40', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('291', '淨化器', '40', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('292', '加濕器', '40', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('293', '掃地機器人', '40', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('294', '吸塵器', '40', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('295', '掛燙機/熨斗', '40', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('296', '插座', '40', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('297', '電話機', '40', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('298', '清潔機', '40', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('299', '除濕機', '40', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('300', '乾衣機', '40', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('301', '收錄/音機', '40', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('302', '電風扇', '40', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('303', '冷風扇', '40', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('304', '其它生活電器', '40', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('305', '生活電器配件', '40', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('306', '淨水器', '40', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('307', '飲水機', '40', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('308', '剃鬚刀', '41', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('309', '剃/脱毛器', '41', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('310', '口腔護理', '41', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('311', '電吹風', '41', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('312', '美容器', '41', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('313', '理髮器', '41', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('314', '卷/直髮器', '41', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('315', '按摩椅', '41', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('316', '按摩器', '41', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('317', '足浴盆', '41', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('318', '血壓計', '41', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('319', '電子秤/廚房秤', '41', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('320', '血糖儀', '41', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('321', '體温計', '41', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('322', '其它健康電器', '41', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('323', '計步器/脂肪檢測儀', '41', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('324', '電動工具', '42', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('325', '手動工具', '42', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('326', '儀器儀表', '42', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('327', '浴霸/排氣扇', '42', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('328', '燈具', '42', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('329', 'LED燈', '42', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('330', '潔身器', '42', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('331', '水槽', '42', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('332', '龍頭', '42', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('333', '淋浴花灑', '42', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('334', '廚衞五金', '42', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('335', '傢俱五金', '42', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('336', '門鈴', '42', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('337', '電氣開關', '42', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('338', '插座', '42', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('339', '電工電料', '42', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('340', '監控安防', '42', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('341', '電線/線纜', '42', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('342', '數碼相機', '43', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('343', '單電/微單相機', '43', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('344', '單反相機', '43', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('345', '攝像機', '43', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('346', '拍立得', '43', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('347', '運動相機', '43', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('348', '鏡頭', '43', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('349', '户外器材', '43', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('350', '影棚器材', '43', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('351', '沖印服務', '43', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('352', '數碼相框', '43', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('353', '存儲卡', '44', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('354', '讀卡器', '44', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('355', '濾鏡', '44', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('356', '閃光燈/手柄', '44', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('357', '相機包', '44', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('358', '三腳架/雲台', '44', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('359', '相機清潔/貼膜', '44', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('360', '機身附件', '44', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('361', '鏡頭附件', '44', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('362', '電池/充電器', '44', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('363', '移動電源', '44', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('364', '數碼支架', '44', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('365', '智能手環', '45', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('366', '智能手錶', '45', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('367', '智能眼鏡', '45', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('368', '運動跟蹤器', '45', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('369', '健康監測', '45', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('370', '智能配飾', '45', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('371', '智能家居', '45', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('372', '體感車', '45', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('373', '其他配件', '45', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('374', '智能機器人', '45', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('375', '無人機', '45', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('376', 'MP3/MP4', '46', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('377', '智能設備', '46', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('378', '耳機/耳麥', '46', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('379', '便攜/無線音箱', '46', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('380', '音箱/音響', '46', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('381', '高清播放器', '46', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('382', '收音機', '46', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('383', 'MP3/MP4配件', '46', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('384', '麥克風', '46', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('385', '專業音頻', '46', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('386', '蘋果配件', '46', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('387', '學生平板', '47', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('388', '點讀機/筆', '47', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('389', '早教益智', '47', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('390', '錄音筆', '47', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('391', '電紙書', '47', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('392', '電子詞典', '47', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('393', '復讀機', '47', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('394', '延保服務', '48', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('395', '殺毒軟件', '48', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('396', '積分商品', '48', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('397', '桌布/罩件', '49', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('398', '地毯地墊', '49', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('399', '沙發墊套/椅墊', '49', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('400', '牀品套件', '49', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('401', '被子', '49', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('402', '枕芯', '49', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('403', '牀單被罩', '49', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('404', '毯子', '49', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('405', '牀墊/牀褥', '49', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('406', '蚊帳', '49', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('407', '抱枕靠墊', '49', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('408', '毛巾浴巾', '49', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('409', '電熱毯', '49', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('410', '窗簾/窗紗', '49', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('411', '布藝軟飾', '49', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('412', '涼蓆', '49', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('413', '枱燈', '50', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('414', '節能燈', '50', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('415', '裝飾燈', '50', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('416', '落地燈', '50', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('417', '應急燈/手電', '50', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('418', 'LED燈', '50', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('419', '吸頂燈', '50', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('420', '五金電器', '50', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('421', '筒燈射燈', '50', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('422', '吊燈', '50', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('423', '氛圍照明', '50', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('424', '保暖防護', '51', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('425', '收納用品', '51', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('426', '雨傘雨具', '51', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('427', '浴室用品', '51', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('428', '縫紉/針織用品', '51', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('429', '洗曬/熨燙', '51', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('430', '淨化除味', '51', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('431', '相框/照片牆', '52', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('432', '裝飾字畫', '52', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('433', '節慶飾品', '52', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('434', '手工/十字繡', '52', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('435', '裝飾擺件', '52', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('436', '簾藝隔斷', '52', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('437', '牆貼/裝飾貼', '52', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('438', '鍾飾', '52', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('439', '花瓶花藝', '52', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('440', '香薰蠟燭', '52', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('441', '創意家居', '52', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('442', '寵物主糧', '53', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('443', '寵物零食', '53', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('444', '醫療保健', '53', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('445', '家居日用', '53', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('446', '寵物玩具', '53', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('447', '出行裝備', '53', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('448', '洗護美容', '53', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('449', '筆記本', '54', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('450', '超極本', '54', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('451', '遊戲本', '54', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('452', '平板電腦', '54', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('453', '平板電腦配件', '54', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('454', '台式機', '54', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('455', '服務器/工作站', '54', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('456', '筆記本配件', '54', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('457', '一體機', '54', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('458', 'CPU', '55', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('459', '主板', '55', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('460', '顯卡', '55', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('461', '硬盤', '55', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('462', 'SSD固態硬盤', '55', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('463', '內存', '55', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('464', '機箱', '55', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('465', '電源', '55', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('466', '顯示器', '55', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('467', '刻錄機/光驅', '55', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('468', '散熱器', '55', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('469', '聲卡/擴展卡', '55', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('470', '裝機配件', '55', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('471', '組裝電腦', '55', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('472', '移動硬盤', '56', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('473', 'U盤', '56', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('474', '鼠標', '56', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('475', '鍵盤', '56', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('476', '鼠標墊', '56', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('477', '攝像頭', '56', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('478', '手寫板', '56', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('479', '硬盤盒', '56', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('480', '插座', '56', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('481', '線纜', '56', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('482', 'UPS電源', '56', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('483', '電腦工具', '56', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('484', '遊戲設備', '56', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('485', '電玩', '56', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('486', '電腦清潔', '56', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('487', '網絡儀表儀器', '56', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('488', '遊戲機', '57', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('489', '遊戲耳機', '57', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('490', '手柄/方向盤', '57', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('491', '遊戲軟件', '57', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('492', '遊戲周邊', '57', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('493', '路由器', '58', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('494', '網卡', '58', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('495', '交換機', '58', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('496', '網絡存儲', '58', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('497', '4G/3G上網', '58', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('498', '網絡盒子', '58', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('499', '網絡配件', '58', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('500', '投影機', '59', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('501', '投影配件', '59', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('502', '多功能一體機', '59', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('503', '打印機', '59', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('504', '傳真設備', '59', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('505', '驗鈔/點鈔機', '59', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('506', '掃描設備', '59', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('507', '複合機', '59', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('508', '碎紙機', '59', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('509', '考勤機', '59', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('510', '收款/POS機', '59', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('511', '會議音頻視頻', '59', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('512', '保險櫃', '59', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('513', '裝訂/封裝機', '59', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('514', '安防監控', '59', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('515', '辦公傢俱', '59', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('516', '白板', '59', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('517', '硒鼓/墨粉', '60', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('518', '墨盒', '60', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('519', '色帶', '60', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('520', '紙類', '60', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('521', '辦公文具', '60', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('522', '學生文具', '60', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('523', '財會用品', '60', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('524', '文件管理', '60', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('525', '本冊/便籤', '60', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('526', '計算器', '60', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('527', '筆類', '60', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('528', '畫具畫材', '60', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('529', '刻錄碟片/附件', '60', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('530', '上門安裝', '61', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('531', '延保服務', '61', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('532', '維修保養', '61', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('533', '電腦軟件', '61', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('534', '京東服務', '61', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('535', '炒鍋', '62', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('536', '煎鍋', '62', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('537', '壓力鍋', '62', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('538', '蒸鍋', '62', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('539', '湯鍋', '62', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('540', '奶鍋', '62', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('541', '鍋具套裝', '62', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('542', '煲類', '62', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('543', '水壺', '62', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('544', '火鍋', '62', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('545', '菜刀', '63', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('546', '剪刀', '63', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('547', '刀具套裝', '63', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('548', '砧板', '63', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('549', '瓜果刀/刨', '63', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('550', '多功能刀', '63', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('551', '保鮮盒', '64', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('552', '烘焙/燒烤', '64', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('553', '飯盒/提鍋', '64', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('554', '儲物/置物架', '64', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('555', '廚房DIY/小工具', '64', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('556', '塑料杯', '65', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('557', '運動水壺', '65', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('558', '玻璃杯', '65', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('559', '陶瓷/馬克杯', '65', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('560', '保温杯', '65', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('561', '保温壺', '65', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('562', '酒杯/酒具', '65', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('563', '杯具套裝', '65', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('564', '餐具套裝', '66', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('565', '碗/碟/盤', '66', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('566', '筷勺/刀叉', '66', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('567', '一次性用品', '66', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('568', '果盤/果籃', '66', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('569', '自助餐爐', '67', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('570', '酒店餐具', '67', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('571', '酒店水具', '67', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('572', '整套茶具', '68', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('573', '茶杯', '68', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('574', '茶壺', '68', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('575', '茶盤茶托', '68', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('576', '茶葉罐', '68', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('577', '茶具配件', '68', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('578', '茶寵擺件', '68', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('579', '咖啡具', '68', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('580', '其他', '68', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('581', '紙品濕巾', '69', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('582', '衣物清潔', '69', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('583', '清潔工具', '69', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('584', '驅蟲用品', '69', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('585', '家庭清潔', '69', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('586', '皮具護理', '69', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('587', '一次性用品', '69', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('588', '潔面', '70', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('589', '乳液麪霜', '70', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('590', '面膜', '70', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('591', '剃鬚', '70', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('592', '套裝', '70', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('593', '精華', '70', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('594', '眼霜', '70', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('595', '卸妝', '70', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('596', '防曬', '70', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('597', '防曬隔離', '70', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('598', 'T區護理', '70', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('599', '眼部護理', '70', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('600', '精華露', '70', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('601', '爽膚水', '70', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('602', '沐浴', '71', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('603', '潤膚', '71', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('604', '頸部', '71', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('605', '手足', '71', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('606', '纖體塑形', '71', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('607', '美胸', '71', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('608', '套裝', '71', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('609', '精油', '71', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('610', '洗髮護髮', '71', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('611', '染髮/造型', '71', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('612', '香薰精油', '71', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('613', '磨砂/浴鹽', '71', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('614', '手工/香皂', '71', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('615', '洗髮', '71', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('616', '護髮', '71', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('617', '染髮', '71', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('618', '磨砂膏', '71', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('619', '香皂', '71', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('620', '牙膏/牙粉', '72', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('621', '牙刷/牙線', '72', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('622', '漱口水', '72', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('623', '套裝', '72', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('624', '衞生巾', '73', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('625', '衞生護墊', '73', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('626', '私密護理', '73', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('627', '脱毛膏', '73', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('628', '其他', '73', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('629', '洗髮', '74', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('630', '護髮', '74', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('631', '染髮', '74', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('632', '造型', '74', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('633', '假髮', '74', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('634', '套裝', '74', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('635', '美髮工具', '74', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('636', '臉部護理', '74', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('637', '香水', '75', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('638', '底妝', '75', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('639', '腮紅', '75', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('640', '眼影', '75', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('641', '唇部', '75', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('642', '美甲', '75', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('643', '眼線', '75', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('644', '美妝工具', '75', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('645', '套裝', '75', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('646', '防曬隔離', '75', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('647', '卸妝', '75', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('648', '眉筆', '75', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('649', '睫毛膏', '75', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('650', 'T恤', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('651', '襯衫', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('652', '針織衫', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('653', '雪紡衫', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('654', '衞衣', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('655', '馬甲', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('656', '連衣裙', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('657', '半身裙', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('658', '牛仔褲', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('659', '休閒褲', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('660', '打底褲', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('661', '正裝褲', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('662', '小西裝', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('663', '短外套', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('664', '風衣', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('665', '毛呢大衣', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('666', '真皮皮衣', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('667', '棉服', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('668', '羽絨服', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('669', '大碼女裝', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('670', '中老年女裝', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('671', '婚紗', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('672', '打底衫', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('673', '旗袍/唐裝', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('674', '加絨褲', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('675', '吊帶/背心', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('676', '羊絨衫', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('677', '短褲', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('678', '皮草', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('679', '禮服', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('680', '仿皮皮衣', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('681', '羊毛衫', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('682', '設計師/潮牌', '76', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('683', '襯衫', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('684', 'T恤', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('685', 'POLO衫', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('686', '針織衫', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('687', '羊絨衫', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('688', '衞衣', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('689', '馬甲/背心', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('690', '夾克', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('691', '風衣', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('692', '毛呢大衣', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('693', '仿皮皮衣', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('694', '西服', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('695', '棉服', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('696', '羽絨服', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('697', '牛仔褲', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('698', '休閒褲', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('699', '西褲', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('700', '西服套裝', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('701', '大碼男裝', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('702', '中老年男裝', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('703', '唐裝/中山裝', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('704', '工裝', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('705', '真皮皮衣', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('706', '加絨褲', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('707', '衞褲/運動褲', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('708', '短褲', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('709', '設計師/潮牌', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('710', '羊毛衫', '77', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('711', '文胸', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('712', '女式內褲', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('713', '男式內褲', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('714', '睡衣/家居服', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('715', '塑身美體', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('716', '泳衣', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('717', '吊帶/背心', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('718', '抹胸', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('719', '連褲襪/絲襪', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('720', '美腿襪', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('721', '商務男襪', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('722', '保暖內衣', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('723', '情侶睡衣', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('724', '文胸套裝', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('725', '少女文胸', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('726', '休閒棉襪', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('727', '大碼內衣', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('728', '內衣配件', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('729', '打底褲襪', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('730', '打底衫', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('731', '秋衣秋褲', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('732', '情趣內衣', '78', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('733', '服裝洗護', '79', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('734', '太陽鏡', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('735', '光學鏡架/鏡片', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('736', '圍巾/手套/帽子套裝', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('737', '袖釦', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('738', '棒球帽', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('739', '毛線帽', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('740', '遮陽帽', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('741', '老花鏡', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('742', '裝飾眼鏡', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('743', '防輻射眼鏡', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('744', '游泳鏡', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('745', '女士絲巾/圍巾/披肩', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('746', '男士絲巾/圍巾', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('747', '鴨舌帽', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('748', '貝雷帽', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('749', '禮帽', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('750', '真皮手套', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('751', '毛線手套', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('752', '防曬手套', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('753', '男士腰帶/禮盒', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('754', '女士腰帶/禮盒', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('755', '鑰匙扣', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('756', '遮陽傘/雨傘', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('757', '口罩', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('758', '耳罩/耳包', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('759', '假領', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('760', '毛線/布面料', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('761', '領帶/領結/領帶夾', '80', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('762', '男表', '81', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('763', '瑞表', '81', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('764', '女表', '81', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('765', '國表', '81', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('766', '日韓表', '81', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('767', '歐美表', '81', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('768', '德表', '81', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('769', '兒童手錶', '81', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('770', '智能手錶', '81', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('771', '鬧鐘', '81', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('772', '座鐘掛鐘', '81', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('773', '鐘錶配件', '81', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('774', '商務休閒鞋', '82', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('775', '正裝鞋', '82', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('776', '休閒鞋', '82', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('777', '涼鞋/沙灘鞋', '82', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('778', '男靴', '82', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('779', '功能鞋', '82', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('780', '拖鞋/人字拖', '82', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('781', '雨鞋/雨靴', '82', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('782', '傳統布鞋', '82', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('783', '鞋配件', '82', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('784', '帆布鞋', '82', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('785', '增高鞋', '82', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('786', '工裝鞋', '82', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('787', '定製鞋', '82', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('788', '高跟鞋', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('789', '單鞋', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('790', '休閒鞋', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('791', '涼鞋', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('792', '女靴', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('793', '雪地靴', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('794', '拖鞋/人字拖', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('795', '踝靴', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('796', '筒靴', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('797', '帆布鞋', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('798', '雨鞋/雨靴', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('799', '媽媽鞋', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('800', '鞋配件', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('801', '特色鞋', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('802', '魚嘴鞋', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('803', '布鞋/繡花鞋', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('804', '馬丁靴', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('805', '坡跟鞋', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('806', '鬆糕鞋', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('807', '內增高', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('808', '防水台', '83', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('809', '嬰幼奶粉', '84', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('810', '孕媽奶粉', '84', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('811', '益生菌/初乳', '85', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('812', '米粉/菜粉', '85', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('813', '果泥/果汁', '85', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('814', 'DHA', '85', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('815', '寶寶零食', '85', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('816', '鈣鐵鋅/維生素', '85', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('817', '清火/開胃', '85', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('818', '麪條/粥', '85', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('819', '嬰兒尿褲', '86', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('820', '拉拉褲', '86', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('821', '嬰兒濕巾', '86', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('822', '成人尿褲', '86', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('823', '奶瓶奶嘴', '87', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('824', '吸奶器', '87', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('825', '暖奶消毒', '87', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('826', '兒童餐具', '87', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('827', '水壺/水杯', '87', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('828', '牙膠安撫', '87', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('829', '圍兜/防濺衣', '87', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('830', '輔食料理機', '87', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('831', '食物存儲', '87', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('832', '寶寶護膚', '88', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('833', '洗髮沐浴', '88', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('834', '奶瓶清洗', '88', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('835', '驅蚊防曬', '88', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('836', '理髮器', '88', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('837', '洗澡用具', '88', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('838', '嬰兒口腔清潔', '88', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('839', '洗衣液/皂', '88', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('840', '日常護理', '88', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('841', '座便器', '88', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('842', '嬰兒推車', '89', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('843', '餐椅搖椅', '89', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('844', '嬰兒牀', '89', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('845', '學步車', '89', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('846', '三輪車', '89', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('847', '自行車', '89', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('848', '電動車', '89', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('849', '扭扭車', '89', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('850', '滑板車', '89', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('851', '嬰兒牀墊', '89', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('852', '嬰兒外出服', '90', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('853', '嬰兒內衣', '90', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('854', '嬰兒禮盒', '90', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('855', '嬰兒鞋帽襪', '90', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('856', '安全防護', '90', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('857', '家居牀品', '90', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('858', '睡袋/抱被', '90', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('859', '爬行墊', '90', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('860', '媽咪包/背嬰帶', '91', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('861', '產後塑身', '91', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('862', '文胸/內褲', '91', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('863', '防輻射服', '91', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('864', '孕媽裝', '91', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('865', '孕期營養', '91', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('866', '孕婦護膚', '91', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('867', '待產護理', '91', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('868', '月子裝', '91', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('869', '防溢乳墊', '91', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('870', '套裝', '92', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('871', '上衣', '92', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('872', '褲子', '92', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('873', '裙子', '92', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('874', '內衣/家居服', '92', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('875', '羽絨服/棉服', '92', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('876', '親子裝', '92', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('877', '兒童配飾', '92', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('878', '禮服/演出服', '92', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('879', '運動鞋', '92', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('880', '皮鞋/帆布鞋', '92', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('881', '靴子', '92', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('882', '涼鞋', '92', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('883', '功能鞋', '92', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('884', '户外/運動服', '92', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('885', '提籃式', '93', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('886', '安全座椅', '93', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('887', '增高墊', '93', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('888', '錢包', '94', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('889', '手拿包', '94', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('890', '單肩包', '94', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('891', '雙肩包', '94', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('892', '手提包', '94', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('893', '斜挎包', '94', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('894', '鑰匙包', '94', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('895', '卡包/零錢包', '94', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('896', '男士錢包', '95', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('897', '男士手包', '95', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('898', '卡包名片夾', '95', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('899', '商務公文包', '95', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('900', '雙肩包', '95', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('901', '單肩/斜挎包', '95', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('902', '鑰匙包', '95', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('903', '電腦包', '96', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('904', '拉桿箱', '96', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('905', '旅行包', '96', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('906', '旅行配件', '96', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('907', '休閒運動包', '96', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('908', '拉桿包', '96', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('909', '登山包', '96', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('910', '媽咪包', '96', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('911', '書包', '96', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('912', '相機包', '96', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('913', '腰包/胸包', '96', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('914', '火機煙具', '97', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('915', '禮品文具', '97', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('916', '軍刀軍具', '97', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('917', '收藏品', '97', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('918', '工藝禮品', '97', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('919', '創意禮品', '97', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('920', '禮盒禮券', '97', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('921', '鮮花綠植', '97', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('922', '婚慶節慶', '97', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('923', '京東卡', '97', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('924', '美妝禮品', '97', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('925', '禮品定製', '97', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('926', '京東福卡', '97', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('927', '古董文玩', '97', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('928', '箱包', '98', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('929', '錢包', '98', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('930', '服飾', '98', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('931', '腰帶', '98', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('932', '太陽鏡/眼鏡框', '98', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('933', '配件', '98', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('934', '鞋靴', '98', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('935', '飾品', '98', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('936', '名品腕錶', '98', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('937', '高檔化妝品', '98', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('938', '婚嫁首飾', '99', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('939', '婚紗攝影', '99', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('940', '婚紗禮服', '99', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('941', '婚慶服務', '99', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('942', '婚慶禮品/用品', '99', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('943', '婚宴', '99', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('944', '餅乾蛋糕', '100', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('945', '糖果/巧克力', '100', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('946', '休閒零食', '100', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('947', '衝調飲品', '100', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('948', '糧油調味', '100', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('949', '牛奶', '100', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('950', '其他特產', '101', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('951', '新疆', '101', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('952', '北京', '101', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('953', '山西', '101', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('954', '內蒙古', '101', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('955', '福建', '101', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('956', '湖南', '101', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('957', '四川', '101', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('958', '雲南', '101', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('959', '東北', '101', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('960', '休閒零食', '102', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('961', '堅果炒貨', '102', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('962', '肉乾肉脯', '102', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('963', '蜜餞果乾', '102', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('964', '糖果/巧克力', '102', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('965', '餅乾蛋糕', '102', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('966', '無糖食品', '102', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('967', '米麪雜糧', '103', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('968', '食用油', '103', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('969', '調味品', '103', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('970', '南北乾貨', '103', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('971', '方便食品', '103', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('972', '有機食品', '103', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('973', '飲用水', '104', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('974', '飲料', '104', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('975', '牛奶乳品', '104', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('976', '咖啡/奶茶', '104', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('977', '衝飲穀物', '104', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('978', '蜂蜜/柚子茶', '104', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('979', '成人奶粉', '104', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('980', '月餅', '105', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('981', '大閘蟹', '105', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('982', '粽子', '105', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('983', '卡券', '105', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('984', '鐵觀音', '106', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('985', '普洱', '106', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('986', '龍井', '106', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('987', '綠茶', '106', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('988', '紅茶', '106', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('989', '烏龍茶', '106', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('990', '花草茶', '106', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('991', '花果茶', '106', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('992', '養生茶', '106', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('993', '黑茶', '106', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('994', '白茶', '106', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('995', '其它茶', '106', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('996', '項鍊', '107', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('997', '手鍊/腳鏈', '107', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('998', '戒指', '107', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('999', '耳飾', '107', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1000', '毛衣鏈', '107', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1001', '髮飾/髮卡', '107', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1002', '胸針', '107', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1003', '飾品配件', '107', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1004', '婚慶飾品', '107', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1005', '黃金吊墜', '108', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1006', '黃金項鍊', '108', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1007', '黃金轉運珠', '108', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1008', '黃金手鐲/手鍊/腳鏈', '108', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1009', '黃金耳飾', '108', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1010', '黃金戒指', '108', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1011', 'K金吊墜', '109', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1012', 'K金項鍊', '109', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1013', 'K金手鐲/手鍊/腳鏈', '109', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1014', 'K金戒指', '109', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1015', 'K金耳飾', '109', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1016', '投資金', '110', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1017', '投資銀', '110', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1018', '投資收藏', '110', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1019', '銀吊墜/項鍊', '111', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1020', '銀手鐲/手鍊/腳鏈', '111', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1021', '銀戒指', '111', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1022', '銀耳飾', '111', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1023', '足銀手鐲', '111', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1024', '寶寶銀飾', '111', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1025', '裸鑽', '112', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1026', '鑽戒', '112', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1027', '鑽石項鍊/吊墜', '112', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1028', '鑽石耳飾', '112', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1029', '鑽石手鐲/手鍊', '112', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1030', '項鍊/吊墜', '113', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1031', '手鐲/手串', '113', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1032', '戒指', '113', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1033', '耳飾', '113', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1034', '掛件/擺件/把件', '113', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1035', '玉石孤品', '113', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1036', '項鍊/吊墜', '114', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1037', '耳飾', '114', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1038', '手鐲/手鍊/腳鏈', '114', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1039', '戒指', '114', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1040', '頭飾/胸針', '114', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1041', '擺件/掛件', '114', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1042', '琥珀/蜜蠟', '115', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1043', '碧璽', '115', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1044', '紅寶石/藍寶石', '115', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1045', '坦桑石', '115', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1046', '珊瑚', '115', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1047', '祖母綠', '115', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1048', '葡萄石', '115', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1049', '其他天然寶石', '115', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1050', '項鍊/吊墜', '115', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1051', '耳飾', '115', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1052', '手鐲/手鍊', '115', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1053', '戒指', '115', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1054', '鉑金項鍊/吊墜', '116', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1055', '鉑金手鐲/手鍊/腳鏈', '116', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1056', '鉑金戒指', '116', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1057', '鉑金耳飾', '116', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1058', '小葉紫檀', '117', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1059', '黃花梨', '117', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1060', '沉香木', '117', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1061', '金絲楠', '117', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1062', '菩提', '117', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1063', '其他', '117', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1064', '橄欖核/核桃', '117', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1065', '檀香', '117', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1066', '珍珠項鍊', '118', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1067', '珍珠吊墜', '118', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1068', '珍珠耳飾', '118', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1069', '珍珠手鍊', '118', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1070', '珍珠戒指', '118', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1071', '珍珠胸針', '118', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1072', '機油', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1073', '正時皮帶', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1074', '添加劑', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1075', '汽車喇叭', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1076', '防凍液', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1077', '汽車玻璃', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1078', '濾清器', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1079', '火花塞', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1080', '減震器', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1081', '柴機油/輔助油', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1082', '雨刷', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1083', '車燈', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1084', '後視鏡', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1085', '輪胎', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1086', '輪轂', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1087', '剎車片/盤', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1088', '維修配件', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1089', '蓄電池', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1090', '底盤裝甲/護板', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1091', '貼膜', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1092', '汽修工具', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1093', '改裝配件', '119', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1094', '導航儀', '120', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1095', '安全預警儀', '120', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1096', '行車記錄儀', '120', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1097', '倒車雷達', '120', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1098', '藍牙設備', '120', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1099', '車載影音', '120', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1100', '淨化器', '120', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1101', '電源', '120', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1102', '智能駕駛', '120', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1103', '車載電台', '120', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1104', '車載電器配件', '120', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1105', '吸塵器', '120', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1106', '智能車機', '120', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1107', '冰箱', '120', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1108', '汽車音響', '120', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1109', '車載生活電器', '120', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1110', '車蠟', '121', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1111', '補漆筆', '121', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1112', '玻璃水', '121', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1113', '清潔劑', '121', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1114', '洗車工具', '121', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1115', '鍍晶鍍膜', '121', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1116', '打蠟機', '121', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1117', '洗車配件', '121', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1118', '洗車機', '121', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1119', '洗車水槍', '121', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1120', '毛巾撣子', '121', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1121', '腳墊', '122', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1122', '座墊', '122', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1123', '座套', '122', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1124', '後備箱墊', '122', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1125', '頭枕腰靠', '122', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1126', '方向盤套', '122', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1127', '香水', '122', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1128', '空氣淨化', '122', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1129', '掛件擺件', '122', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1130', '功能小件', '122', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1131', '車身裝飾件', '122', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1132', '車衣', '122', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1133', '安全座椅', '123', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1134', '胎壓監測', '123', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1135', '防盜設備', '123', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1136', '應急救援', '123', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1137', '保温箱', '123', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1138', '地鎖', '123', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1139', '摩托車', '123', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1140', '充氣泵', '123', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1141', '儲物箱', '123', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1142', '自駕野營', '123', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1143', '摩托車裝備', '123', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1144', '清洗美容', '124', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1145', '功能升級', '124', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1146', '保養維修', '124', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1147', '油卡充值', '124', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1148', '車險', '124', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1149', '加油卡', '124', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1150', 'ETC', '124', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1151', '駕駛培訓', '124', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1152', '賽事服裝', '125', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1153', '賽事用品', '125', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1154', '制動系統', '125', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1155', '懸掛系統', '125', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1156', '進氣系統', '125', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1157', '排氣系統', '125', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1158', '電子管理', '125', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1159', '車身強化', '125', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1160', '賽事座椅', '125', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1161', '跑步鞋', '126', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1162', '休閒鞋', '126', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1163', '籃球鞋', '126', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1164', '板鞋', '126', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1165', '帆布鞋', '126', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1166', '足球鞋', '126', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1167', '乒羽網鞋', '126', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1168', '專項運動鞋', '126', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1169', '訓練鞋', '126', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1170', '拖鞋', '126', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1171', '運動包', '126', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1172', '羽絨服', '127', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1173', '棉服', '127', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1174', '運動褲', '127', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1175', '夾克/風衣', '127', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1176', '衞衣/套頭衫', '127', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1177', 'T恤', '127', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1178', '套裝', '127', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1179', '乒羽網服', '127', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1180', '健身服', '127', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1181', '運動背心', '127', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1182', '毛衫/線衫', '127', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1183', '運動配飾', '127', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1184', '摺疊車', '128', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1185', '山地車/公路車', '128', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1186', '電動車', '128', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1187', '其他整車', '128', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1188', '騎行服', '128', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1189', '騎行裝備', '128', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1190', '平衡車', '128', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1191', '魚竿魚線', '129', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1192', '浮漂魚餌', '129', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1193', '釣魚桌椅', '129', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1194', '釣魚配件', '129', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1195', '釣箱魚包', '129', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1196', '其它', '129', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1197', '泳鏡', '130', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1198', '泳帽', '130', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1199', '游泳包防水包', '130', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1200', '女士泳衣', '130', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1201', '男士泳衣', '130', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1202', '比基尼', '130', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1203', '其它', '130', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1204', '衝鋒衣褲', '131', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1205', '速乾衣褲', '131', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1206', '滑雪服', '131', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1207', '羽絨服/棉服', '131', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1208', '休閒衣褲', '131', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1209', '抓絨衣褲', '131', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1210', '軟殼衣褲', '131', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1211', 'T恤', '131', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1212', '户外風衣', '131', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1213', '功能內衣', '131', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1214', '軍迷服飾', '131', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1215', '登山鞋', '131', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1216', '雪地靴', '131', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1217', '徒步鞋', '131', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1218', '越野跑鞋', '131', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1219', '休閒鞋', '131', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1220', '工裝鞋', '131', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1221', '溯溪鞋', '131', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1222', '沙灘/涼拖', '131', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1223', '户外襪', '131', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1224', '帳篷/墊子', '132', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1225', '睡袋/吊牀', '132', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1226', '登山攀巖', '132', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1227', '户外配飾', '132', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1228', '揹包', '132', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1229', '户外照明', '132', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1230', '户外儀表', '132', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1231', '户外工具', '132', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1232', '望遠鏡', '132', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1233', '旅遊用品', '132', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1234', '便攜桌椅牀', '132', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1235', '野餐燒烤', '132', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1236', '軍迷用品', '132', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1237', '救援裝備', '132', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1238', '滑雪裝備', '132', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1239', '極限户外', '132', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1240', '衝浪潛水', '132', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1241', '綜合訓練器', '133', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1242', '其他大型器械', '133', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1243', '啞鈴', '133', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1244', '仰卧板/收腹機', '133', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1245', '其他中小型器材', '133', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1246', '瑜伽舞蹈', '133', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1247', '甩脂機', '133', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1248', '踏步機', '133', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1249', '武術搏擊', '133', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1250', '健身車/動感單車', '133', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1251', '跑步機', '133', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1252', '運動護具', '133', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1253', '羽毛球', '134', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1254', '乒乓球', '134', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1255', '籃球', '134', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1256', '足球', '134', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1257', '網球', '134', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1258', '排球', '134', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1259', '高爾夫', '134', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1260', '枱球', '134', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1261', '棋牌麻將', '134', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1262', '輪滑滑板', '134', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1263', '其他', '134', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1264', '0-6個月', '135', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1265', '6-12個月', '135', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1266', '1-3歲', '135', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1267', '3-6歲', '135', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1268', '6-14歲', '135', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1269', '14歲以上', '135', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1270', '遙控車', '136', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1271', '遙控飛機', '136', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1272', '遙控船', '136', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1273', '機器人', '136', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1274', '軌道/助力', '136', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1275', '毛絨/布藝', '137', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1276', '靠墊/抱枕', '137', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1277', '芭比娃娃', '138', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1278', '卡通娃娃', '138', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1279', '智能娃娃', '138', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1280', '仿真模型', '139', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1281', '拼插模型', '139', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1282', '收藏愛好', '139', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1283', '炫舞毯', '140', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1284', '爬行墊/毯', '140', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1285', '户外玩具', '140', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1286', '戲水玩具', '140', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1287', '電影周邊', '141', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1288', '卡通周邊', '141', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1289', '網遊周邊', '141', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1290', '搖鈴/牀鈴', '142', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1291', '健身架', '142', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1292', '早教啓智', '142', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1293', '拖拉玩具', '142', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1294', '積木', '143', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1295', '拼圖', '143', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1296', '磁力棒', '143', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1297', '立體拼插', '143', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1298', '手工彩泥', '144', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1299', '繪畫工具', '144', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1300', '情景玩具', '144', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1301', '減壓玩具', '145', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1302', '創意玩具', '145', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1303', '鋼琴', '146', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1304', '電子琴/電鋼琴', '146', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1305', '吉他/尤克里裏', '146', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1306', '打擊樂器', '146', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1307', '西洋管絃', '146', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1308', '民族管絃樂器', '146', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1309', '樂器配件', '146', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1310', '電腦音樂', '146', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1311', '工藝禮品樂器', '146', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1312', '口琴/口風琴/豎笛', '146', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1313', '手風琴', '146', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1314', '雙色球', '147', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1315', '大樂透', '147', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1316', '福彩3D', '147', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1317', '排列三', '147', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1318', '排列五', '147', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1319', '七星彩', '147', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1320', '七樂彩', '147', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1321', '競彩足球', '147', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1322', '競彩籃球', '147', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1323', '新時時彩', '147', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1324', '國內機票', '148', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1325', '國內酒店', '149', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1326', '酒店團購', '149', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1327', '度假', '150', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1328', '景點', '150', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1329', '租車', '150', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1330', '火車票', '150', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1331', '旅遊團購', '150', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1332', '手機充值', '151', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1333', '遊戲點卡', '152', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1334', 'QQ充值', '152', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1335', '電影票', '153', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1336', '演唱會', '153', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1337', '話劇歌劇', '153', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1338', '音樂會', '153', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1339', '體育賽事', '153', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1340', '舞蹈芭蕾', '153', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1341', '戲曲綜藝', '153', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1342', '東北', '154', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1343', '華北', '154', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1344', '西北', '154', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1345', '華中', '154', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1346', '華東', '154', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1347', '華南', '154', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1348', '西南', '154', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1349', '蘋果', '155', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1350', '橙子', '155', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1351', '奇異果/獼猴桃', '155', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1352', '車釐子/櫻桃', '155', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1353', '芒果', '155', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1354', '藍莓', '155', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1355', '火龍果', '155', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1356', '葡萄/提子', '155', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1357', '柚子', '155', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1358', '香蕉', '155', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1359', '牛油果', '155', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1360', '梨', '155', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1361', '菠蘿/鳳梨', '155', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1362', '桔/橘', '155', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1363', '檸檬', '155', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1364', '草莓', '155', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1365', '桃/李/杏', '155', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1366', '更多水果', '155', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1367', '水果禮盒/券', '155', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1368', '牛肉', '156', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1369', '羊肉', '156', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1370', '豬肉', '156', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1371', '內臟類', '156', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1372', '魚類', '157', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1373', '蝦類', '157', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1374', '蟹類', '157', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1375', '貝類', '157', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1376', '海蔘', '157', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1377', '海產乾貨', '157', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1378', '其他水產', '157', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1379', '海產禮盒', '157', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1380', '雞肉', '158', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1381', '鴨肉', '158', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1382', '蛋類', '158', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1383', '其他禽類', '158', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1384', '水餃/餛飩', '159', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1385', '湯圓/元宵', '159', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1386', '麪點', '159', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1387', '火鍋丸串', '159', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1388', '速凍半成品', '159', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1389', '奶酪黃油', '159', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1390', '熟食', '160', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1391', '臘腸/臘肉', '160', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1392', '火腿', '160', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1393', '糕點', '160', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1394', '禮品卡券', '160', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1395', '冷藏果蔬汁', '161', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1396', '冰激凌', '161', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1397', '其他', '161', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1398', '葉菜類', '162', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1399', '茄果瓜類', '162', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1400', '根莖類', '162', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1401', '鮮菌菇', '162', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1402', '葱薑蒜椒', '162', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1403', '半加工蔬菜', '162', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1404', '微型車', '163', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1405', '小型車', '163', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1406', '緊湊型車', '163', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1407', '中型車', '163', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1408', '中大型車', '163', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1409', '豪華車', '163', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1410', 'MPV', '163', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1411', 'SUV', '163', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1412', '跑車', '163', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1413', '微型車（二手）', '164', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1414', '小型車（二手）', '164', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1415', '緊湊型車（二手）', '164', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1416', '中型車（二手）', '164', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1417', '中大型車（二手）', '164', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1418', '豪華車（二手）', '164', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1419', 'MPV（二手）', '164', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1420', 'SUV（二手）', '164', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1421', '跑車（二手）', '164', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1422', '皮卡（二手）', '164', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1423', '麪包車（二手）', '164', '3', '1', '0', null, null, '0');
INSERT INTO `pms_category` VALUES ('1431', 'dsa323', '1', '2', '1', null, null, null, null);
INSERT INTO `pms_category` VALUES ('1432', 'fdsffdsadddd大薩達', '1431', '3', '1', null, null, null, null);

-- ----------------------------
-- Table structure for pms_category_brand_relation
-- ----------------------------
DROP TABLE IF EXISTS `pms_category_brand_relation`;
CREATE TABLE `pms_category_brand_relation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_id` bigint(20) DEFAULT NULL COMMENT '品牌id',
  `catalog_id` bigint(20) DEFAULT NULL COMMENT '分類id',
  `brand_name` varchar(255) DEFAULT NULL,
  `catalog_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COMMENT='品牌分類關聯';

-- ----------------------------
-- Records of pms_category_brand_relation
-- ----------------------------
INSERT INTO `pms_category_brand_relation` VALUES ('13', '9', '225', '華為', '手機');
INSERT INTO `pms_category_brand_relation` VALUES ('15', '9', '250', '華為', '平板電視');
INSERT INTO `pms_category_brand_relation` VALUES ('16', '9', '449', '華為', '筆記本');
INSERT INTO `pms_category_brand_relation` VALUES ('17', '10', '449', '小米', '筆記本');
INSERT INTO `pms_category_brand_relation` VALUES ('18', '10', '225', '小米', '手機');
INSERT INTO `pms_category_brand_relation` VALUES ('19', '10', '231', '小米', '移動電源');
INSERT INTO `pms_category_brand_relation` VALUES ('20', '10', '233', '小米', '藍牙耳機');
INSERT INTO `pms_category_brand_relation` VALUES ('21', '10', '250', '小米', '平板電視');
INSERT INTO `pms_category_brand_relation` VALUES ('22', '10', '449', '小米', '筆記本');
INSERT INTO `pms_category_brand_relation` VALUES ('23', '11', '225', 'oppo', '手機');
INSERT INTO `pms_category_brand_relation` VALUES ('24', '11', '227', 'oppo', '合約機');
INSERT INTO `pms_category_brand_relation` VALUES ('25', '12', '225', 'Apple', '手機');
INSERT INTO `pms_category_brand_relation` VALUES ('26', '12', '243', 'Apple', 'iPhone 配件');
INSERT INTO `pms_category_brand_relation` VALUES ('27', '12', '366', 'Apple', '智能手錶');

-- ----------------------------
-- Table structure for pms_comment_replay
-- ----------------------------
DROP TABLE IF EXISTS `pms_comment_replay`;
CREATE TABLE `pms_comment_replay` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `comment_id` bigint(20) DEFAULT NULL COMMENT '評論id',
  `reply_id` bigint(20) DEFAULT NULL COMMENT '回覆id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品評價回覆關係';

-- ----------------------------
-- Records of pms_comment_replay
-- ----------------------------

-- ----------------------------
-- Table structure for pms_product_attr_value
-- ----------------------------
DROP TABLE IF EXISTS `pms_product_attr_value`;
CREATE TABLE `pms_product_attr_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `spu_id` bigint(20) DEFAULT NULL COMMENT '商品id',
  `attr_id` bigint(20) DEFAULT NULL COMMENT '屬性id',
  `attr_name` varchar(200) DEFAULT NULL COMMENT '屬性名',
  `attr_value` varchar(200) DEFAULT NULL COMMENT '屬性值',
  `attr_sort` int(11) DEFAULT NULL COMMENT '順序',
  `quick_show` tinyint(4) DEFAULT NULL COMMENT '快速展示【是否展示在介紹上；0-否 1-是】',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COMMENT='spu屬性值';

-- ----------------------------
-- Records of pms_product_attr_value
-- ----------------------------
INSERT INTO `pms_product_attr_value` VALUES ('55', '13', '7', '入網型號', 'A2217', null, '0');
INSERT INTO `pms_product_attr_value` VALUES ('56', '13', '8', '上市年份', '2018', null, '0');
INSERT INTO `pms_product_attr_value` VALUES ('57', '13', '13', '機身長度（mm）', '158.3', null, '0');
INSERT INTO `pms_product_attr_value` VALUES ('58', '13', '14', '機身材質工藝', '以官網信息為準', null, '0');
INSERT INTO `pms_product_attr_value` VALUES ('59', '13', '15', 'CPU品牌', '以官網信息為準', null, '1');
INSERT INTO `pms_product_attr_value` VALUES ('60', '13', '16', 'CPU型號', 'A13仿生', null, '1');
INSERT INTO `pms_product_attr_value` VALUES ('61', '11', '7', '入網型號', 'LIO-AL00', null, '1');
INSERT INTO `pms_product_attr_value` VALUES ('62', '11', '8', '上市年份', '2019', null, '0');
INSERT INTO `pms_product_attr_value` VALUES ('63', '11', '11', '機身顏色', '黑色', null, '1');
INSERT INTO `pms_product_attr_value` VALUES ('64', '11', '13', '機身長度（mm）', '158.3', null, '1');
INSERT INTO `pms_product_attr_value` VALUES ('65', '11', '14', '機身材質工藝', '玻璃;陶瓷', null, '0');
INSERT INTO `pms_product_attr_value` VALUES ('66', '11', '15', 'CPU品牌', '海思（Hisilicon）', null, '1');
INSERT INTO `pms_product_attr_value` VALUES ('67', '11', '16', 'CPU型號', 'HUAWEI Kirin 970', null, '1');

-- ----------------------------
-- Table structure for pms_sku_images
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku_images`;
CREATE TABLE `pms_sku_images` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT 'sku_id',
  `img_url` varchar(255) DEFAULT NULL COMMENT '圖片地址',
  `img_sort` int(11) DEFAULT NULL COMMENT '排序',
  `default_img` int(11) DEFAULT NULL COMMENT '默認圖[0 - 不是默認圖，1 - 是默認圖]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=111 DEFAULT CHARSET=utf8mb4 COMMENT='sku圖片';

-- ----------------------------
-- Records of pms_sku_images
-- ----------------------------
INSERT INTO `pms_sku_images` VALUES ('1', '1', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('2', '1', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/20f83b0c-86ba-4a64-808a-f2ace190ea2c_1f15cdbcf9e1273c.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('3', '1', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('4', '1', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('5', '1', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('6', '1', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('7', '1', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('8', '1', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('9', '1', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/d12516dd-43cf-4ace-8dc9-14618d2d75e4_919c850652e98031.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('10', '1', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/60e65a44-f943-4ed5-87c8-8cf90f403018_d511faab82abb34b.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('11', '2', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('12', '2', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/20f83b0c-86ba-4a64-808a-f2ace190ea2c_1f15cdbcf9e1273c.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('13', '2', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('14', '2', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('15', '2', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('16', '2', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('17', '2', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('18', '2', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('19', '2', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('20', '2', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/60e65a44-f943-4ed5-87c8-8cf90f403018_d511faab82abb34b.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('21', '3', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('22', '3', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('23', '3', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('24', '3', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('25', '3', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('26', '3', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/099f297e-ddea-4fb5-87c7-78cd88e500c0_28f296629cca865e.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('27', '3', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('28', '3', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/7aa1019e-4e01-49dd-8ac4-7d2794d0b1a8_335b2c690e43a8f8.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('29', '3', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('30', '3', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('31', '4', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('32', '4', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('33', '4', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('34', '4', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('35', '4', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('36', '4', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/099f297e-ddea-4fb5-87c7-78cd88e500c0_28f296629cca865e.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('37', '4', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('38', '4', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/7aa1019e-4e01-49dd-8ac4-7d2794d0b1a8_335b2c690e43a8f8.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('39', '4', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('40', '4', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('41', '5', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('42', '5', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/20f83b0c-86ba-4a64-808a-f2ace190ea2c_1f15cdbcf9e1273c.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('43', '5', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('44', '5', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/9a6ba5c0-0a31-4364-8759-012bdfbf5ad3_3c24f9cd69534030.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('45', '5', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/a73ef55a-79b4-41d9-8eb6-760c8b5a33dc_23d9fbb256ea5d4a.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('46', '5', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('47', '5', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('48', '5', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('49', '5', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('50', '5', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('51', '6', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('52', '6', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('53', '6', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('54', '6', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/9a6ba5c0-0a31-4364-8759-012bdfbf5ad3_3c24f9cd69534030.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('55', '6', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/a73ef55a-79b4-41d9-8eb6-760c8b5a33dc_23d9fbb256ea5d4a.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('56', '6', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('57', '6', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('58', '6', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('59', '6', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('60', '6', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('61', '7', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('62', '7', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('63', '7', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('64', '7', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('65', '7', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('66', '7', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('67', '7', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/38492c9f-b0e0-4cba-87a9-77cb6189ea73_73ab4d2e818d2211.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('68', '7', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('69', '7', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/d12516dd-43cf-4ace-8dc9-14618d2d75e4_919c850652e98031.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('70', '7', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/60e65a44-f943-4ed5-87c8-8cf90f403018_d511faab82abb34b.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('71', '8', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('72', '8', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('73', '8', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('74', '8', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('75', '8', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('76', '8', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('77', '8', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/38492c9f-b0e0-4cba-87a9-77cb6189ea73_73ab4d2e818d2211.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('78', '8', '', null, '0');
INSERT INTO `pms_sku_images` VALUES ('79', '8', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/d12516dd-43cf-4ace-8dc9-14618d2d75e4_919c850652e98031.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('80', '8', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/60e65a44-f943-4ed5-87c8-8cf90f403018_d511faab82abb34b.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('81', '9', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/462ef293-2b8b-4c53-85f2-1726e14dc976_23cd65077f12f7f5.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('82', '9', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/bc4825d6-2a6c-43f8-8d75-5f35b77b9514_a2c208410ae84d1f.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('83', '9', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/f968b6ac-2fca-4440-842f-8db8b76478f0_b8494bf281991f94.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('84', '10', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/462ef293-2b8b-4c53-85f2-1726e14dc976_23cd65077f12f7f5.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('85', '10', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/bc4825d6-2a6c-43f8-8d75-5f35b77b9514_a2c208410ae84d1f.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('86', '10', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/f968b6ac-2fca-4440-842f-8db8b76478f0_b8494bf281991f94.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('87', '11', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/462ef293-2b8b-4c53-85f2-1726e14dc976_23cd65077f12f7f5.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('88', '11', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/bc4825d6-2a6c-43f8-8d75-5f35b77b9514_a2c208410ae84d1f.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('89', '11', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/f968b6ac-2fca-4440-842f-8db8b76478f0_b8494bf281991f94.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('90', '12', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/462ef293-2b8b-4c53-85f2-1726e14dc976_23cd65077f12f7f5.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('91', '13', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/462ef293-2b8b-4c53-85f2-1726e14dc976_23cd65077f12f7f5.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('92', '14', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/462ef293-2b8b-4c53-85f2-1726e14dc976_23cd65077f12f7f5.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('93', '15', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('94', '16', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('95', '17', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('96', '18', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('97', '19', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('98', '20', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('99', '21', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/1756fa6d-1934-4d5c-8faf-84b3d882fe53_5b5e74d0978360a1.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('100', '21', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/e21777b3-18ac-4580-819e-497c3aa25e4f_6a1b2703a9ed8737.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('101', '21', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/2419c5cf-a641-4ec1-8f94-64981dc207f6_63e862164165f483.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('102', '22', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/1756fa6d-1934-4d5c-8faf-84b3d882fe53_5b5e74d0978360a1.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('103', '22', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/e21777b3-18ac-4580-819e-497c3aa25e4f_6a1b2703a9ed8737.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('104', '22', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/2419c5cf-a641-4ec1-8f94-64981dc207f6_63e862164165f483.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('105', '23', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/1756fa6d-1934-4d5c-8faf-84b3d882fe53_5b5e74d0978360a1.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('106', '23', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/e21777b3-18ac-4580-819e-497c3aa25e4f_6a1b2703a9ed8737.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('107', '23', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/2419c5cf-a641-4ec1-8f94-64981dc207f6_63e862164165f483.jpg', null, '0');
INSERT INTO `pms_sku_images` VALUES ('108', '24', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('109', '25', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', null, '1');
INSERT INTO `pms_sku_images` VALUES ('110', '26', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', null, '1');

-- ----------------------------
-- Table structure for pms_sku_info
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku_info`;
CREATE TABLE `pms_sku_info` (
  `sku_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'skuId',
  `spu_id` bigint(20) DEFAULT NULL COMMENT 'spuId',
  `sku_name` varchar(255) DEFAULT NULL COMMENT 'sku名稱',
  `sku_desc` varchar(2000) DEFAULT NULL COMMENT 'sku介紹描述',
  `catalog_id` bigint(20) DEFAULT NULL COMMENT '所屬分類id',
  `brand_id` bigint(20) DEFAULT NULL COMMENT '品牌id',
  `sku_default_img` varchar(255) DEFAULT NULL COMMENT '默認圖片',
  `sku_title` varchar(255) DEFAULT NULL COMMENT '標題',
  `sku_subtitle` varchar(2000) DEFAULT NULL COMMENT '副標題',
  `price` decimal(18,4) DEFAULT NULL COMMENT '價格',
  `sale_count` bigint(20) DEFAULT NULL COMMENT '銷量',
  PRIMARY KEY (`sku_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COMMENT='sku信息';

-- ----------------------------
-- Records of pms_sku_info
-- ----------------------------
INSERT INTO `pms_sku_info` VALUES ('1', '11', '華為 HUAWEI Mate 30 Pro 星河銀 8GB+256GB', null, '225', '9', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/60e65a44-f943-4ed5-87c8-8cf90f403018_d511faab82abb34b.jpg', '華為 HUAWEI Mate 30 Pro 星河銀 8GB+256GB麒麟990旗艦芯片OLED環幕屏雙4000萬徠卡電影四攝4G全網通手機', '【現貨搶購！享白條12期免息！】麒麟990，OLED環幕屏雙4000萬徠卡電影四攝；Mate30系列享12期免息》', '6299.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('2', '11', '華為 HUAWEI Mate 30 Pro 星河銀 8GB+128GB', null, '225', '9', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', '華為 HUAWEI Mate 30 Pro 星河銀 8GB+128GB麒麟990旗艦芯片OLED環幕屏雙4000萬徠卡電影四攝4G全網通手機', '【現貨搶購！享白條12期免息！】麒麟990，OLED環幕屏雙4000萬徠卡電影四攝；Mate30系列享12期免息》', '5799.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('3', '11', '華為 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB', null, '225', '9', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', '華為 HUAWEI Mate 30 Pro 亮黑色 8GB+256GB麒麟990旗艦芯片OLED環幕屏雙4000萬徠卡電影四攝4G全網通手機', '【現貨搶購！享白條12期免息！】麒麟990，OLED環幕屏雙4000萬徠卡電影四攝；Mate30系列享12期免息》', '6299.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('4', '11', '華為 HUAWEI Mate 30 Pro 亮黑色 8GB+128GB', null, '225', '9', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', '華為 HUAWEI Mate 30 Pro 亮黑色 8GB+128GB麒麟990旗艦芯片OLED環幕屏雙4000萬徠卡電影四攝4G全網通手機', '【現貨搶購！享白條12期免息！】麒麟990，OLED環幕屏雙4000萬徠卡電影四攝；Mate30系列享12期免息》', '5799.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('5', '11', '華為 HUAWEI Mate 30 Pro 翡冷翠 8GB+256GB', null, '225', '9', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', '華為 HUAWEI Mate 30 Pro 翡冷翠 8GB+256GB麒麟990旗艦芯片OLED環幕屏雙4000萬徠卡電影四攝4G全網通手機', '【現貨搶購！享白條12期免息！】麒麟990，OLED環幕屏雙4000萬徠卡電影四攝；Mate30系列享12期免息》', '6299.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('6', '11', '華為 HUAWEI Mate 30 Pro 翡冷翠 8GB+128GB', null, '225', '9', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', '華為 HUAWEI Mate 30 Pro 翡冷翠 8GB+128GB麒麟990旗艦芯片OLED環幕屏雙4000萬徠卡電影四攝4G全網通手機', '【現貨搶購！享白條12期免息！】麒麟990，OLED環幕屏雙4000萬徠卡電影四攝；Mate30系列享12期免息》', '5799.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('7', '11', '華為 HUAWEI Mate 30 Pro 羅蘭紫 8GB+256GB', null, '225', '9', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/60e65a44-f943-4ed5-87c8-8cf90f403018_d511faab82abb34b.jpg', '華為 HUAWEI Mate 30 Pro 羅蘭紫 8GB+256GB麒麟990旗艦芯片OLED環幕屏雙4000萬徠卡電影四攝4G全網通手機', '【現貨搶購！享白條12期免息！】麒麟990，OLED環幕屏雙4000萬徠卡電影四攝；Mate30系列享12期免息》', '6299.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('8', '11', '華為 HUAWEI Mate 30 Pro 羅蘭紫 8GB+128GB', null, '225', '9', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/60e65a44-f943-4ed5-87c8-8cf90f403018_d511faab82abb34b.jpg', '華為 HUAWEI Mate 30 Pro 羅蘭紫 8GB+128GB麒麟990旗艦芯片OLED環幕屏雙4000萬徠卡電影四攝4G全網通手機', '【現貨搶購！享白條12期免息！】麒麟990，OLED環幕屏雙4000萬徠卡電影四攝；Mate30系列享12期免息》', '5799.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('9', '13', ' Apple iPhone 11 (A2223)  黑色 128GB ', null, '225', '12', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/bc4825d6-2a6c-43f8-8d75-5f35b77b9514_a2c208410ae84d1f.jpg', ' Apple iPhone 11 (A2223)  黑色 128GB 移動聯通電信4G手機 雙卡雙待 最後幾件優惠', 'iPhoneXS系列性能強勁，樣樣出色，現特惠來襲，低至5399元！詳情請點擊！', '5999.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('10', '13', ' Apple iPhone 11 (A2223)  黑色 256GB', null, '225', '12', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/bc4825d6-2a6c-43f8-8d75-5f35b77b9514_a2c208410ae84d1f.jpg', ' Apple iPhone 11 (A2223)  黑色 256GB 移動聯通電信4G手機 雙卡雙待', 'iPhoneXS系列性能強勁，樣樣出色，現特惠來襲，低至5399元！詳情請點擊！', '6799.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('11', '13', ' Apple iPhone 11 (A2223)  黑色 64GB', null, '225', '12', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/bc4825d6-2a6c-43f8-8d75-5f35b77b9514_a2c208410ae84d1f.jpg', ' Apple iPhone 11 (A2223)  黑色 64GB  移動聯通電信4G手機 雙卡雙待', 'iPhoneXS系列性能強勁，樣樣出色，現特惠來襲，低至5399元！詳情請點擊！', '5499.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('12', '13', ' Apple iPhone 11 (A2223)  白色 128GB ', null, '225', '12', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/462ef293-2b8b-4c53-85f2-1726e14dc976_23cd65077f12f7f5.jpg', ' Apple iPhone 11 (A2223)  白色 128GB  移動聯通電信4G手機 雙卡雙待', 'iPhoneXS系列性能強勁，樣樣出色，現特惠來襲，低至5399元！詳情請點擊！', '5999.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('13', '13', ' Apple iPhone 11 (A2223)  白色 256GB', null, '225', '12', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/462ef293-2b8b-4c53-85f2-1726e14dc976_23cd65077f12f7f5.jpg', ' Apple iPhone 11 (A2223)  白色 256GB  移動聯通電信4G手機 雙卡雙待', 'iPhoneXS系列性能強勁，樣樣出色，現特惠來襲，低至5399元！詳情請點擊！', '6799.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('14', '13', ' Apple iPhone 11 (A2223)  白色 64GB', null, '225', '12', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/462ef293-2b8b-4c53-85f2-1726e14dc976_23cd65077f12f7f5.jpg', ' Apple iPhone 11 (A2223)  白色 64GB  移動聯通電信4G手機 雙卡雙待', 'iPhoneXS系列性能強勁，樣樣出色，現特惠來襲，低至5399元！詳情請點擊！', '5499.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('15', '13', ' Apple iPhone 11 (A2223)  綠色 128GB ', null, '225', '12', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', ' Apple iPhone 11 (A2223)  綠色 128GB  移動聯通電信4G手機 雙卡雙待', 'iPhoneXS系列性能強勁，樣樣出色，現特惠來襲，低至5399元！詳情請點擊！', '5999.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('16', '13', ' Apple iPhone 11 (A2223)  綠色 256GB', null, '225', '12', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', ' Apple iPhone 11 (A2223)  綠色 256GB  移動聯通電信4G手機 雙卡雙待', 'iPhoneXS系列性能強勁，樣樣出色，現特惠來襲，低至5399元！詳情請點擊！', '6799.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('17', '13', ' Apple iPhone 11 (A2223)  綠色 64GB', null, '225', '12', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', ' Apple iPhone 11 (A2223)  綠色 64GB  移動聯通電信4G手機 雙卡雙待', 'iPhoneXS系列性能強勁，樣樣出色，現特惠來襲，低至5399元！詳情請點擊！', '5499.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('18', '13', ' Apple iPhone 11 (A2223)  黃色 128GB ', null, '225', '12', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', ' Apple iPhone 11 (A2223)  黃色 128GB  移動聯通電信4G手機 雙卡雙待', 'iPhoneXS系列性能強勁，樣樣出色，現特惠來襲，低至5399元！詳情請點擊！', '5999.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('19', '13', ' Apple iPhone 11 (A2223)  黃色 256GB', null, '225', '12', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', ' Apple iPhone 11 (A2223)  黃色 256GB  移動聯通電信4G手機 雙卡雙待', 'iPhoneXS系列性能強勁，樣樣出色，現特惠來襲，低至5399元！詳情請點擊！', '6799.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('20', '13', ' Apple iPhone 11 (A2223)  黃色 64GB', null, '225', '12', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', ' Apple iPhone 11 (A2223)  黃色 64GB  移動聯通電信4G手機 雙卡雙待', 'iPhoneXS系列性能強勁，樣樣出色，現特惠來襲，低至5399元！詳情請點擊！', '5499.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('21', '13', ' Apple iPhone 11 (A2223)  紅色 128GB ', null, '225', '12', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/e21777b3-18ac-4580-819e-497c3aa25e4f_6a1b2703a9ed8737.jpg', ' Apple iPhone 11 (A2223)  紅色 128GB  移動聯通電信4G手機 雙卡雙待', 'iPhoneXS系列性能強勁，樣樣出色，現特惠來襲，低至5399元！詳情請點擊！', '5999.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('22', '13', ' Apple iPhone 11 (A2223)  紅色 256GB', null, '225', '12', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/e21777b3-18ac-4580-819e-497c3aa25e4f_6a1b2703a9ed8737.jpg', ' Apple iPhone 11 (A2223)  紅色 256GB  移動聯通電信4G手機 雙卡雙待', 'iPhoneXS系列性能強勁，樣樣出色，現特惠來襲，低至5399元！詳情請點擊！', '6799.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('23', '13', ' Apple iPhone 11 (A2223)  紅色 64GB', null, '225', '12', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/e21777b3-18ac-4580-819e-497c3aa25e4f_6a1b2703a9ed8737.jpg', ' Apple iPhone 11 (A2223)  紅色 64GB  移動聯通電信4G手機 雙卡雙待', 'iPhoneXS系列性能強勁，樣樣出色，現特惠來襲，低至5399元！詳情請點擊！', '5499.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('24', '13', ' Apple iPhone 11 (A2223)  紫色 128GB ', null, '225', '12', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', ' Apple iPhone 11 (A2223)  紫色 128GB  移動聯通電信4G手機 雙卡雙待', 'iPhoneXS系列性能強勁，樣樣出色，現特惠來襲，低至5399元！詳情請點擊！', '5999.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('25', '13', ' Apple iPhone 11 (A2223)  紫色 256GB', null, '225', '12', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', ' Apple iPhone 11 (A2223)  紫色 256GB  移動聯通電信4G手機 雙卡雙待', 'iPhoneXS系列性能強勁，樣樣出色，現特惠來襲，低至5399元！詳情請點擊！', '6799.0000', '0');
INSERT INTO `pms_sku_info` VALUES ('26', '13', ' Apple iPhone 11 (A2223)  紫色 64GB', null, '225', '12', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', ' Apple iPhone 11 (A2223)  紫色 64GB  移動聯通電信4G手機 雙卡雙待', 'iPhoneXS系列性能強勁，樣樣出色，現特惠來襲，低至5399元！詳情請點擊！', '5499.0000', '0');

-- ----------------------------
-- Table structure for pms_sku_sale_attr_value
-- ----------------------------
DROP TABLE IF EXISTS `pms_sku_sale_attr_value`;
CREATE TABLE `pms_sku_sale_attr_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT 'sku_id',
  `attr_id` bigint(20) DEFAULT NULL COMMENT 'attr_id',
  `attr_name` varchar(200) DEFAULT NULL COMMENT '銷售屬性名',
  `attr_value` varchar(200) DEFAULT NULL COMMENT '銷售屬性值',
  `attr_sort` int(11) DEFAULT NULL COMMENT '順序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=53 DEFAULT CHARSET=utf8mb4 COMMENT='sku銷售屬性&值';

-- ----------------------------
-- Records of pms_sku_sale_attr_value
-- ----------------------------
INSERT INTO `pms_sku_sale_attr_value` VALUES ('1', '1', '9', '顏色', '星河銀', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('2', '1', '12', '版本', '8GB+256GB', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('3', '2', '9', '顏色', '星河銀', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('4', '2', '12', '版本', '8GB+128GB', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('5', '3', '9', '顏色', '亮黑色', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('6', '3', '12', '版本', '8GB+256GB', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('7', '4', '9', '顏色', '亮黑色', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('8', '4', '12', '版本', '8GB+128GB', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('9', '5', '9', '顏色', '翡冷翠', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('10', '5', '12', '版本', '8GB+256GB', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('11', '6', '9', '顏色', '翡冷翠', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('12', '6', '12', '版本', '8GB+128GB', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('13', '7', '9', '顏色', '羅蘭紫', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('14', '7', '12', '版本', '8GB+256GB', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('15', '8', '9', '顏色', '羅蘭紫', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('16', '8', '12', '版本', '8GB+128GB', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('17', '9', '9', '顏色', '黑色', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('18', '9', '12', '版本', '128GB ', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('19', '10', '9', '顏色', '黑色', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('20', '10', '12', '版本', '256GB', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('21', '11', '9', '顏色', '黑色', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('22', '11', '12', '版本', '64GB', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('23', '12', '9', '顏色', '白色', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('24', '12', '12', '版本', '128GB ', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('25', '13', '9', '顏色', '白色', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('26', '13', '12', '版本', '256GB', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('27', '14', '9', '顏色', '白色', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('28', '14', '12', '版本', '64GB', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('29', '15', '9', '顏色', '綠色', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('30', '15', '12', '版本', '128GB ', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('31', '16', '9', '顏色', '綠色', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('32', '16', '12', '版本', '256GB', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('33', '17', '9', '顏色', '綠色', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('34', '17', '12', '版本', '64GB', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('35', '18', '9', '顏色', '黃色', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('36', '18', '12', '版本', '128GB ', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('37', '19', '9', '顏色', '黃色', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('38', '19', '12', '版本', '256GB', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('39', '20', '9', '顏色', '黃色', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('40', '20', '12', '版本', '64GB', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('41', '21', '9', '顏色', '紅色', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('42', '21', '12', '版本', '128GB ', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('43', '22', '9', '顏色', '紅色', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('44', '22', '12', '版本', '256GB', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('45', '23', '9', '顏色', '紅色', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('46', '23', '12', '版本', '64GB', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('47', '24', '9', '顏色', '紫色', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('48', '24', '12', '版本', '128GB ', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('49', '25', '9', '顏色', '紫色', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('50', '25', '12', '版本', '256GB', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('51', '26', '9', '顏色', '紫色', null);
INSERT INTO `pms_sku_sale_attr_value` VALUES ('52', '26', '12', '版本', '64GB', null);

-- ----------------------------
-- Table structure for pms_spu_comment
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_comment`;
CREATE TABLE `pms_spu_comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) DEFAULT NULL COMMENT 'sku_id',
  `spu_id` bigint(20) DEFAULT NULL COMMENT 'spu_id',
  `spu_name` varchar(255) DEFAULT NULL COMMENT '商品名字',
  `member_nick_name` varchar(255) DEFAULT NULL COMMENT '會員暱稱',
  `star` tinyint(1) DEFAULT NULL COMMENT '星級',
  `member_ip` varchar(64) DEFAULT NULL COMMENT '會員ip',
  `create_time` datetime DEFAULT NULL COMMENT '創建時間',
  `show_status` tinyint(1) DEFAULT NULL COMMENT '顯示狀態[0-不顯示，1-顯示]',
  `spu_attributes` varchar(255) DEFAULT NULL COMMENT '購買時屬性組合',
  `likes_count` int(11) DEFAULT NULL COMMENT '點贊數',
  `reply_count` int(11) DEFAULT NULL COMMENT '回覆數',
  `resources` varchar(1000) DEFAULT NULL COMMENT '評論圖片/視頻[json數據；[{type:文件類型,url:資源路徑}]]',
  `content` text COMMENT '內容',
  `member_icon` varchar(255) DEFAULT NULL COMMENT '用户頭像',
  `comment_type` tinyint(4) DEFAULT NULL COMMENT '評論類型[0 - 對商品的直接評論，1 - 對評論的回覆]',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品評價';

-- ----------------------------
-- Records of pms_spu_comment
-- ----------------------------

-- ----------------------------
-- Table structure for pms_spu_images
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_images`;
CREATE TABLE `pms_spu_images` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `spu_id` bigint(20) DEFAULT NULL COMMENT 'spu_id',
  `img_name` varchar(200) DEFAULT NULL COMMENT '圖片名',
  `img_url` varchar(255) DEFAULT NULL COMMENT '圖片地址',
  `img_sort` int(11) DEFAULT NULL COMMENT '順序',
  `default_img` tinyint(4) DEFAULT NULL COMMENT '是否默認圖',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=95 DEFAULT CHARSET=utf8mb4 COMMENT='spu圖片';

-- ----------------------------
-- Records of pms_spu_images
-- ----------------------------
INSERT INTO `pms_spu_images` VALUES ('71', '11', null, 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/ef2691e5-de1a-4ca3-827d-a60f39fbda93_0d40c24b264aa511.jpg', null, null);
INSERT INTO `pms_spu_images` VALUES ('72', '11', null, 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/20f83b0c-86ba-4a64-808a-f2ace190ea2c_1f15cdbcf9e1273c.jpg', null, null);
INSERT INTO `pms_spu_images` VALUES ('73', '11', null, 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/89e324b9-d0cf-4f4f-8e81-94bb219910b3_2b1837c6c50add30.jpg', null, null);
INSERT INTO `pms_spu_images` VALUES ('74', '11', null, 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/9a6ba5c0-0a31-4364-8759-012bdfbf5ad3_3c24f9cd69534030.jpg', null, null);
INSERT INTO `pms_spu_images` VALUES ('75', '11', null, 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/a73ef55a-79b4-41d9-8eb6-760c8b5a33dc_23d9fbb256ea5d4a.jpg', null, null);
INSERT INTO `pms_spu_images` VALUES ('76', '11', null, 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/099f297e-ddea-4fb5-87c7-78cd88e500c0_28f296629cca865e.jpg', null, null);
INSERT INTO `pms_spu_images` VALUES ('77', '11', null, 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/38492c9f-b0e0-4cba-87a9-77cb6189ea73_73ab4d2e818d2211.jpg', null, null);
INSERT INTO `pms_spu_images` VALUES ('78', '11', null, 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/7aa1019e-4e01-49dd-8ac4-7d2794d0b1a8_335b2c690e43a8f8.jpg', null, null);
INSERT INTO `pms_spu_images` VALUES ('79', '11', null, 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/d12516dd-43cf-4ace-8dc9-14618d2d75e4_919c850652e98031.jpg', null, null);
INSERT INTO `pms_spu_images` VALUES ('80', '11', null, 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/60e65a44-f943-4ed5-87c8-8cf90f403018_d511faab82abb34b.jpg', null, null);
INSERT INTO `pms_spu_images` VALUES ('88', '13', null, 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/1756fa6d-1934-4d5c-8faf-84b3d882fe53_5b5e74d0978360a1.jpg', null, null);
INSERT INTO `pms_spu_images` VALUES ('89', '13', null, 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/299481e9-4704-4824-8b18-60c7d268353c_7ae0120ec27dc3a7.jpg', null, null);
INSERT INTO `pms_spu_images` VALUES ('90', '13', null, 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/462ef293-2b8b-4c53-85f2-1726e14dc976_23cd65077f12f7f5.jpg', null, null);
INSERT INTO `pms_spu_images` VALUES ('91', '13', null, 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/bc4825d6-2a6c-43f8-8d75-5f35b77b9514_a2c208410ae84d1f.jpg', null, null);
INSERT INTO `pms_spu_images` VALUES ('92', '13', null, 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/f968b6ac-2fca-4440-842f-8db8b76478f0_b8494bf281991f94.jpg', null, null);
INSERT INTO `pms_spu_images` VALUES ('93', '13', null, 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/e21777b3-18ac-4580-819e-497c3aa25e4f_6a1b2703a9ed8737.jpg', null, null);
INSERT INTO `pms_spu_images` VALUES ('94', '13', null, 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/2419c5cf-a641-4ec1-8f94-64981dc207f6_63e862164165f483.jpg', null, null);

-- ----------------------------
-- Table structure for pms_spu_info
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_info`;
CREATE TABLE `pms_spu_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `spu_name` varchar(200) DEFAULT NULL COMMENT '商品名稱',
  `spu_description` varchar(1000) DEFAULT NULL COMMENT '商品描述',
  `catalog_id` bigint(20) DEFAULT NULL COMMENT '所屬分類id',
  `brand_id` bigint(20) DEFAULT NULL COMMENT '品牌id',
  `weight` decimal(18,4) DEFAULT NULL,
  `publish_status` tinyint(4) DEFAULT NULL COMMENT '上架狀態[0 - 下架，1 - 上架]',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COMMENT='spu信息';

-- ----------------------------
-- Records of pms_spu_info
-- ----------------------------
INSERT INTO `pms_spu_info` VALUES ('11', '華為 HUAWEI Mate 30 Pro', '華為 HUAWEI Mate 30 Pro', '225', '9', '0.1980', '1', '2019-11-26 10:10:57', '2019-12-15 23:04:16');
INSERT INTO `pms_spu_info` VALUES ('13', ' Apple iPhone 11 (A2223) ', ' Apple iPhone 11 (A2223) ', '225', '12', '0.1940', '1', '2019-11-27 05:37:30', '2019-12-15 23:25:19');

-- ----------------------------
-- Table structure for pms_spu_info_desc
-- ----------------------------
DROP TABLE IF EXISTS `pms_spu_info_desc`;
CREATE TABLE `pms_spu_info_desc` (
  `spu_id` bigint(20) NOT NULL COMMENT '商品id',
  `decript` longtext COMMENT '商品介紹',
  PRIMARY KEY (`spu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='spu信息介紹';

-- ----------------------------
-- Records of pms_spu_info_desc
-- ----------------------------
INSERT INTO `pms_spu_info_desc` VALUES ('11', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/702b76e6-ce3e-4268-8216-a12db5607347_73366cc235d68202.jpg,https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-26/38956d81-5dff-4abd-8ce8-144908e869d8_528211b97272d88a.jpg');
INSERT INTO `pms_spu_info_desc` VALUES ('13', 'https://gulimall-hello.oss-cn-beijing.aliyuncs.com/2019-11-27/ffc5a377-b037-4f26-84a0-df5b1c7cf42d_f205d9c99a2b4b01.jpg');

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
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of undo_log
-- ----------------------------
