/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : finance

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 17/10/2018 17:52:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for banner
-- ----------------------------
DROP TABLE IF EXISTS `banner`;
CREATE TABLE `banner`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '地址',
  `link_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '跳转地址',
  `sort` smallint(6) NULL DEFAULT 0 COMMENT '排序',
  `operate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '操作人员ID',
  `operate_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '操作人员名称',
  `status` smallint(6) NULL DEFAULT 0 COMMENT 'banner状态：0-正常，1-禁用，9-删除',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = 'banner配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for base_room
-- ----------------------------
DROP TABLE IF EXISTS `base_room`;
CREATE TABLE `base_room`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `type` smallint(6) NULL DEFAULT NULL COMMENT '类型：1-数字货币，2-A股',
  `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '图标地址',
  `level` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '倍数，以逗号分隔取值',
  `sort` smallint(6) NULL DEFAULT 0 COMMENT '排序',
  `operate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '操作人员ID',
  `operate_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '操作人员名称',
  `status` smallint(6) NULL DEFAULT 0 COMMENT '场次状态：0-正常，1-禁用，9-删除',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '场次基本信息配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for cash_order
-- ----------------------------
DROP TABLE IF EXISTS `cash_order`;
CREATE TABLE `cash_order`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户ID',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '订单号',
  `amount` double(10, 2) NULL DEFAULT 0.00 COMMENT '金额',
  `fee` double(10, 2) NULL DEFAULT 0.00 COMMENT '手续费',
  `operate_mode_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '操作方式ID',
  `operate_mode_type` smallint(6) NULL DEFAULT NULL COMMENT '操作方式类型：1-支付宝，2-微信，3-银行卡',
  `cash_account` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '提现账号',
  `status` smallint(6) NULL DEFAULT 0 COMMENT '订单状态：0-审核中，1-审核通过，2-审核拒绝',
  `review_time` timestamp(0) NULL DEFAULT NULL COMMENT '审核时间',
  `sys_user_id` varchar(0) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '审核人ID',
  `sys_user_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '审核人名称',
  `review_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '审核备注',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '提现订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for index
-- ----------------------------
DROP TABLE IF EXISTS `index`;
CREATE TABLE `index`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `code` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '代码',
  `market` varchar(6) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '市场',
  `sort` smallint(6) NULL DEFAULT 0 COMMENT '排序',
  `operate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '操作人员ID',
  `operate_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '操作人员名称',
  `status` smallint(6) NULL DEFAULT 0 COMMENT '行情指数状态：0-正常，1-禁用，9-删除',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '行情指数 配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for log_room
-- ----------------------------
DROP TABLE IF EXISTS `log_room`;
CREATE TABLE `log_room`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `room_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '场次记录ID',
  `room_info` longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '上一次场次记录信息，JSON数据格式展示',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '转入，转出操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for log_sms
-- ----------------------------
DROP TABLE IF EXISTS `log_sms`;
CREATE TABLE `log_sms`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `mobile` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '手机号码',
  `msg` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '发送消息',
  `send_time` timestamp(0) NULL DEFAULT NULL COMMENT '发送时间',
  `send_result` smallint(6) NULL DEFAULT 0 COMMENT '发送结果：0-发送成功，1-发送失败',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '短信发送日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for log_user_amount
-- ----------------------------
DROP TABLE IF EXISTS `log_user_amount`;
CREATE TABLE `log_user_amount`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户ID',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '订单号',
  `operate_type` smallint(6) NULL DEFAULT NULL COMMENT '操作类型：1-充值，2-提现，3-转入，4-转出',
  `operate_result` smallint(6) NULL DEFAULT 0 COMMENT '操作结果：0-审核中，1-成功，2-失败',
  `amount` double(10, 2) NULL DEFAULT 0.00 COMMENT '发生金额',
  `remarks` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '用户资金操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for log_user_login
-- ----------------------------
DROP TABLE IF EXISTS `log_user_login`;
CREATE TABLE `log_user_login`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户ID',
  `login_ip` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '登录的IP地址',
  `client_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '客户端唯一性标识',
  `mac` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT 'PC端Mac地址',
  `token` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '登录token，登录异常时为异常提示信息',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '用户登录日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for operate_mode
-- ----------------------------
DROP TABLE IF EXISTS `operate_mode`;
CREATE TABLE `operate_mode`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '显示名称',
  `qr_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '二维码地址',
  `type` smallint(6) NULL DEFAULT NULL COMMENT '类型：1-支付宝，2-微信，3-银行卡',
  `sort` smallint(6) NULL DEFAULT 0 COMMENT '排序',
  `operate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '操作人员ID',
  `operate_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '操作人员名称',
  `status` smallint(6) NULL DEFAULT 0 COMMENT '操作方式状态：0-正常，1-禁用，9-删除',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '充值或者提现的操作方式 配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for pay_order
-- ----------------------------
DROP TABLE IF EXISTS `pay_order`;
CREATE TABLE `pay_order`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户ID',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '订单号',
  `product_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '商品ID',
  `fictitious_coin` double(10, 2) NULL DEFAULT 0.00 COMMENT '虚拟币',
  `amount` double(10, 2) NULL DEFAULT 0.00 COMMENT '金额',
  `operate_mode_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '操作方式ID',
  `operate_mode_type` smallint(6) NULL DEFAULT NULL COMMENT '操作方式类型：1-支付宝，2-微信，3-银行卡',
  `status` smallint(6) NULL DEFAULT 0 COMMENT '订单状态：0-审核中，1-审核通过，2-审核拒绝',
  `review_time` timestamp(0) NULL DEFAULT NULL COMMENT '审核时间',
  `sys_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '审核人ID',
  `sys_user_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '审核人名称',
  `review_desc` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '审核备注',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '充值订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for position
-- ----------------------------
DROP TABLE IF EXISTS `position`;
CREATE TABLE `position`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '标的名称',
  `code` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '标的代码',
  `market` varchar(6) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '市场名称',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户ID',
  `room_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '场次记录ID',
  `type` smallint(6) NULL DEFAULT NULL COMMENT '类型：1-数字货币，2-A股',
  `direction` smallint(6) NULL DEFAULT NULL COMMENT '方向：1-认涨，2-认跌',
  `num` int(11) NULL DEFAULT 0 COMMENT '持仓数量',
  `buy_num` int(11) NULL DEFAULT 0 COMMENT '今日购买数量',
  `usable_num` int(11) NULL DEFAULT 0 COMMENT '可用数量',
  `frozen_num` int(11) NULL DEFAULT 0 COMMENT '冻结数量',
  `buy_price` double(10, 2) NULL DEFAULT 0.00 COMMENT '买入总价格',
  `sell_price` double(10, 2) NULL DEFAULT 0.00 COMMENT '卖出总价格',
  `buy_fee` double(10, 2) NULL DEFAULT 0.00 COMMENT '买入总手续费',
  `sell_fee` double(10, 2) NULL DEFAULT 0.00 COMMENT '卖出总手续费',
  `win_price` double(10, 2) NULL DEFAULT 0.00 COMMENT '止盈价',
  `fail_price` double(10, 2) NULL DEFAULT 0.00 COMMENT '止损价',
  `status` smallint(6) NULL DEFAULT 0 COMMENT '状态：0-正常，1-无效，2-已结算退场',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '用户持仓表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '产品名称',
  `fictitious_coin` double(10, 2) NULL DEFAULT NULL COMMENT '虚拟币',
  `amount` double(10, 2) NULL DEFAULT NULL COMMENT '金额',
  `icon_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '图标地址',
  `operate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '操作人员ID',
  `operate_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '操作人员名称',
  `status` smallint(6) NULL DEFAULT 0 COMMENT '商品状态：0-正常，1-禁用，9-删除',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '商品配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for proxy
-- ----------------------------
DROP TABLE IF EXISTS `proxy`;
CREATE TABLE `proxy`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户ID',
  `mobile` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '手机号码',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '登录密码',
  `bank_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT '1' COMMENT '银行卡号',
  `id_card` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '身份证号码',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '姓名',
  `bank_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '开户行',
  `sex` smallint(6) NULL DEFAULT 1 COMMENT '性别：0-女，1-男',
  `proxy_level` smallint(6) NULL DEFAULT NULL COMMENT '代理级别，值越小，等级越高',
  `audit_status` smallint(6) NULL DEFAULT NULL COMMENT '审核状态：0-待审核，1-审核通过，2-审核拒绝',
  `audit_time` timestamp(0) NULL DEFAULT NULL COMMENT '审核时间',
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '上级代理ID',
  `extract_ratio` double(2, 2) NULL DEFAULT NULL COMMENT '提成比例',
  `extract_time` timestamp(0) NULL DEFAULT NULL COMMENT '提成比例生效时间',
  `last_login_time` timestamp(0) NULL DEFAULT NULL COMMENT '最后一次登录时间',
  `status` smallint(6) NULL DEFAULT 0 COMMENT '代理用户状态：0-正常，1-禁用，9-删除',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '代理用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for proxy_account
-- ----------------------------
DROP TABLE IF EXISTS `proxy_account`;
CREATE TABLE `proxy_account`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `proxy_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '代理用户ID',
  `subordinate_num` int(11) NULL DEFAULT 0 COMMENT '下级的用户总数量',
  `amount` double(10, 2) NULL DEFAULT 0.00 COMMENT '总金额',
  `frozen_price` double(10, 2) NULL DEFAULT 0.00 COMMENT '总冻结金额',
  `usable_price` double(10, 2) NULL DEFAULT NULL COMMENT '总可用金额',
  `settle_price` double(10, 2) NULL DEFAULT NULL COMMENT '总提现金额',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '代理用户金额表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for proxy_detail
-- ----------------------------
DROP TABLE IF EXISTS `proxy_detail`;
CREATE TABLE `proxy_detail`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `proxy_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '代理用户ID',
  `amount` double(10, 2) NULL DEFAULT 0.00 COMMENT '结算金额',
  `time` timestamp(0) NULL DEFAULT NULL COMMENT '结算时间',
  `pay_price` double(10, 2) NULL DEFAULT 0.00 COMMENT '本日充值金额',
  `subordinate_contribution` double(10, 2) NULL DEFAULT 0.00 COMMENT '下级的贡献额度',
  `frozen_price` double(10, 2) NULL DEFAULT 0.00 COMMENT '冻结金额',
  `usable_price` double(10, 2) NULL DEFAULT NULL COMMENT '可领取金额',
  `status` smallint(6) NULL DEFAULT 0 COMMENT '结算状态：0-审核中，1-审核通过，2-审核拒绝',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '代理用户结算明细' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for room
-- ----------------------------
DROP TABLE IF EXISTS `room`;
CREATE TABLE `room`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户ID',
  `base_room_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '场次ID',
  `type` smallint(6) NULL DEFAULT NULL COMMENT '类型：1-数字货币，2-A股',
  `multiple` int(11) NULL DEFAULT NULL COMMENT '倍数',
  `principal` double(10, 2) NULL DEFAULT 0.00 COMMENT '本金',
  `close_position` double(10, 2) NULL DEFAULT 0.00 COMMENT '额度',
  `bond_amount` double(10, 2) NULL DEFAULT 0.00 COMMENT '保证金',
  `fee` double(10, 2) NULL DEFAULT 0.00 COMMENT '手续费',
  `frozen_price` double(10, 2) NULL DEFAULT 0.00 COMMENT '冻结金额',
  `usable_price` double(10, 2) NULL DEFAULT 0.00 COMMENT '可用交易金额',
  `entrust_price` double(10, 2) NULL DEFAULT 0.00 COMMENT '委托金额',
  `status` smallint(6) NULL DEFAULT NULL COMMENT '状态：0-正常，1-无效，2-已结算退场',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '场次记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `job_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '任务名',
  `job_group` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '任务组',
  `method_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '要执行的方法',
  `bean_class` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '定时任务所在的类路径',
  `cron_expression` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '时间表达式',
  `concurrent` smallint(6) NULL DEFAULT 1 COMMENT '是否并发：0-是，1-否',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '备注',
  `status` smallint(6) NULL DEFAULT NULL COMMENT '状态：0-正常，1-暂停',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '定时任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES ('015c939a44214243af0b1084ba75fcd8', 'test', 'testGroup', 'excute', 'com.fanteng.finance.quartz.task.TestTask', '0/10 * * * * ?', 1, '测试', 0, '2018-06-30 02:16:12', NULL);

-- ----------------------------
-- Table structure for sys_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_resource`;
CREATE TABLE `sys_resource`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '资源名称',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '资源编码',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '资源地址',
  `method` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '访问方式',
  `type` smallint(6) NULL DEFAULT NULL COMMENT '资源类型：0-菜单，1-按钮，2-功能',
  `sort` smallint(6) NULL DEFAULT NULL COMMENT '排序',
  `icon` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '图标',
  `parent_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '上级资源ID',
  `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '资源描述',
  `status` smallint(6) NULL DEFAULT 0 COMMENT '资源状态：0-正常，1-禁用',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '后台资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_resource
-- ----------------------------
INSERT INTO `sys_resource` VALUES ('07cc93688ffb48a598f2e1f3bc5bf24c', '后台资源树形选择', 'SYS_RESOURCE_MENU', '/sysResource/view', 'GET', 2, 4, 'fa fa-tree', '172e6d5275534507aeed3b294880b622', '后台资源树形选择选择框', 0, '2018-10-09 15:15:05', NULL);
INSERT INTO `sys_resource` VALUES ('1093e6d09be543a7b4062f8d27bf6a53', '后台用户新增', 'SYS_USER_SAVE', '/sysUser', 'POST', 1, 1, 'fa fa-plus-circle', 'bde09fdf52e24ef1850575b5b9afd292', '后台用户新增权限', 0, '2018-10-17 15:56:58', NULL);
INSERT INTO `sys_resource` VALUES ('14360ad05ada4a639fc6381ee8b56f27', '后台角色修改', 'SYS_ROLE_UPDATE', '/sysRole', 'PUT', 1, 2, 'fa fa-edit', 'eb95e80d781146589f3d420f5aa71136', '后台角色修改权限', 0, '2018-10-16 15:51:06', NULL);
INSERT INTO `sys_resource` VALUES ('172e6d5275534507aeed3b294880b622', '后台资源管理', 'SYS_RESOURCE', '/sysResource/gotoList', 'GET', 0, 2, 'fa fa-file-text', 'eebaad47f58547f2b541f89b59dff980', '后台资源管理，由开发人员维护', 0, '2018-07-22 12:21:01', NULL);
INSERT INTO `sys_resource` VALUES ('3a06bae96acb4be1b7cc78063c0fa8d7', '后台角色新增或修改页面', 'SYS_ROLE_INFO', '/sysRole/gotoInfo', 'GET', 2, 1, 'fa fa-info-circle', 'eb95e80d781146589f3d420f5aa71136', '跳转至后台角色新增或修改页面', 0, '2018-10-09 16:42:20', NULL);
INSERT INTO `sys_resource` VALUES ('436df2ba5ece4964978dc9d81bf3e0f5', '后台用户列表', 'SYS_USER_LIST', '/sysUser', 'GET', 1, 0, 'fa fa-user', 'bde09fdf52e24ef1850575b5b9afd292', '后台用户页面', 0, '2018-08-27 18:16:51', '2018-10-10 10:02:02');
INSERT INTO `sys_resource` VALUES ('47c489298f60418291fe25d18ae786e9', '后台资源新增', 'SYS_RESOURCE_SAVE', '/sysResource', 'POST', 1, 1, 'fa fa-plus-circle', '172e6d5275534507aeed3b294880b622', '后台资源新增权限', 0, '2018-10-08 15:41:24', NULL);
INSERT INTO `sys_resource` VALUES ('55f66939ec7e4a3a968ea34bc3ac8c8c', '后台用户启用/禁用', 'SYS_USER_USABLE', '/sysUser/usable', 'PUT', 1, 4, 'fa fa-ban', 'bde09fdf52e24ef1850575b5b9afd292', '后台用户启用/禁用权限', 0, '2018-10-17 16:02:10', '2018-10-17 17:49:00');
INSERT INTO `sys_resource` VALUES ('6fe0d5a1d3eb4afaa43de6afb30dcc1a', '后台用户修改', 'SYS_USER_UPDATE', '/sysUser', 'PUT', 1, 2, 'fa fa-edit', 'bde09fdf52e24ef1850575b5b9afd292', '后台用户修改权限', 0, '2018-10-17 15:58:04', '2018-10-17 15:58:24');
INSERT INTO `sys_resource` VALUES ('7889a95b20c042019a75b9dabcc21df0', '后台角色启用/禁用', 'SYS_ROLE_USABLE', '/sysRole/usable', 'PUT', 1, 2, 'fa fa-ban', 'eb95e80d781146589f3d420f5aa71136', '后台角色启用/禁用权限', 0, '2018-10-17 15:46:51', '2018-10-17 15:49:15');
INSERT INTO `sys_resource` VALUES ('82100d63a2d84d889c44a5d68cb03480', '后台用户删除', 'SYS_USER_DELETE', '/sysUser', 'DELETE', 1, 3, 'fa fa-remove', 'bde09fdf52e24ef1850575b5b9afd292', '后台用户删除权限', 0, '2018-10-17 15:59:53', NULL);
INSERT INTO `sys_resource` VALUES ('952854adceb046378f0c682dd9d0fc90', '验证后台角色代码', 'SYS_ROLE_CODE', '/sysRole/checkCode', 'POST', 2, 2, 'fa fa-anchor', 'eb95e80d781146589f3d420f5aa71136', '验证后台角色代码是否唯一', 0, '2018-10-09 17:39:31', NULL);
INSERT INTO `sys_resource` VALUES ('a86045834b024551b419ef9c5ba508bf', '后台角色权限', 'SYS_ROLE_PERMISSION', '/sysRole/getPermission', 'POST', 2, 3, 'fa fa-bolt', 'eb95e80d781146589f3d420f5aa71136', '后台角色权限展示', 0, '2018-10-09 18:17:06', NULL);
INSERT INTO `sys_resource` VALUES ('b34f49a564ed4999801c694562db30ae', '后台用户新增或修改页面', 'SYS_USER_INFO', '/sysUser/gotoInfo', 'GET', 2, 1, 'fa fa-info-circle', 'bde09fdf52e24ef1850575b5b9afd292', '跳转至后台用户新增或修改页面', 0, '2018-10-17 11:38:18', NULL);
INSERT INTO `sys_resource` VALUES ('bde09fdf52e24ef1850575b5b9afd292', '后台用户管理', 'SYS_USER', '/sysUser/gotoList', 'GET', 0, 0, 'fa fa-user', 'eebaad47f58547f2b541f89b59dff980', '后台用户管理，由开发人员维护', 0, '2018-07-22 12:12:41', NULL);
INSERT INTO `sys_resource` VALUES ('c221c9a353c141a3aa41cab0751d8b84', '后台资源删除', 'SYS_RESOURCE_DELETE', '/sysResource', 'DELETE', 1, 3, 'fa fa-remove', '172e6d5275534507aeed3b294880b622', '后台资源删除权限', 0, '2018-10-08 15:50:07', NULL);
INSERT INTO `sys_resource` VALUES ('c6daf6c28ce8470191d22b4258363d09', '后台资源修改', 'SYS_RESOURCE_EDIT', '/sysResource', 'PUT', 1, 2, 'fa fa-edit', '172e6d5275534507aeed3b294880b622', '后台资源修改权限', 0, '2018-10-08 15:48:37', NULL);
INSERT INTO `sys_resource` VALUES ('cac138901c3d4f22a515876840ce517b', '后台角色新增', 'SYS_ROLE_SAVE', '/sysRole', 'POST', 1, 1, 'fa fa-plus-circle', 'eb95e80d781146589f3d420f5aa71136', '后台角色新增权限', 0, '2018-10-16 10:27:31', NULL);
INSERT INTO `sys_resource` VALUES ('d4bcfb18749f44549db6f938685805d4', '后台资源新增或修改页面', 'SYS_RESOURCE_INFO', '/sysResource/gotoInfo', 'GET', 2, 5, 'fa fa-info-circle', '172e6d5275534507aeed3b294880b622', '跳转至后台资源新增或修改页面', 0, '2018-10-09 15:17:37', '2018-10-09 16:42:54');
INSERT INTO `sys_resource` VALUES ('d72db9034bc044ad81acd6cef2e63be1', '后台角色列表', 'SYS_ROLE_LIST', '/sysRole', 'GET', 1, 0, 'fa fa-key', 'eb95e80d781146589f3d420f5aa71136', '后台角色页面', 0, '2018-09-04 21:47:02', '2018-10-10 10:02:12');
INSERT INTO `sys_resource` VALUES ('dc092418c0b142ee945fbb443a23e741', '后台角色删除', 'SYS_ROLE_DELETE', '/sysRole', 'DELETE', 1, 3, 'fa fa-remove', 'eb95e80d781146589f3d420f5aa71136', '后台角色删除权限', 0, '2018-10-16 15:50:11', NULL);
INSERT INTO `sys_resource` VALUES ('e6215cecf00a45ef8e331734386ca1eb', '后台资源启用/禁用', 'SYS_RESOURCE_USABLE', '/sysResource/usable', 'PUT', 1, 4, 'fa fa-ban', '172e6d5275534507aeed3b294880b622', '后台资源启用/禁用权限', 0, '2018-10-17 15:35:35', NULL);
INSERT INTO `sys_resource` VALUES ('eb95e80d781146589f3d420f5aa71136', '后台角色管理', 'SYS_ROLE', '/sysRole/gotoList', 'GET', 0, 1, 'fa fa-key', 'eebaad47f58547f2b541f89b59dff980', '后台角色管理，由开发人员维护', 0, '2018-07-22 12:17:32', NULL);
INSERT INTO `sys_resource` VALUES ('eebaad47f58547f2b541f89b59dff980', '系统管理', 'SYS_MANAGE', NULL, 'GET', 0, 0, 'fa fa-gears', NULL, '后台系统管理，由开发人员维护', 0, '2018-07-22 12:05:22', NULL);
INSERT INTO `sys_resource` VALUES ('f41c9cb3a8e041a7b596ebf08882cdf4', '验证后台资源代码', 'SYS_RESOURCE_CODE', '/sysResource/checkCode', 'POST', 2, 6, 'fa fa-anchor', '172e6d5275534507aeed3b294880b622', '验证后台资源代码是否唯一', 0, '2018-10-09 15:20:02', '2018-10-09 17:36:27');
INSERT INTO `sys_resource` VALUES ('fce787f79ca649769a7f0aafeb5ea165', '后台资源列表', 'SYS_RESOURCE_LIST', '/sysResource', 'GET', 1, 0, 'fa fa-file-text', '172e6d5275534507aeed3b294880b622', '后台资源页面', 0, '2018-09-04 21:45:05', '2018-10-10 10:01:47');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '角色名称',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '角色编码',
  `status` smallint(6) NULL DEFAULT 0 COMMENT '角色状态：0-正常，1-禁用',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '后台角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('2c9d8c5a193a44ffbb962b56696c555e', '后台资源管理查看', 'SYS_RESOURCE_VIEW', 0, '2018-10-16 16:31:08', '2018-10-17 15:49:32');
INSERT INTO `sys_role` VALUES ('d1a9814e21e94c7684dd0703b0c74fe3', '超级管理员', 'ADMINISTRATOR', 0, '2018-07-02 23:22:45', NULL);

-- ----------------------------
-- Table structure for sys_role_resource
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_resource`;
CREATE TABLE `sys_role_resource`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `sys_role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '后台角色ID',
  `sys_resource_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '后台资源ID',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '后台角色 - 后台资源 关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_resource
-- ----------------------------
INSERT INTO `sys_role_resource` VALUES ('18241f91816d4593af984f02ccbcbdea', '2c9d8c5a193a44ffbb962b56696c555e', 'f41c9cb3a8e041a7b596ebf08882cdf4', '2018-10-16 16:31:08');
INSERT INTO `sys_role_resource` VALUES ('5dce5f31a48a457f9215630bdfed5a2b', '2c9d8c5a193a44ffbb962b56696c555e', 'eebaad47f58547f2b541f89b59dff980', '2018-10-16 16:31:08');
INSERT INTO `sys_role_resource` VALUES ('7ea4aa508f8244bba17cb8970aa50818', '2c9d8c5a193a44ffbb962b56696c555e', '07cc93688ffb48a598f2e1f3bc5bf24c', '2018-10-16 16:31:08');
INSERT INTO `sys_role_resource` VALUES ('7ebe024b6967408fb4178917d862e8cf', '2c9d8c5a193a44ffbb962b56696c555e', 'd4bcfb18749f44549db6f938685805d4', '2018-10-16 16:31:08');
INSERT INTO `sys_role_resource` VALUES ('aa622b42a0f747fdbeddd37a15f53566', '2c9d8c5a193a44ffbb962b56696c555e', '172e6d5275534507aeed3b294880b622', '2018-10-16 16:31:08');
INSERT INTO `sys_role_resource` VALUES ('e3256af2959e44379ee6e2a4da954bcc', '2c9d8c5a193a44ffbb962b56696c555e', 'fce787f79ca649769a7f0aafeb5ea165', '2018-10-16 16:31:08');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `nick_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `real_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `user_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `mobile` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '手机号码',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '头像',
  `status` smallint(6) NULL DEFAULT 0 COMMENT '后台用户状态：0-正常，1-禁用，9-删除',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '后台用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('44bfcab8d4a040868c047a9494807b7d', '아이유', '哈哈', 'gemini', '$2a$10$SImUFd9zncI4HDgNI3v3peHM.72sQkN2c2xQQ.oy5h0OK1H24Q0Vm', '17357103526', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539682975555&di=6c56c48fb1da3db0d3ca42d75230f96d&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20180709%2F0d689e50c9f04cf6ab0631303aef5900.gif', 0, '2018-10-17 16:16:12', '2018-10-17 16:21:25');
INSERT INTO `sys_user` VALUES ('5c7a8200248b4adaa382da7602857b9f', '小雅', 'Gemini', 'admin', '$2a$10$B7aO5TVsnFmID7pUhJk95.VAXUf1sm79SI6PHSW/WyPbPCxGREOFC', '18779141750', 'http://img.idol001.com/origin/2017/03/04/7a34c37577fdf173114f8b4a1ebfcd8d1488625417.gif', 0, '2018-07-02 23:24:42', '2018-10-17 16:02:53');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `sys_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '后台用户ID',
  `sys_role_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '后台角色ID',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '后台用户 - 后台角色 关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('3d2c0d89064a4afab13058529f4f32f0', '5c7a8200248b4adaa382da7602857b9f', 'd1a9814e21e94c7684dd0703b0c74fe3', '2018-07-02 23:24:42');
INSERT INTO `sys_user_role` VALUES ('6a77f9cc03bb4fa085178ce2508c2388', '44bfcab8d4a040868c047a9494807b7d', '2c9d8c5a193a44ffbb962b56696c555e', '2018-10-17 16:21:25');

-- ----------------------------
-- Table structure for system_config
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '显示名称',
  `key` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '编码',
  `value` longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '值',
  `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `operate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '操作人员ID',
  `operate_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '操作人员名称',
  `status` smallint(6) NULL DEFAULT NULL COMMENT '配置状态：0-正常，1-禁用，9-删除',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '系统配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tips
-- ----------------------------
DROP TABLE IF EXISTS `tips`;
CREATE TABLE `tips`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `type` smallint(6) NULL DEFAULT NULL COMMENT '类型：1-充值，2-提现',
  `content` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '提示内容',
  `operate_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '操作人员ID',
  `operate_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '操作人员名称',
  `status` smallint(6) NULL DEFAULT NULL COMMENT '状态：0-正常，1-禁用，9-删除',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '提示配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for trade
-- ----------------------------
DROP TABLE IF EXISTS `trade`;
CREATE TABLE `trade`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '标的名称',
  `code` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '标的代码',
  `market` varchar(6) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '市场',
  `type` smallint(6) NULL DEFAULT NULL COMMENT '标的类型：1-数字货币，2-A股',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户ID',
  `position_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '持仓合约ID',
  `room_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '场次ID',
  `entrust_price` double(10, 2) NULL DEFAULT 0.00 COMMENT '委托价格',
  `entrust_num` int(11) NULL DEFAULT 0 COMMENT '委托数量',
  `entrust_type` smallint(6) NULL DEFAULT NULL COMMENT '委托类型：1-限价，2-市价',
  `entrust_time` timestamp(0) NULL DEFAULT NULL COMMENT '委托时间',
  `entrust_direction` smallint(6) NULL DEFAULT NULL COMMENT '委托方向：1-多，2-空',
  `entrust_frozen_price` double(10, 2) NULL DEFAULT 0.00 COMMENT '委托冻结金额',
  `trade_type` smallint(6) NULL DEFAULT NULL COMMENT '交易类型：1-买入，2-卖出',
  `trade_num` int(11) NULL DEFAULT 0 COMMENT '交易数量',
  `trade_price` double(10, 2) NULL DEFAULT 0.00 COMMENT '交易金额',
  `trade_time` timestamp(0) NULL DEFAULT NULL COMMENT '交易时间',
  `fee` double(10, 2) NULL DEFAULT 0.00 COMMENT '手续费',
  `trade_amount` double(10, 2) NULL DEFAULT 0.00 COMMENT '交易总金额，包含手续费',
  `win_price` double(10, 2) NULL DEFAULT NULL COMMENT '止盈价',
  `fail_price` double(10, 2) NULL DEFAULT NULL COMMENT '止损价',
  `forcedsell` smallint(6) NULL DEFAULT NULL COMMENT '强平标志：0-非强平，1-强平',
  `cancel_the_order_num` int(11) NULL DEFAULT 0 COMMENT '撤单数量',
  `status` smallint(6) NULL DEFAULT 0 COMMENT '标的状态：0-委托中，1-成功，2-取消',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '交易标的表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_account
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户ID',
  `amount` double(10, 2) NULL DEFAULT 0.00 COMMENT '账户总余额',
  `pay_price` double(10, 2) NULL DEFAULT 0.00 COMMENT '总充值金额',
  `cash_price` double(10, 2) NULL DEFAULT 0.00 COMMENT '总提现值金额',
  `frozen_price` double(10, 2) NULL DEFAULT 0.00 COMMENT '冻结金额',
  `usable_price` double(10, 2) NULL DEFAULT 0.00 COMMENT '可用金额',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '用户金额表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `invitation_code` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '邀请码',
  `mobile` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '手机号码',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '登录密码',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
  `nick_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户名称',
  `sex` smallint(6) NULL DEFAULT 1 COMMENT '用户性别：0-女，1-男',
  `id_card` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '身份证号码',
  `bank_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '银行卡号',
  `referee_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '推荐人ID',
  `status` smallint(6) NULL DEFAULT 0 COMMENT '用户状态：0-正常，1-禁用，9-删除',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uc_userInfo`(`invitation_code`, `mobile`, `id_card`, `bank_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
