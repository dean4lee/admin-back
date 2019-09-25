/*
 Navicat Premium Data Transfer

 Source Server         : 47
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : 106.14.186.54:3306
 Source Schema         : admin

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 25/09/2019 16:19:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `parent_id` bigint(20) NOT NULL COMMENT '父级id（0-根部门）',
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除状态（0-正常，1-删除）',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `creation_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_group`;
CREATE TABLE `sys_group`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户组名',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除状态（0-正常，1-删除）',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `creation_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户组' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_group_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_role`;
CREATE TABLE `sys_group_role`  (
  `group_id` bigint(20) NOT NULL COMMENT '用户组id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户组与角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_log`;
CREATE TABLE `sys_login_log`  (
  `id` bigint(16) NOT NULL,
  `username` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户',
  `os_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备型号',
  `device_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备类型',
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登陆的ip',
  `login_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登陆时间',
  `status` bit(1) NOT NULL DEFAULT b'0' COMMENT '登陆状态（0-失败，1-成功）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登陆失败的原因',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户登陆日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log`  (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `username` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ip',
  `url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求URL',
  `method` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行方法',
  `exec_time` bigint(20) NOT NULL COMMENT '执行时间/ms',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '参数',
  `result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '执行成功后的返回信息',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
  `exception` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '执行失败后的异常信息',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `status` bit(1) NOT NULL COMMENT '执行状态，0-失败，1-成功',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1569398299516001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_res
-- ----------------------------
DROP TABLE IF EXISTS `sys_res`;
CREATE TABLE `sys_res`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源名称',
  `type` int(1) NOT NULL COMMENT '类型（1-菜单，2-权限）',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路径',
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父级id，0-根菜单',
  `perm_char` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限字符',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `seq` int(5) NOT NULL DEFAULT 0 COMMENT '排序',
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除状态（0-正常，1-删除）',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `creation_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_res
-- ----------------------------
INSERT INTO `sys_res` VALUES (1568792652982000, '用户管理', 1, '/sys/user', 1568793417548000, 'sys:user:page', NULL, 0, b'0', 1, '2019-09-18 15:45:35', NULL, NULL);
INSERT INTO `sys_res` VALUES (1568792740183000, '角色管理', 1, '/sys/role', 1568793417548000, 'sys:role:page', NULL, 1, b'0', 1, '2019-09-18 15:46:16', NULL, NULL);
INSERT INTO `sys_res` VALUES (1568792818721000, '资源管理', 1, '/sys/res', 1568793417548000, 'sys:res:page', NULL, 3, b'0', 1, '2019-09-18 15:47:28', NULL, NULL);
INSERT INTO `sys_res` VALUES (1568792934828000, '用户组管理', 1, '/sys/group', 1568793417548000, 'sys:group:page', NULL, 2, b'0', 1, '2019-09-18 15:49:47', NULL, NULL);
INSERT INTO `sys_res` VALUES (1568793034295000, '登陆日志', 1, '/log/login', 1568793473396000, 'log:login:page', NULL, 0, b'0', 1, '2019-09-18 15:50:51', NULL, NULL);
INSERT INTO `sys_res` VALUES (1568793053657000, '操作日志', 1, '/log/operation', 1568793473396000, 'log:operation:page', NULL, 1, b'0', 1, '2019-09-18 15:51:24', 1, '2019-09-21 11:50:53');
INSERT INTO `sys_res` VALUES (1568793417548000, '系统管理', 1, '/sys', 0, 'sys:page', NULL, 0, b'0', 1, '2019-09-18 15:57:32', NULL, NULL);
INSERT INTO `sys_res` VALUES (1568793473396000, '日志管理', 1, '/log', 0, 'log:page', NULL, 1, b'0', 1, '2019-09-18 15:58:29', NULL, NULL);
INSERT INTO `sys_res` VALUES (1568862344386000, '用户新增', 2, '/sys/user/add', 1568792652982000, 'sys:user:add', NULL, 0, b'0', 1, '2019-09-19 11:06:16', NULL, NULL);
INSERT INTO `sys_res` VALUES (1568876389002000, '用户修改', 2, '/sys/user/update', 1568792652982000, 'sys:user:update', NULL, 1, b'0', 1, '2019-09-19 15:00:26', NULL, NULL);
INSERT INTO `sys_res` VALUES (1568881517753000, '用户删除', 2, '/sys/userdelete', 1568792652982000, 'sys:user:delete', NULL, 2, b'0', 1, '2019-09-19 16:25:00', NULL, NULL);
INSERT INTO `sys_res` VALUES (1568881776302000, '用户状态', 2, '/sys/user/status', 1568792652982000, 'sys:user:status', NULL, 3, b'0', 1, '2019-09-19 16:29:27', NULL, NULL);
INSERT INTO `sys_res` VALUES (1568881786217000, '用户查询', 2, '/sys/user/list', 1568792652982000, 'sys:user:list', NULL, 4, b'0', 1, '2019-09-19 16:30:20', NULL, NULL);
INSERT INTO `sys_res` VALUES (1568942634425000, '角色新增', 2, '/sys/role/add', 1568792740183000, 'sys:role:add', NULL, 0, b'0', 1, '2019-09-20 09:24:24', NULL, NULL);
INSERT INTO `sys_res` VALUES (1568944480258000, '角色修改', 2, '/sys/role/update', 1568792740183000, 'sys:role:update', NULL, 1, b'0', 1, '2019-09-20 09:55:11', NULL, NULL);
INSERT INTO `sys_res` VALUES (1568968597143000, '角色删除', 2, '/sys/role/delete', 1568792740183000, 'sys:role:delete', NULL, 2, b'0', 1, '2019-09-20 16:37:08', NULL, NULL);
INSERT INTO `sys_res` VALUES (1568968652358000, '角色查询', 2, '/sys/role/list', 1568792740183000, 'sys:role:list', NULL, 3, b'0', 1, '2019-09-20 16:38:02', NULL, NULL);
INSERT INTO `sys_res` VALUES (1569025948597000, '用户组新增', 2, '/sys/group/add', 1568792934828000, 'sys:group:add', NULL, 0, b'0', 1, '2019-09-21 08:33:05', NULL, NULL);
INSERT INTO `sys_res` VALUES (1569031805602000, '用户组修改', 2, '/sys/group/update', 1568792934828000, 'sys:group:update', NULL, 1, b'0', 1, '2019-09-21 10:11:04', NULL, NULL);
INSERT INTO `sys_res` VALUES (1569033417472000, '用户组删除', 2, '/sys/group/delete', 1568792934828000, 'sys:group:delete', NULL, 2, b'0', 1, '2019-09-21 10:37:29', NULL, NULL);
INSERT INTO `sys_res` VALUES (1569033453895000, '用户组查询', 2, '/sys/group/list', 1568792934828000, 'sys:group:list', NULL, 3, b'0', 1, '2019-09-21 10:38:22', NULL, NULL);
INSERT INTO `sys_res` VALUES (1569035253007000, '资源添加', 2, '/sys/res:add', 1568792818721000, 'sys:res:add', NULL, 0, b'0', 1, '2019-09-21 11:08:07', NULL, NULL);
INSERT INTO `sys_res` VALUES (1569036597841001, '资源修改', 2, '/sys/res/update', 1568792818721000, 'sys:res:update', NULL, 1, b'0', 1, '2019-09-21 11:29:58', NULL, NULL);
INSERT INTO `sys_res` VALUES (1569036746501000, '资源删除', 2, '/sys/res/delete', 1568792818721000, 'sys:res:delete', NULL, 2, b'0', 1, '2019-09-21 11:32:27', NULL, NULL);
INSERT INTO `sys_res` VALUES (1569036891266001, '资源查询', 2, '/sys/res/list', 1568792818721000, 'sys:res:list', NULL, 3, b'0', 1, '2019-09-21 11:34:51', NULL, NULL);
INSERT INTO `sys_res` VALUES (1569039944773001, '登录日志查询', 2, '/log/login/list', 1568793034295000, 'log:login:list', NULL, 0, b'0', 1, '2019-09-21 12:25:45', 1, '2019-09-21 17:07:35');
INSERT INTO `sys_res` VALUES (1569039988688001, '操作日志查询', 2, '/log/operation/list', 1568793053657000, 'log:operation:list', NULL, 0, b'0', 1, '2019-09-21 12:26:29', NULL, NULL);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名',
  `role_char` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色字符',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除状态（0-正常，1-删除）',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `creation_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1568772939750000, 'admin', 'root', '最高权限', b'0', 1, '2019-09-18 10:16:40', 1, '2019-09-21 17:09:24');

-- ----------------------------
-- Table structure for sys_role_res
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_res`;
CREATE TABLE `sys_role_res`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `res_id` bigint(20) NOT NULL COMMENT '资源id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色与资源关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_res
-- ----------------------------
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568881517753000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568881786217000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1569025948597000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568793417548000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568944480258000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568792818721000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568968597143000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1569039944773001);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568793053657000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568792740183000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1569036891266001);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568793034295000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568793473396000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1569036746501000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1569036597841001);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1569039988688001);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568876389002000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568881776302000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568792934828000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568968652358000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1569033453895000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568792652982000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568862344386000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568942634425000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1569031805602000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1569033417472000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1569035253007000);

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(20) NOT NULL,
  `username` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '盐',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '所属部门id',
  `nickname` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `sex` int(1) NOT NULL DEFAULT 0 COMMENT '性别（0-女，1-男）',
  `age` int(2) NULL DEFAULT NULL COMMENT '年龄',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` bit(1) NOT NULL DEFAULT b'0' COMMENT '锁定状态（0-正常，1-锁定）',
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除状态（0-正常，1-删除）',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `creation_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '9867a651e0b92d41c0f6c1f9ec509e52', '91e75166-e6db-45be-9fc7-a175be776860', 0, 'gm', '17777777777', 'shadow4lee@163.com', 1, NULL, '系统管理员', b'0', b'0', 1, '2019-09-18 09:54:11', 1, '2019-09-25 15:40:29');

-- ----------------------------
-- Table structure for sys_user_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_group`;
CREATE TABLE `sys_user_group`  (
  `user_id` bigint(20) NOT NULL,
  `group_id` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户与用户组关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户与角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1568772939750000);

SET FOREIGN_KEY_CHECKS = 1;
