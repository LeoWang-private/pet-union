/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : localhost:3306
 Source Schema         : bysk_sys

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 14/10/2020 16:21:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `system` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0',
  `del_flag` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_dict_del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES (57, 'cert_type', '身份认证类型 退休', '2020-06-05 15:11:20', '2020-06-05 16:14:36', '身份认证类型', '0', 0);

-- ----------------------------
-- Table structure for sys_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_item`;
CREATE TABLE `sys_dict_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `dict_id` int(11) NOT NULL,
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sort` int(11) NOT NULL DEFAULT 0 COMMENT '排序（升序）',
  `create_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remarks` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `del_flag` tinyint(1) NULL DEFAULT 0,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_dict_value`(`value`) USING BTREE,
  INDEX `sys_dict_label`(`label`) USING BTREE,
  INDEX `sys_dict_del_flag`(`del_flag`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 202 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '字典项' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_item
-- ----------------------------
INSERT INTO `sys_dict_item` VALUES (201, 57, '1', '身份证', 'cert_type', 'string', 0, '2020-06-05 15:19:16', '2020-06-05 15:19:16', NULL, 0);
INSERT INTO `sys_dict_item` VALUES (202, 57, '2', '退休证', 'cert_type', 'string', 0, '2020-06-05 15:19:27', '2020-06-05 15:19:27', NULL, 0);

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `service_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `remote_addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `user_agent` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `request_uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `time` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '执行时间',
  `del_flag` tinyint(1) NULL DEFAULT 0,
  `result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `tenant_id` int(11) NULL DEFAULT 0 COMMENT '所属租户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sys_log_create_by`(`create_by`) USING BTREE,
  INDEX `sys_log_request_uri`(`request_uri`) USING BTREE,
  INDEX `sys_log_type`(`type`) USING BTREE,
  INDEX `sys_log_create_date`(`create_time`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES (1, '0', '添加部门', 'app', 'admin', '2020-04-23 10:50:22', '2020-04-23 11:34:30', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '/dept', 'POST', '[{\"deptId\":7,\"name\":\"市场部\",\"sort\":0,\"parentId\":0}]', '290', 0, '{\"msg\":\"执行成功\",\"code\":0,\"data\":true}', 1);
INSERT INTO `sys_log` VALUES (2, '0', '添加部门', 'app', 'admin', '2020-04-23 11:29:35', '2020-04-23 11:34:31', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '/dept', 'POST', '[{\"deptId\":8,\"name\":\"研发4部\",\"sort\":1,\"parentId\":6}]', '130', 0, '{\"code\":0,\"data\":true}', 1);
INSERT INTO `sys_log` VALUES (3, '0', '添加部门', 'app', 'admin', '2020-04-23 11:30:39', '2020-04-23 11:34:32', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '/dept', 'POST', '[{\"deptId\":9,\"name\":\"研发5部\",\"sort\":1,\"parentId\":8}]', '104', 0, '{\"code\":0,\"data\":true}', 1);
INSERT INTO `sys_log` VALUES (4, '0', '添加部门', 'app', 'admin', '2020-05-19 02:16:44', NULL, '192.168.2.102', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '/dept', 'POST', '[{\"deptId\":10,\"name\":\"fdsa\",\"sort\":12}]', '107', 0, '{\"code\":200,\"data\":true}', 0);
INSERT INTO `sys_log` VALUES (5, '0', '删除部门', 'app', 'admin', '2020-05-19 02:16:49', NULL, '192.168.2.102', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '/dept', 'DELETE', '[10]', '100', 0, '{\"code\":200,\"data\":true}', 0);
INSERT INTO `sys_log` VALUES (6, '0', '修改部门', 'app', 'admin', '2020-05-19 02:16:56', NULL, '192.168.2.102', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '/dept/', 'PUT', '[{\"createTime\":1587524900000,\"deptId\":4,\"name\":\"部门测试2\",\"sort\":0}]', '107', 0, '{\"code\":200,\"data\":true}', 0);
INSERT INTO `sys_log` VALUES (7, '0', '添加部门', 'app', 'admin', '2020-05-19 02:17:21', NULL, '192.168.2.102', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '/dept', 'POST', '[{\"deptId\":11,\"name\":\"ffda\",\"sort\":2,\"parentId\":3}]', '99', 0, '{\"code\":200,\"data\":true}', 0);
INSERT INTO `sys_log` VALUES (8, '0', '删除部门', 'app', 'admin', '2020-05-19 02:22:05', NULL, '192.168.2.102', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '/dept', 'DELETE', '[11]', '97', 0, '{\"code\":200,\"data\":true}', 0);
INSERT INTO `sys_log` VALUES (9, '0', '添加部门', 'app', 'admin', '2020-05-19 02:22:23', NULL, '192.168.2.102', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '/dept', 'POST', '[{\"deptId\":12,\"name\":\"fdafa\",\"sort\":1,\"parentId\":4}]', '98', 0, '{\"code\":200,\"data\":true}', 0);
INSERT INTO `sys_log` VALUES (10, '0', '删除部门', 'app', 'admin', '2020-05-19 02:22:29', NULL, '192.168.2.102', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '/dept', 'DELETE', '[12]', '97', 0, '{\"code\":200,\"data\":true}', 0);
INSERT INTO `sys_log` VALUES (11, '0', '添加部门', 'app', 'admin', '2020-05-19 02:22:49', NULL, '192.168.2.102', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36', '/dept', 'POST', '[{\"deptId\":13,\"name\":\"销售部\",\"sort\":1,\"parentId\":3}]', '97', 0, '{\"code\":200,\"data\":true}', 0);
INSERT INTO `sys_log` VALUES (12, '0', '删除字典', 'app', 'admin', '2020-06-05 15:18:00', NULL, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36', '/dict', 'DELETE', '[58]', '397', 0, '{\"code\":200,\"data\":true}', 0);
INSERT INTO `sys_log` VALUES (13, '0', '添加字典', 'app', 'admin', '2020-06-05 15:51:13', NULL, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36', '/dict', 'POST', '[{\"description\":\"身份认证类型\",\"id\":59,\"type\":\"cert_type\",\"remarks\":\"身份认证类型\"}]', '317', 0, '{\"code\":200,\"data\":true}', 0);
INSERT INTO `sys_log` VALUES (14, '0', '添加字典', 'app', 'admin', '2020-06-05 15:52:21', NULL, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36', '/dict', 'POST', '[{\"description\":\"身份认证类型\",\"id\":60,\"type\":\"cert_type\",\"remarks\":\"身份认证类型\"}]', '309', 0, '{\"code\":200,\"data\":true}', 0);
INSERT INTO `sys_log` VALUES (15, '0', '修改字典', 'app', 'admin', '2020-06-05 16:14:37', NULL, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36', '/dict', 'PUT', '[{\"description\":\"身份认证类型 退休\",\"id\":57}]', '306', 0, '{\"code\":200,\"data\":true}', 0);
INSERT INTO `sys_log` VALUES (16, '0', '修改字典', 'app', 'admin', '2020-06-05 16:14:56', NULL, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36', '/dict', 'PUT', '[{\"description\":\"身份认证类型 退休\",\"id\":57}]', '2', 0, '{\"code\":200,\"data\":true}', 0);

-- ----------------------------
-- Table structure for sys_oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `sys_oauth_client_details`;
CREATE TABLE `sys_oauth_client_details`  (
  `client_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `resource_ids` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `scope` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `authorized_grant_types` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `authorities` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `access_token_validity` int(11) NULL DEFAULT NULL,
  `refresh_token_validity` int(11) NULL DEFAULT NULL,
  `additional_information` varchar(4096) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `autoapprove` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '终端信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_oauth_client_details
-- ----------------------------
INSERT INTO `sys_oauth_client_details` VALUES ('abc', 'jz-admin-service', 'abc', 'server', 'password,refresh_token,authorization_code,client_credentials,implicit', NULL, NULL, 30000, 30000, NULL, NULL);
INSERT INTO `sys_oauth_client_details` VALUES ('app', NULL, 'app', 'server', 'password,refresh_token,authorization_code,client_credentials,implicit', NULL, NULL, 43200, 2592000, NULL, 'true');
INSERT INTO `sys_oauth_client_details` VALUES ('daemon', NULL, 'daemon', 'server', 'password,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true');
INSERT INTO `sys_oauth_client_details` VALUES ('gen', NULL, 'gen', 'server', 'password,refresh_token', NULL, NULL, NULL, NULL, NULL, 'true');
INSERT INTO `sys_oauth_client_details` VALUES ('jz', NULL, 'jz', 'server', 'password,refresh_token,authorization_code,client_credentials', 'http://localhost:4040/sso1/login,http://localhost:4041/sso1/login', NULL, NULL, NULL, NULL, 'true');
INSERT INTO `sys_oauth_client_details` VALUES ('mp', NULL, 'mp', 'server', 'authorization_code', 'http://baidu.com', NULL, NULL, NULL, NULL, 'false');
INSERT INTO `sys_oauth_client_details` VALUES ('test', NULL, 'test', 'server', 'password,refresh_token,authorization_code', 'http://baidu.com', NULL, NULL, NULL, NULL, 'true');

-- ----------------------------
-- Table structure for sys_user_auth
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_auth`;
CREATE TABLE `sys_user_auth`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_info_id` bigint(20) NOT NULL COMMENT '用户基本信息表ID',
  `identity` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '登录名',
  `credential` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录密码',
  `identity_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '登录账号类型 0用户名 1手机号 2邮箱 3微信 4微博',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '管理员账号' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_auth
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_info`;
CREATE TABLE `sys_user_info`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `real_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '昵称',
  `sex` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '0' COMMENT '性别,字典字段设置0男，1女，2保密',
  `phone` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `cert_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件类型，字典字段',
  `cert_num` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件号',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像地址',
  `lock_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '锁定标记0-正常，9-锁定',
  `del_flag` tinyint(1) NOT NULL DEFAULT 0 COMMENT '删除标志',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户基本信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_info
-- ----------------------------
INSERT INTO `sys_user_info` VALUES (1253241557519851522, '张三', NULL, '0', NULL, NULL, NULL, '123', '0', 0, '2020-04-23 16:37:26', NULL);
INSERT INTO `sys_user_info` VALUES (1253241780275138562, '张三', NULL, '0', NULL, NULL, NULL, '123', '0', 0, '2020-04-23 16:38:19', NULL);
INSERT INTO `sys_user_info` VALUES (1253242008764055554, '张三', NULL, '0', NULL, NULL, NULL, '123', '0', 0, '2020-04-23 16:39:13', NULL);
INSERT INTO `sys_user_info` VALUES (1253242716422803457, '张三', NULL, '0', NULL, NULL, NULL, '123', '0', 0, '2020-04-23 16:42:02', NULL);
INSERT INTO `sys_user_info` VALUES (1253246595763998722, '张三', NULL, '0', NULL, NULL, NULL, '123', '0', 0, '2020-04-23 16:57:27', NULL);
INSERT INTO `sys_user_info` VALUES (1253246801179992066, '1253246800995442690', NULL, '0', NULL, NULL, NULL, '123', '0', 0, '2020-04-23 16:58:16', NULL);
INSERT INTO `sys_user_info` VALUES (1253247016431697921, '1253247016251342849', NULL, '0', NULL, NULL, NULL, '123', '0', 0, '2020-04-23 16:59:07', NULL);

SET FOREIGN_KEY_CHECKS = 1;
