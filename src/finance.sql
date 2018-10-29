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

 Date: 29/10/2018 14:21:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
INSERT INTO `schedule_job` VALUES ('015c939a44214243af0b1084ba75fcd8', 'test', 'testGroup', 'excute', 'com.fanteng.finance.quartz.task.TestTask', '0/30 * * * * ?', 1, '测试', 1, '2018-06-30 02:16:12', '2018-10-29 09:54:42');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config`  (
  `id` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '开发主键',
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '名称',
  `code` varchar(16) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '编码',
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
INSERT INTO `sys_config` VALUES ('7b6b726944df4a6da9cd642b16b178fc', '最大充值金额', 'MAX_PAY_MONEY', '100000', '对冲宝最大充值金额限制', 1, '2018-10-29 10:11:43', '2018-10-29 10:14:02');
INSERT INTO `sys_config` VALUES ('8c1c72f06cf14fd0afd623c4a8535f3c', '测试', 'TEST', '测试redis缓存', '这是一个测试数据', 1, '2018-10-29 10:13:32', '2018-10-29 10:13:49');

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
INSERT INTO `sys_resource` VALUES ('0d710818c76744d4b9b9e9c22e5ee229', '后台系统配置新增或修改页面', 'SYS_CONFIG_INFO', '/sysConfig/gotoInfo', 'GET', 2, 3, 'fa fa-info-circle', '7f375cff304a499296eeecd58ed1abb8', '跳转至后台系统配置新增或修改页面', 0, '2018-10-29 10:10:08', NULL);
INSERT INTO `sys_resource` VALUES ('0f491c03b75d47b3818d6f0f28f8bc8a', '后台系统配置列表', 'SYS_CONFIG_LIST', '/sysConfig', 'GET', 1, 0, 'fa fa-search', '7f375cff304a499296eeecd58ed1abb8', '后台配置列表查询', 0, '2018-10-29 10:02:39', NULL);
INSERT INTO `sys_resource` VALUES ('1093e6d09be543a7b4062f8d27bf6a53', '后台用户新增', 'SYS_USER_SAVE', '/sysUser', 'POST', 1, 1, 'fa fa-plus-circle', 'bde09fdf52e24ef1850575b5b9afd292', '后台用户新增权限', 0, '2018-10-17 15:56:58', NULL);
INSERT INTO `sys_resource` VALUES ('14360ad05ada4a639fc6381ee8b56f27', '后台角色修改', 'SYS_ROLE_UPDATE', '/sysRole', 'PUT', 1, 2, 'fa fa-edit', 'eb95e80d781146589f3d420f5aa71136', '后台角色修改权限', 0, '2018-10-16 15:51:06', NULL);
INSERT INTO `sys_resource` VALUES ('172e6d5275534507aeed3b294880b622', '后台资源管理', 'SYS_RESOURCE', '/sysResource/gotoList', 'GET', 0, 2, 'fa fa-file-text', 'eebaad47f58547f2b541f89b59dff980', '后台资源管理，由开发人员维护', 0, '2018-07-22 12:21:01', NULL);
INSERT INTO `sys_resource` VALUES ('3a06bae96acb4be1b7cc78063c0fa8d7', '后台角色新增或修改页面', 'SYS_ROLE_INFO', '/sysRole/gotoInfo', 'GET', 2, 1, 'fa fa-info-circle', 'eb95e80d781146589f3d420f5aa71136', '跳转至后台角色新增或修改页面', 0, '2018-10-09 16:42:20', NULL);
INSERT INTO `sys_resource` VALUES ('3e7f05e4a0664f159e08b9766fae37ef', '后台系统配置新增', 'SYS_CONFIG_SAVE', '/sysConfig', 'POST', 1, 1, 'fa fa-plus-circle', '7f375cff304a499296eeecd58ed1abb8', '后台系统配置新增权限', 0, '2018-10-29 10:05:44', NULL);
INSERT INTO `sys_resource` VALUES ('436df2ba5ece4964978dc9d81bf3e0f5', '后台用户列表', 'SYS_USER_LIST', '/sysUser', 'GET', 1, 0, 'fa fa-user', 'bde09fdf52e24ef1850575b5b9afd292', '后台用户页面', 0, '2018-08-27 18:16:51', '2018-10-10 10:02:02');
INSERT INTO `sys_resource` VALUES ('47c489298f60418291fe25d18ae786e9', '后台资源新增', 'SYS_RESOURCE_SAVE', '/sysResource', 'POST', 1, 1, 'fa fa-plus-circle', '172e6d5275534507aeed3b294880b622', '后台资源新增权限', 0, '2018-10-08 15:41:24', NULL);
INSERT INTO `sys_resource` VALUES ('55c84c2900174d4e8bd59605eec985b1', '后台系统配置删除', 'SYS_CONFIG_DELETE', '/sysConfig', 'DELETE', 1, 3, 'fa fa-remove', '7f375cff304a499296eeecd58ed1abb8', '后台系统配置删除权限', 0, '2018-10-29 10:07:47', NULL);
INSERT INTO `sys_resource` VALUES ('55f66939ec7e4a3a968ea34bc3ac8c8c', '后台用户启用/禁用', 'SYS_USER_USABLE', '/sysUser/usable', 'PUT', 1, 4, 'fa fa-ban', 'bde09fdf52e24ef1850575b5b9afd292', '后台用户启用/禁用权限', 0, '2018-10-17 16:02:10', '2018-10-17 17:49:00');
INSERT INTO `sys_resource` VALUES ('58360d4fbf8b464a86b115d98a74de9f', '后台系统配置启用/禁用', 'SYS_CONFIG_USABLE', '/sysConfig/usable', 'PUT', 1, 4, 'fa fa-ban', '7f375cff304a499296eeecd58ed1abb8', '后台系统配置启用/禁用 权限', 0, '2018-10-29 10:08:53', NULL);
INSERT INTO `sys_resource` VALUES ('6fe0d5a1d3eb4afaa43de6afb30dcc1a', '后台用户修改', 'SYS_USER_UPDATE', '/sysUser', 'PUT', 1, 2, 'fa fa-edit', 'bde09fdf52e24ef1850575b5b9afd292', '后台用户修改权限', 0, '2018-10-17 15:58:04', '2018-10-17 15:58:24');
INSERT INTO `sys_resource` VALUES ('7889a95b20c042019a75b9dabcc21df0', '后台角色启用/禁用', 'SYS_ROLE_USABLE', '/sysRole/usable', 'PUT', 1, 2, 'fa fa-ban', 'eb95e80d781146589f3d420f5aa71136', '后台角色启用/禁用权限', 0, '2018-10-17 15:46:51', '2018-10-17 15:49:15');
INSERT INTO `sys_resource` VALUES ('7f375cff304a499296eeecd58ed1abb8', '后台系统配置', 'SYS_CONFIG', '/sysConfig/gotoList', 'GET', 0, 3, 'fa fa-gear', 'eebaad47f58547f2b541f89b59dff980', '后台系统配置页面', 0, '2018-10-29 10:01:17', NULL);
INSERT INTO `sys_resource` VALUES ('82100d63a2d84d889c44a5d68cb03480', '后台用户删除', 'SYS_USER_DELETE', '/sysUser', 'DELETE', 1, 3, 'fa fa-remove', 'bde09fdf52e24ef1850575b5b9afd292', '后台用户删除权限', 0, '2018-10-17 15:59:53', NULL);
INSERT INTO `sys_resource` VALUES ('8cdc836f375144d0834693cb2f641abd', '后台系统配置修改', 'SYS_CONFIG_UPDATE', '/sysConfig', 'PUT', 1, 2, 'fa fa-edit', '7f375cff304a499296eeecd58ed1abb8', '后台系统配置修改权限', 0, '2018-10-29 10:06:46', NULL);
INSERT INTO `sys_resource` VALUES ('952854adceb046378f0c682dd9d0fc90', '验证后台角色代码', 'SYS_ROLE_CODE', '/sysRole/checkCode', 'POST', 2, 2, 'fa fa-anchor', 'eb95e80d781146589f3d420f5aa71136', '验证后台角色代码是否唯一', 0, '2018-10-09 17:39:31', NULL);
INSERT INTO `sys_resource` VALUES ('a86045834b024551b419ef9c5ba508bf', '后台角色权限', 'SYS_ROLE_PERMISSION', '/sysRole/getPermission', 'POST', 2, 3, 'fa fa-bolt', 'eb95e80d781146589f3d420f5aa71136', '后台角色权限展示', 0, '2018-10-09 18:17:06', NULL);
INSERT INTO `sys_resource` VALUES ('b34f49a564ed4999801c694562db30ae', '后台用户新增或修改页面', 'SYS_USER_INFO', '/sysUser/gotoInfo', 'GET', 2, 1, 'fa fa-info-circle', 'bde09fdf52e24ef1850575b5b9afd292', '跳转至后台用户新增或修改页面', 0, '2018-10-17 11:38:18', NULL);
INSERT INTO `sys_resource` VALUES ('bde09fdf52e24ef1850575b5b9afd292', '后台用户管理', 'SYS_USER', '/sysUser/gotoList', 'GET', 0, 0, 'fa fa-user', 'eebaad47f58547f2b541f89b59dff980', '后台用户管理，由开发人员维护', 0, '2018-07-22 12:12:41', NULL);
INSERT INTO `sys_resource` VALUES ('c221c9a353c141a3aa41cab0751d8b84', '后台资源删除', 'SYS_RESOURCE_DELETE', '/sysResource', 'DELETE', 1, 3, 'fa fa-remove', '172e6d5275534507aeed3b294880b622', '后台资源删除权限', 0, '2018-10-08 15:50:07', NULL);
INSERT INTO `sys_resource` VALUES ('c6daf6c28ce8470191d22b4258363d09', '后台资源修改', 'SYS_RESOURCE_UPDATE', '/sysResource', 'PUT', 1, 2, 'fa fa-edit', '172e6d5275534507aeed3b294880b622', '后台资源修改权限', 0, '2018-10-08 15:48:37', '2018-10-18 13:42:14');
INSERT INTO `sys_resource` VALUES ('cac138901c3d4f22a515876840ce517b', '后台角色新增', 'SYS_ROLE_SAVE', '/sysRole', 'POST', 1, 1, 'fa fa-plus-circle', 'eb95e80d781146589f3d420f5aa71136', '后台角色新增权限', 0, '2018-10-16 10:27:31', NULL);
INSERT INTO `sys_resource` VALUES ('d4bcfb18749f44549db6f938685805d4', '后台资源新增或修改页面', 'SYS_RESOURCE_INFO', '/sysResource/gotoInfo', 'GET', 2, 5, 'fa fa-info-circle', '172e6d5275534507aeed3b294880b622', '跳转至后台资源新增或修改页面', 0, '2018-10-09 15:17:37', '2018-10-09 16:42:54');
INSERT INTO `sys_resource` VALUES ('d72db9034bc044ad81acd6cef2e63be1', '后台角色列表', 'SYS_ROLE_LIST', '/sysRole', 'GET', 1, 0, 'fa fa-key', 'eb95e80d781146589f3d420f5aa71136', '后台角色页面', 0, '2018-09-04 21:47:02', '2018-10-10 10:02:12');
INSERT INTO `sys_resource` VALUES ('dc092418c0b142ee945fbb443a23e741', '后台角色删除', 'SYS_ROLE_DELETE', '/sysRole', 'DELETE', 1, 3, 'fa fa-remove', 'eb95e80d781146589f3d420f5aa71136', '后台角色删除权限', 0, '2018-10-16 15:50:11', NULL);
INSERT INTO `sys_resource` VALUES ('e6215cecf00a45ef8e331734386ca1eb', '后台资源启用/禁用', 'SYS_RESOURCE_USABLE', '/sysResource/usable', 'PUT', 1, 4, 'fa fa-ban', '172e6d5275534507aeed3b294880b622', '后台资源启用/禁用权限', 0, '2018-10-17 15:35:35', NULL);
INSERT INTO `sys_resource` VALUES ('eb95e80d781146589f3d420f5aa71136', '后台角色管理', 'SYS_ROLE', '/sysRole/gotoList', 'GET', 0, 1, 'fa fa-key', 'eebaad47f58547f2b541f89b59dff980', '后台角色管理，由开发人员维护', 0, '2018-07-22 12:17:32', NULL);
INSERT INTO `sys_resource` VALUES ('eebaad47f58547f2b541f89b59dff980', '系统管理', 'SYS_MANAGE', NULL, 'GET', 0, 0, 'fa fa-gears', NULL, '后台系统管理，由开发人员维护', 0, '2018-07-22 12:05:22', NULL);
INSERT INTO `sys_resource` VALUES ('ef044654ebca4d58a466e8e701bea5e3', '定时任务管理', 'QUARTZ_JOB', '/quartz', 'GET', 0, 4, 'fa fa-cc-jcb', 'eebaad47f58547f2b541f89b59dff980', '定时任务管理页面', 0, '2018-10-18 17:35:01', '2018-10-29 10:00:00');
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
INSERT INTO `sys_role` VALUES ('2c9d8c5a193a44ffbb962b56696c555e', '后台资源管理查看', 'SYS_RESOURCE_VIEW', 0, '2018-10-16 16:31:08', '2018-10-24 17:21:19');
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
INSERT INTO `sys_role_resource` VALUES ('28e3b7698d0841448c3aa011239a7ca8', '2c9d8c5a193a44ffbb962b56696c555e', '172e6d5275534507aeed3b294880b622', '2018-10-18 15:11:20');
INSERT INTO `sys_role_resource` VALUES ('3d8a4520ab6a47eab5d1e2e713c66d7e', '2c9d8c5a193a44ffbb962b56696c555e', 'eebaad47f58547f2b541f89b59dff980', '2018-10-18 15:11:20');
INSERT INTO `sys_role_resource` VALUES ('66480a2864884596865b5ca322e389aa', '2c9d8c5a193a44ffbb962b56696c555e', '07cc93688ffb48a598f2e1f3bc5bf24c', '2018-10-18 15:11:20');
INSERT INTO `sys_role_resource` VALUES ('8638d7c589f34eb79c58c4b247100ac4', '2c9d8c5a193a44ffbb962b56696c555e', 'f41c9cb3a8e041a7b596ebf08882cdf4', '2018-10-18 15:11:20');
INSERT INTO `sys_role_resource` VALUES ('9386f27d0714463b91c581a9ede42b1f', '2c9d8c5a193a44ffbb962b56696c555e', 'd4bcfb18749f44549db6f938685805d4', '2018-10-18 15:11:20');
INSERT INTO `sys_role_resource` VALUES ('e480ad3c0b954316bbffbb98f7c13e7c', '2c9d8c5a193a44ffbb962b56696c555e', 'fce787f79ca649769a7f0aafeb5ea165', '2018-10-18 15:11:20');

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
INSERT INTO `sys_user` VALUES ('5c7a8200248b4adaa382da7602857b9f', '小雅', 'Gemini', 'admin', '$2a$10$B7aO5TVsnFmID7pUhJk95.VAXUf1sm79SI6PHSW/WyPbPCxGREOFC', '18779141750', 'http://192.168.1.29/group1/M00/00/00/fwAAAVvP6zmAGrOcABOnzW9uiS8787.gif', 0, '2018-07-02 23:24:42', '2018-10-17 16:02:53');

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

SET FOREIGN_KEY_CHECKS = 1;
