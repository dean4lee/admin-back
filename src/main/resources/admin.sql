/*
 Navicat Premium Data Transfer

 Source Server         : 111
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : 106.14.186.54:3306
 Source Schema         : admin

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 14/10/2019 11:02:06
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
  `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父级id（0-根部门）',
  `seq` int(5) NOT NULL DEFAULT 0 COMMENT '排序',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除状态（0-正常，1-删除）',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `creation_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modifier` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1569659656619000, '第一层', 0, 0, NULL, b'0', 1, '2019-09-28 16:34:30', 1, '2019-10-14 11:01:14');
INSERT INTO `sys_dept` VALUES (1569661157895000, 'test', 1569659656619000, 1, NULL, b'0', 1, '2019-09-28 16:59:33', NULL, NULL);
INSERT INTO `sys_dept` VALUES (1569729083883000, '开发部', 1569659656619000, 0, '', b'0', 1, '2019-09-29 11:51:24', 1, '2019-09-29 15:16:36');
INSERT INTO `sys_dept` VALUES (1569737846413000, '123', 1569661157895000, 0, NULL, b'0', 1, '2019-09-29 14:17:26', 1, '2019-09-29 17:29:20');

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
-- Records of sys_group
-- ----------------------------
INSERT INTO `sys_group` VALUES (1569468327468001, '测试用户组', NULL, b'0', 1, '2019-09-26 11:25:27', NULL, NULL);

-- ----------------------------
-- Table structure for sys_group_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_group_role`;
CREATE TABLE `sys_group_role`  (
  `group_id` bigint(20) NOT NULL COMMENT '用户组id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户组与角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_group_role
-- ----------------------------
INSERT INTO `sys_group_role` VALUES (1569468327468001, 1569468302284001);
INSERT INTO `sys_group_role` VALUES (1569468327468001, 1569468312325001);

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
-- Records of sys_login_log
-- ----------------------------
INSERT INTO `sys_login_log` VALUES (1569400094157000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', '2019-09-25 16:28:14', b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1569402650177000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', '2019-09-25 17:10:50', b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1569467493307000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', '2019-09-26 11:11:33', b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1569468176712000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', '2019-09-26 11:22:57', b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1569468709348000, 'test', 'Windows 10', 'Computer', '123.235.65.206', '2019-09-26 11:31:49', b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1569485620675000, 'admin', 'Windows 10', 'Computer', '221.7.196.97', '2019-09-26 16:13:41', b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1569745213434000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', '2019-09-29 16:20:13', b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1570106250861000, 'admin', 'Mac OS X (iPhone)', 'Mobile', '112.224.71.180', '2019-10-03 20:37:31', b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1570589853455000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', '2019-10-09 10:57:33', b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1570837693685000, 'admin', 'Windows 10', 'Computer', '111.43.19.6', '2019-10-12 07:48:14', b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571018515859000, 'admin', 'iOS 11 (iPhone)', 'Mobile', '127.0.0.1', '2019-10-14 10:01:56', b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571018589852000, 'admin', 'Windows 10', 'Computer', '127.0.0.1', '2019-10-14 10:03:10', b'1', NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 1571022073954001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_operation_log
-- ----------------------------
INSERT INTO `sys_operation_log` VALUES (1569402657527000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/user/status', 'cn.inslee.adminback.ctrl.sys.SysUserCtrl.status', 177, '{\"id\":1,\"status\":true}', NULL, '用户状态修改', '测试项目，无法修改admin用户', '2019-09-25 17:10:58', b'0');
INSERT INTO `sys_operation_log` VALUES (1569402670282000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/role/delete', 'cn.inslee.adminback.ctrl.sys.SysRoleCtrl.delete', 3, '1568772939750000', NULL, '角色删除', '测试项目，无法修改admin角色', '2019-09-25 17:11:10', b'0');
INSERT INTO `sys_operation_log` VALUES (1569403466116000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/res/delete', 'cn.inslee.adminback.ctrl.sys.SysResCtrl.delete', 79, '1568793417548000', NULL, '资源删除', '测试项目，无法修改admin角色关联的资源', '2019-09-25 17:24:26', b'0');
INSERT INTO `sys_operation_log` VALUES (1569403471414000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/res/update', 'cn.inslee.adminback.ctrl.sys.SysResCtrl.update', 123, '{\"id\":1568793473396000,\"name\":\"日志管理\",\"parentId\":0,\"permChar\":\"log:page\",\"seq\":1,\"url\":\"/log\"}', NULL, '资源修改', '测试项目，无法修改admin角色关联的资源', '2019-09-25 17:24:31', b'0');
INSERT INTO `sys_operation_log` VALUES (1569403477457000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/user/delete', 'cn.inslee.adminback.ctrl.sys.SysUserCtrl.delete', 3, '1', NULL, '用户删除', '测试项目，无法修改admin用户', '2019-09-25 17:24:37', b'0');
INSERT INTO `sys_operation_log` VALUES (1569468302284000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/role/add', 'cn.inslee.adminback.ctrl.sys.SysRoleCtrl.add', 66, '{\"name\":\"test\",\"resIds\":[1568793053657000,1569039988688001,1568793034295000,1568793473396000,1569039944773001]}', '{\"code\":200,\"msg\":\"角色添加成功\",\"status\":true}', '角色添加', NULL, '2019-09-26 11:25:02', b'1');
INSERT INTO `sys_operation_log` VALUES (1569468312325000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/role/add', 'cn.inslee.adminback.ctrl.sys.SysRoleCtrl.add', 35, '{\"name\":\"test1\",\"resIds\":[1569036746501000,1569036597841001,1569036891266001,1568793417548000,1568792818721000,1569035253007000]}', '{\"code\":200,\"msg\":\"角色添加成功\",\"status\":true}', '角色添加', NULL, '2019-09-26 11:25:12', b'1');
INSERT INTO `sys_operation_log` VALUES (1569468327468000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/group/add', 'cn.inslee.adminback.ctrl.sys.SysGroupCtrl.add', 45, '{\"name\":\"测试用户组\",\"roleIds\":[1569468302284001,1569468312325001]}', '{\"code\":200,\"msg\":\"用户组添加成功\",\"status\":true}', '用户组添加', NULL, '2019-09-26 11:25:27', b'1');
INSERT INTO `sys_operation_log` VALUES (1569468639303000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/user/add', 'cn.inslee.adminback.ctrl.sys.SysUserCtrl.add', 54, '{\"email\":\"shadow4lee@163.com\",\"groupIds\":[1569468327468001],\"roleIds\":[],\"username\":\"test\"}', NULL, '用户添加', '邮箱已经存在', '2019-09-26 11:30:39', b'0');
INSERT INTO `sys_operation_log` VALUES (1569468665446000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/user/add', 'cn.inslee.adminback.ctrl.sys.SysUserCtrl.add', 93, '{\"email\":\"dean.lee@aliyun.com\",\"groupIds\":[1569468327468001],\"roleIds\":[],\"username\":\"test\"}', '{\"code\":200,\"msg\":\"用户添加成功\",\"status\":true}', '用户添加', NULL, '2019-09-26 11:31:05', b'1');
INSERT INTO `sys_operation_log` VALUES (1570837707879000, 1, 'admin', '111.43.19.6', 'http://admin.inslee.cn/sys/user/status', 'cn.inslee.adminback.ctrl.sys.SysUserCtrl.status', 28, '{\"id\":1569468665446001,\"status\":true}', '{\"code\":200,\"msg\":\"用户状态修改成功\",\"status\":true}', '用户状态修改', NULL, '2019-10-12 07:48:28', b'1');
INSERT INTO `sys_operation_log` VALUES (1570837709507000, 1, 'admin', '111.43.19.6', 'http://admin.inslee.cn/sys/user/status', 'cn.inslee.adminback.ctrl.sys.SysUserCtrl.status', 19, '{\"id\":1569468665446001,\"status\":false}', '{\"code\":200,\"msg\":\"用户状态修改成功\",\"status\":true}', '用户状态修改', NULL, '2019-10-12 07:48:30', b'1');
INSERT INTO `sys_operation_log` VALUES (1571018950128000, 1, 'admin', '127.0.0.1', 'http://localhost:8080/sys/user/resetPwd', 'cn.inslee.admin.ctrl.sys.SysUserCtrl.resetPwd', 173, '1569468665446001', '{\"code\":200,\"msg\":\"重置密码成功\",\"status\":true}', '重置用户密码', NULL, '2019-10-14 10:09:10', b'1');
INSERT INTO `sys_operation_log` VALUES (1571019039453000, 1, 'admin', '127.0.0.1', 'http://localhost:8080/sys/res/add', 'cn.inslee.admin.ctrl.sys.SysResCtrl.add', 73, '{\"name\":\"数据监控\",\"parentId\":1568793473396000,\"permChar\":\"log:druid:page\",\"seq\":2,\"type\":1,\"url\":\"/log/druid\"}', '{\"code\":200,\"msg\":\"添加资源成功\",\"status\":true}', '资源添加', NULL, '2019-10-14 10:10:39', b'1');
INSERT INTO `sys_operation_log` VALUES (1571019065711000, 1, 'admin', '127.0.0.1', 'http://localhost:8080/sys/res/add', 'cn.inslee.admin.ctrl.sys.SysResCtrl.add', 67, '{\"name\":\"数据监控查询\",\"parentId\":1571019039453001,\"permChar\":\"log:druid:list\",\"type\":2,\"url\":\"/log/druid/list\"}', '{\"code\":200,\"msg\":\"添加资源成功\",\"status\":true}', '资源添加', NULL, '2019-10-14 10:11:06', b'1');
INSERT INTO `sys_operation_log` VALUES (1571019080321000, 1, 'admin', '127.0.0.1', 'http://localhost:8080/sys/role/update', 'cn.inslee.admin.ctrl.sys.SysRoleCtrl.update', 312, '{\"id\":1568772939750000,\"name\":\"admin\",\"remark\":\"最高权限\",\"resIds\":[1568881517753000,1568881786217000,1569025948597000,1568793417548000,1568944480258000,1568792818721000,1568968597143000,1569039944773001,1571019039453001,1571019065711001,1568793053657000,1568792740183000,1569036891266001,1568793034295000,1568793473396000,1569036746501000,1569036597841001,1569039988688001,1568876389002000,1568881776302000,1568792934828000,1568968652358000,1569033453895000,1568792652982000,1568862344386000,1568942634425000,1569031805602000,1569033417472000,1569035253007000],\"roleChar\":\"root\"}', '{\"code\":200,\"msg\":\"角色修改成功\",\"status\":true}', '角色修改', NULL, '2019-10-14 10:11:20', b'1');
INSERT INTO `sys_operation_log` VALUES (1571021635026000, 1, 'admin', '127.0.0.1', 'http://localhost:8080/sys/user/add', 'cn.inslee.admin.ctrl.sys.SysUserCtrl.add', 235, '{\"deptId\":1569661157895000,\"email\":\"aaa@163.com\",\"groupIds\":[],\"roleIds\":[1569468302284001],\"username\":\"test\"}', NULL, '用户添加', '用户名已经存在', '2019-10-14 10:53:55', b'0');
INSERT INTO `sys_operation_log` VALUES (1571021639110000, 1, 'admin', '127.0.0.1', 'http://localhost:8080/sys/user/add', 'cn.inslee.admin.ctrl.sys.SysUserCtrl.add', 244, '{\"deptId\":1569661157895000,\"email\":\"aaa@163.com\",\"groupIds\":[],\"roleIds\":[1569468302284001],\"username\":\"test1\"}', '{\"code\":200,\"msg\":\"用户添加成功\",\"status\":true}', '用户添加', NULL, '2019-10-14 10:53:59', b'1');
INSERT INTO `sys_operation_log` VALUES (1571021728820000, 1, 'admin', '127.0.0.1', 'http://localhost:8080/sys/res/add', 'cn.inslee.admin.ctrl.sys.SysResCtrl.add', 98, '{\"name\":\"部门管理\",\"parentId\":1568793417548000,\"permChar\":\"sys:dept:page\",\"seq\":4,\"type\":1,\"url\":\"/sys/dept\"}', '{\"code\":200,\"msg\":\"添加资源成功\",\"status\":true}', '资源添加', NULL, '2019-10-14 10:55:29', b'1');
INSERT INTO `sys_operation_log` VALUES (1571021754612000, 1, 'admin', '127.0.0.1', 'http://localhost:8080/sys/res/add', 'cn.inslee.admin.ctrl.sys.SysResCtrl.add', 66, '{\"name\":\"部门添加\",\"parentId\":1571021728820001,\"permChar\":\"sys:dept:add\",\"type\":2,\"url\":\"/sys/dept/add\"}', '{\"code\":200,\"msg\":\"添加资源成功\",\"status\":true}', '资源添加', NULL, '2019-10-14 10:55:55', b'1');
INSERT INTO `sys_operation_log` VALUES (1571021786935000, 1, 'admin', '127.0.0.1', 'http://localhost:8080/sys/res/add', 'cn.inslee.admin.ctrl.sys.SysResCtrl.add', 69, '{\"name\":\"部门修改\",\"parentId\":1571021728820001,\"permChar\":\"sys:dept:update\",\"seq\":1,\"type\":2,\"url\":\"/sys/dept/update\"}', '{\"code\":200,\"msg\":\"添加资源成功\",\"status\":true}', '资源添加', NULL, '2019-10-14 10:56:27', b'1');
INSERT INTO `sys_operation_log` VALUES (1571021817630000, 1, 'admin', '127.0.0.1', 'http://localhost:8080/sys/res/add', 'cn.inslee.admin.ctrl.sys.SysResCtrl.add', 70, '{\"icon\":\"sys:dept:delete\",\"name\":\"部门删除\",\"parentId\":1571021728820001,\"seq\":2,\"type\":2,\"url\":\"/sys/dept/delete\"}', '{\"code\":200,\"msg\":\"添加资源成功\",\"status\":true}', '资源添加', NULL, '2019-10-14 10:56:58', b'1');
INSERT INTO `sys_operation_log` VALUES (1571021835594000, 1, 'admin', '127.0.0.1', 'http://localhost:8080/sys/res/add', 'cn.inslee.admin.ctrl.sys.SysResCtrl.add', 71, '{\"name\":\"部门查询\",\"parentId\":1571021728820001,\"permChar\":\"sys:dept:list\",\"seq\":3,\"type\":2,\"url\":\"/sys/dept/list\"}', '{\"code\":200,\"msg\":\"添加资源成功\",\"status\":true}', '资源添加', NULL, '2019-10-14 10:57:16', b'1');
INSERT INTO `sys_operation_log` VALUES (1571021844918000, 1, 'admin', '127.0.0.1', 'http://localhost:8080/sys/role/update', 'cn.inslee.admin.ctrl.sys.SysRoleCtrl.update', 286, '{\"id\":1568772939750000,\"name\":\"admin\",\"remark\":\"最高权限\",\"resIds\":[1568881517753000,1568881786217000,1571021786935001,1569025948597000,1568793417548000,1568944480258000,1568792818721000,1568968597143000,1569039944773001,1571019039453001,1571019065711001,1568793053657000,1568792740183000,1569036891266001,1568793034295000,1568793473396000,1569036746501000,1569036597841001,1571021728820001,1569039988688001,1568876389002000,1571021754612001,1568881776302000,1568792934828000,1568968652358000,1569033453895000,1571021835594001,1568792652982000,1568862344386000,1568942634425000,1569031805602000,1571021817630001,1569033417472000,1569035253007000],\"roleChar\":\"root\"}', '{\"code\":200,\"msg\":\"角色修改成功\",\"status\":true}', '角色修改', NULL, '2019-10-14 10:57:25', b'1');
INSERT INTO `sys_operation_log` VALUES (1571022007143000, 1, 'admin', '127.0.0.1', 'http://localhost:8080/sys/res/update', 'cn.inslee.admin.ctrl.sys.SysResCtrl.update', 104, '{\"id\":1571021817630001,\"name\":\"部门删除\",\"parentId\":1571021728820001,\"permChar\":\"sys:dept:delete\",\"seq\":2,\"url\":\"/sys/dept/delete\"}', '{\"code\":200,\"msg\":\"修改资源成功\",\"status\":true}', '资源修改', NULL, '2019-10-14 11:00:07', b'1');
INSERT INTO `sys_operation_log` VALUES (1571022073954000, 1, 'admin', '127.0.0.1', 'http://localhost:8080/sys/dept/update', 'cn.inslee.admin.ctrl.sys.SysDeptCtrl.update', 80, '{\"id\":1569659656619000,\"name\":\"第一层\",\"parentId\":0,\"seq\":0}', '{\"code\":200,\"msg\":\"部门修改成功\",\"status\":true}', '部门修改', NULL, '2019-10-14 11:01:14', b'1');

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
INSERT INTO `sys_res` VALUES (1568793417548000, '系统管理', 1, '/sys', 0, 'sys:page', NULL, 0, b'0', 1, '2019-09-18 15:57:32', 1, '2019-09-25 17:11:31');
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
INSERT INTO `sys_res` VALUES (1571019039453001, '数据监控', 1, '/log/druid', 1568793473396000, 'log:druid:page', NULL, 2, b'0', 1, '2019-10-14 10:10:39', NULL, NULL);
INSERT INTO `sys_res` VALUES (1571019065711001, '数据监控查询', 2, '/log/druid/list', 1571019039453001, 'log:druid:list', NULL, 0, b'0', 1, '2019-10-14 10:11:06', NULL, NULL);
INSERT INTO `sys_res` VALUES (1571021728820001, '部门管理', 1, '/sys/dept', 1568793417548000, 'sys:dept:page', NULL, 4, b'0', 1, '2019-10-14 10:55:29', NULL, NULL);
INSERT INTO `sys_res` VALUES (1571021754612001, '部门添加', 2, '/sys/dept/add', 1571021728820001, 'sys:dept:add', NULL, 0, b'0', 1, '2019-10-14 10:55:55', NULL, NULL);
INSERT INTO `sys_res` VALUES (1571021786935001, '部门修改', 2, '/sys/dept/update', 1571021728820001, 'sys:dept:update', NULL, 1, b'0', 1, '2019-10-14 10:56:27', NULL, NULL);
INSERT INTO `sys_res` VALUES (1571021817630001, '部门删除', 2, '/sys/dept/delete', 1571021728820001, 'sys:dept:delete', NULL, 2, b'0', 1, '2019-10-14 10:56:58', 1, '2019-10-14 11:00:07');
INSERT INTO `sys_res` VALUES (1571021835594001, '部门查询', 2, '/sys/dept/list', 1571021728820001, 'sys:dept:list', NULL, 3, b'0', 1, '2019-10-14 10:57:16', NULL, NULL);

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
INSERT INTO `sys_role` VALUES (1568772939750000, 'admin', 'root', '最高权限', b'0', 1, '2019-09-18 10:16:40', 1, '2019-10-14 10:57:25');
INSERT INTO `sys_role` VALUES (1569468302284001, 'test', NULL, NULL, b'0', 1, '2019-09-26 11:25:02', NULL, NULL);
INSERT INTO `sys_role` VALUES (1569468312325001, 'test1', NULL, NULL, b'0', 1, '2019-09-26 11:25:12', NULL, NULL);

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
INSERT INTO `sys_role_res` VALUES (1569468302284001, 1568793053657000);
INSERT INTO `sys_role_res` VALUES (1569468302284001, 1569039988688001);
INSERT INTO `sys_role_res` VALUES (1569468302284001, 1568793034295000);
INSERT INTO `sys_role_res` VALUES (1569468302284001, 1568793473396000);
INSERT INTO `sys_role_res` VALUES (1569468302284001, 1569039944773001);
INSERT INTO `sys_role_res` VALUES (1569468312325001, 1569036746501000);
INSERT INTO `sys_role_res` VALUES (1569468312325001, 1569036597841001);
INSERT INTO `sys_role_res` VALUES (1569468312325001, 1569036891266001);
INSERT INTO `sys_role_res` VALUES (1569468312325001, 1568793417548000);
INSERT INTO `sys_role_res` VALUES (1569468312325001, 1568792818721000);
INSERT INTO `sys_role_res` VALUES (1569468312325001, 1569035253007000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568881517753000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568881786217000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1571021786935001);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1569025948597000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568793417548000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568944480258000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568792818721000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568968597143000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1569039944773001);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1571019039453001);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1571019065711001);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568793053657000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568792740183000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1569036891266001);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568793034295000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568793473396000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1569036746501000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1569036597841001);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1571021728820001);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1569039988688001);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568876389002000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1571021754612001);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568881776302000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568792934828000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568968652358000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1569033453895000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1571021835594001);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568792652982000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568862344386000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1568942634425000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1569031805602000);
INSERT INTO `sys_role_res` VALUES (1568772939750000, 1571021817630001);
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
  `dept_id` bigint(20) NOT NULL COMMENT '所属部门id',
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
INSERT INTO `sys_user` VALUES (1, 'admin', '9867a651e0b92d41c0f6c1f9ec509e52', '91e75166-e6db-45be-9fc7-a175be776860', 1569659656619000, 'gm', '17777777777', 'shadow4lee@163.com', 1, NULL, '系统管理员', b'0', b'0', 1, '2019-09-18 09:54:11', 1, '2019-09-25 15:40:29');
INSERT INTO `sys_user` VALUES (1569468665446001, 'test', '068fad57e014b42473f83fc29b6106c1', 'e3f8f00a-2f79-4ffd-8a36-c680d53795c3', 1569729083883000, NULL, NULL, 'dean.lee@aliyun.com', 0, NULL, NULL, b'0', b'0', 1, '2019-09-26 11:31:05', 1, '2019-10-14 10:09:10');
INSERT INTO `sys_user` VALUES (1571021639110001, 'test1', '75cf9d1a24a8cf5b45ca375e7a4d9f1d', 'f67d2c4c-1d53-4d08-b692-44b25320244d', 1569661157895000, NULL, NULL, 'aaa@163.com', 0, NULL, NULL, b'0', b'0', 1, '2019-10-14 10:53:59', NULL, NULL);

-- ----------------------------
-- Table structure for sys_user_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_group`;
CREATE TABLE `sys_user_group`  (
  `user_id` bigint(20) NOT NULL,
  `group_id` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户与用户组关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_group
-- ----------------------------
INSERT INTO `sys_user_group` VALUES (1569468665446001, 1569468327468001);

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
INSERT INTO `sys_user_role` VALUES (1571021639110001, 1569468302284001);

SET FOREIGN_KEY_CHECKS = 1;
