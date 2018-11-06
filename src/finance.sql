/*
 Navicat Premium Data Transfer

 Source Server         : 虚拟机
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : finance

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 06/11/2018 16:39:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for log_user_account
-- ----------------------------
DROP TABLE IF EXISTS `log_user_account`;
CREATE TABLE `log_user_account`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户ID',
  `user_account_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户账户ID',
  `operation_type` smallint(6) NULL DEFAULT 0 COMMENT '操作类型：0-注册奖励，1-充值，2-充值奖励，3-提现，4-转入BTC房间，5-转出BTC房间，6-转入A股房间，7-转出A股房间，8-收取BTC过夜费，9-收取A股过夜费',
  `amount` decimal(18, 2) NULL DEFAULT 0.00 COMMENT '发生金额',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '用户账户记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for operate_config
-- ----------------------------
DROP TABLE IF EXISTS `operate_config`;
CREATE TABLE `operate_config`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `value` smallint(6) NULL DEFAULT NULL COMMENT '值：1-支付宝，2-微信，3-银行卡',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '图片地址',
  `type` smallint(6) NULL DEFAULT NULL COMMENT '类型：1-充值，2-提现',
  `status` smallint(6) NULL DEFAULT 0 COMMENT '状态：0-正常，1-禁用',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '充值 - 提现 方式配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of operate_config
-- ----------------------------
INSERT INTO `operate_config` VALUES ('1c81a570ee4c4a578892c81e88d1f928', '支付宝', 1, 'http://192.168.1.29/group1/M00/00/00/wKgBHVvhRdWAf334AAAhqEWjpGQ59..jpg', 1, 0, '2018-11-05 16:51:03', '2018-11-06 15:42:14');
INSERT INTO `operate_config` VALUES ('3be0b45abc4443efbc38763e78bd058e', '微信', 2, NULL, 2, 0, '2018-11-05 16:52:12', NULL);
INSERT INTO `operate_config` VALUES ('3c710775a266408291a6fe0e82eb798e', '支付宝', 1, NULL, 2, 0, '2018-11-05 16:52:02', NULL);
INSERT INTO `operate_config` VALUES ('3f1497b131094b8c97954f41210f3b68', '银行卡', 3, NULL, 1, 0, '2018-11-05 16:51:49', NULL);
INSERT INTO `operate_config` VALUES ('cb966d1ea38b4047946cdc10456eefa0', '银行卡', 3, NULL, 2, 0, '2018-11-05 16:52:27', NULL);
INSERT INTO `operate_config` VALUES ('d6a820c7e2614f21ab869f27038c3baa', '微信', 2, NULL, 1, 0, '2018-11-05 16:51:31', NULL);

-- ----------------------------
-- Table structure for recharge_order
-- ----------------------------
DROP TABLE IF EXISTS `recharge_order`;
CREATE TABLE `recharge_order`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户账号',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '订单号',
  `amount` decimal(18, 2) NULL DEFAULT 0.00 COMMENT '订单金额',
  `type` smallint(6) NULL DEFAULT 1 COMMENT '充值方式：1-支付宝，2-微信，3-银行卡',
  `status` smallint(6) NULL DEFAULT 0 COMMENT '充值状态：0-审核中，1-审核通过，2-审核拒绝',
  `sys_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '审核人员ID',
  `sys_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '审核人员姓名',
  `audit_note` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '审核备注',
  `audit_time` timestamp(0) NULL DEFAULT NULL COMMENT '审核时间',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '充值订单表' ROW_FORMAT = Dynamic;

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
INSERT INTO `schedule_job` VALUES ('015c939a44214243af0b1084ba75fcd8', 'test', 'testGroup', 'excute', 'com.fanteng.finance.quartz.task.TestTask', '0/30 * * * * ?', 1, '测试', 1, '2018-06-30 02:16:12', '2018-10-31 11:19:15');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '编码',
  `content` longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '内容',
  `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '描述',
  `status` smallint(6) NULL DEFAULT 0 COMMENT '配置状态：0-正常，1-禁用',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '系统配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('a7f7ad6ab8f0435e86300c2273f0b6d1', '用户注册奖励开关', 'user_register_switch', 'true', '用户注册奖励开关', 0, '2018-11-01 16:43:37', NULL);
INSERT INTO `sys_config` VALUES ('c72ec47557ea46179e8105f23cfc4ba4', '金币比例配置', 'amount_proportion', '100', '人民币和平台金币的比例', 0, '2018-11-01 17:49:19', NULL);
INSERT INTO `sys_config` VALUES ('e5b423dd5a624e148640acb4459d23e8', '用户注册奖励金额', 'user_register_amount', '6600', '用户注册奖励金额', 0, '2018-11-01 16:45:11', NULL);

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
INSERT INTO `sys_resource` VALUES ('043ed750ed734b378d00cec669081f3d', '充值管理', 'RECHARGEORDER', '/rechargeOrder/gotoList', 'GET', 0, 1, 'fa fa-money', 'fb621735b4484c529fa1112136f9ac39', '充值列表页面', 0, '2018-11-05 17:09:57', NULL);
INSERT INTO `sys_resource` VALUES ('04c09eb50db44a0091c173a7a5d08e4e', '充值审核', 'RECHARGEORDER_AUDIT', '/rechargeOrder', 'PUT', 1, 1, 'fa fa-gavel', '043ed750ed734b378d00cec669081f3d', '充值审核权限', 0, '2018-11-05 17:14:29', NULL);
INSERT INTO `sys_resource` VALUES ('07cc93688ffb48a598f2e1f3bc5bf24c', '后台资源树形选择', 'SYS_RESOURCE_MENU', '/sysResource/view', 'GET', 2, 4, 'fa fa-tree', '172e6d5275534507aeed3b294880b622', '后台资源树形选择选择框', 0, '2018-10-09 15:15:05', NULL);
INSERT INTO `sys_resource` VALUES ('0d710818c76744d4b9b9e9c22e5ee229', '后台系统配置新增或修改页面', 'SYS_CONFIG_INFO', '/sysConfig/gotoInfo', 'GET', 2, 3, 'fa fa-info-circle', '7f375cff304a499296eeecd58ed1abb8', '跳转至后台系统配置新增或修改页面', 0, '2018-10-29 10:10:08', NULL);
INSERT INTO `sys_resource` VALUES ('0f491c03b75d47b3818d6f0f28f8bc8a', '后台系统配置列表', 'SYS_CONFIG_LIST', '/sysConfig', 'GET', 1, 0, 'fa fa-search', '7f375cff304a499296eeecd58ed1abb8', '后台配置列表查询', 0, '2018-10-29 10:02:39', NULL);
INSERT INTO `sys_resource` VALUES ('1093e6d09be543a7b4062f8d27bf6a53', '后台用户新增', 'SYS_USER_SAVE', '/sysUser', 'POST', 1, 1, 'fa fa-plus-circle', 'bde09fdf52e24ef1850575b5b9afd292', '后台用户新增权限', 0, '2018-10-17 15:56:58', NULL);
INSERT INTO `sys_resource` VALUES ('14360ad05ada4a639fc6381ee8b56f27', '后台角色修改', 'SYS_ROLE_UPDATE', '/sysRole', 'PUT', 1, 2, 'fa fa-edit', 'eb95e80d781146589f3d420f5aa71136', '后台角色修改权限', 0, '2018-10-16 15:51:06', NULL);
INSERT INTO `sys_resource` VALUES ('172e6d5275534507aeed3b294880b622', '后台资源管理', 'SYS_RESOURCE', '/sysResource/gotoList', 'GET', 0, 2, 'fa fa-file-text', 'eebaad47f58547f2b541f89b59dff980', '后台资源管理，由开发人员维护', 0, '2018-07-22 12:21:01', NULL);
INSERT INTO `sys_resource` VALUES ('3a06bae96acb4be1b7cc78063c0fa8d7', '后台角色新增或修改页面', 'SYS_ROLE_INFO', '/sysRole/gotoInfo', 'GET', 2, 1, 'fa fa-info-circle', 'eb95e80d781146589f3d420f5aa71136', '跳转至后台角色新增或修改页面', 0, '2018-10-09 16:42:20', NULL);
INSERT INTO `sys_resource` VALUES ('3e7f05e4a0664f159e08b9766fae37ef', '后台系统配置新增', 'SYS_CONFIG_SAVE', '/sysConfig', 'POST', 1, 1, 'fa fa-plus-circle', '7f375cff304a499296eeecd58ed1abb8', '后台系统配置新增权限', 0, '2018-10-29 10:05:44', NULL);
INSERT INTO `sys_resource` VALUES ('41f8141bf91248ef81965b8789446960', '充值列表查看', 'RECHARGEORDER_LIST', '/rechargeOrder', 'GET', 1, 0, 'fa fa-search', '043ed750ed734b378d00cec669081f3d', '充值列表查看权限', 0, '2018-11-05 17:11:23', NULL);
INSERT INTO `sys_resource` VALUES ('436df2ba5ece4964978dc9d81bf3e0f5', '后台用户列表', 'SYS_USER_LIST', '/sysUser', 'GET', 1, 0, 'fa fa-user', 'bde09fdf52e24ef1850575b5b9afd292', '后台用户页面', 0, '2018-08-27 18:16:51', '2018-10-10 10:02:02');
INSERT INTO `sys_resource` VALUES ('47c489298f60418291fe25d18ae786e9', '后台资源新增', 'SYS_RESOURCE_SAVE', '/sysResource', 'POST', 1, 1, 'fa fa-plus-circle', '172e6d5275534507aeed3b294880b622', '后台资源新增权限', 0, '2018-10-08 15:41:24', NULL);
INSERT INTO `sys_resource` VALUES ('55c84c2900174d4e8bd59605eec985b1', '后台系统配置删除', 'SYS_CONFIG_DELETE', '/sysConfig', 'DELETE', 1, 3, 'fa fa-remove', '7f375cff304a499296eeecd58ed1abb8', '后台系统配置删除权限', 0, '2018-10-29 10:07:47', NULL);
INSERT INTO `sys_resource` VALUES ('55f66939ec7e4a3a968ea34bc3ac8c8c', '后台用户启用/禁用', 'SYS_USER_USABLE', '/sysUser/usable', 'PUT', 1, 4, 'fa fa-ban', 'bde09fdf52e24ef1850575b5b9afd292', '后台用户启用/禁用权限', 0, '2018-10-17 16:02:10', '2018-10-17 17:49:00');
INSERT INTO `sys_resource` VALUES ('58360d4fbf8b464a86b115d98a74de9f', '后台系统配置启用/禁用', 'SYS_CONFIG_USABLE', '/sysConfig/usable', 'PUT', 1, 4, 'fa fa-ban', '7f375cff304a499296eeecd58ed1abb8', '后台系统配置启用/禁用 权限', 0, '2018-10-29 10:08:53', NULL);
INSERT INTO `sys_resource` VALUES ('6fe0d5a1d3eb4afaa43de6afb30dcc1a', '后台用户修改', 'SYS_USER_UPDATE', '/sysUser', 'PUT', 1, 2, 'fa fa-edit', 'bde09fdf52e24ef1850575b5b9afd292', '后台用户修改权限', 0, '2018-10-17 15:58:04', '2018-10-17 15:58:24');
INSERT INTO `sys_resource` VALUES ('70d04e8d022949a4bb740cc3f9fd99a9', '资金操作配置', 'CAPITAL_OPERATION_CONFIG', '/operateConfig/gotoList', 'GET', 0, 5, 'fa fa-anchor', 'fb621735b4484c529fa1112136f9ac39', '资金操作配置页面', 0, '2018-11-05 16:27:06', '2018-11-05 16:35:47');
INSERT INTO `sys_resource` VALUES ('7889a95b20c042019a75b9dabcc21df0', '后台角色启用/禁用', 'SYS_ROLE_USABLE', '/sysRole/usable', 'PUT', 1, 2, 'fa fa-ban', 'eb95e80d781146589f3d420f5aa71136', '后台角色启用/禁用权限', 0, '2018-10-17 15:46:51', '2018-10-17 15:49:15');
INSERT INTO `sys_resource` VALUES ('7f375cff304a499296eeecd58ed1abb8', '后台系统配置', 'SYS_CONFIG', '/sysConfig/gotoList', 'GET', 0, 3, 'fa fa-gear', 'eebaad47f58547f2b541f89b59dff980', '后台系统配置页面', 0, '2018-10-29 10:01:17', NULL);
INSERT INTO `sys_resource` VALUES ('8207de92362f42d5985e7e7e2f595146', '资金操作配置启用/禁用', 'OPERATECONFIG_USABLE', '/operateConfig/usable', 'PUT', 1, 4, 'fa fa-ban', '70d04e8d022949a4bb740cc3f9fd99a9', '资金操作配置启用/禁用权限', 0, '2018-11-05 16:45:19', NULL);
INSERT INTO `sys_resource` VALUES ('82100d63a2d84d889c44a5d68cb03480', '后台用户删除', 'SYS_USER_DELETE', '/sysUser', 'DELETE', 1, 3, 'fa fa-remove', 'bde09fdf52e24ef1850575b5b9afd292', '后台用户删除权限', 0, '2018-10-17 15:59:53', NULL);
INSERT INTO `sys_resource` VALUES ('8cdc836f375144d0834693cb2f641abd', '后台系统配置修改', 'SYS_CONFIG_UPDATE', '/sysConfig', 'PUT', 1, 2, 'fa fa-edit', '7f375cff304a499296eeecd58ed1abb8', '后台系统配置修改权限', 0, '2018-10-29 10:06:46', NULL);
INSERT INTO `sys_resource` VALUES ('952854adceb046378f0c682dd9d0fc90', '验证后台角色代码', 'SYS_ROLE_CODE', '/sysRole/checkCode', 'POST', 2, 2, 'fa fa-anchor', 'eb95e80d781146589f3d420f5aa71136', '验证后台角色代码是否唯一', 0, '2018-10-09 17:39:31', NULL);
INSERT INTO `sys_resource` VALUES ('a86045834b024551b419ef9c5ba508bf', '后台角色权限', 'SYS_ROLE_PERMISSION', '/sysRole/getPermission', 'POST', 2, 3, 'fa fa-bolt', 'eb95e80d781146589f3d420f5aa71136', '后台角色权限展示', 0, '2018-10-09 18:17:06', NULL);
INSERT INTO `sys_resource` VALUES ('ac380a322c994f2abd3db8f57cf94b4a', '资金操作配置新增/修改页面', 'OPERATECONFIG_GOTOINFO', '/operateConfig/gotoInfo', 'GET', 2, 5, 'fa fa-info-circle', '70d04e8d022949a4bb740cc3f9fd99a9', '资金操作配置新增/修改页面', 0, '2018-11-05 16:47:36', NULL);
INSERT INTO `sys_resource` VALUES ('b34f49a564ed4999801c694562db30ae', '后台用户新增或修改页面', 'SYS_USER_INFO', '/sysUser/gotoInfo', 'GET', 2, 1, 'fa fa-info-circle', 'bde09fdf52e24ef1850575b5b9afd292', '跳转至后台用户新增或修改页面', 0, '2018-10-17 11:38:18', NULL);
INSERT INTO `sys_resource` VALUES ('bde09fdf52e24ef1850575b5b9afd292', '后台用户管理', 'SYS_USER', '/sysUser/gotoList', 'GET', 0, 0, 'fa fa-user', 'eebaad47f58547f2b541f89b59dff980', '后台用户管理，由开发人员维护', 0, '2018-07-22 12:12:41', NULL);
INSERT INTO `sys_resource` VALUES ('c221c9a353c141a3aa41cab0751d8b84', '后台资源删除', 'SYS_RESOURCE_DELETE', '/sysResource', 'DELETE', 1, 3, 'fa fa-remove', '172e6d5275534507aeed3b294880b622', '后台资源删除权限', 0, '2018-10-08 15:50:07', NULL);
INSERT INTO `sys_resource` VALUES ('c4c150b45e914ce1abab1a3c3b22935d', '资金操作配置删除', 'OPERATECONFIG_DELETE', '/operateConfig', 'DELETE', 1, 3, 'fa fa-remove', '70d04e8d022949a4bb740cc3f9fd99a9', '资金操作配置删除权限', 0, '2018-11-05 16:43:11', '2018-11-05 16:43:42');
INSERT INTO `sys_resource` VALUES ('c6daf6c28ce8470191d22b4258363d09', '后台资源修改', 'SYS_RESOURCE_UPDATE', '/sysResource', 'PUT', 1, 2, 'fa fa-edit', '172e6d5275534507aeed3b294880b622', '后台资源修改权限', 0, '2018-10-08 15:48:37', '2018-10-18 13:42:14');
INSERT INTO `sys_resource` VALUES ('cac138901c3d4f22a515876840ce517b', '后台角色新增', 'SYS_ROLE_SAVE', '/sysRole', 'POST', 1, 1, 'fa fa-plus-circle', 'eb95e80d781146589f3d420f5aa71136', '后台角色新增权限', 0, '2018-10-16 10:27:31', NULL);
INSERT INTO `sys_resource` VALUES ('d4bcfb18749f44549db6f938685805d4', '后台资源新增或修改页面', 'SYS_RESOURCE_INFO', '/sysResource/gotoInfo', 'GET', 2, 5, 'fa fa-info-circle', '172e6d5275534507aeed3b294880b622', '跳转至后台资源新增或修改页面', 0, '2018-10-09 15:17:37', '2018-10-09 16:42:54');
INSERT INTO `sys_resource` VALUES ('d72db9034bc044ad81acd6cef2e63be1', '后台角色列表', 'SYS_ROLE_LIST', '/sysRole', 'GET', 1, 0, 'fa fa-key', 'eb95e80d781146589f3d420f5aa71136', '后台角色页面', 0, '2018-09-04 21:47:02', '2018-10-10 10:02:12');
INSERT INTO `sys_resource` VALUES ('dc092418c0b142ee945fbb443a23e741', '后台角色删除', 'SYS_ROLE_DELETE', '/sysRole', 'DELETE', 1, 3, 'fa fa-remove', 'eb95e80d781146589f3d420f5aa71136', '后台角色删除权限', 0, '2018-10-16 15:50:11', NULL);
INSERT INTO `sys_resource` VALUES ('defbea561d9b472f82ae7b7254eb4e3a', '资金操作配置新增', 'OPERATECONFIG_SAVE', '/operateConfig', 'POST', 1, 1, 'fa fa-plus-circle', '70d04e8d022949a4bb740cc3f9fd99a9', '资金操作配置新增权限', 0, '2018-11-05 16:32:51', '2018-11-05 16:40:28');
INSERT INTO `sys_resource` VALUES ('e6215cecf00a45ef8e331734386ca1eb', '后台资源启用/禁用', 'SYS_RESOURCE_USABLE', '/sysResource/usable', 'PUT', 1, 4, 'fa fa-ban', '172e6d5275534507aeed3b294880b622', '后台资源启用/禁用权限', 0, '2018-10-17 15:35:35', NULL);
INSERT INTO `sys_resource` VALUES ('eb95e80d781146589f3d420f5aa71136', '后台角色管理', 'SYS_ROLE', '/sysRole/gotoList', 'GET', 0, 1, 'fa fa-id-badge', 'eebaad47f58547f2b541f89b59dff980', '后台角色管理，由开发人员维护', 0, '2018-07-22 12:17:32', '2018-10-31 10:49:23');
INSERT INTO `sys_resource` VALUES ('eebaad47f58547f2b541f89b59dff980', '系统管理', 'SYS_MANAGE', NULL, 'GET', 0, 0, 'fa fa-gears', NULL, '后台系统管理，由开发人员维护', 0, '2018-07-22 12:05:22', NULL);
INSERT INTO `sys_resource` VALUES ('ef044654ebca4d58a466e8e701bea5e3', '定时任务管理', 'QUARTZ_JOB', '/quartz', 'GET', 0, 4, 'fa fa-history', 'eebaad47f58547f2b541f89b59dff980', '定时任务管理页面', 0, '2018-10-18 17:35:01', '2018-10-31 10:40:12');
INSERT INTO `sys_resource` VALUES ('f2d9c680bc8e40078ccb1379a2618c03', '资金操作配置列表', 'OPERATECONFIG_LIST', '/operateConfig', 'GET', 1, 0, 'fa fa-search', '70d04e8d022949a4bb740cc3f9fd99a9', '资金操作配置查看权限', 0, '2018-11-05 16:30:13', '2018-11-05 16:39:58');
INSERT INTO `sys_resource` VALUES ('f41c9cb3a8e041a7b596ebf08882cdf4', '验证后台资源代码', 'SYS_RESOURCE_CODE', '/sysResource/checkCode', 'POST', 2, 6, 'fa fa-anchor', '172e6d5275534507aeed3b294880b622', '验证后台资源代码是否唯一', 0, '2018-10-09 15:20:02', '2018-10-09 17:36:27');
INSERT INTO `sys_resource` VALUES ('f8f3a2edce38488483b02df2c15adf74', '资金操作配置修改', 'OPERATECONFIG_UPDATE', '/operateConfig', 'PUT', 1, 2, 'fa fa-edit', '70d04e8d022949a4bb740cc3f9fd99a9', '资金操作配置修改权限', 0, '2018-11-05 16:39:25', NULL);
INSERT INTO `sys_resource` VALUES ('fb621735b4484c529fa1112136f9ac39', '财务管理', 'FINANCE_MANAGE', '', 'GET', 0, 1, 'fa fa-rmb', '', '财务管理模块', 0, '2018-11-05 16:20:23', NULL);
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
INSERT INTO `sys_role` VALUES ('2c9d8c5a193a44ffbb962b56696c555e', '后台资源管理查看', 'SYS_RESOURCE_VIEW', 0, '2018-10-16 16:31:08', '2018-11-06 15:44:20');
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
INSERT INTO `sys_role_resource` VALUES ('0c19f2c071dd4d77b8f7b25a2aba7146', '2c9d8c5a193a44ffbb962b56696c555e', 'eb95e80d781146589f3d420f5aa71136', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('145f1dece8864c0e85871dcdc7c536af', '2c9d8c5a193a44ffbb962b56696c555e', 'b34f49a564ed4999801c694562db30ae', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('2a11b8cf33204f7c9ad9901883132067', '2c9d8c5a193a44ffbb962b56696c555e', '0f491c03b75d47b3818d6f0f28f8bc8a', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('2bcf1c4aa0804bc587c9a8411abbd662', '2c9d8c5a193a44ffbb962b56696c555e', 'f8f3a2edce38488483b02df2c15adf74', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('2dd63a0946e24e5c96af8e3215147e22', '2c9d8c5a193a44ffbb962b56696c555e', '0d710818c76744d4b9b9e9c22e5ee229', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('45757fc5d2c746ebbe8b46075c36d9a8', '2c9d8c5a193a44ffbb962b56696c555e', '41f8141bf91248ef81965b8789446960', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('47768fa6cb8946c1bb7cc09f4ca1d58b', '2c9d8c5a193a44ffbb962b56696c555e', 'fb621735b4484c529fa1112136f9ac39', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('4d6363214ac7425abcb09bce56988b28', '2c9d8c5a193a44ffbb962b56696c555e', 'c4c150b45e914ce1abab1a3c3b22935d', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('5952253f3e514a1cb887ca564dca7a32', '2c9d8c5a193a44ffbb962b56696c555e', '952854adceb046378f0c682dd9d0fc90', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('5c32b5b869a64e13a39e423f67244b8c', '2c9d8c5a193a44ffbb962b56696c555e', '70d04e8d022949a4bb740cc3f9fd99a9', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('654a0497ed4240dd981b2a009032eb17', '2c9d8c5a193a44ffbb962b56696c555e', '043ed750ed734b378d00cec669081f3d', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('805388a8302a4d8ea46080a86b2a5372', '2c9d8c5a193a44ffbb962b56696c555e', '436df2ba5ece4964978dc9d81bf3e0f5', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('86643ee5c1ce46159b6426435c34c7af', '2c9d8c5a193a44ffbb962b56696c555e', '7f375cff304a499296eeecd58ed1abb8', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('87375085dfce402ebb31f65ee1503368', '2c9d8c5a193a44ffbb962b56696c555e', 'ac380a322c994f2abd3db8f57cf94b4a', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('8e3ab04143084ca5b9134e4c69efb724', '2c9d8c5a193a44ffbb962b56696c555e', 'd72db9034bc044ad81acd6cef2e63be1', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('9ee51390e82e40799f83243640e6be16', '2c9d8c5a193a44ffbb962b56696c555e', 'defbea561d9b472f82ae7b7254eb4e3a', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('b1c39da2791c4f3a9488cf595c1df033', '2c9d8c5a193a44ffbb962b56696c555e', '04c09eb50db44a0091c173a7a5d08e4e', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('b2322b726b0f434582628efafadbe103', '2c9d8c5a193a44ffbb962b56696c555e', '8207de92362f42d5985e7e7e2f595146', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('ba541e1bd5304e81bbef31f4af17dd53', '2c9d8c5a193a44ffbb962b56696c555e', 'bde09fdf52e24ef1850575b5b9afd292', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('c7a4c8d7077a423e97c6ce5ca060e847', '2c9d8c5a193a44ffbb962b56696c555e', '3a06bae96acb4be1b7cc78063c0fa8d7', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('cc2f870fbd42498881b08491c10f3609', '2c9d8c5a193a44ffbb962b56696c555e', 'f2d9c680bc8e40078ccb1379a2618c03', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('fa59c69f6f18413d92bfd0ec1c14fd30', '2c9d8c5a193a44ffbb962b56696c555e', 'eebaad47f58547f2b541f89b59dff980', '2018-11-06 15:44:20');
INSERT INTO `sys_role_resource` VALUES ('fd8c7d7a901b44ddbe70df7b6bfdd0cc', '2c9d8c5a193a44ffbb962b56696c555e', 'a86045834b024551b419ef9c5ba508bf', '2018-11-06 15:44:20');

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
  `status` smallint(6) NULL DEFAULT 0 COMMENT '后台用户状态：0-正常，1-禁用',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '后台用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('44bfcab8d4a040868c047a9494807b7d', '아이유', '哈哈', 'gemini', '$2a$10$SImUFd9zncI4HDgNI3v3peHM.72sQkN2c2xQQ.oy5h0OK1H24Q0Vm', '17357103526', 'http://192.168.1.29/group1/M00/00/00/fwAAAVvP4xiAMyCfADigTlftgu0011.gif', 0, '2018-10-17 16:16:12', '2018-10-17 16:21:25');
INSERT INTO `sys_user` VALUES ('5c7a8200248b4adaa382da7602857b9f', '小雅', 'Gemini', 'admin', '$2a$10$ba8Nv8B40RPiQECrYPotYuB8EVKY3KDQ.45D.8MLP5ozuOc2PoaKW', '18779141750', 'http://192.168.1.29/group1/M00/00/00/fwAAAVvP6zmAGrOcABOnzW9uiS8787.gif', 0, '2018-07-02 23:24:42', '2018-11-01 11:19:37');

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
-- Table structure for user_account
-- ----------------------------
DROP TABLE IF EXISTS `user_account`;
CREATE TABLE `user_account`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户ID',
  `amount` decimal(18, 2) NULL DEFAULT 0.00 COMMENT '账户总金额',
  `available_amount` decimal(18, 2) NULL DEFAULT 0.00 COMMENT '可用金额',
  `frozen_amount` decimal(18, 2) NULL DEFAULT 0.00 COMMENT '冻结金额',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '用户账户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `nick_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `user_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '账户',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '密码',
  `mobile` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '手机号码',
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
  `inviter_id` bigint(20) NULL DEFAULT NULL COMMENT '邀请人ID',
  `status` smallint(6) NULL DEFAULT 0 COMMENT '用户状态：0-正常，1-禁用',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for withdraw_order
-- ----------------------------
DROP TABLE IF EXISTS `withdraw_order`;
CREATE TABLE `withdraw_order`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户账号',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '订单号',
  `amount` decimal(18, 2) NULL DEFAULT 0.00 COMMENT '订单金额',
  `type` smallint(6) NULL DEFAULT NULL COMMENT '提现方式：1-支付宝，2-微信，3-银行卡',
  `account_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '提现账号',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '提现人姓名',
  `status` smallint(6) NULL DEFAULT 0 COMMENT '提现状态：0-审核中，1-审核通过，2-审核拒绝',
  `sys_user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '审核人员ID',
  `sys_user_name` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '审核人员姓名',
  `audit_note` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '审核备注',
  `audit_time` timestamp(0) NULL DEFAULT NULL COMMENT '审核时间',
  `if_pay` smallint(6) NULL DEFAULT 0 COMMENT '是否打款：0-未打款，1-已打款',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '提现订单表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
