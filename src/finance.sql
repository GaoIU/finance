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

 Date: 02/11/2018 17:57:08
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
  `amount` decimal(10, 0) NULL DEFAULT 0 COMMENT '发生金额',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '用户账户记录表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log_user_account
-- ----------------------------
INSERT INTO `log_user_account` VALUES ('230fe045bc6a47679c99d988d3e99a0d', 'a57f059d6a9241869d7412cb753ce66c', '29b1bc673ddc456e83116e62e348d107', 0, 6600, '2018-11-01 17:08:26');

-- ----------------------------
-- Table structure for operate_config
-- ----------------------------
DROP TABLE IF EXISTS `operate_config`;
CREATE TABLE `operate_config`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `value` smallint(6) NULL DEFAULT NULL COMMENT '值：1-支付宝，2-微信，3-银行卡',
  `type` smallint(6) NULL DEFAULT NULL COMMENT '类型：1-充值，2-提现',
  `status` smallint(6) NULL DEFAULT 0 COMMENT '状态：0-正常，1-禁用',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '充值 - 提现 方式配置表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of operate_config
-- ----------------------------
INSERT INTO `operate_config` VALUES ('179bf85c792a4beeb879ebb107f7e863', '银行卡', 3, 1, 0, '2018-11-02 14:17:11', '2018-11-02 14:17:37');
INSERT INTO `operate_config` VALUES ('5ef59e32156a448c8261b10daa26e2d8', '微信', 2, 1, 0, '2018-11-02 14:16:59', '2018-11-02 14:17:23');
INSERT INTO `operate_config` VALUES ('6391eb34cf324ecdafd4ed920c999b50', '银行卡', 3, 2, 0, '2018-11-02 14:18:28', NULL);
INSERT INTO `operate_config` VALUES ('74befb033bc64dbc8b8cc3d333bb3051', '支付宝', 1, 2, 0, '2018-11-02 14:17:51', NULL);
INSERT INTO `operate_config` VALUES ('c846a8afe71b4c1facdc2d0d726c99fb', '微信', 2, 2, 0, '2018-11-02 14:17:59', NULL);
INSERT INTO `operate_config` VALUES ('cd998bdd027d4f939f53be259c4dc485', '支付宝', 1, 1, 0, '2018-11-02 14:14:11', NULL);

-- ----------------------------
-- Table structure for recharge_order
-- ----------------------------
DROP TABLE IF EXISTS `recharge_order`;
CREATE TABLE `recharge_order`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户账号',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '订单号',
  `amount` decimal(10, 0) NULL DEFAULT 0 COMMENT '订单金额',
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
-- Records of recharge_order
-- ----------------------------
INSERT INTO `recharge_order` VALUES ('a99d4046cade4e05bdd8872a38e3e74c', 'a57f059d6a9241869d7412cb753ce66c', '18779141750', '2018110217543206628733448', 5000, 1, 0, NULL, NULL, NULL, NULL, '2018-11-02 17:54:32', NULL);

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
INSERT INTO `schedule_job` VALUES ('015c939a44214243af0b1084ba75fcd8', 'test', 'testGroup', 'excute', 'com.fanteng.finance.quartz.task.TestTask', '0/30 * * * * ?', 1, '测试', 1, '2018-06-30 02:16:12', NULL);

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
INSERT INTO `sys_config` VALUES ('67484e1e210645bd9a90aec9ea6b95d4', '测试消息', 'TEST', '测试消息', '测试消息', 1, '2018-10-26 17:14:10', '2018-10-26 17:46:00');

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
INSERT INTO `sys_resource` VALUES ('0709489e062a44cf91048f74f40119a4', '充值-提现操作配置启用/禁用', 'OPERATECONFIG_USABLE', '/operateConfig/usable', 'PUT', 1, 4, 'fa fa-ban', '569086006d5e43ac97d6521524301d99', '充值-提现操作配置启用/禁用权限', 0, '2018-11-02 13:59:23', NULL);
INSERT INTO `sys_resource` VALUES ('07cc93688ffb48a598f2e1f3bc5bf24c', '后台资源树形选择', 'SYS_RESOURCE_MENU', '/sysResource/view', 'GET', 2, 4, 'fa fa-tree', '172e6d5275534507aeed3b294880b622', '后台资源树形选择选择框', 0, '2018-10-09 15:15:05', NULL);
INSERT INTO `sys_resource` VALUES ('1093e6d09be543a7b4062f8d27bf6a53', '后台用户新增', 'SYS_USER_SAVE', '/sysUser', 'POST', 1, 1, 'fa fa-plus-circle', 'bde09fdf52e24ef1850575b5b9afd292', '后台用户新增权限', 0, '2018-10-17 15:56:58', NULL);
INSERT INTO `sys_resource` VALUES ('14360ad05ada4a639fc6381ee8b56f27', '后台角色修改', 'SYS_ROLE_UPDATE', '/sysRole', 'PUT', 1, 2, 'fa fa-edit', 'eb95e80d781146589f3d420f5aa71136', '后台角色修改权限', 0, '2018-10-16 15:51:06', NULL);
INSERT INTO `sys_resource` VALUES ('172e6d5275534507aeed3b294880b622', '后台资源管理', 'SYS_RESOURCE', '/sysResource/gotoList', 'GET', 0, 2, 'fa fa-file-text', 'eebaad47f58547f2b541f89b59dff980', '后台资源管理，由开发人员维护', 0, '2018-07-22 12:21:01', NULL);
INSERT INTO `sys_resource` VALUES ('17b1447248b9434dac0865e9b7c31777', '后台系统配置列表', 'SYS_CONFIG_LIST', '/sysConfig', 'GET', 1, 0, 'fa fa-search', '8b99b9d205d341f9adecc45d98d7ebfa', '后台配置列表查询', 0, '2018-10-26 16:51:37', '2018-10-26 16:52:01');
INSERT INTO `sys_resource` VALUES ('30850083799644a6bfca8bd99d4ea41a', '后台系统配置新增或修改页面', 'SYS_CONFIG_INFO', '/sysConfig/gotoInfo', 'GET', 2, 1, 'fa fa-info-circle', '8b99b9d205d341f9adecc45d98d7ebfa', '跳转至后台系统配置新增或修改页面', 0, '2018-10-26 16:57:53', '2018-10-26 16:58:32');
INSERT INTO `sys_resource` VALUES ('3a06bae96acb4be1b7cc78063c0fa8d7', '后台角色新增或修改页面', 'SYS_ROLE_INFO', '/sysRole/gotoInfo', 'GET', 2, 1, 'fa fa-info-circle', 'eb95e80d781146589f3d420f5aa71136', '跳转至后台角色新增或修改页面', 0, '2018-10-09 16:42:20', NULL);
INSERT INTO `sys_resource` VALUES ('436df2ba5ece4964978dc9d81bf3e0f5', '后台用户列表', 'SYS_USER_LIST', '/sysUser', 'GET', 1, 0, 'fa fa-search', 'bde09fdf52e24ef1850575b5b9afd292', '后台用户页面', 0, '2018-08-27 18:16:51', '2018-10-26 16:53:37');
INSERT INTO `sys_resource` VALUES ('47c489298f60418291fe25d18ae786e9', '后台资源新增', 'SYS_RESOURCE_SAVE', '/sysResource', 'POST', 1, 1, 'fa fa-plus-circle', '172e6d5275534507aeed3b294880b622', '后台资源新增权限', 0, '2018-10-08 15:41:24', NULL);
INSERT INTO `sys_resource` VALUES ('5270398dcaf34585ba6a20907c44a2b6', '后台系统配置删除', 'SYS_CONFIG_DELETE', '/sysConfig', 'DELETE', 1, 3, 'fa fa-remove', '8b99b9d205d341f9adecc45d98d7ebfa', '后台系统配置删除权限', 0, '2018-10-26 17:02:41', NULL);
INSERT INTO `sys_resource` VALUES ('541c146b2f34428795e334de4762bde7', '后台系统配置启用/禁用', 'SYS_CONFIG_USABLE', '/sysConfig/usable', 'PUT', 1, 4, 'fa fa-ban', '8b99b9d205d341f9adecc45d98d7ebfa', '后台系统配置启用/禁用 权限', 0, '2018-10-26 17:08:10', NULL);
INSERT INTO `sys_resource` VALUES ('55f66939ec7e4a3a968ea34bc3ac8c8c', '后台用户启用/禁用', 'SYS_USER_USABLE', '/sysUser/usable', 'PUT', 1, 4, 'fa fa-ban', 'bde09fdf52e24ef1850575b5b9afd292', '后台用户启用/禁用权限', 0, '2018-10-17 16:02:10', '2018-10-17 17:49:00');
INSERT INTO `sys_resource` VALUES ('569086006d5e43ac97d6521524301d99', '充值-提现操作配置', 'OPERATECONFIG', '/operateConfig/gotoList', 'GET', 0, 0, 'fa fa-sliders', 'b0ebd68b5ee34ce1a6b5dd7488f36dba', '充值-提现操作配置页面', 0, '2018-11-02 11:09:59', '2018-11-02 11:54:39');
INSERT INTO `sys_resource` VALUES ('57593ea81caf49a1b91558b8681d0ff3', '充值-提现操作配置列表', 'OPERATECONFIG_LIST', '/operateConfig', 'GET', 1, 0, 'fa fa-search', '569086006d5e43ac97d6521524301d99', '充值-提现操作配置列表查看权限', 0, '2018-11-02 11:54:30', NULL);
INSERT INTO `sys_resource` VALUES ('5da0057125654ee7b332cedf662b6f28', '后台系统配置修改', 'SYS_CONFIG_UPDATE', '/sysConfig', 'PUT', 1, 2, 'fa fa-edit', '8b99b9d205d341f9adecc45d98d7ebfa', '后台系统配置修改权限', 0, '2018-10-26 17:01:40', NULL);
INSERT INTO `sys_resource` VALUES ('6fe0d5a1d3eb4afaa43de6afb30dcc1a', '后台用户修改', 'SYS_USER_UPDATE', '/sysUser', 'PUT', 1, 2, 'fa fa-edit', 'bde09fdf52e24ef1850575b5b9afd292', '后台用户修改权限', 0, '2018-10-17 15:58:04', '2018-10-17 15:58:24');
INSERT INTO `sys_resource` VALUES ('73abc828c63443b3be7edf5145d4c0cb', '充值-提现操作配置新增或修改页面', 'OPERATECONFIG_INFO', '/operateConfig/gotoInfo', 'GET', 2, 4, 'fa fa-info-circle', '569086006d5e43ac97d6521524301d99', '充值-提现操作配置新增或修改页面', 0, '2018-11-02 13:43:59', NULL);
INSERT INTO `sys_resource` VALUES ('7889a95b20c042019a75b9dabcc21df0', '后台角色启用/禁用', 'SYS_ROLE_USABLE', '/sysRole/usable', 'PUT', 1, 2, 'fa fa-ban', 'eb95e80d781146589f3d420f5aa71136', '后台角色启用/禁用权限', 0, '2018-10-17 15:46:51', '2018-10-17 15:49:15');
INSERT INTO `sys_resource` VALUES ('82100d63a2d84d889c44a5d68cb03480', '后台用户删除', 'SYS_USER_DELETE', '/sysUser', 'DELETE', 1, 3, 'fa fa-remove', 'bde09fdf52e24ef1850575b5b9afd292', '后台用户删除权限', 0, '2018-10-17 15:59:53', NULL);
INSERT INTO `sys_resource` VALUES ('8b99b9d205d341f9adecc45d98d7ebfa', '后台系统配置', 'SYS_CONFIG', '/sysConfig/gotoList', 'GET', 0, 3, 'fa fa-gear', 'eebaad47f58547f2b541f89b59dff980', '后台系统配置页面', 0, '2018-10-26 16:46:34', '2018-10-26 16:47:10');
INSERT INTO `sys_resource` VALUES ('952854adceb046378f0c682dd9d0fc90', '验证后台角色代码', 'SYS_ROLE_CODE', '/sysRole/checkCode', 'POST', 2, 2, 'fa fa-anchor', 'eb95e80d781146589f3d420f5aa71136', '验证后台角色代码是否唯一', 0, '2018-10-09 17:39:31', NULL);
INSERT INTO `sys_resource` VALUES ('a86045834b024551b419ef9c5ba508bf', '后台角色权限', 'SYS_ROLE_PERMISSION', '/sysRole/getPermission', 'POST', 2, 3, 'fa fa-bolt', 'eb95e80d781146589f3d420f5aa71136', '后台角色权限展示', 0, '2018-10-09 18:17:06', NULL);
INSERT INTO `sys_resource` VALUES ('b0ebd68b5ee34ce1a6b5dd7488f36dba', '财务管理', 'ORDER_MANAGE', '', 'GET', 0, 1, 'fa fa-cny', '', '财务管理', 0, '2018-11-02 11:05:51', '2018-11-02 11:25:24');
INSERT INTO `sys_resource` VALUES ('b34f49a564ed4999801c694562db30ae', '后台用户新增或修改页面', 'SYS_USER_INFO', '/sysUser/gotoInfo', 'GET', 2, 1, 'fa fa-info-circle', 'bde09fdf52e24ef1850575b5b9afd292', '跳转至后台用户新增或修改页面', 0, '2018-10-17 11:38:18', NULL);
INSERT INTO `sys_resource` VALUES ('b6900735bce44789a530174613d18d4e', '添加充值-提现操作配置', 'OPERATECONFIG_SAVE', '/operateConfig', 'POST', 1, 1, 'fa fa-plus-circle', '569086006d5e43ac97d6521524301d99', '添加充值-提现操作配置权限', 0, '2018-11-02 13:55:06', NULL);
INSERT INTO `sys_resource` VALUES ('bde09fdf52e24ef1850575b5b9afd292', '后台用户管理', 'SYS_USER', '/sysUser/gotoList', 'GET', 0, 0, 'fa fa-user', 'eebaad47f58547f2b541f89b59dff980', '后台用户管理，由开发人员维护', 0, '2018-07-22 12:12:41', NULL);
INSERT INTO `sys_resource` VALUES ('bec842318c084862aa5b895c065faf7b', '后台系统配置新增', 'SYS_CONFIG_SAVE', '/sysConfig', 'POST', 1, 1, 'fa fa-plus-circle', '8b99b9d205d341f9adecc45d98d7ebfa', '后台系统配置新增权限', 0, '2018-10-26 17:00:27', NULL);
INSERT INTO `sys_resource` VALUES ('c221c9a353c141a3aa41cab0751d8b84', '后台资源删除', 'SYS_RESOURCE_DELETE', '/sysResource', 'DELETE', 1, 3, 'fa fa-remove', '172e6d5275534507aeed3b294880b622', '后台资源删除权限', 0, '2018-10-08 15:50:07', NULL);
INSERT INTO `sys_resource` VALUES ('c68a23d3da1d469492f406de3a59a0bf', '修改充值-提现操作配置', 'OPERATECONFIG_UPDATE', '/operateConfig', 'PUT', 1, 2, 'fa fa-edit', '569086006d5e43ac97d6521524301d99', '修改充值-提现操作配置权限', 0, '2018-11-02 13:56:50', NULL);
INSERT INTO `sys_resource` VALUES ('c6daf6c28ce8470191d22b4258363d09', '后台资源修改', 'SYS_RESOURCE_UPDATE', '/sysResource', 'PUT', 1, 2, 'fa fa-edit', '172e6d5275534507aeed3b294880b622', '后台资源修改权限', 0, '2018-10-08 15:48:37', '2018-10-18 13:42:14');
INSERT INTO `sys_resource` VALUES ('cac138901c3d4f22a515876840ce517b', '后台角色新增', 'SYS_ROLE_SAVE', '/sysRole', 'POST', 1, 1, 'fa fa-plus-circle', 'eb95e80d781146589f3d420f5aa71136', '后台角色新增权限', 0, '2018-10-16 10:27:31', NULL);
INSERT INTO `sys_resource` VALUES ('d4bcfb18749f44549db6f938685805d4', '后台资源新增或修改页面', 'SYS_RESOURCE_INFO', '/sysResource/gotoInfo', 'GET', 2, 5, 'fa fa-info-circle', '172e6d5275534507aeed3b294880b622', '跳转至后台资源新增或修改页面', 0, '2018-10-09 15:17:37', '2018-10-09 16:42:54');
INSERT INTO `sys_resource` VALUES ('d72db9034bc044ad81acd6cef2e63be1', '后台角色列表', 'SYS_ROLE_LIST', '/sysRole', 'GET', 1, 0, 'fa fa-search', 'eb95e80d781146589f3d420f5aa71136', '后台角色页面', 0, '2018-09-04 21:47:02', '2018-10-26 16:52:27');
INSERT INTO `sys_resource` VALUES ('dc092418c0b142ee945fbb443a23e741', '后台角色删除', 'SYS_ROLE_DELETE', '/sysRole', 'DELETE', 1, 3, 'fa fa-remove', 'eb95e80d781146589f3d420f5aa71136', '后台角色删除权限', 0, '2018-10-16 15:50:11', NULL);
INSERT INTO `sys_resource` VALUES ('dcdc311efb484ec5abe2caec1169be6c', '删除充值-提现操作配置', 'OPERATECONFIG_DELETE', '/operateConfig', 'DELETE', 1, 3, 'fa fa-remove', '569086006d5e43ac97d6521524301d99', '删除充值-提现操作配置权限', 0, '2018-11-02 13:57:52', NULL);
INSERT INTO `sys_resource` VALUES ('e6215cecf00a45ef8e331734386ca1eb', '后台资源启用/禁用', 'SYS_RESOURCE_USABLE', '/sysResource/usable', 'PUT', 1, 4, 'fa fa-ban', '172e6d5275534507aeed3b294880b622', '后台资源启用/禁用权限', 0, '2018-10-17 15:35:35', NULL);
INSERT INTO `sys_resource` VALUES ('eb95e80d781146589f3d420f5aa71136', '后台角色管理', 'SYS_ROLE', '/sysRole/gotoList', 'GET', 0, 1, 'fa fa-key', 'eebaad47f58547f2b541f89b59dff980', '后台角色管理，由开发人员维护', 0, '2018-07-22 12:17:32', NULL);
INSERT INTO `sys_resource` VALUES ('eebaad47f58547f2b541f89b59dff980', '系统管理', 'SYS_MANAGE', NULL, 'GET', 0, 0, 'fa fa-gears', NULL, '后台系统管理，由开发人员维护', 0, '2018-07-22 12:05:22', NULL);
INSERT INTO `sys_resource` VALUES ('ef044654ebca4d58a466e8e701bea5e3', '定时任务管理', 'QUARTZ_JOB', 'https://www.layui.com/admin/?from=fly', 'GET', 0, 4, 'fa fa-cc-jcb', 'eebaad47f58547f2b541f89b59dff980', '定时任务管理页面', 0, '2018-10-18 17:35:01', '2018-10-26 16:47:02');
INSERT INTO `sys_resource` VALUES ('f41c9cb3a8e041a7b596ebf08882cdf4', '验证后台资源代码', 'SYS_RESOURCE_CODE', '/sysResource/checkCode', 'POST', 2, 6, 'fa fa-anchor', '172e6d5275534507aeed3b294880b622', '验证后台资源代码是否唯一', 0, '2018-10-09 15:20:02', '2018-10-09 17:36:27');
INSERT INTO `sys_resource` VALUES ('fce787f79ca649769a7f0aafeb5ea165', '后台资源列表', 'SYS_RESOURCE_LIST', '/sysResource', 'GET', 1, 0, 'fa fa-search', '172e6d5275534507aeed3b294880b622', '后台资源页面', 0, '2018-09-04 21:45:05', '2018-10-26 16:52:57');

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
INSERT INTO `sys_role` VALUES ('2c9d8c5a193a44ffbb962b56696c555e', '后台资源管理查看', 'SYS_RESOURCE_VIEW', 0, '2018-10-16 16:31:08', '2018-11-02 14:43:11');
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
INSERT INTO `sys_role_resource` VALUES ('129789a17a784ca3a7a2181422765aaa', '2c9d8c5a193a44ffbb962b56696c555e', '73abc828c63443b3be7edf5145d4c0cb', '2018-11-02 14:43:11');
INSERT INTO `sys_role_resource` VALUES ('6721ad572ea2474fa1e2b3f8c03791b6', '2c9d8c5a193a44ffbb962b56696c555e', 'b6900735bce44789a530174613d18d4e', '2018-11-02 14:43:11');
INSERT INTO `sys_role_resource` VALUES ('a74e7358701c4310971cb7b6379b7937', '2c9d8c5a193a44ffbb962b56696c555e', '569086006d5e43ac97d6521524301d99', '2018-11-02 14:43:11');
INSERT INTO `sys_role_resource` VALUES ('e1e9a832e24949ff95ed229bc759826b', '2c9d8c5a193a44ffbb962b56696c555e', 'dcdc311efb484ec5abe2caec1169be6c', '2018-11-02 14:43:11');
INSERT INTO `sys_role_resource` VALUES ('e91efdb06c5e43c3983bb6ac664af44a', '2c9d8c5a193a44ffbb962b56696c555e', 'c68a23d3da1d469492f406de3a59a0bf', '2018-11-02 14:43:11');
INSERT INTO `sys_role_resource` VALUES ('e9cdb1ebd6404c5eb99083920e4b4b8e', '2c9d8c5a193a44ffbb962b56696c555e', 'b0ebd68b5ee34ce1a6b5dd7488f36dba', '2018-11-02 14:43:11');
INSERT INTO `sys_role_resource` VALUES ('f4a6c49e397447819a6986280395d17b', '2c9d8c5a193a44ffbb962b56696c555e', '0709489e062a44cf91048f74f40119a4', '2018-11-02 14:43:11');
INSERT INTO `sys_role_resource` VALUES ('f77839d5e83e489dbd555fbee31492b7', '2c9d8c5a193a44ffbb962b56696c555e', '57593ea81caf49a1b91558b8681d0ff3', '2018-11-02 14:43:11');

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
INSERT INTO `sys_user` VALUES ('44bfcab8d4a040868c047a9494807b7d', '아이유', '哈哈', 'gemini', '$2a$10$SImUFd9zncI4HDgNI3v3peHM.72sQkN2c2xQQ.oy5h0OK1H24Q0Vm', '17357103526', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539682975555&di=6c56c48fb1da3db0d3ca42d75230f96d&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20180709%2F0d689e50c9f04cf6ab0631303aef5900.gif', 0, '2018-10-17 16:16:12', '2018-10-17 16:21:25');
INSERT INTO `sys_user` VALUES ('5c7a8200248b4adaa382da7602857b9f', '小雅', 'Gemini', 'admin', '$2a$10$B7aO5TVsnFmID7pUhJk95.VAXUf1sm79SI6PHSW/WyPbPCxGREOFC', '18779141750', 'http://192.168.1.29/group1/M00/00/00/wKgBHVvQGb2ADPyBAAAhqEWjpGQ46..jpg', 0, '2018-07-02 23:24:42', '2018-10-24 15:05:37');

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
  `amount` decimal(10, 0) NULL DEFAULT 0 COMMENT '账户总金额',
  `available_amount` decimal(10, 0) NULL DEFAULT 0 COMMENT '可用金额',
  `frozen_amount` decimal(10, 0) NULL DEFAULT 0 COMMENT '冻结金额',
  `create_time` timestamp(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '用户账户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_account
-- ----------------------------
INSERT INTO `user_account` VALUES ('29b1bc673ddc456e83116e62e348d107', 'a57f059d6a9241869d7412cb753ce66c', 6600, 6600, 0, '2018-11-01 17:08:26', NULL);

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
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('a57f059d6a9241869d7412cb753ce66c', 'LTIE8B58', '18779141750', '$2a$10$b.XgD4j4V5AXVepxHTpzGe7YpZRah6KLS/b9kjbjXG30Gbr0rDr0.', '18779141750', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1539682975555&di=6c56c48fb1da3db0d3ca42d75230f96d&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20180709%2F0d689e50c9f04cf6ab0631303aef5900.gif', NULL, 0, '2018-11-01 17:08:26', NULL);

-- ----------------------------
-- Table structure for withdraw_order
-- ----------------------------
DROP TABLE IF EXISTS `withdraw_order`;
CREATE TABLE `withdraw_order`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `user_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户账号',
  `order_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '订单号',
  `amount` decimal(10, 0) NULL DEFAULT 0 COMMENT '订单金额',
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
