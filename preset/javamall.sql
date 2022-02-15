/*
 Navicat Premium Data Transfer

 Source Server         : mysql5.7
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : javamall

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 15/02/2022 12:17:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `sys_captcha`;
CREATE TABLE `sys_captcha`  (
  `uuid` char(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'uuid',
  `code` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '驗證碼',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '過期時間',
  PRIMARY KEY (`uuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系統驗證碼' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_captcha
-- ----------------------------
INSERT INTO `sys_captcha` VALUES ('b6049c5a-36c8-4b86-8b2e-de83b55aa48f', 'a3e7b', '2022-02-12 18:15:03');
INSERT INTO `sys_captcha` VALUES ('fbf92ffd-1b2c-4fe1-8825-4649d3745f61', '8ea47', '2022-02-11 09:56:37');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'key',
  `param_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '狀態   0：隱藏   1：顯示',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '備註',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `param_key`(`param_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系統配置信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '請求方法',
  `params` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '請求參數',
  `time` bigint(20) NOT NULL COMMENT '執行時長(毫秒)',
  `ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '創建時間',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 46 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系統日誌' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (32, 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[5]', 37, '0:0:0:0:0:0:0:1', '2022-02-11 14:39:36');
INSERT INTO `sys_log` VALUES (33, 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[6]', 3, '0:0:0:0:0:0:0:1', '2022-02-11 14:39:42');
INSERT INTO `sys_log` VALUES (34, 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[6]', 3, '0:0:0:0:0:0:0:1', '2022-02-11 14:39:53');
INSERT INTO `sys_log` VALUES (35, 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[6]', 17, '0:0:0:0:0:0:0:1', '2022-02-11 14:40:37');
INSERT INTO `sys_log` VALUES (36, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":65,\"parentId\":46,\"name\":\"首頁推薦\",\"url\":\"product/brand\",\"perms\":\"\",\"type\":1,\"icon\":\"shouye\",\"orderNum\":0}]', 18, '0:0:0:0:0:0:0:1', '2022-02-11 14:57:01');
INSERT INTO `sys_log` VALUES (37, 'admin', '保存菜单', 'io.renren.modules.sys.controller.SysMenuController.save()', '[{\"menuId\":76,\"parentId\":41,\"name\":\"更新sup\",\"url\":\"product/spuupdate\",\"perms\":\"\",\"type\":1,\"icon\":\"\",\"orderNum\":0}]', 22, '0:0:0:0:0:0:0:1', '2022-02-12 19:08:50');
INSERT INTO `sys_log` VALUES (38, 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[76]', 34, '0:0:0:0:0:0:0:1', '2022-02-12 19:13:53');
INSERT INTO `sys_log` VALUES (39, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":65,\"parentId\":46,\"name\":\"首頁推薦\",\"url\":\"product/homeadv\",\"perms\":\"\",\"type\":1,\"icon\":\"shouye\",\"orderNum\":0}]', 14, '0:0:0:0:0:0:0:1', '2022-02-13 17:22:23');
INSERT INTO `sys_log` VALUES (40, 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[27]', 25, '0:0:0:0:0:0:0:1', '2022-02-13 17:30:08');
INSERT INTO `sys_log` VALUES (41, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":45,\"parentId\":0,\"name\":\"會員管理\",\"url\":\"member/member\",\"perms\":\"\",\"type\":1,\"icon\":\"admin\",\"orderNum\":5}]', 10, '0:0:0:0:0:0:0:1', '2022-02-13 17:32:50');
INSERT INTO `sys_log` VALUES (42, 'admin', '删除菜单', 'io.renren.modules.sys.controller.SysMenuController.delete()', '[42]', 14, '0:0:0:0:0:0:0:1', '2022-02-13 17:33:26');
INSERT INTO `sys_log` VALUES (43, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":69,\"parentId\":41,\"name\":\"新增商品\",\"url\":\"product/spuadd\",\"perms\":\"\",\"type\":1,\"icon\":\"bianji\",\"orderNum\":0}]', 20, '0:0:0:0:0:0:0:1', '2022-02-13 21:28:21');
INSERT INTO `sys_log` VALUES (44, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":68,\"parentId\":41,\"name\":\"spu管理\",\"url\":\"product/spu\",\"perms\":\"\",\"type\":1,\"icon\":\"config\",\"orderNum\":1}]', 16, '0:0:0:0:0:0:0:1', '2022-02-13 21:28:33');
INSERT INTO `sys_log` VALUES (45, 'admin', '修改菜单', 'io.renren.modules.sys.controller.SysMenuController.update()', '[{\"menuId\":73,\"parentId\":41,\"name\":\"sku管理\",\"url\":\"product/manager\",\"perms\":\"\",\"type\":1,\"icon\":\"zonghe\",\"orderNum\":2}]', 13, '0:0:0:0:0:0:0:1', '2022-02-13 21:29:15');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NULL DEFAULT NULL COMMENT '父菜單ID，一級菜單為0',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜單名稱',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜單URL',
  `perms` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授權(多個用逗號分隔，如：user:list,user:create)',
  `type` int(11) NULL DEFAULT NULL COMMENT '類型   0：目錄   1：菜單   2：按鈕',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜單圖標',
  `order_num` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 74 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜單管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系統管理', NULL, NULL, 0, 'system', 0);
INSERT INTO `sys_menu` VALUES (2, 1, '管理員列表', 'sys/user', NULL, 1, 'admin', 1);
INSERT INTO `sys_menu` VALUES (3, 1, '角色管理', 'sys/role', NULL, 1, 'role', 2);
INSERT INTO `sys_menu` VALUES (4, 1, '菜單管理', 'sys/menu', NULL, 1, 'menu', 3);
INSERT INTO `sys_menu` VALUES (15, 2, '查看', NULL, 'sys:user:list,sys:user:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (16, 2, '新增', NULL, 'sys:user:save,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (17, 2, '修改', NULL, 'sys:user:update,sys:role:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (18, 2, '刪除', NULL, 'sys:user:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (19, 3, '查看', NULL, 'sys:role:list,sys:role:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (20, 3, '新增', NULL, 'sys:role:save,sys:menu:list', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (21, 3, '修改', NULL, 'sys:role:update,sys:menu:list', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (22, 3, '刪除', NULL, 'sys:role:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (23, 4, '查看', NULL, 'sys:menu:list,sys:menu:info', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (24, 4, '新增', NULL, 'sys:menu:save,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (25, 4, '修改', NULL, 'sys:menu:update,sys:menu:select', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (26, 4, '刪除', NULL, 'sys:menu:delete', 2, NULL, 0);
INSERT INTO `sys_menu` VALUES (29, 1, '系統日誌', 'sys/log', 'sys:log:list', 1, 'log', 7);
INSERT INTO `sys_menu` VALUES (31, 0, '商品系統', '', '', 0, 'editor', 1);
INSERT INTO `sys_menu` VALUES (32, 31, '分類維護', 'product/category', '', 1, 'menu', 2);
INSERT INTO `sys_menu` VALUES (34, 31, '品牌管理', 'product/brand', '', 1, 'editor', 1);
INSERT INTO `sys_menu` VALUES (37, 31, '屬性管理', '', '', 0, 'system', 3);
INSERT INTO `sys_menu` VALUES (38, 37, '屬性分組', 'product/attrgroup', '', 1, 'tubiao', 0);
INSERT INTO `sys_menu` VALUES (39, 37, '規格參數', 'product/baseattr', '', 1, 'log', 0);
INSERT INTO `sys_menu` VALUES (40, 37, '銷售屬性', 'product/saleattr', '', 1, 'zonghe', 0);
INSERT INTO `sys_menu` VALUES (41, 31, '商品維護', 'product/spu', '', 0, 'zonghe', 4);
INSERT INTO `sys_menu` VALUES (43, 0, '庫存系統', '', '', 0, 'shouye', 2);
INSERT INTO `sys_menu` VALUES (44, 0, '訂單系統', '', '', 0, 'config', 3);
INSERT INTO `sys_menu` VALUES (45, 0, '會員管理', 'member/member', '', 1, 'admin', 5);
INSERT INTO `sys_menu` VALUES (46, 0, '內容管理', '', '', 0, 'sousuo', 6);
INSERT INTO `sys_menu` VALUES (54, 43, '庫存工作單', 'ware/task', '', 1, 'log', 0);
INSERT INTO `sys_menu` VALUES (55, 43, '商品庫存', 'ware/sku', '', 1, 'jiesuo', 0);
INSERT INTO `sys_menu` VALUES (56, 44, '訂單查詢', 'order/order', '', 1, 'zhedie', 0);
INSERT INTO `sys_menu` VALUES (57, 44, '退貨單處理', 'order/return', '', 1, 'shanchu', 0);
INSERT INTO `sys_menu` VALUES (59, 44, '支付流水查詢', 'order/payment', '', 1, 'job', 0);
INSERT INTO `sys_menu` VALUES (60, 44, '退款流水查詢', 'order/refund', '', 1, 'mudedi', 0);
INSERT INTO `sys_menu` VALUES (61, 45, '會員列表', 'member/member', '', 1, 'geren', 0);
INSERT INTO `sys_menu` VALUES (65, 46, '首頁推薦', 'product/homeadv', '', 1, 'shouye', 0);
INSERT INTO `sys_menu` VALUES (68, 41, 'spu管理', 'product/spu', '', 1, 'config', 1);
INSERT INTO `sys_menu` VALUES (69, 41, '新增商品', 'product/spuadd', '', 1, 'bianji', 0);
INSERT INTO `sys_menu` VALUES (73, 41, 'sku管理', 'product/manager', '', 1, 'zonghe', 2);

-- ----------------------------
-- Table structure for sys_oss
-- ----------------------------
DROP TABLE IF EXISTS `sys_oss`;
CREATE TABLE `sys_oss`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'URL地址',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '創建時間',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件上傳' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oss
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名稱',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '備註',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '創建者ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '創建時間',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NULL DEFAULT NULL COMMENT '菜單ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色與菜單對應關係' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密碼',
  `salt` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '鹽',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '郵箱',
  `mobile` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手機號',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '狀態  0：禁用   1：正常',
  `create_user_id` bigint(20) NULL DEFAULT NULL COMMENT '創建者ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '創建時間',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系統用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '9ec9750e709431dad22365cabc5c625482e574c74adaebba7dd02f1129e4ce1d', 'YzcmCZNvbXocrsz9dm8e', 'root@renren.io', '13612345678', 1, 1, '2016-11-11 11:11:11');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) NULL DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户與角色對應關係' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_token
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_token`;
CREATE TABLE `sys_user_token`  (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'token',
  `expire_time` datetime(0) NULL DEFAULT NULL COMMENT '過期時間',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新時間',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `token`(`token`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系統用户Token' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_token
-- ----------------------------
INSERT INTO `sys_user_token` VALUES (1, 'ca4587772628e4539ecef60076442dfd', '2022-02-15 23:03:36', '2022-02-15 11:03:36');

-- ----------------------------
-- Table structure for t_cart
-- ----------------------------
DROP TABLE IF EXISTS `t_cart`;
CREATE TABLE `t_cart`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '商品sku編號',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT 'order_id',
  `sku_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品sku名字',
  `sku_title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'order_sn',
  `sku_pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品sku圖片',
  `sku_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品sku價格',
  `sku_attrs_vals` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品銷售屬性組合（JSON）',
  `count` int(11) NULL DEFAULT NULL COMMENT '商品購買的數量',
  `checked` bit(20) NULL DEFAULT NULL COMMENT 'spu_id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '訂單項信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_cart
-- ----------------------------
INSERT INTO `t_cart` VALUES (2, 31, 3, NULL, 'Samsung Galaxy S21 星魅銀', 'http://localhost:9000/javamall/20220214221012%2B0800silver.jpg', 30998.0000, '顏色：星魅銀', 1, b'00000000000000000001');

-- ----------------------------
-- Table structure for t_member
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `level_id` bigint(20) NULL DEFAULT NULL COMMENT '會員等級id',
  `username` char(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密碼',
  `nickname` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '暱稱',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手機號碼',
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '郵箱',
  `header` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '頭像',
  `gender` tinyint(4) NULL DEFAULT NULL COMMENT '性別',
  `birth` date NULL DEFAULT NULL COMMENT '生日',
  `city` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在城市',
  `job` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '職業',
  `sign` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '個性簽名',
  `source_type` tinyint(4) NULL DEFAULT NULL COMMENT '用户來源',
  `integration` int(11) NULL DEFAULT NULL COMMENT '積分',
  `growth` int(11) NULL DEFAULT NULL COMMENT '成長值',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '啓用狀態',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '註冊時間',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '會員' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_member
-- ----------------------------
INSERT INTO `t_member` VALUES (2, NULL, 'admin', 'admin', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `t_member` VALUES (3, 1, 'malladmin', '$2a$10$0PClEFduGr6DsiYgm/..tezRIcwjH.XUBDQHcfQpWcM2mK5/ekrTG', 'malladmin', 'yoziming@gmail.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-02-11 11:07:13');

-- ----------------------------
-- Table structure for t_member_level
-- ----------------------------
DROP TABLE IF EXISTS `t_member_level`;
CREATE TABLE `t_member_level`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '等級名稱',
  `growth_point` int(11) NULL DEFAULT NULL COMMENT '等級需要的成長值',
  `default_status` tinyint(4) NULL DEFAULT NULL COMMENT '是否為默認等級[0->不是；1->是]',
  `free_freight_point` decimal(18, 4) NULL DEFAULT NULL COMMENT '免運費標準',
  `comment_growth_point` int(11) NULL DEFAULT NULL COMMENT '每次評價獲取的成長值',
  `priviledge_free_freight` tinyint(4) NULL DEFAULT NULL COMMENT '是否有免郵特權',
  `priviledge_member_price` tinyint(4) NULL DEFAULT NULL COMMENT '是否有會員價格特權',
  `priviledge_birthday` tinyint(4) NULL DEFAULT NULL COMMENT '是否有生日特權',
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '備註',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '會員等級' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_member_level
-- ----------------------------

-- ----------------------------
-- Table structure for t_member_receive_address
-- ----------------------------
DROP TABLE IF EXISTS `t_member_receive_address`;
CREATE TABLE `t_member_receive_address`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT 'member_id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收貨人姓名',
  `phone` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '電話',
  `post_code` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '郵政編碼',
  `province` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份/直轄市',
  `city` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市',
  `region` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '區',
  `detail_address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '詳細地址(街道)',
  `areacode` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省市區代碼',
  `default_status` tinyint(1) NULL DEFAULT NULL COMMENT '是否默認',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '會員收貨地址' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_member_receive_address
-- ----------------------------
INSERT INTO `t_member_receive_address` VALUES (6, 3, '王大明', '0912345678', NULL, '台北市 ', '天龍區 ', 'XX里 ', 'OO路', NULL, 1);

-- ----------------------------
-- Table structure for t_order
-- ----------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` bigint(20) NULL DEFAULT NULL COMMENT 'member_id',
  `order_sn` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '訂單號',
  `coupon_id` bigint(20) NULL DEFAULT NULL COMMENT '使用的優惠券',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'create_time',
  `member_username` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `total_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '訂單總額',
  `pay_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '應付總額',
  `freight_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '運費金額',
  `promotion_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '促銷優化金額（促銷價、滿減、階梯價）',
  `integration_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '積分抵扣金額',
  `coupon_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '優惠券抵扣金額',
  `discount_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '後台調整訂單使用的折扣金額',
  `pay_type` tinyint(4) NULL DEFAULT NULL COMMENT '支付方式【1->支付寶；2->微信；3->銀聯； 4->貨到付款；】',
  `source_type` tinyint(4) NULL DEFAULT NULL COMMENT '訂單來源[0->PC訂單；1->app訂單]',
  `status` tinyint(4) NULL DEFAULT NULL COMMENT '訂單狀態【0->待付款；1->待發貨；2->已發貨；3->已完成；4->已關閉；5->無效訂單】',
  `delivery_company` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物流公司(配送方式)',
  `delivery_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物流單號',
  `auto_confirm_day` int(11) NULL DEFAULT NULL COMMENT '自動確認時間（天）',
  `integration` int(11) NULL DEFAULT NULL COMMENT '可以獲得的積分',
  `growth` int(11) NULL DEFAULT NULL COMMENT '可以獲得的成長值',
  `bill_type` tinyint(4) NULL DEFAULT NULL COMMENT '發票類型[0->不開發票；1->電子發票；2->紙質發票]',
  `bill_header` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '發票抬頭',
  `bill_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '發票內容',
  `bill_receiver_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收票人電話',
  `bill_receiver_email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收票人郵箱',
  `receiver_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收貨人姓名',
  `receiver_phone` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收貨人電話',
  `receiver_post_code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收貨人郵編',
  `receiver_province` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '省份/直轄市',
  `receiver_city` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '城市',
  `receiver_region` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '區',
  `receiver_detail_address` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '詳細地址',
  `note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '訂單備註',
  `confirm_status` tinyint(4) NULL DEFAULT NULL COMMENT '確認收貨狀態[0->未確認；1->已確認]',
  `delete_status` tinyint(4) NULL DEFAULT NULL COMMENT '刪除狀態【0->未刪除；1->已刪除】',
  `use_integration` int(11) NULL DEFAULT NULL COMMENT '下單時使用的積分',
  `payment_time` datetime(0) NULL DEFAULT NULL COMMENT '支付時間',
  `delivery_time` datetime(0) NULL DEFAULT NULL COMMENT '發貨時間',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '確認收貨時間',
  `comment_time` datetime(0) NULL DEFAULT NULL COMMENT '評價時間',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改時間',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '訂單' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order
-- ----------------------------
INSERT INTO `t_order` VALUES (16, 3, '202202142258129591493238151345352705', NULL, '2022-02-14 22:58:13', 'malladmin', 30998.0000, 30998.0000, 0.0000, 0.0000, 0.0000, 0.0000, NULL, NULL, NULL, 2, '黑貓快遞', 'abc123', 7, 30998, 30998, NULL, NULL, NULL, NULL, NULL, '王大明', '0912345678', NULL, '台北市 ', '天龍區 ', 'XX里 ', 'OO路', NULL, NULL, 0, NULL, NULL, '2022-02-15 11:06:58', NULL, NULL, '2022-02-15 11:06:58');

-- ----------------------------
-- Table structure for t_order_item
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item`;
CREATE TABLE `t_order_item`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'order_id',
  `order_sn` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'order_sn',
  `spu_id` bigint(20) NULL DEFAULT NULL COMMENT 'spu_id',
  `spu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'spu_name',
  `spu_pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'spu_pic',
  `spu_brand` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '品牌',
  `category_id` bigint(20) NULL DEFAULT NULL COMMENT '商品分類id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '商品sku編號',
  `sku_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品sku名字',
  `sku_pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品sku圖片',
  `sku_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品sku價格',
  `sku_quantity` int(11) NULL DEFAULT NULL COMMENT '商品購買的數量',
  `sku_attrs_vals` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品銷售屬性組合（JSON）',
  `promotion_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品促銷分解金額',
  `coupon_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '優惠券優惠分解金額',
  `integration_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '積分優惠分解金額',
  `real_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '該商品經過優惠後的分解金額',
  `gift_integration` int(11) NULL DEFAULT NULL COMMENT '贈送積分',
  `gift_growth` int(11) NULL DEFAULT NULL COMMENT '贈送成長值',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '訂單項信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order_item
-- ----------------------------
INSERT INTO `t_order_item` VALUES (1, NULL, '202202122058313741492483253859323906', 1, 'iPhone 13 pro', NULL, '1', 225, 1, 'iPhone 13 pro 黑', 'http://localhost:9000/javamall/20220212184254%2B0800iphone-13-pro-blue-select.jpg', 32900.0000, 3, '顏色：黑', 0.0000, 0.0000, 0.0000, 98700.0000, 98700, 98700);
INSERT INTO `t_order_item` VALUES (2, NULL, '202202122113111271492486943756267522', 1, 'iPhone 13 pro', NULL, '1', 225, 1, 'iPhone 13 pro 黑', 'http://localhost:9000/javamall/20220212184254%2B0800iphone-13-pro-blue-select.jpg', 32900.0000, 2, '顏色：黑', 0.0000, 0.0000, 0.0000, 65800.0000, 65800, 65800);
INSERT INTO `t_order_item` VALUES (3, NULL, '202202122148406651492495875740581889', 1, 'iPhone 13 pro', NULL, '1', 225, 1, 'iPhone 13 pro 黑', 'http://localhost:9000/javamall/20220212184254%2B0800iphone-13-pro-blue-select.jpg', 32900.0000, 2, '顏色：黑', 0.0000, 0.0000, 0.0000, 65800.0000, 65800, 65800);
INSERT INTO `t_order_item` VALUES (4, NULL, '202202122149061491492495982561116162', 1, 'iPhone 13 pro', NULL, '1', 225, 1, 'iPhone 13 pro 黑', 'http://localhost:9000/javamall/20220212184254%2B0800iphone-13-pro-blue-select.jpg', 32900.0000, 2, '顏色：黑', 0.0000, 0.0000, 0.0000, 65800.0000, 65800, 65800);
INSERT INTO `t_order_item` VALUES (5, NULL, '202202122156235801492497817346793473', 1, 'iPhone 13 pro', NULL, '1', 225, 1, 'iPhone 13 pro 黑', 'http://localhost:9000/javamall/20220212184254%2B0800iphone-13-pro-blue-select.jpg', 32900.0000, 2, '顏色：黑', 0.0000, 0.0000, 0.0000, 65800.0000, 65800, 65800);
INSERT INTO `t_order_item` VALUES (6, NULL, '202202131640076501492780614401675266', 1, 'iPhone 13 pro', NULL, '1', 225, 1, 'iPhone 13 pro 黑', 'http://localhost:9000/javamall/20220212184254%2B0800iphone-13-pro-blue-select.jpg', 32900.0000, 2, '顏色：黑', 0.0000, 0.0000, 0.0000, 65800.0000, 65800, 65800);
INSERT INTO `t_order_item` VALUES (7, NULL, '202202131640447461492780769951633409', 1, 'iPhone 13 pro', NULL, '1', 225, 1, 'iPhone 13 pro 黑', 'http://localhost:9000/javamall/20220212184254%2B0800iphone-13-pro-blue-select.jpg', 32900.0000, 2, '顏色：黑', 0.0000, 0.0000, 0.0000, 65800.0000, 65800, 65800);
INSERT INTO `t_order_item` VALUES (8, NULL, '202202131644007261492781591989108738', 1, 'iPhone 13 pro', NULL, '1', 225, 1, 'iPhone 13 pro 黑', 'http://localhost:9000/javamall/20220212184254%2B0800iphone-13-pro-blue-select.jpg', 32900.0000, 2, '顏色：黑', 0.0000, 0.0000, 0.0000, 65800.0000, 65800, 65800);
INSERT INTO `t_order_item` VALUES (9, NULL, '202202131651483031492783553149263874', 1, 'iPhone 13 pro', NULL, '1', 225, 1, 'iPhone 13 pro 黑', 'http://localhost:9000/javamall/20220212184254%2B0800iphone-13-pro-blue-select.jpg', 32900.0000, 2, '顏色：黑', 0.0000, 0.0000, 0.0000, 65800.0000, 65800, 65800);
INSERT INTO `t_order_item` VALUES (10, NULL, '202202131655044091492784375673167874', 1, 'iPhone 13 pro', NULL, '1', 225, 1, 'iPhone 13 pro 黑', 'http://localhost:9000/javamall/20220212184254%2B0800iphone-13-pro-blue-select.jpg', 32900.0000, 2, '顏色：黑', 0.0000, 0.0000, 0.0000, 65800.0000, 65800, 65800);
INSERT INTO `t_order_item` VALUES (11, NULL, '202202131659116791492785412798685186', 1, 'iPhone 13 pro', NULL, '1', 225, 1, 'iPhone 13 pro 黑', 'http://localhost:9000/javamall/20220212184254%2B0800iphone-13-pro-blue-select.jpg', 32900.0000, 2, '顏色：黑', 0.0000, 0.0000, 0.0000, 65800.0000, 65800, 65800);
INSERT INTO `t_order_item` VALUES (13, NULL, '202202131659449951492785552477396994', 1, 'iPhone 13 pro', NULL, '1', 225, 1, 'iPhone 13 pro 黑', 'http://localhost:9000/javamall/20220212184254%2B0800iphone-13-pro-blue-select.jpg', 32900.0000, 2, '顏色：黑', 0.0000, 0.0000, 0.0000, 65800.0000, 65800, 65800);
INSERT INTO `t_order_item` VALUES (14, NULL, '202202131802496701492801426550689793', 1, 'iPhone 13 pro', NULL, '1', 225, 1, 'iPhone 13 pro 黑', 'http://localhost:9000/javamall/20220212184254%2B0800iphone-13-pro-blue-select.jpg', 32900.0000, 2, '顏色：黑', 0.0000, 0.0000, 0.0000, 65800.0000, 65800, 65800);
INSERT INTO `t_order_item` VALUES (15, NULL, '202202131804011131492801726242099202', 1, 'iPhone 13 pro', NULL, '1', 225, 1, 'iPhone 13 pro 黑', 'http://localhost:9000/javamall/20220212184254%2B0800iphone-13-pro-blue-select.jpg', 32900.0000, 2, '顏色：黑', 0.0000, 0.0000, 0.0000, 65800.0000, 65800, 65800);
INSERT INTO `t_order_item` VALUES (16, NULL, '202202142258129591493238151345352705', 13, 'Samsung Galaxy S21', NULL, '4', 225, 31, 'Samsung Galaxy S21 星魅銀', 'http://localhost:9000/javamall/20220214221012%2B0800silver.jpg', 30998.0000, 1, '顏色：星魅銀', 0.0000, 0.0000, 0.0000, 30998.0000, 30998, 30998);

-- ----------------------------
-- Table structure for t_order_payment_info
-- ----------------------------
DROP TABLE IF EXISTS `t_order_payment_info`;
CREATE TABLE `t_order_payment_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_sn` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '訂單號（對外業務號）',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '訂單id',
  `alipay_trade_no` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付寶交易流水號',
  `total_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '支付總金額',
  `subject` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '交易內容',
  `payment_status` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '支付狀態',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '創建時間',
  `confirm_time` datetime(0) NULL DEFAULT NULL COMMENT '確認時間',
  `callback_content` varchar(4000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '回調內容',
  `callback_time` datetime(0) NULL DEFAULT NULL COMMENT '回調時間',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '支付信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order_payment_info
-- ----------------------------
INSERT INTO `t_order_payment_info` VALUES (1, '202202131640076501492780614401675266', NULL, NULL, NULL, NULL, 'TRADE_SUCCESS', '2022-02-13 16:40:11', NULL, NULL, '2022-02-13 16:40:11');
INSERT INTO `t_order_payment_info` VALUES (2, '202202131640447461492780769951633409', NULL, NULL, NULL, NULL, 'TRADE_SUCCESS', '2022-02-13 16:40:49', NULL, NULL, '2022-02-13 16:40:49');
INSERT INTO `t_order_payment_info` VALUES (3, '202202131640447461492780769951633409', NULL, NULL, NULL, NULL, 'TRADE_SUCCESS', '2022-02-13 16:40:53', NULL, NULL, '2022-02-13 16:40:53');
INSERT INTO `t_order_payment_info` VALUES (4, '202202131640447461492780769951633409', NULL, NULL, NULL, NULL, 'TRADE_SUCCESS', '2022-02-13 16:40:57', NULL, NULL, '2022-02-13 16:40:57');
INSERT INTO `t_order_payment_info` VALUES (5, '202202131644007261492781591989108738', NULL, NULL, NULL, NULL, 'TRADE_SUCCESS', '2022-02-13 16:44:03', NULL, NULL, '2022-02-13 16:44:03');
INSERT INTO `t_order_payment_info` VALUES (6, '202202131651483031492783553149263874', NULL, NULL, NULL, NULL, 'TRADE_SUCCESS', '2022-02-13 16:51:51', NULL, NULL, '2022-02-13 16:51:51');
INSERT INTO `t_order_payment_info` VALUES (7, '202202131655044091492784375673167874', NULL, NULL, NULL, NULL, 'TRADE_SUCCESS', '2022-02-13 16:55:06', NULL, NULL, '2022-02-13 16:55:06');
INSERT INTO `t_order_payment_info` VALUES (8, '202202131659116791492785412798685186', NULL, NULL, NULL, NULL, 'TRADE_SUCCESS', '2022-02-13 16:59:14', NULL, NULL, '2022-02-13 16:59:14');
INSERT INTO `t_order_payment_info` VALUES (9, '202202131659449951492785552477396994', NULL, NULL, NULL, NULL, 'TRADE_SUCCESS', '2022-02-13 16:59:57', NULL, NULL, '2022-02-13 16:59:57');
INSERT INTO `t_order_payment_info` VALUES (10, '202202131802496701492801426550689793', NULL, NULL, NULL, NULL, 'TRADE_SUCCESS', '2022-02-13 18:02:54', NULL, NULL, '2022-02-13 18:02:54');
INSERT INTO `t_order_payment_info` VALUES (11, '202202131804011131492801726242099202', NULL, NULL, NULL, NULL, 'TRADE_SUCCESS', '2022-02-13 18:04:11', NULL, NULL, '2022-02-13 18:04:11');
INSERT INTO `t_order_payment_info` VALUES (12, '202202142258129591493238151345352705', NULL, NULL, NULL, NULL, 'TRADE_SUCCESS', '2022-02-14 22:58:19', NULL, NULL, '2022-02-14 22:58:19');

-- ----------------------------
-- Table structure for t_order_refund_info
-- ----------------------------
DROP TABLE IF EXISTS `t_order_refund_info`;
CREATE TABLE `t_order_refund_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_return_id` bigint(20) NULL DEFAULT NULL COMMENT '退款的訂單',
  `refund` decimal(18, 4) NULL DEFAULT NULL COMMENT '退款金額',
  `refund_sn` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退款交易流水號',
  `refund_status` tinyint(1) NULL DEFAULT NULL COMMENT '退款狀態',
  `refund_channel` tinyint(4) NULL DEFAULT NULL COMMENT '退款渠道[1-支付寶，2-微信，3-銀聯，4-匯款]',
  `refund_content` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '退款信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order_refund_info
-- ----------------------------

-- ----------------------------
-- Table structure for t_order_return_apply
-- ----------------------------
DROP TABLE IF EXISTS `t_order_return_apply`;
CREATE TABLE `t_order_return_apply`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'order_id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '退貨商品id',
  `order_sn` char(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '訂單編號',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '申請時間',
  `member_username` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '會員用户名',
  `return_amount` decimal(18, 4) NULL DEFAULT NULL COMMENT '退款金額',
  `return_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退貨人姓名',
  `return_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退貨人電話',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '申請狀態[0->待處理；1->退貨中；2->已完成；3->已拒絕]',
  `handle_time` datetime(0) NULL DEFAULT NULL COMMENT '處理時間',
  `sku_img` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品圖片',
  `sku_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品名稱',
  `sku_brand` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品品牌',
  `sku_attrs_vals` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '商品銷售屬性(JSON)',
  `sku_count` int(11) NULL DEFAULT NULL COMMENT '退貨數量',
  `sku_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品單價',
  `sku_real_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '商品實際支付單價',
  `reason` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '原因',
  `description` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `desc_pics` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '憑證圖片，以逗號隔開',
  `handle_note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '處理備註',
  `handle_man` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '處理人員',
  `receive_man` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收貨人',
  `receive_time` datetime(0) NULL DEFAULT NULL COMMENT '收貨時間',
  `receive_note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收貨備註',
  `receive_phone` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收貨電話',
  `company_address` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '公司收貨地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '訂單退貨申請' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_order_return_apply
-- ----------------------------
INSERT INTO `t_order_return_apply` VALUES (1, 1, NULL, '202202122058313741492483253859323906', '2022-02-13 17:11:41', 'malladmin', 50.0000, NULL, NULL, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for t_product_attr
-- ----------------------------
DROP TABLE IF EXISTS `t_product_attr`;
CREATE TABLE `t_product_attr`  (
  `attr_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '屬性id',
  `attr_name` char(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '屬性名',
  `search_type` tinyint(4) NULL DEFAULT NULL COMMENT '是否需要檢索[0-不需要，1-需要]',
  `value_type` tinyint(4) NULL DEFAULT NULL COMMENT '值類型[0-為單個值，1-可以選擇多個值]',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '屬性圖標',
  `value_select` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '可選值列表[用逗號分隔]',
  `attr_type` tinyint(4) NULL DEFAULT NULL COMMENT '屬性類型[0-銷售屬性，1-基本屬性',
  `enable` bigint(20) NULL DEFAULT NULL COMMENT '啓用狀態[0 - 禁用，1 - 啓用]',
  `catalog_id` bigint(20) NULL DEFAULT NULL COMMENT '所屬分類',
  `show_desc` tinyint(4) NULL DEFAULT NULL COMMENT '快速展示【是否展示在介紹上；0-否 1-是】，在sku中仍然可以調整',
  PRIMARY KEY (`attr_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品屬性' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product_attr
-- ----------------------------
INSERT INTO `t_product_attr` VALUES (1, '螢幕尺寸', 1, 0, 'a', '5.7吋;8吋以上;7吋;6.7吋', 1, 1, 225, 1);
INSERT INTO `t_product_attr` VALUES (2, '重量', 1, 0, 'a', '150g;200g', 1, 1, 225, 1);
INSERT INTO `t_product_attr` VALUES (4, '顏色', 0, 0, 'a', '黑;白;土豪金', 0, 1, 225, 0);
INSERT INTO `t_product_attr` VALUES (5, '4G上網', 1, 0, 'a', 'YES', 1, 1, 225, 1);
INSERT INTO `t_product_attr` VALUES (6, '5G上網', 1, 0, 'a', 'YES;NO', 1, 1, 225, 1);
INSERT INTO `t_product_attr` VALUES (7, '尺寸', 1, 0, 'a', 'S;M;L', 0, 1, 650, 1);
INSERT INTO `t_product_attr` VALUES (8, '顏色', 1, 0, 'a', '粉紅;藍;白', 0, 1, 650, 1);
INSERT INTO `t_product_attr` VALUES (9, '材質', 1, 0, 'a', '布;棉麻', 1, 1, 650, 1);

-- ----------------------------
-- Table structure for t_product_attr_attrgroup_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_product_attr_attrgroup_relation`;
CREATE TABLE `t_product_attr_attrgroup_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `attr_id` bigint(20) NULL DEFAULT NULL COMMENT '屬性id',
  `attr_group_id` bigint(20) NULL DEFAULT NULL COMMENT '屬性分組id',
  `attr_sort` int(11) NULL DEFAULT NULL COMMENT '屬性組內排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '屬性&屬性分組關聯' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product_attr_attrgroup_relation
-- ----------------------------
INSERT INTO `t_product_attr_attrgroup_relation` VALUES (1, 1, 1, NULL);
INSERT INTO `t_product_attr_attrgroup_relation` VALUES (2, 2, 1, NULL);
INSERT INTO `t_product_attr_attrgroup_relation` VALUES (3, 3, 1, NULL);
INSERT INTO `t_product_attr_attrgroup_relation` VALUES (4, 5, 2, NULL);
INSERT INTO `t_product_attr_attrgroup_relation` VALUES (5, 6, 2, NULL);
INSERT INTO `t_product_attr_attrgroup_relation` VALUES (8, 9, 4, NULL);

-- ----------------------------
-- Table structure for t_product_attr_group
-- ----------------------------
DROP TABLE IF EXISTS `t_product_attr_group`;
CREATE TABLE `t_product_attr_group`  (
  `attr_group_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分組id',
  `attr_group_name` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '組名',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `descript` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '組圖標',
  `catalog_id` bigint(20) NULL DEFAULT NULL COMMENT '所屬分類id',
  PRIMARY KEY (`attr_group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '屬性分組' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product_attr_group
-- ----------------------------
INSERT INTO `t_product_attr_group` VALUES (1, '基本屬性', 0, '手機基本屬性', 'a', 225);
INSERT INTO `t_product_attr_group` VALUES (2, '上網', 2, '上網功能', 'a', 225);
INSERT INTO `t_product_attr_group` VALUES (4, '基本屬性', 0, '衣物屬性', 'a', 650);

-- ----------------------------
-- Table structure for t_product_attr_value
-- ----------------------------
DROP TABLE IF EXISTS `t_product_attr_value`;
CREATE TABLE `t_product_attr_value`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `spu_id` bigint(20) NULL DEFAULT NULL COMMENT '商品id',
  `attr_id` bigint(20) NULL DEFAULT NULL COMMENT '屬性id',
  `attr_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '屬性名',
  `attr_value` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '屬性值',
  `attr_sort` int(11) NULL DEFAULT NULL COMMENT '順序',
  `quick_show` tinyint(4) NULL DEFAULT NULL COMMENT '快速展示【是否展示在介紹上；0-否 1-是】',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'spu屬性值' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product_attr_value
-- ----------------------------
INSERT INTO `t_product_attr_value` VALUES (7, 4, 1, '螢幕尺寸', '6', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (8, 4, 2, '重量', '150g', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (9, 4, 3, '上網頻段', '4G', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (10, 5, 1, '螢幕尺寸', '6', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (11, 5, 2, '重量', '150g', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (12, 5, 3, '上網頻段', '4G', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (13, 1, 1, '螢幕尺寸', '5.7吋', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (14, 1, 2, '重量', '200g', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (15, 1, 5, '4G上網', 'YES', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (16, 1, 6, '5G上網', 'YES', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (21, 2, 1, '螢幕尺寸', '7吋', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (22, 2, 2, '重量', '200g', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (23, 2, 5, '4G上網', 'YES', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (24, 2, 6, '5G上網', 'NO', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (25, 7, 1, '螢幕尺寸', '5.7吋', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (26, 8, 1, '螢幕尺寸', '5.7吋', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (27, 8, 2, '重量', '150g', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (28, 9, 1, '螢幕尺寸', '5.7吋', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (29, 11, 9, '材質', '棉麻', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (30, 12, 1, '螢幕尺寸', '6.7吋', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (31, 12, 2, '重量', '200g', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (32, 12, 5, '4G上網', 'YES', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (33, 12, 6, '5G上網', 'YES', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (34, 13, 1, '螢幕尺寸', '6.7吋', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (35, 13, 2, '重量', '199g', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (36, 13, 5, '4G上網', 'YES', NULL, 1);
INSERT INTO `t_product_attr_value` VALUES (37, 13, 6, '5G上網', 'YES', NULL, 1);

-- ----------------------------
-- Table structure for t_product_brand
-- ----------------------------
DROP TABLE IF EXISTS `t_product_brand`;
CREATE TABLE `t_product_brand`  (
  `brand_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '品牌id',
  `name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '品牌名',
  `logo` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '品牌logo地址',
  `descript` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '介紹',
  `show_status` tinyint(4) NULL DEFAULT NULL COMMENT '顯示狀態[0-不顯示；1-顯示]',
  `first_letter` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '檢索首字母',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`brand_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '品牌' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product_brand
-- ----------------------------
INSERT INTO `t_product_brand` VALUES (1, 'apple', 'http://localhost:9000/javamall/20220212181122%2B0800apple.png', 'apple', 1, 'a', 0);
INSERT INTO `t_product_brand` VALUES (2, '小米', 'http://localhost:9000/javamall/20220212185010%2B0800%E5%B0%8F%E7%B1%B3.png', '小米', 1, 'm', 0);
INSERT INTO `t_product_brand` VALUES (3, '自有品牌', 'http://localhost:9000/javamall/20220213214529%2B0800own.jpg', '自有品牌', 1, '', 0);
INSERT INTO `t_product_brand` VALUES (4, 'SAMSUNG三星', 'http://localhost:9000/javamall/20220214215911%2B0800220214_215904.png', '三星', 1, 's', 0);

-- ----------------------------
-- Table structure for t_product_category
-- ----------------------------
DROP TABLE IF EXISTS `t_product_category`;
CREATE TABLE `t_product_category`  (
  `cat_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分類id',
  `name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分類名稱',
  `parent_cid` bigint(20) NULL DEFAULT NULL COMMENT '父分類id',
  `cat_level` int(11) NULL DEFAULT NULL COMMENT '層級',
  `show_status` tinyint(4) NULL DEFAULT NULL COMMENT '是否顯示[0-不顯示，1顯示]',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `icon` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '圖標地址',
  `product_unit` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '計量單位',
  `product_count` int(11) NULL DEFAULT NULL COMMENT '商品數量',
  PRIMARY KEY (`cat_id`) USING BTREE,
  INDEX `parent_cid`(`parent_cid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1434 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '商品三級分類' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product_category
-- ----------------------------
INSERT INTO `t_product_category` VALUES (1, '圖書、音像、電子書刊', 0, 1, 1, 1, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (2, '手機', 0, 1, 1, 0, '111q', NULL, 0);
INSERT INTO `t_product_category` VALUES (3, '家用電器', 0, 1, 1, 2, 'aaa', NULL, 0);
INSERT INTO `t_product_category` VALUES (4, '數位科技', 0, 1, 1, 3, 'aaa', NULL, 0);
INSERT INTO `t_product_category` VALUES (5, '家居家裝', 0, 1, 1, 4, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (6, '電腦辦公', 0, 1, 1, 5, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (7, '廚具', 0, 1, 1, 6, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (8, '個護化妝', 0, 1, 1, 7, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (9, '服飾內衣', 0, 1, 1, 8, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (10, '鐘錶', 0, 1, 1, 9, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (11, '鞋靴', 0, 1, 1, 10, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (12, '母嬰', 0, 1, 1, 11, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (13, '禮品箱包', 0, 1, 1, 12, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (14, '食品飲料、保健食品', 0, 1, 1, 13, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (16, '汽車用品', 0, 1, 1, 15, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (17, '運動健康', 0, 1, 1, 16, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (18, '玩具樂器', 0, 1, 1, 17, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (22, '電子書刊', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (23, '音像', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (24, '英文原版', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (25, '文藝', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (26, '少兒', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (27, '人文社科', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (28, '經管勵志', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (29, '生活', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (30, '科技', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (31, '教育', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (32, '港台圖書', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (33, '其他', 1, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (34, '手機通訊', 2, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (36, '手機配件', 2, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (37, '大 家 電', 3, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (38, '廚衞大電', 3, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (39, '廚房小電', 3, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (40, '生活電器', 3, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (41, '個護健康', 3, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (42, '五金家裝', 3, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (43, '攝影攝像', 4, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (44, '相機配件', 4, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (45, '智能設備', 4, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (46, '影音娛樂', 4, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (47, '電子教育', 4, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (48, '虛擬商品', 4, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (49, '家紡', 5, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (50, '燈具', 5, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (51, '生活日用', 5, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (52, '家裝軟飾', 5, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (53, '寵物生活', 5, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (54, '電腦整機', 6, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (55, '電腦配件', 6, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (56, '外設產品', 6, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (57, '遊戲設備', 6, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (58, '網絡產品', 6, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (59, '辦公設備', 6, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (60, '文具/耗材', 6, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (61, '服務產品', 6, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (62, '烹飪鍋具', 7, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (63, '刀剪菜板', 7, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (64, '廚房配件', 7, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (65, '水具酒具', 7, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (66, '餐具', 7, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (67, '酒店用品', 7, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (68, '茶具/咖啡具', 7, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (69, '清潔用品', 8, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (70, '面部護膚', 8, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (71, '身體護理', 8, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (72, '口腔護理', 8, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (73, '女性護理', 8, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (74, '洗髮護髮', 8, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (75, '香水彩妝', 8, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (76, '女裝', 9, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (77, '男裝', 9, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (78, '內衣', 9, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (79, '洗衣服務', 9, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (80, '服飾配件', 9, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (81, '鐘錶', 10, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (82, '流行男鞋', 11, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (83, '時尚女鞋', 11, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (84, '奶粉', 12, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (85, '營養輔食', 12, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (86, '尿褲濕巾', 12, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (87, '餵養用品', 12, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (88, '洗護用品', 12, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (89, '童車童牀', 12, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (90, '寢居服飾', 12, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (91, '媽媽專區', 12, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (92, '童裝童鞋', 12, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (93, '安全座椅', 12, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (94, '潮流女包', 13, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (95, '精品男包', 13, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (96, '功能箱包', 13, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (97, '禮品', 13, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (98, '奢侈品', 13, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (99, '婚慶', 13, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (100, '進口食品', 14, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (101, '地方特產', 14, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (102, '休閒食品', 14, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (103, '糧油調味', 14, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (104, '飲料衝調', 14, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (105, '食品禮券', 14, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (106, '茗茶', 14, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (119, '維修保養', 16, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (120, '車載電器', 16, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (121, '美容清洗', 16, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (122, '汽車裝飾', 16, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (123, '安全自駕', 16, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (124, '汽車服務', 16, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (125, '賽事改裝', 16, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (126, '運動鞋包', 17, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (127, '運動服飾', 17, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (128, '騎行運動', 17, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (129, '垂釣用品', 17, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (130, '游泳用品', 17, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (131, '户外鞋服', 17, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (132, '户外裝備', 17, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (133, '健身訓練', 17, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (134, '體育用品', 17, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (135, '適用年齡', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (136, '遙控/電動', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (137, '毛絨布藝', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (138, '娃娃玩具', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (139, '模型玩具', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (140, '健身玩具', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (141, '動漫玩具', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (142, '益智玩具', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (143, '積木拼插', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (144, 'DIY玩具', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (145, '創意減壓', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (146, '樂器', 18, 2, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (165, '電子書', 22, 3, 1, 1, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (166, '網絡原創', 22, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (167, '數字雜誌', 22, 3, 1, 2, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (168, '多媒體圖書', 22, 3, 1, 3, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (169, '音樂', 23, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (170, '影視', 23, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (171, '教育音像', 23, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (172, '少兒', 24, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (173, '商務投資', 24, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (174, '英語學習與考試', 24, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (175, '文學', 24, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (176, '傳記', 24, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (177, '勵志', 24, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (178, '小説', 25, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (179, '文學', 25, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (180, '青春文學', 25, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (181, '傳記', 25, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (182, '藝術', 25, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (183, '少兒', 26, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (184, '0-2歲', 26, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (185, '3-6歲', 26, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (186, '7-10歲', 26, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (187, '11-14歲', 26, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (188, '歷史', 27, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (189, '哲學', 27, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (190, '國學', 27, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (191, '政治/軍事', 27, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (192, '法律', 27, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (193, '人文社科', 27, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (194, '心理學', 27, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (195, '文化', 27, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (196, '社會科學', 27, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (197, '經濟', 28, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (198, '金融與投資', 28, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (199, '管理', 28, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (200, '勵志與成功', 28, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (201, '生活', 29, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (202, '健身與保健', 29, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (203, '家庭與育兒', 29, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (204, '旅遊', 29, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (205, '烹飪美食', 29, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (206, '工業技術', 30, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (207, '科普讀物', 30, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (208, '建築', 30, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (209, '醫學', 30, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (210, '科學與自然', 30, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (211, '計算機與互聯網', 30, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (212, '電子通信', 30, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (213, '中小學教輔', 31, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (214, '教育與考試', 31, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (215, '外語學習', 31, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (216, '大中專教材', 31, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (217, '字典詞典', 31, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (218, '藝術/設計/收藏', 32, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (219, '經濟管理', 32, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (220, '文化/學術', 32, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (221, '少兒', 32, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (222, '工具書', 33, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (223, '雜誌/期刊', 33, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (224, '套裝書', 33, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (225, '手機(空機)', 34, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (226, '對講機', 34, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (231, '移動電源', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (232, '電池/移動電源', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (233, '藍牙耳機', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (234, '充電器/數據線', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (235, '蘋果周邊', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (236, '手機耳機', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (237, '手機貼膜', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (238, '手機存儲卡', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (239, '充電器', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (240, '數據線', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (241, '手機保護套', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (242, '車載配件', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (243, 'iPhone 配件', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (244, '手機電池', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (245, '創意配件', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (246, '便攜/無線音響', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (247, '手機飾品', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (248, '拍照配件', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (249, '手機支架', 36, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (250, '平板電視', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (251, '空調', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (252, '冰箱', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (253, '洗衣機', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (254, '家庭影院', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (255, 'DVD/電視盒子', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (256, '迷你音響', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (257, '冷櫃/冰吧', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (258, '家電配件', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (259, '功放', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (260, '迴音壁/Soundbar', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (261, 'Hi-Fi專區', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (262, '電視盒子', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (263, '酒櫃', 37, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (264, '燃氣灶', 38, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (265, '油煙機', 38, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (266, '熱水器', 38, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (267, '消毒櫃', 38, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (268, '洗碗機', 38, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (269, '料理機', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (270, '榨汁機', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (271, '電飯煲', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (272, '電壓力鍋', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (273, '豆漿機', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (274, '咖啡機', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (275, '微波爐', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (276, '電烤箱', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (277, '電磁爐', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (278, '麪包機', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (279, '煮蛋器', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (280, '酸奶機', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (281, '電燉鍋', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (282, '電水壺/熱水瓶', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (283, '電餅鐺', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (284, '多用途鍋', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (285, '電燒烤爐', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (286, '果蔬解毒機', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (287, '其它廚房電器', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (288, '養生壺/煎藥壺', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (289, '電熱飯盒', 39, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (290, '取暖電器', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (291, '淨化器', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (292, '加濕器', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (293, '掃地機器人', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (294, '吸塵器', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (295, '掛燙機/熨斗', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (296, '插座', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (297, '電話機', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (298, '清潔機', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (299, '除濕機', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (300, '乾衣機', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (301, '收錄/音機', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (302, '電風扇', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (303, '冷風扇', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (304, '其它生活電器', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (305, '生活電器配件', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (306, '淨水器', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (307, '飲水機', 40, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (308, '剃鬚刀', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (309, '剃/脱毛器', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (310, '口腔護理', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (311, '電吹風', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (312, '美容器', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (313, '理髮器', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (314, '卷/直髮器', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (315, '按摩椅', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (316, '按摩器', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (317, '足浴盆', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (318, '血壓計', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (319, '電子秤/廚房秤', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (320, '血糖儀', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (321, '體温計', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (322, '其它健康電器', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (323, '計步器/脂肪檢測儀', 41, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (324, '電動工具', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (325, '手動工具', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (326, '儀器儀表', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (327, '浴霸/排氣扇', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (328, '燈具', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (329, 'LED燈', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (330, '潔身器', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (331, '水槽', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (332, '龍頭', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (333, '淋浴花灑', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (334, '廚衞五金', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (335, '傢俱五金', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (336, '門鈴', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (337, '電氣開關', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (338, '插座', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (339, '電工電料', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (340, '監控安防', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (341, '電線/線纜', 42, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (342, '數碼相機', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (343, '單電/微單相機', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (344, '單反相機', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (345, '攝像機', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (346, '拍立得', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (347, '運動相機', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (348, '鏡頭', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (349, '户外器材', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (350, '影棚器材', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (351, '沖印服務', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (352, '數碼相框', 43, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (353, '存儲卡', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (354, '讀卡器', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (355, '濾鏡', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (356, '閃光燈/手柄', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (357, '相機包', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (358, '三腳架/雲台', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (359, '相機清潔/貼膜', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (360, '機身附件', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (361, '鏡頭附件', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (362, '電池/充電器', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (363, '移動電源', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (364, '數碼支架', 44, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (365, '智能手環', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (366, '智能手錶', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (367, '智能眼鏡', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (368, '運動跟蹤器', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (369, '健康監測', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (370, '智能配飾', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (371, '智能家居', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (372, '體感車', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (373, '其他配件', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (374, '智能機器人', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (375, '無人機', 45, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (376, 'MP3/MP4', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (377, '智能設備', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (378, '耳機/耳麥', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (379, '便攜/無線音箱', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (380, '音箱/音響', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (381, '高清播放器', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (382, '收音機', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (383, 'MP3/MP4配件', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (384, '麥克風', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (385, '專業音頻', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (386, '蘋果配件', 46, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (387, '學生平板', 47, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (388, '點讀機/筆', 47, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (389, '早教益智', 47, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (390, '錄音筆', 47, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (391, '電紙書', 47, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (392, '電子詞典', 47, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (393, '復讀機', 47, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (394, '延保服務', 48, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (395, '殺毒軟件', 48, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (396, '積分商品', 48, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (397, '桌布/罩件', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (398, '地毯地墊', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (399, '沙發墊套/椅墊', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (400, '牀品套件', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (401, '被子', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (402, '枕芯', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (403, '牀單被罩', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (404, '毯子', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (405, '牀墊/牀褥', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (406, '蚊帳', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (407, '抱枕靠墊', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (408, '毛巾浴巾', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (409, '電熱毯', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (410, '窗簾/窗紗', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (411, '布藝軟飾', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (412, '涼蓆', 49, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (413, '枱燈', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (414, '節能燈', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (415, '裝飾燈', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (416, '落地燈', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (417, '應急燈/手電', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (418, 'LED燈', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (419, '吸頂燈', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (420, '五金電器', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (421, '筒燈射燈', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (422, '吊燈', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (423, '氛圍照明', 50, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (424, '保暖防護', 51, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (425, '收納用品', 51, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (426, '雨傘雨具', 51, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (427, '浴室用品', 51, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (428, '縫紉/針織用品', 51, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (429, '洗曬/熨燙', 51, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (430, '淨化除味', 51, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (431, '相框/照片牆', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (432, '裝飾字畫', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (433, '節慶飾品', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (434, '手工/十字繡', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (435, '裝飾擺件', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (436, '簾藝隔斷', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (437, '牆貼/裝飾貼', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (438, '鍾飾', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (439, '花瓶花藝', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (440, '香薰蠟燭', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (441, '創意家居', 52, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (442, '寵物主糧', 53, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (443, '寵物零食', 53, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (444, '醫療保健', 53, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (445, '家居日用', 53, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (446, '寵物玩具', 53, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (447, '出行裝備', 53, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (448, '洗護美容', 53, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (449, '筆記本', 54, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (450, '超極本', 54, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (451, '遊戲本', 54, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (452, '平板電腦', 54, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (453, '平板電腦配件', 54, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (454, '台式機', 54, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (455, '服務器/工作站', 54, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (456, '筆記本配件', 54, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (457, '一體機', 54, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (458, 'CPU', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (459, '主板', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (460, '顯卡', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (461, '硬盤', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (462, 'SSD固態硬盤', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (463, '內存', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (464, '機箱', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (465, '電源', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (466, '顯示器', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (467, '刻錄機/光驅', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (468, '散熱器', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (469, '聲卡/擴展卡', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (470, '裝機配件', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (471, '組裝電腦', 55, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (472, '移動硬盤', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (473, 'U盤', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (474, '鼠標', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (475, '鍵盤', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (476, '鼠標墊', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (477, '攝像頭', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (478, '手寫板', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (479, '硬盤盒', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (480, '插座', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (481, '線纜', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (482, 'UPS電源', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (483, '電腦工具', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (484, '遊戲設備', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (485, '電玩', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (486, '電腦清潔', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (487, '網絡儀表儀器', 56, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (488, '遊戲機', 57, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (489, '遊戲耳機', 57, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (490, '手柄/方向盤', 57, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (491, '遊戲軟件', 57, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (492, '遊戲周邊', 57, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (493, '路由器', 58, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (494, '網卡', 58, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (495, '交換機', 58, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (496, '網絡存儲', 58, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (497, '4G/3G上網', 58, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (498, '網絡盒子', 58, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (499, '網絡配件', 58, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (500, '投影機', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (501, '投影配件', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (502, '多功能一體機', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (503, '打印機', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (504, '傳真設備', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (505, '驗鈔/點鈔機', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (506, '掃描設備', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (507, '複合機', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (508, '碎紙機', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (509, '考勤機', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (510, '收款/POS機', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (511, '會議音頻視頻', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (512, '保險櫃', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (513, '裝訂/封裝機', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (514, '安防監控', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (515, '辦公傢俱', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (516, '白板', 59, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (517, '硒鼓/墨粉', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (518, '墨盒', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (519, '色帶', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (520, '紙類', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (521, '辦公文具', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (522, '學生文具', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (523, '財會用品', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (524, '文件管理', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (525, '本冊/便籤', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (526, '計算器', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (527, '筆類', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (528, '畫具畫材', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (529, '刻錄碟片/附件', 60, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (530, '上門安裝', 61, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (531, '延保服務', 61, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (532, '維修保養', 61, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (533, '電腦軟件', 61, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (534, '京東服務', 61, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (535, '炒鍋', 62, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (536, '煎鍋', 62, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (537, '壓力鍋', 62, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (538, '蒸鍋', 62, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (539, '湯鍋', 62, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (540, '奶鍋', 62, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (541, '鍋具套裝', 62, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (542, '煲類', 62, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (543, '水壺', 62, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (544, '火鍋', 62, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (545, '菜刀', 63, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (546, '剪刀', 63, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (547, '刀具套裝', 63, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (548, '砧板', 63, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (549, '瓜果刀/刨', 63, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (550, '多功能刀', 63, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (551, '保鮮盒', 64, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (552, '烘焙/燒烤', 64, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (553, '飯盒/提鍋', 64, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (554, '儲物/置物架', 64, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (555, '廚房DIY/小工具', 64, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (556, '塑料杯', 65, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (557, '運動水壺', 65, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (558, '玻璃杯', 65, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (559, '陶瓷/馬克杯', 65, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (560, '保温杯', 65, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (561, '保温壺', 65, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (562, '酒杯/酒具', 65, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (563, '杯具套裝', 65, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (564, '餐具套裝', 66, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (565, '碗/碟/盤', 66, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (566, '筷勺/刀叉', 66, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (567, '一次性用品', 66, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (568, '果盤/果籃', 66, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (569, '自助餐爐', 67, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (570, '酒店餐具', 67, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (571, '酒店水具', 67, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (572, '整套茶具', 68, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (573, '茶杯', 68, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (574, '茶壺', 68, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (575, '茶盤茶托', 68, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (576, '茶葉罐', 68, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (577, '茶具配件', 68, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (578, '茶寵擺件', 68, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (579, '咖啡具', 68, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (580, '其他', 68, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (581, '紙品濕巾', 69, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (582, '衣物清潔', 69, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (583, '清潔工具', 69, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (584, '驅蟲用品', 69, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (585, '家庭清潔', 69, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (586, '皮具護理', 69, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (587, '一次性用品', 69, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (588, '潔面', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (589, '乳液麪霜', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (590, '面膜', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (591, '剃鬚', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (592, '套裝', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (593, '精華', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (594, '眼霜', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (595, '卸妝', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (596, '防曬', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (597, '防曬隔離', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (598, 'T區護理', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (599, '眼部護理', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (600, '精華露', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (601, '爽膚水', 70, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (602, '沐浴', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (603, '潤膚', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (604, '頸部', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (605, '手足', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (606, '纖體塑形', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (607, '美胸', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (608, '套裝', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (609, '精油', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (610, '洗髮護髮', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (611, '染髮/造型', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (612, '香薰精油', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (613, '磨砂/浴鹽', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (614, '手工/香皂', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (615, '洗髮', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (616, '護髮', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (617, '染髮', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (618, '磨砂膏', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (619, '香皂', 71, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (620, '牙膏/牙粉', 72, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (621, '牙刷/牙線', 72, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (622, '漱口水', 72, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (623, '套裝', 72, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (624, '衞生巾', 73, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (625, '衞生護墊', 73, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (626, '私密護理', 73, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (627, '脱毛膏', 73, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (628, '其他', 73, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (629, '洗髮', 74, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (630, '護髮', 74, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (631, '染髮', 74, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (632, '造型', 74, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (633, '假髮', 74, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (634, '套裝', 74, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (635, '美髮工具', 74, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (636, '臉部護理', 74, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (637, '香水', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (638, '底妝', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (639, '腮紅', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (640, '眼影', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (641, '唇部', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (642, '美甲', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (643, '眼線', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (644, '美妝工具', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (645, '套裝', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (646, '防曬隔離', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (647, '卸妝', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (648, '眉筆', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (649, '睫毛膏', 75, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (650, 'T恤', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (651, '襯衫', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (652, '針織衫', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (653, '雪紡衫', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (654, '衞衣', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (655, '馬甲', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (656, '連衣裙', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (657, '半身裙', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (658, '牛仔褲', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (659, '休閒褲', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (660, '打底褲', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (661, '正裝褲', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (662, '小西裝', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (663, '短外套', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (664, '風衣', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (665, '毛呢大衣', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (666, '真皮皮衣', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (667, '棉服', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (668, '羽絨服', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (669, '大碼女裝', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (670, '中老年女裝', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (671, '婚紗', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (672, '打底衫', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (673, '旗袍/唐裝', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (674, '加絨褲', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (675, '吊帶/背心', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (676, '羊絨衫', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (677, '短褲', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (678, '皮草', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (679, '禮服', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (680, '仿皮皮衣', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (681, '羊毛衫', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (682, '設計師/潮牌', 76, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (683, '襯衫', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (684, 'T恤', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (685, 'POLO衫', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (686, '針織衫', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (687, '羊絨衫', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (688, '衞衣', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (689, '馬甲/背心', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (690, '夾克', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (691, '風衣', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (692, '毛呢大衣', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (693, '仿皮皮衣', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (694, '西服', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (695, '棉服', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (696, '羽絨服', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (697, '牛仔褲', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (698, '休閒褲', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (699, '西褲', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (700, '西服套裝', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (701, '大碼男裝', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (702, '中老年男裝', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (703, '唐裝/中山裝', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (704, '工裝', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (705, '真皮皮衣', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (706, '加絨褲', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (707, '衞褲/運動褲', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (708, '短褲', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (709, '設計師/潮牌', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (710, '羊毛衫', 77, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (711, '文胸', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (712, '女式內褲', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (713, '男式內褲', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (714, '睡衣/家居服', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (715, '塑身美體', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (716, '泳衣', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (717, '吊帶/背心', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (718, '抹胸', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (719, '連褲襪/絲襪', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (720, '美腿襪', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (721, '商務男襪', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (722, '保暖內衣', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (723, '情侶睡衣', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (724, '文胸套裝', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (725, '少女文胸', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (726, '休閒棉襪', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (727, '大碼內衣', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (728, '內衣配件', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (729, '打底褲襪', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (730, '打底衫', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (731, '秋衣秋褲', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (732, '情趣內衣', 78, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (733, '服裝洗護', 79, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (734, '太陽鏡', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (735, '光學鏡架/鏡片', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (736, '圍巾/手套/帽子套裝', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (737, '袖釦', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (738, '棒球帽', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (739, '毛線帽', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (740, '遮陽帽', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (741, '老花鏡', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (742, '裝飾眼鏡', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (743, '防輻射眼鏡', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (744, '游泳鏡', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (745, '女士絲巾/圍巾/披肩', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (746, '男士絲巾/圍巾', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (747, '鴨舌帽', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (748, '貝雷帽', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (749, '禮帽', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (750, '真皮手套', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (751, '毛線手套', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (752, '防曬手套', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (753, '男士腰帶/禮盒', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (754, '女士腰帶/禮盒', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (755, '鑰匙扣', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (756, '遮陽傘/雨傘', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (757, '口罩', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (758, '耳罩/耳包', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (759, '假領', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (760, '毛線/布面料', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (761, '領帶/領結/領帶夾', 80, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (762, '男表', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (763, '瑞表', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (764, '女表', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (765, '國表', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (766, '日韓表', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (767, '歐美表', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (768, '德表', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (769, '兒童手錶', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (770, '智能手錶', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (771, '鬧鐘', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (772, '座鐘掛鐘', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (773, '鐘錶配件', 81, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (774, '商務休閒鞋', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (775, '正裝鞋', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (776, '休閒鞋', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (777, '涼鞋/沙灘鞋', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (778, '男靴', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (779, '功能鞋', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (780, '拖鞋/人字拖', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (781, '雨鞋/雨靴', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (782, '傳統布鞋', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (783, '鞋配件', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (784, '帆布鞋', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (785, '增高鞋', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (786, '工裝鞋', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (787, '定製鞋', 82, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (788, '高跟鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (789, '單鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (790, '休閒鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (791, '涼鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (792, '女靴', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (793, '雪地靴', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (794, '拖鞋/人字拖', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (795, '踝靴', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (796, '筒靴', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (797, '帆布鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (798, '雨鞋/雨靴', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (799, '媽媽鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (800, '鞋配件', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (801, '特色鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (802, '魚嘴鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (803, '布鞋/繡花鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (804, '馬丁靴', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (805, '坡跟鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (806, '鬆糕鞋', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (807, '內增高', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (808, '防水台', 83, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (809, '嬰幼奶粉', 84, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (810, '孕媽奶粉', 84, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (811, '益生菌/初乳', 85, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (812, '米粉/菜粉', 85, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (813, '果泥/果汁', 85, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (814, 'DHA', 85, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (815, '寶寶零食', 85, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (816, '鈣鐵鋅/維生素', 85, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (817, '清火/開胃', 85, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (818, '麪條/粥', 85, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (819, '嬰兒尿褲', 86, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (820, '拉拉褲', 86, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (821, '嬰兒濕巾', 86, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (822, '成人尿褲', 86, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (823, '奶瓶奶嘴', 87, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (824, '吸奶器', 87, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (825, '暖奶消毒', 87, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (826, '兒童餐具', 87, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (827, '水壺/水杯', 87, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (828, '牙膠安撫', 87, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (829, '圍兜/防濺衣', 87, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (830, '輔食料理機', 87, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (831, '食物存儲', 87, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (832, '寶寶護膚', 88, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (833, '洗髮沐浴', 88, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (834, '奶瓶清洗', 88, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (835, '驅蚊防曬', 88, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (836, '理髮器', 88, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (837, '洗澡用具', 88, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (838, '嬰兒口腔清潔', 88, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (839, '洗衣液/皂', 88, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (840, '日常護理', 88, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (841, '座便器', 88, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (842, '嬰兒推車', 89, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (843, '餐椅搖椅', 89, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (844, '嬰兒牀', 89, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (845, '學步車', 89, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (846, '三輪車', 89, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (847, '自行車', 89, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (848, '電動車', 89, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (849, '扭扭車', 89, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (850, '滑板車', 89, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (851, '嬰兒牀墊', 89, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (852, '嬰兒外出服', 90, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (853, '嬰兒內衣', 90, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (854, '嬰兒禮盒', 90, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (855, '嬰兒鞋帽襪', 90, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (856, '安全防護', 90, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (857, '家居牀品', 90, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (858, '睡袋/抱被', 90, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (859, '爬行墊', 90, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (860, '媽咪包/背嬰帶', 91, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (861, '產後塑身', 91, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (862, '文胸/內褲', 91, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (863, '防輻射服', 91, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (864, '孕媽裝', 91, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (865, '孕期營養', 91, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (866, '孕婦護膚', 91, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (867, '待產護理', 91, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (868, '月子裝', 91, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (869, '防溢乳墊', 91, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (870, '套裝', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (871, '上衣', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (872, '褲子', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (873, '裙子', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (874, '內衣/家居服', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (875, '羽絨服/棉服', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (876, '親子裝', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (877, '兒童配飾', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (878, '禮服/演出服', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (879, '運動鞋', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (880, '皮鞋/帆布鞋', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (881, '靴子', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (882, '涼鞋', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (883, '功能鞋', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (884, '户外/運動服', 92, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (885, '提籃式', 93, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (886, '安全座椅', 93, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (887, '增高墊', 93, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (888, '錢包', 94, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (889, '手拿包', 94, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (890, '單肩包', 94, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (891, '雙肩包', 94, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (892, '手提包', 94, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (893, '斜挎包', 94, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (894, '鑰匙包', 94, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (895, '卡包/零錢包', 94, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (896, '男士錢包', 95, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (897, '男士手包', 95, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (898, '卡包名片夾', 95, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (899, '商務公文包', 95, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (900, '雙肩包', 95, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (901, '單肩/斜挎包', 95, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (902, '鑰匙包', 95, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (903, '電腦包', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (904, '拉桿箱', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (905, '旅行包', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (906, '旅行配件', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (907, '休閒運動包', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (908, '拉桿包', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (909, '登山包', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (910, '媽咪包', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (911, '書包', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (912, '相機包', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (913, '腰包/胸包', 96, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (914, '火機煙具', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (915, '禮品文具', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (916, '軍刀軍具', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (917, '收藏品', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (918, '工藝禮品', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (919, '創意禮品', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (920, '禮盒禮券', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (921, '鮮花綠植', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (922, '婚慶節慶', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (923, '京東卡', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (924, '美妝禮品', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (925, '禮品定製', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (926, '京東福卡', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (927, '古董文玩', 97, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (928, '箱包', 98, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (929, '錢包', 98, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (930, '服飾', 98, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (931, '腰帶', 98, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (932, '太陽鏡/眼鏡框', 98, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (933, '配件', 98, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (934, '鞋靴', 98, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (935, '飾品', 98, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (936, '名品腕錶', 98, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (937, '高檔化妝品', 98, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (938, '婚嫁首飾', 99, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (939, '婚紗攝影', 99, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (940, '婚紗禮服', 99, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (941, '婚慶服務', 99, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (942, '婚慶禮品/用品', 99, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (943, '婚宴', 99, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (944, '餅乾蛋糕', 100, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (945, '糖果/巧克力', 100, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (946, '休閒零食', 100, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (947, '衝調飲品', 100, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (948, '糧油調味', 100, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (949, '牛奶', 100, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (950, '其他特產', 101, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (951, '新疆', 101, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (952, '北京', 101, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (953, '山西', 101, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (954, '內蒙古', 101, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (955, '福建', 101, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (956, '湖南', 101, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (957, '四川', 101, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (958, '雲南', 101, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (959, '東北', 101, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (960, '休閒零食', 102, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (961, '堅果炒貨', 102, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (962, '肉乾肉脯', 102, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (963, '蜜餞果乾', 102, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (964, '糖果/巧克力', 102, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (965, '餅乾蛋糕', 102, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (966, '無糖食品', 102, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (967, '米麪雜糧', 103, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (968, '食用油', 103, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (969, '調味品', 103, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (970, '南北乾貨', 103, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (971, '方便食品', 103, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (972, '有機食品', 103, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (973, '飲用水', 104, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (974, '飲料', 104, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (975, '牛奶乳品', 104, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (976, '咖啡/奶茶', 104, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (977, '衝飲穀物', 104, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (978, '蜂蜜/柚子茶', 104, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (979, '成人奶粉', 104, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (980, '月餅', 105, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (981, '大閘蟹', 105, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (982, '粽子', 105, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (983, '卡券', 105, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (984, '鐵觀音', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (985, '普洱', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (986, '龍井', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (987, '綠茶', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (988, '紅茶', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (989, '烏龍茶', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (990, '花草茶', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (991, '花果茶', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (992, '養生茶', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (993, '黑茶', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (994, '白茶', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (995, '其它茶', 106, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1072, '機油', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1073, '正時皮帶', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1074, '添加劑', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1075, '汽車喇叭', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1076, '防凍液', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1077, '汽車玻璃', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1078, '濾清器', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1079, '火花塞', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1080, '減震器', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1081, '柴機油/輔助油', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1082, '雨刷', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1083, '車燈', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1084, '後視鏡', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1085, '輪胎', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1086, '輪轂', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1087, '剎車片/盤', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1088, '維修配件', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1089, '蓄電池', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1090, '底盤裝甲/護板', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1091, '貼膜', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1092, '汽修工具', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1093, '改裝配件', 119, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1094, '導航儀', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1095, '安全預警儀', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1096, '行車記錄儀', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1097, '倒車雷達', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1098, '藍牙設備', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1099, '車載影音', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1100, '淨化器', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1101, '電源', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1102, '智能駕駛', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1103, '車載電台', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1104, '車載電器配件', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1105, '吸塵器', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1106, '智能車機', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1107, '冰箱', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1108, '汽車音響', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1109, '車載生活電器', 120, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1110, '車蠟', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1111, '補漆筆', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1112, '玻璃水', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1113, '清潔劑', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1114, '洗車工具', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1115, '鍍晶鍍膜', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1116, '打蠟機', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1117, '洗車配件', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1118, '洗車機', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1119, '洗車水槍', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1120, '毛巾撣子', 121, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1121, '腳墊', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1122, '座墊', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1123, '座套', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1124, '後備箱墊', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1125, '頭枕腰靠', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1126, '方向盤套', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1127, '香水', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1128, '空氣淨化', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1129, '掛件擺件', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1130, '功能小件', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1131, '車身裝飾件', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1132, '車衣', 122, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1133, '安全座椅', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1134, '胎壓監測', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1135, '防盜設備', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1136, '應急救援', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1137, '保温箱', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1138, '地鎖', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1139, '摩托車', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1140, '充氣泵', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1141, '儲物箱', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1142, '自駕野營', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1143, '摩托車裝備', 123, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1144, '清洗美容', 124, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1145, '功能升級', 124, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1146, '保養維修', 124, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1147, '油卡充值', 124, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1148, '車險', 124, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1149, '加油卡', 124, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1150, 'ETC', 124, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1151, '駕駛培訓', 124, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1152, '賽事服裝', 125, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1153, '賽事用品', 125, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1154, '制動系統', 125, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1155, '懸掛系統', 125, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1156, '進氣系統', 125, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1157, '排氣系統', 125, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1158, '電子管理', 125, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1159, '車身強化', 125, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1160, '賽事座椅', 125, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1161, '跑步鞋', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1162, '休閒鞋', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1163, '籃球鞋', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1164, '板鞋', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1165, '帆布鞋', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1166, '足球鞋', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1167, '乒羽網鞋', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1168, '專項運動鞋', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1169, '訓練鞋', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1170, '拖鞋', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1171, '運動包', 126, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1172, '羽絨服', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1173, '棉服', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1174, '運動褲', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1175, '夾克/風衣', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1176, '衞衣/套頭衫', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1177, 'T恤', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1178, '套裝', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1179, '乒羽網服', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1180, '健身服', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1181, '運動背心', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1182, '毛衫/線衫', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1183, '運動配飾', 127, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1184, '摺疊車', 128, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1185, '山地車/公路車', 128, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1186, '電動車', 128, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1187, '其他整車', 128, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1188, '騎行服', 128, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1189, '騎行裝備', 128, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1190, '平衡車', 128, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1191, '魚竿魚線', 129, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1192, '浮漂魚餌', 129, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1193, '釣魚桌椅', 129, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1194, '釣魚配件', 129, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1195, '釣箱魚包', 129, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1196, '其它', 129, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1197, '泳鏡', 130, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1198, '泳帽', 130, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1199, '游泳包防水包', 130, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1200, '女士泳衣', 130, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1201, '男士泳衣', 130, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1202, '比基尼', 130, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1203, '其它', 130, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1204, '衝鋒衣褲', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1205, '速乾衣褲', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1206, '滑雪服', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1207, '羽絨服/棉服', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1208, '休閒衣褲', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1209, '抓絨衣褲', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1210, '軟殼衣褲', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1211, 'T恤', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1212, '户外風衣', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1213, '功能內衣', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1214, '軍迷服飾', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1215, '登山鞋', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1216, '雪地靴', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1217, '徒步鞋', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1218, '越野跑鞋', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1219, '休閒鞋', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1220, '工裝鞋', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1221, '溯溪鞋', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1222, '沙灘/涼拖', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1223, '户外襪', 131, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1224, '帳篷/墊子', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1225, '睡袋/吊牀', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1226, '登山攀巖', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1227, '户外配飾', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1228, '揹包', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1229, '户外照明', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1230, '户外儀表', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1231, '户外工具', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1232, '望遠鏡', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1233, '旅遊用品', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1234, '便攜桌椅牀', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1235, '野餐燒烤', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1236, '軍迷用品', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1237, '救援裝備', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1238, '滑雪裝備', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1239, '極限户外', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1240, '衝浪潛水', 132, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1241, '綜合訓練器', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1242, '其他大型器械', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1243, '啞鈴', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1244, '仰卧板/收腹機', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1245, '其他中小型器材', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1246, '瑜伽舞蹈', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1247, '甩脂機', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1248, '踏步機', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1249, '武術搏擊', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1250, '健身車/動感單車', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1251, '跑步機', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1252, '運動護具', 133, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1253, '羽毛球', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1254, '乒乓球', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1255, '籃球', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1256, '足球', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1257, '網球', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1258, '排球', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1259, '高爾夫', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1260, '枱球', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1261, '棋牌麻將', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1262, '輪滑滑板', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1263, '其他', 134, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1264, '0-6個月', 135, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1265, '6-12個月', 135, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1266, '1-3歲', 135, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1267, '3-6歲', 135, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1268, '6-14歲', 135, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1269, '14歲以上', 135, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1270, '遙控車', 136, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1271, '遙控飛機', 136, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1272, '遙控船', 136, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1273, '機器人', 136, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1274, '軌道/助力', 136, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1275, '毛絨/布藝', 137, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1276, '靠墊/抱枕', 137, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1277, '芭比娃娃', 138, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1278, '卡通娃娃', 138, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1279, '智能娃娃', 138, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1280, '仿真模型', 139, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1281, '拼插模型', 139, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1282, '收藏愛好', 139, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1283, '炫舞毯', 140, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1284, '爬行墊/毯', 140, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1285, '户外玩具', 140, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1286, '戲水玩具', 140, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1287, '電影周邊', 141, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1288, '卡通周邊', 141, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1289, '網遊周邊', 141, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1290, '搖鈴/牀鈴', 142, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1291, '健身架', 142, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1292, '早教啓智', 142, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1293, '拖拉玩具', 142, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1294, '積木', 143, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1295, '拼圖', 143, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1296, '磁力棒', 143, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1297, '立體拼插', 143, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1298, '手工彩泥', 144, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1299, '繪畫工具', 144, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1300, '情景玩具', 144, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1301, '減壓玩具', 145, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1302, '創意玩具', 145, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1303, '鋼琴', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1304, '電子琴/電鋼琴', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1305, '吉他/尤克里裏', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1306, '打擊樂器', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1307, '西洋管絃', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1308, '民族管絃樂器', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1309, '樂器配件', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1310, '電腦音樂', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1311, '工藝禮品樂器', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1312, '口琴/口風琴/豎笛', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1313, '手風琴', 146, 3, 1, 0, NULL, NULL, 0);
INSERT INTO `t_product_category` VALUES (1433, 'aaa', 22, 3, 1, 0, '1', '台', NULL);

-- ----------------------------
-- Table structure for t_product_category_brand_relation
-- ----------------------------
DROP TABLE IF EXISTS `t_product_category_brand_relation`;
CREATE TABLE `t_product_category_brand_relation`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `brand_id` bigint(20) NULL DEFAULT NULL COMMENT '品牌id',
  `catalog_id` bigint(20) NULL DEFAULT NULL COMMENT '分類id',
  `brand_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `catalog_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '品牌分類關聯' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product_category_brand_relation
-- ----------------------------
INSERT INTO `t_product_category_brand_relation` VALUES (1, 1, 225, 'apple', '手機(空機)');
INSERT INTO `t_product_category_brand_relation` VALUES (2, 2, 225, '小米', '手機(空機)');
INSERT INTO `t_product_category_brand_relation` VALUES (3, 3, 650, '自有品牌', 'T恤');
INSERT INTO `t_product_category_brand_relation` VALUES (5, 1, 233, 'apple', '藍牙耳機');
INSERT INTO `t_product_category_brand_relation` VALUES (6, 4, 233, 'SAMSUNG三星', '藍牙耳機');
INSERT INTO `t_product_category_brand_relation` VALUES (7, 4, 225, 'SAMSUNG三星', '手機(空機)');

-- ----------------------------
-- Table structure for t_product_home_adv
-- ----------------------------
DROP TABLE IF EXISTS `t_product_home_adv`;
CREATE TABLE `t_product_home_adv`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名字',
  `pic` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '圖片地址',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '開始時間',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '結束時間',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '狀態',
  `click_count` int(11) NULL DEFAULT NULL COMMENT '點擊數',
  `url` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '廣告詳情連接地址',
  `note` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '備註',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `publisher_id` bigint(20) NULL DEFAULT NULL COMMENT '發佈者',
  `auth_id` bigint(20) NULL DEFAULT NULL COMMENT '審核者',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '運營位置（1輪播圖，2品牌製造商，3新品）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '首頁輪播廣告' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product_home_adv
-- ----------------------------
INSERT INTO `t_product_home_adv` VALUES (11, '', 'http://localhost:9000/javamall/20220213182457%2B08001%20%281%29.jpg', NULL, NULL, 1, NULL, '', '', 1, NULL, NULL, 1);
INSERT INTO `t_product_home_adv` VALUES (12, '', 'http://localhost:9000/javamall/20220213182506%2B08001%20%281%29.png', NULL, NULL, 1, NULL, '', '', 2, NULL, NULL, 1);
INSERT INTO `t_product_home_adv` VALUES (13, '', 'http://localhost:9000/javamall/20220213182515%2B08001%20%282%29.png', NULL, NULL, 1, NULL, '', '', 3, NULL, NULL, 1);
INSERT INTO `t_product_home_adv` VALUES (14, '', 'http://localhost:9000/javamall/20220213182523%2B08001%20%283%29.png', NULL, NULL, 1, NULL, '', '', 4, NULL, NULL, 1);
INSERT INTO `t_product_home_adv` VALUES (15, '', 'http://localhost:9000/javamall/20220213182606%2B0800220213_182353.png', NULL, NULL, 1, NULL, '', '手機', 1, NULL, NULL, 3);
INSERT INTO `t_product_home_adv` VALUES (16, '', 'http://localhost:9000/javamall/20220213182620%2B0800220213_182400.png', NULL, NULL, 1, NULL, '', '', 1, NULL, NULL, 3);
INSERT INTO `t_product_home_adv` VALUES (17, '', 'http://localhost:9000/javamall/20220213182633%2B0800220213_182421.png', NULL, NULL, 1, NULL, '', '直覺 SWEET TOUCH~職業洗髮精', 1, NULL, NULL, 2);
INSERT INTO `t_product_home_adv` VALUES (18, '', 'http://localhost:9000/javamall/20220213182654%2B0800220213_182446.png', NULL, NULL, 1, NULL, '', '親親JIUJIU 醫用口罩(30入)', 1, NULL, NULL, 2);

-- ----------------------------
-- Table structure for t_product_sku_images
-- ----------------------------
DROP TABLE IF EXISTS `t_product_sku_images`;
CREATE TABLE `t_product_sku_images`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'sku_id',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '圖片地址',
  `img_sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `default_img` int(11) NULL DEFAULT NULL COMMENT '默認圖[0 - 不是默認圖，1 - 是默認圖]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 40 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'sku圖片' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product_sku_images
-- ----------------------------
INSERT INTO `t_product_sku_images` VALUES (1, 1, 'http://localhost:9000/javamall/20220212184254%2B0800iphone-13-pro-blue-select.jpg', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (2, 8, 'http://localhost:9000/javamall/20220213190405%2B0800220213_182353.png', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (3, 9, 'http://localhost:9000/javamall/20220213190724%2B0800spu2.png', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (4, 10, 'http://localhost:9000/javamall/20220213195713%2B0800%E5%95%86%E5%93%81%E5%9C%96%E5%8F%8A1.png', NULL, 0);
INSERT INTO `t_product_sku_images` VALUES (5, 10, 'http://localhost:9000/javamall/20220213195717%2B0800%E5%95%86%E5%93%81%E5%9C%96%E5%8F%8A2.png', NULL, 0);
INSERT INTO `t_product_sku_images` VALUES (6, 10, 'http://localhost:9000/javamall/20220213195810%2B0800sku1.png', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (7, 11, 'http://localhost:9000/javamall/20220213195713%2B0800%E5%95%86%E5%93%81%E5%9C%96%E5%8F%8A1.png', NULL, 0);
INSERT INTO `t_product_sku_images` VALUES (8, 11, 'http://localhost:9000/javamall/20220213195717%2B0800%E5%95%86%E5%93%81%E5%9C%96%E5%8F%8A2.png', NULL, 0);
INSERT INTO `t_product_sku_images` VALUES (9, 11, 'http://localhost:9000/javamall/20220213195823%2B0800sku2.png', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (10, 12, 'http://localhost:9000/javamall/20220213201541%2B0800%E5%95%86%E5%93%81%E5%9C%96%E5%8F%8A2.png', NULL, 0);
INSERT INTO `t_product_sku_images` VALUES (11, 12, 'http://localhost:9000/javamall/20220213201546%2B0800%E5%95%86%E5%93%81%E5%9C%96%E5%8F%8A1.png', NULL, 0);
INSERT INTO `t_product_sku_images` VALUES (12, 12, 'http://localhost:9000/javamall/20220213201614%2B0800sku1.png', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (13, 13, 'http://localhost:9000/javamall/20220213204748%2B0800p0653204058658-item-d161xf4x1000x1000-m.jpg', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (14, 14, 'http://localhost:9000/javamall/20220213204746%2B0800p0653204058658-item-783fxf4x1000x1000-m.jpg', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (15, 15, 'http://localhost:9000/javamall/20220213204743%2B0800p0653204058658-item-7026xf4x1000x1000-m.jpg', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (16, 16, 'http://localhost:9000/javamall/20220213204748%2B0800p0653204058658-item-d161xf4x1000x1000-m.jpg', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (17, 17, 'http://localhost:9000/javamall/20220213204746%2B0800p0653204058658-item-783fxf4x1000x1000-m.jpg', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (18, 18, 'http://localhost:9000/javamall/20220213204743%2B0800p0653204058658-item-7026xf4x1000x1000-m.jpg', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (19, 19, 'http://localhost:9000/javamall/20220213204748%2B0800p0653204058658-item-d161xf4x1000x1000-m.jpg', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (20, 20, 'http://localhost:9000/javamall/20220213204746%2B0800p0653204058658-item-783fxf4x1000x1000-m.jpg', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (21, 21, 'http://localhost:9000/javamall/20220213204743%2B0800p0653204058658-item-7026xf4x1000x1000-m.jpg', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (22, 22, 'http://localhost:9000/javamall/20220213214225%2B0800p0653204058658-item-d161xf4x1000x1000-m.jpg', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (23, 23, 'http://localhost:9000/javamall/20220213214220%2B0800p0653204058658-item-783fxf4x1000x1000-m.jpg', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (24, 24, 'http://localhost:9000/javamall/20220213214222%2B0800p0653204058658-item-7026xf4x1000x1000-m.jpg', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (25, 25, 'http://localhost:9000/javamall/20220213214225%2B0800p0653204058658-item-d161xf4x1000x1000-m.jpg', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (26, 26, 'http://localhost:9000/javamall/20220213214220%2B0800p0653204058658-item-783fxf4x1000x1000-m.jpg', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (27, 27, 'http://localhost:9000/javamall/20220213214222%2B0800p0653204058658-item-7026xf4x1000x1000-m.jpg', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (28, 28, 'http://localhost:9000/javamall/20220214220145%2B0800main.jpg', NULL, 0);
INSERT INTO `t_product_sku_images` VALUES (29, 28, 'http://localhost:9000/javamall/20220214220222%2B0800violet.jpg', NULL, 0);
INSERT INTO `t_product_sku_images` VALUES (30, 28, 'http://localhost:9000/javamall/20220214220236%2B0800silver.jpg', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (31, 29, 'http://localhost:9000/javamall/20220214220145%2B0800main.jpg', NULL, 0);
INSERT INTO `t_product_sku_images` VALUES (32, 29, 'http://localhost:9000/javamall/20220214220222%2B0800violet.jpg', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (33, 29, 'http://localhost:9000/javamall/20220214220236%2B0800silver.jpg', NULL, 0);
INSERT INTO `t_product_sku_images` VALUES (34, 30, 'http://localhost:9000/javamall/20220214221009%2B0800main.jpg', NULL, 0);
INSERT INTO `t_product_sku_images` VALUES (35, 30, 'http://localhost:9000/javamall/20220214221012%2B0800silver.jpg', NULL, 0);
INSERT INTO `t_product_sku_images` VALUES (36, 30, 'http://localhost:9000/javamall/20220214221015%2B0800violet.jpg', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (37, 31, 'http://localhost:9000/javamall/20220214221009%2B0800main.jpg', NULL, 0);
INSERT INTO `t_product_sku_images` VALUES (38, 31, 'http://localhost:9000/javamall/20220214221012%2B0800silver.jpg', NULL, 1);
INSERT INTO `t_product_sku_images` VALUES (39, 31, 'http://localhost:9000/javamall/20220214221015%2B0800violet.jpg', NULL, 0);

-- ----------------------------
-- Table structure for t_product_sku_info
-- ----------------------------
DROP TABLE IF EXISTS `t_product_sku_info`;
CREATE TABLE `t_product_sku_info`  (
  `sku_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'skuId',
  `spu_id` bigint(20) NULL DEFAULT NULL COMMENT 'spuId',
  `sku_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sku名稱',
  `sku_desc` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'sku介紹描述',
  `catalog_id` bigint(20) NULL DEFAULT NULL COMMENT '所屬分類id',
  `brand_id` bigint(20) NULL DEFAULT NULL COMMENT '品牌id',
  `sku_default_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '默認圖片',
  `sku_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '標題',
  `sku_subtitle` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '副標題',
  `price` decimal(18, 4) NULL DEFAULT NULL COMMENT '價格',
  `sale_count` bigint(20) NULL DEFAULT NULL COMMENT '銷量',
  PRIMARY KEY (`sku_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'sku信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product_sku_info
-- ----------------------------
INSERT INTO `t_product_sku_info` VALUES (1, 1, 'iPhone 13 pro 黑', NULL, 225, 1, 'http://localhost:9000/javamall/20220212184254%2B0800iphone-13-pro-blue-select.jpg', 'iPhone 13 pro 黑', 'Pro 相機系統，威力驚人提升。 顯示器反應超靈敏，讓體驗全面更新。 全世界最快速的智慧型手機晶片。 耐用度格外出色。 電池續航力更是大幅躍升。', 32900.0000, 0);
INSERT INTO `t_product_sku_info` VALUES (2, 1, 'iPhone 13 pro 白', NULL, 225, 1, 'http://localhost:9000/javamall/20220212184254%2B0800iphone-13-pro-blue-select.jpg', 'iPhone 13 pro 白', 'Pro 相機系統，威力驚人提升。 顯示器反應超靈敏，讓體驗全面更新。 全世界最快速的智慧型手機晶片。 耐用度格外出色。 電池續航力更是大幅躍升。', 32900.0000, 0);
INSERT INTO `t_product_sku_info` VALUES (3, 1, 'iPhone 13 pro 土豪金', NULL, 225, 1, 'http://localhost:9000/javamall/20220212184254%2B0800iphone-13-pro-blue-select.jpg', 'iPhone 13 pro 土豪金', 'sku subtitle', 99999.0000, 0);
INSERT INTO `t_product_sku_info` VALUES (4, 2, '小米11 土豪金', NULL, 225, 2, 'http://localhost:9000/javamall/20220212185112%2B0800Xiaomi_xiaomi_11_1229063029339_360x270.jpg', '小米11 土豪金', '', 12345.0000, 0);
INSERT INTO `t_product_sku_info` VALUES (5, 2, '小米11 黑', NULL, 225, 2, 'http://localhost:9000/javamall/20220212185112%2B0800Xiaomi_xiaomi_11_1229063029339_360x270.jpg', '小米11 黑', '', 55555.0000, 0);
INSERT INTO `t_product_sku_info` VALUES (22, 11, '棉麻刺繡V領繫帶拼接寬鬆短袖T恤上衣 S 藍', NULL, 650, 3, 'http://localhost:9000/javamall/20220213214225%2B0800p0653204058658-item-d161xf4x1000x1000-m.jpg', '棉麻刺繡V領繫帶拼接寬鬆短袖T恤上衣 S 藍', '', 499.0000, 0);
INSERT INTO `t_product_sku_info` VALUES (23, 11, '棉麻刺繡V領繫帶拼接寬鬆短袖T恤上衣 S 粉紅', NULL, 650, 3, 'http://localhost:9000/javamall/20220213214220%2B0800p0653204058658-item-783fxf4x1000x1000-m.jpg', '棉麻刺繡V領繫帶拼接寬鬆短袖T恤上衣 S 粉紅', '', 499.0000, 0);
INSERT INTO `t_product_sku_info` VALUES (24, 11, '棉麻刺繡V領繫帶拼接寬鬆短袖T恤上衣 S 白', NULL, 650, 3, 'http://localhost:9000/javamall/20220213214222%2B0800p0653204058658-item-7026xf4x1000x1000-m.jpg', '棉麻刺繡V領繫帶拼接寬鬆短袖T恤上衣 S 白', '', 499.0000, 0);
INSERT INTO `t_product_sku_info` VALUES (25, 11, '棉麻刺繡V領繫帶拼接寬鬆短袖T恤上衣 M 藍', NULL, 650, 3, 'http://localhost:9000/javamall/20220213214225%2B0800p0653204058658-item-d161xf4x1000x1000-m.jpg', '棉麻刺繡V領繫帶拼接寬鬆短袖T恤上衣 M 藍', '', 499.0000, 0);
INSERT INTO `t_product_sku_info` VALUES (26, 11, '棉麻刺繡V領繫帶拼接寬鬆短袖T恤上衣 M 粉紅', NULL, 650, 3, 'http://localhost:9000/javamall/20220213214220%2B0800p0653204058658-item-783fxf4x1000x1000-m.jpg', '棉麻刺繡V領繫帶拼接寬鬆短袖T恤上衣 M 粉紅', '', 499.0000, 0);
INSERT INTO `t_product_sku_info` VALUES (27, 11, '棉麻刺繡V領繫帶拼接寬鬆短袖T恤上衣 M 白', NULL, 650, 3, 'http://localhost:9000/javamall/20220213214222%2B0800p0653204058658-item-7026xf4x1000x1000-m.jpg', '棉麻刺繡V領繫帶拼接寬鬆短袖T恤上衣 M 白', '', 499.0000, 0);
INSERT INTO `t_product_sku_info` VALUES (30, 13, 'Samsung Galaxy S21 星魅紫', NULL, 225, 4, 'http://localhost:9000/javamall/20220214221015%2B0800violet.jpg', 'Samsung Galaxy S21 星魅紫', '', 30900.0000, 0);
INSERT INTO `t_product_sku_info` VALUES (31, 13, 'Samsung Galaxy S21 星魅銀', NULL, 225, 4, 'http://localhost:9000/javamall/20220214221012%2B0800silver.jpg', 'Samsung Galaxy S21 星魅銀', '', 30998.0000, 0);

-- ----------------------------
-- Table structure for t_product_sku_sale_attr_value
-- ----------------------------
DROP TABLE IF EXISTS `t_product_sku_sale_attr_value`;
CREATE TABLE `t_product_sku_sale_attr_value`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'sku_id',
  `attr_id` bigint(20) NULL DEFAULT NULL COMMENT 'attr_id',
  `attr_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '銷售屬性名',
  `attr_value` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '銷售屬性值',
  `attr_sort` int(11) NULL DEFAULT NULL COMMENT '順序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'sku銷售屬性&值' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product_sku_sale_attr_value
-- ----------------------------
INSERT INTO `t_product_sku_sale_attr_value` VALUES (1, 1, 4, '顏色', '黑', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (2, 2, 4, '顏色', '白', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (3, 3, 4, '顏色', '土豪金', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (4, 4, 4, '顏色', '土豪金', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (5, 5, 4, '顏色', '黑', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (6, 6, 4, '顏色', '黑', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (7, 7, 4, '顏色', '黑', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (8, 8, 4, '顏色', '黑', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (9, 9, 4, '顏色', '黑', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (10, 10, 4, '顏色', '白', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (11, 11, 4, '顏色', '黑', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (12, 12, 4, '顏色', '黑', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (13, 13, 7, '尺寸', 'S', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (14, 13, 8, '顏色', '藍', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (15, 14, 7, '尺寸', 'S', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (16, 14, 8, '顏色', '粉紅', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (17, 15, 7, '尺寸', 'S', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (18, 15, 8, '顏色', '白', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (19, 16, 7, '尺寸', 'M', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (20, 16, 8, '顏色', '藍', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (21, 17, 7, '尺寸', 'M', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (22, 17, 8, '顏色', '粉紅', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (23, 18, 7, '尺寸', 'M', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (24, 18, 8, '顏色', '白', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (25, 19, 7, '尺寸', 'L', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (26, 19, 8, '顏色', '藍', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (27, 20, 7, '尺寸', 'L', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (28, 20, 8, '顏色', '粉紅', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (29, 21, 7, '尺寸', 'L', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (30, 21, 8, '顏色', '白', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (31, 22, 7, '尺寸', 'S', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (32, 22, 8, '顏色', '藍', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (33, 23, 7, '尺寸', 'S', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (34, 23, 8, '顏色', '粉紅', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (35, 24, 7, '尺寸', 'S', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (36, 24, 8, '顏色', '白', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (37, 25, 7, '尺寸', 'M', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (38, 25, 8, '顏色', '藍', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (39, 26, 7, '尺寸', 'M', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (40, 26, 8, '顏色', '粉紅', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (41, 27, 7, '尺寸', 'M', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (42, 27, 8, '顏色', '白', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (43, 28, 4, '顏色', '星魅銀', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (44, 29, 4, '顏色', '星魅紫', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (45, 30, 4, '顏色', '星魅紫', NULL);
INSERT INTO `t_product_sku_sale_attr_value` VALUES (46, 31, 4, '顏色', '星魅銀', NULL);

-- ----------------------------
-- Table structure for t_product_spu_images
-- ----------------------------
DROP TABLE IF EXISTS `t_product_spu_images`;
CREATE TABLE `t_product_spu_images`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `spu_id` bigint(20) NULL DEFAULT NULL COMMENT 'spu_id',
  `img_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '圖片名',
  `img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '圖片地址',
  `img_sort` int(11) NULL DEFAULT NULL COMMENT '順序',
  `default_img` tinyint(4) NULL DEFAULT NULL COMMENT '是否默認圖',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'spu圖片' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product_spu_images
-- ----------------------------
INSERT INTO `t_product_spu_images` VALUES (1, 1, NULL, 'http://localhost:9000/javamall/20220212184312%2B0800iphone-13-pro-family-select.jpg', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (2, 1, NULL, 'http://localhost:9000/javamall/20220212184315%2B0800finishes_2_graphite__cu1a7xuepsq6_large.jpg', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (3, 2, NULL, 'http://localhost:9000/javamall/20220212185114%2B0800%E4%B8%8B%E8%BC%89.jpg', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (4, 6, NULL, 'http://localhost:9000/javamall/20220213190405%2B0800220213_182353.png', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (5, 7, NULL, 'http://localhost:9000/javamall/20220213190724%2B0800spu2.png', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (6, 7, NULL, 'http://localhost:9000/javamall/20220213190405%2B0800220213_182353.png', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (7, 7, NULL, 'http://localhost:9000/javamall/20220213190742%2B0800sku.png', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (8, 8, NULL, 'http://localhost:9000/javamall/20220213195713%2B0800%E5%95%86%E5%93%81%E5%9C%96%E5%8F%8A1.png', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (9, 8, NULL, 'http://localhost:9000/javamall/20220213195717%2B0800%E5%95%86%E5%93%81%E5%9C%96%E5%8F%8A2.png', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (10, 8, NULL, 'http://localhost:9000/javamall/20220213195810%2B0800sku1.png', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (11, 8, NULL, 'http://localhost:9000/javamall/20220213195823%2B0800sku2.png', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (12, 9, NULL, 'http://localhost:9000/javamall/20220213201541%2B0800%E5%95%86%E5%93%81%E5%9C%96%E5%8F%8A2.png', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (13, 9, NULL, 'http://localhost:9000/javamall/20220213201546%2B0800%E5%95%86%E5%93%81%E5%9C%96%E5%8F%8A1.png', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (14, 9, NULL, 'http://localhost:9000/javamall/20220213201614%2B0800sku1.png', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (15, 10, NULL, 'http://localhost:9000/javamall/20220213204743%2B0800p0653204058658-item-7026xf4x1000x1000-m.jpg', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (16, 10, NULL, 'http://localhost:9000/javamall/20220213204746%2B0800p0653204058658-item-783fxf4x1000x1000-m.jpg', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (17, 10, NULL, 'http://localhost:9000/javamall/20220213204748%2B0800p0653204058658-item-d161xf4x1000x1000-m.jpg', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (18, 11, NULL, 'http://localhost:9000/javamall/20220213214220%2B0800p0653204058658-item-783fxf4x1000x1000-m.jpg', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (19, 11, NULL, 'http://localhost:9000/javamall/20220213214222%2B0800p0653204058658-item-7026xf4x1000x1000-m.jpg', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (20, 11, NULL, 'http://localhost:9000/javamall/20220213214225%2B0800p0653204058658-item-d161xf4x1000x1000-m.jpg', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (21, 12, NULL, 'http://localhost:9000/javamall/20220214220145%2B0800main.jpg', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (22, 12, NULL, 'http://localhost:9000/javamall/20220214220222%2B0800violet.jpg', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (23, 12, NULL, 'http://localhost:9000/javamall/20220214220236%2B0800silver.jpg', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (24, 13, NULL, 'http://localhost:9000/javamall/20220214221009%2B0800main.jpg', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (25, 13, NULL, 'http://localhost:9000/javamall/20220214221012%2B0800silver.jpg', NULL, NULL);
INSERT INTO `t_product_spu_images` VALUES (26, 13, NULL, 'http://localhost:9000/javamall/20220214221015%2B0800violet.jpg', NULL, NULL);

-- ----------------------------
-- Table structure for t_product_spu_info
-- ----------------------------
DROP TABLE IF EXISTS `t_product_spu_info`;
CREATE TABLE `t_product_spu_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `spu_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名稱',
  `spu_description` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品描述',
  `catalog_id` bigint(20) NULL DEFAULT NULL COMMENT '所屬分類id',
  `brand_id` bigint(20) NULL DEFAULT NULL COMMENT '品牌id',
  `weight` decimal(18, 4) NULL DEFAULT NULL,
  `publish_status` tinyint(4) NULL DEFAULT NULL COMMENT '上架狀態[0 - 下架，1 - 上架]',
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'spu信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product_spu_info
-- ----------------------------
INSERT INTO `t_product_spu_info` VALUES (1, 'iPhone 13 pro', 'iPhone 13 pro相機系統，威力驚人提升。 顯示器反應超靈敏，讓體驗全面更新。 全世界最快速的智慧型手機晶片。 耐用度格外出色。 電池續航力更是大幅躍升。', 225, 1, NULL, 1, '2022-02-12 18:43:55', '2022-02-14 22:25:37');
INSERT INTO `t_product_spu_info` VALUES (2, '小米11', '小米11', 225, 2, NULL, 1, '2022-02-12 18:51:44', '2022-02-13 18:41:29');
INSERT INTO `t_product_spu_info` VALUES (11, '棉麻刺繡V領繫帶拼接寬鬆短袖T恤上衣', '日系小麥繩紋刺繡，清新優雅。造型綁帶V領設計，甜美時尚。春夏必備棉麻單品，清爽透氣。', 650, 3, NULL, 1, '2022-02-13 21:43:19', '2022-02-13 21:47:45');
INSERT INTO `t_product_spu_info` VALUES (13, 'Samsung Galaxy S21', '三主鏡超強攝影旗艦機(8G/256G)高通S888旗艦處理器 6.7吋平面FHD+螢幕 4800mAh長效續航', 225, 4, NULL, 1, '2022-02-14 22:10:55', '2022-02-14 22:23:56');

-- ----------------------------
-- Table structure for t_product_spu_info_desc
-- ----------------------------
DROP TABLE IF EXISTS `t_product_spu_info_desc`;
CREATE TABLE `t_product_spu_info_desc`  (
  `spu_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `decript` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品介紹',
  PRIMARY KEY (`spu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'spu信息介紹' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_product_spu_info_desc
-- ----------------------------
INSERT INTO `t_product_spu_info_desc` VALUES (1, 'http://localhost:9000/javamall/20220212184254%2B0800iphone-13-pro-blue-select.jpg');
INSERT INTO `t_product_spu_info_desc` VALUES (2, 'http://localhost:9000/javamall/20220212185112%2B0800Xiaomi_xiaomi_11_1229063029339_360x270.jpg');
INSERT INTO `t_product_spu_info_desc` VALUES (6, '');
INSERT INTO `t_product_spu_info_desc` VALUES (7, 'http://localhost:9000/javamall/20220213190722%2B0800spu.png');
INSERT INTO `t_product_spu_info_desc` VALUES (8, 'http://localhost:9000/javamall/20220213195637%2B0800%E5%95%86%E5%93%81%E4%BB%8B%E7%B4%B91.png,http://localhost:9000/javamall/20220213195707%2B0800%E5%95%86%E5%93%81%E4%BB%8B%E7%B4%B92.png');
INSERT INTO `t_product_spu_info_desc` VALUES (9, 'http://localhost:9000/javamall/20220213201527%2B0800%E5%95%86%E5%93%81%E4%BB%8B%E7%B4%B91.png,http://localhost:9000/javamall/20220213201529%2B0800%E5%95%86%E5%93%81%E4%BB%8B%E7%B4%B92.png');
INSERT INTO `t_product_spu_info_desc` VALUES (10, 'http://localhost:9000/javamall/20220213204737%2B0800118aeabb580242ac110002.jpg,http://localhost:9000/javamall/20220213204740%2B080011c2ea96c80242ac110002.jpg');
INSERT INTO `t_product_spu_info_desc` VALUES (11, 'http://localhost:9000/javamall/20220213214215%2B0800118aeabb580242ac110002.jpg,http://localhost:9000/javamall/20220213214217%2B080011c2ea96c80242ac110002.jpg');
INSERT INTO `t_product_spu_info_desc` VALUES (12, 'http://localhost:9000/javamall/20220214220140%2B0800detail1.jpg,http://localhost:9000/javamall/20220214220143%2B0800detail2.jpg');
INSERT INTO `t_product_spu_info_desc` VALUES (13, 'http://localhost:9000/javamall/20220214221005%2B0800detail1.jpg,http://localhost:9000/javamall/20220214221007%2B0800detail2.jpg');

-- ----------------------------
-- Table structure for t_ware_info
-- ----------------------------
DROP TABLE IF EXISTS `t_ware_info`;
CREATE TABLE `t_ware_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '倉庫名',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '倉庫地址',
  `areacode` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '區域編碼',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '倉庫信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ware_info
-- ----------------------------

-- ----------------------------
-- Table structure for t_ware_order_task
-- ----------------------------
DROP TABLE IF EXISTS `t_ware_order_task`;
CREATE TABLE `t_ware_order_task`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT 'order_id',
  `order_sn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'order_sn',
  `consignee` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收貨人',
  `consignee_tel` char(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收貨人電話',
  `delivery_address` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '配送地址',
  `order_comment` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '訂單備註',
  `payment_way` tinyint(1) NULL DEFAULT NULL COMMENT '付款方式【 1:在線付款 2:貨到付款】',
  `task_status` tinyint(4) NULL DEFAULT NULL COMMENT '任務狀態',
  `order_body` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '訂單描述',
  `tracking_no` char(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '物流單號',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT 'create_time',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '倉庫id',
  `task_comment` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作單備註',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '庫存工作單' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ware_order_task
-- ----------------------------
INSERT INTO `t_ware_order_task` VALUES (11, NULL, '202202131659116791492785412798685186', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-02-13 16:59:12', NULL, NULL);
INSERT INTO `t_ware_order_task` VALUES (13, NULL, '202202131659449951492785552477396994', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-02-13 16:59:45', NULL, NULL);
INSERT INTO `t_ware_order_task` VALUES (14, NULL, '202202131802496701492801426550689793', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-02-13 18:02:50', NULL, NULL);
INSERT INTO `t_ware_order_task` VALUES (15, NULL, '202202131804011131492801726242099202', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-02-13 18:04:01', NULL, NULL);
INSERT INTO `t_ware_order_task` VALUES (16, NULL, '202202142258129591493238151345352705', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2022-02-14 22:58:13', NULL, NULL);

-- ----------------------------
-- Table structure for t_ware_order_task_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_ware_order_task_detail`;
CREATE TABLE `t_ware_order_task_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'sku_id',
  `sku_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'sku_name',
  `sku_num` int(11) NULL DEFAULT NULL COMMENT '購買個數',
  `task_id` bigint(20) NULL DEFAULT NULL COMMENT '工作單id',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '倉庫id',
  `lock_status` int(11) NULL DEFAULT NULL COMMENT '1鎖定2解鎖',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '庫存工作單' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ware_order_task_detail
-- ----------------------------
INSERT INTO `t_ware_order_task_detail` VALUES (1, 1, NULL, 3, 1, 1, 2);
INSERT INTO `t_ware_order_task_detail` VALUES (2, 1, NULL, 2, 2, 1, 2);
INSERT INTO `t_ware_order_task_detail` VALUES (3, 1, NULL, 2, 3, 1, 2);
INSERT INTO `t_ware_order_task_detail` VALUES (4, 1, NULL, 2, 4, 1, 2);
INSERT INTO `t_ware_order_task_detail` VALUES (5, 1, NULL, 2, 5, 1, 2);
INSERT INTO `t_ware_order_task_detail` VALUES (6, 1, NULL, 2, 6, 1, 2);
INSERT INTO `t_ware_order_task_detail` VALUES (7, 1, NULL, 2, 7, 1, 1);
INSERT INTO `t_ware_order_task_detail` VALUES (8, 1, NULL, 2, 8, 1, 2);
INSERT INTO `t_ware_order_task_detail` VALUES (9, 1, NULL, 2, 9, 1, 2);
INSERT INTO `t_ware_order_task_detail` VALUES (10, 1, NULL, 2, 10, 1, 2);
INSERT INTO `t_ware_order_task_detail` VALUES (11, 1, NULL, 2, 11, 1, 2);
INSERT INTO `t_ware_order_task_detail` VALUES (12, 1, NULL, 2, 13, 1, 2);
INSERT INTO `t_ware_order_task_detail` VALUES (13, 1, NULL, 2, 14, 1, 1);
INSERT INTO `t_ware_order_task_detail` VALUES (14, 1, NULL, 2, 15, 1, 1);
INSERT INTO `t_ware_order_task_detail` VALUES (15, 31, NULL, 1, 16, 1, 1);

-- ----------------------------
-- Table structure for t_ware_purchase
-- ----------------------------
DROP TABLE IF EXISTS `t_ware_purchase`;
CREATE TABLE `t_ware_purchase`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `assignee_id` bigint(20) NULL DEFAULT NULL,
  `assignee_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `phone` char(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `priority` int(11) NULL DEFAULT NULL,
  `status` int(11) NULL DEFAULT NULL,
  `ware_id` bigint(20) NULL DEFAULT NULL,
  `amount` decimal(18, 4) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '採購信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ware_purchase
-- ----------------------------

-- ----------------------------
-- Table structure for t_ware_purchase_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_ware_purchase_detail`;
CREATE TABLE `t_ware_purchase_detail`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_id` bigint(20) NULL DEFAULT NULL COMMENT '採購單id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT '採購商品id',
  `sku_num` int(11) NULL DEFAULT NULL COMMENT '採購數量',
  `sku_price` decimal(18, 4) NULL DEFAULT NULL COMMENT '採購金額',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '倉庫id',
  `status` int(11) NULL DEFAULT NULL COMMENT '狀態[0新建，1已分配，2正在採購，3已完成，4採購失敗]',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ware_purchase_detail
-- ----------------------------

-- ----------------------------
-- Table structure for t_ware_sku
-- ----------------------------
DROP TABLE IF EXISTS `t_ware_sku`;
CREATE TABLE `t_ware_sku`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `sku_id` bigint(20) NULL DEFAULT NULL COMMENT 'sku_id',
  `ware_id` bigint(20) NULL DEFAULT NULL COMMENT '倉庫id',
  `stock` int(11) NULL DEFAULT NULL COMMENT '庫存數',
  `sku_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'sku_name',
  `stock_locked` int(11) NULL DEFAULT NULL COMMENT '鎖定庫存',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '商品庫存' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_ware_sku
-- ----------------------------
INSERT INTO `t_ware_sku` VALUES (1, 1, 1, 8, 'iPhone 13 pro 黑', 6);
INSERT INTO `t_ware_sku` VALUES (2, 30, 1, 10, 'Samsung Galaxy S21 星魅紫', 0);
INSERT INTO `t_ware_sku` VALUES (3, 31, 1, 20, 'Samsung Galaxy S21 星魅銀', 1);

SET FOREIGN_KEY_CHECKS = 1;
