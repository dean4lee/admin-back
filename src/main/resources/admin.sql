/*
 Navicat Premium Data Transfer

 Source Server         : 00
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : 106.14.186.54:3306
 Source Schema         : admin

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 29/10/2019 09:20:50
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
  `pid` bigint(20) NOT NULL DEFAULT 0 COMMENT '父级id（0-根部门）',
  `level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '层级，所有父级id',
  `seq` int(5) NOT NULL DEFAULT 0 COMMENT '排序',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除状态（0-正常，1-删除）',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `creation_time` bigint(13) NOT NULL COMMENT '创建时间',
  `modifier` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `modify_time` bigint(13) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE,
  INDEX `pid`(`pid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '部门' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
INSERT INTO `sys_dept` VALUES (1569659656619000, '第一层', 0, '0', 0, NULL, b'0', 1, 1569659670000, 1, 1572309954952);
INSERT INTO `sys_dept` VALUES (1569661157895000, 'test', 1569659656619000, '0,1569659656619000', 1, NULL, b'0', 1, 1569661173000, NULL, NULL);
INSERT INTO `sys_dept` VALUES (1569729083883000, '开发部', 1569659656619000, '0,1569659656619000', 0, '', b'0', 1, 1569729084000, 1, 1569741396000);
INSERT INTO `sys_dept` VALUES (1569737846413000, '123', 1569661157895000, '0,1569659656619000,1569661157895000', 0, NULL, b'0', 1, 1569737846000, 1, 1569749360000);
INSERT INTO `sys_dept` VALUES (1572245734376000, 'aa', 0, '0', 0, NULL, b'0', 1, 1572245734376, 1, 1572249986740);
INSERT INTO `sys_dept` VALUES (1572247345684001, 'aasds', 1572245734376000, '0,1572245734376000', 0, NULL, b'0', 1, 1572247345684, 1, 1572247361251);

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
  `creation_time` bigint(13) NOT NULL COMMENT '创建时间',
  `modifier` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `modify_time` bigint(13) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户组' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_group
-- ----------------------------
INSERT INTO `sys_group` VALUES (1569468327468001, '测试用户组', NULL, b'0', 1, 1569468327000, NULL, NULL);

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
  `login_time` bigint(13) NOT NULL COMMENT '登陆时间',
  `status` bit(1) NOT NULL DEFAULT b'0' COMMENT '登陆状态（0-失败，1-成功）',
  `msg` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登陆失败的原因',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户登陆日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_login_log
-- ----------------------------
INSERT INTO `sys_login_log` VALUES (1569400094157000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1569400094000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1569402650177000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1569402650000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1569467493307000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1569467493000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1569468176712000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1569468177000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1569468709348000, 'test', 'Windows 10', 'Computer', '123.235.65.206', 1569468709000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1569485620675000, 'admin', 'Windows 10', 'Computer', '221.7.196.97', 1569485621000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1569745213434000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1569745213000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1570106250861000, 'admin', 'Mac OS X (iPhone)', 'Mobile', '112.224.71.180', 1570106251000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1570589853455000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1570589853000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1570837693685000, 'admin', 'Windows 10', 'Computer', '111.43.19.6', 1570837694000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571100404165000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1571100404000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571106162628000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1571106163000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571107525494000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1571107526000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571108241235000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1571108241000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571121332842000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1571121333000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571128644192000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1571128644000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571128847474000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1571128847000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571129844469000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1571129844000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571130072425000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1571130072000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571130897927000, 'admin', 'Mac OS X', 'Computer', '122.224.137.178', 1571130898000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571130901018000, 'admin', 'Android Mobile', 'Mobile', '223.104.2.9', 1571130901000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571130923984000, 'admin', 'Windows 10', 'Computer', '117.132.8.78', 1571130924000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571130970553000, 'admin', 'Windows 10', 'Computer', '27.223.78.167', 1571130971000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571131226246000, 'admin', 'Windows 10', 'Computer', '219.147.2.35', 1571131226000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571131777657000, 'admin', 'Windows 10', 'Computer', '61.162.60.198', 1571131778000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571132194606000, 'admin', 'Android Mobile', 'Mobile', '223.104.190.141', 1571132195000, b'0', '密码错误');
INSERT INTO `sys_login_log` VALUES (1571132217884000, 'admin', 'Android Mobile', 'Mobile', '223.104.190.141', 1571132218000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571188077626000, 'admin', 'Windows 10', 'Computer', '61.232.49.66', 1571188078000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571207949610000, 'admin', 'Mac OS X (iPhone)', 'Mobile', '124.64.18.139', 1571207950000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571210648561000, 'admin', 'Android Mobile', 'Mobile', '123.234.111.77', 1571210649000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571565794543000, 'admin', 'Windows 10', 'Computer', '120.87.183.205', 1571565795000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571633755854000, 'admin', 'Windows 7', 'Computer', '113.16.66.137', 1571633756000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571799823612000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1571799824000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571801344174000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1571801344000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571940825967000, 'admin', 'Linux', 'Computer', '112.96.134.222', 1571940826000, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1571995807205000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1571995807205, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1572050518371000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1572050518371, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1572239683430000, 'admin', 'Windows 10', 'Computer', '221.7.41.12', 1572239683430, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1572247529011000, 'admin', 'Windows 10', 'Computer', '218.17.192.250', 1572247529011, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1572247738801000, 'admin', 'Windows 10', 'Computer', '218.17.192.250', 1572247738801, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1572252864540000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1572252864540, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1572309001390000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1572309001401, b'1', NULL);
INSERT INTO `sys_login_log` VALUES (1572311055871000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1572311055871, b'0', '验证码错误');
INSERT INTO `sys_login_log` VALUES (1572311059575000, 'admin', 'Windows 10', 'Computer', '123.235.65.206', 1572311059575, b'1', NULL);

-- ----------------------------
-- Table structure for sys_operation_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_operation_log`;
CREATE TABLE `sys_operation_log`  (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `uid` bigint(20) NOT NULL COMMENT '用户id',
  `username` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `ip` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ip',
  `url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求URL',
  `method` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行方法',
  `exec_time` bigint(20) NOT NULL COMMENT '执行时间/ms',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '参数',
  `result` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '执行成功后的返回信息',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
  `exception` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '执行失败后的异常信息',
  `creation_time` bigint(13) NOT NULL COMMENT '创建时间',
  `status` bit(1) NOT NULL COMMENT '执行状态，0-失败，1-成功',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`, `uid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1572311755710001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统操作日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_operation_log
-- ----------------------------
INSERT INTO `sys_operation_log` VALUES (1569402657527000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/user/status', 'cn.inslee.adminback.ctrl.sys.SysUserCtrl.status', 177, '{\"id\":1,\"status\":true}', NULL, '用户状态修改', '测试项目，无法修改admin用户', 1569402658000, b'0');
INSERT INTO `sys_operation_log` VALUES (1569402670282000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/role/delete', 'cn.inslee.adminback.ctrl.sys.SysRoleCtrl.delete', 3, '1568772939750000', NULL, '角色删除', '测试项目，无法修改admin角色', 1569402670000, b'0');
INSERT INTO `sys_operation_log` VALUES (1569403466116000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/res/delete', 'cn.inslee.adminback.ctrl.sys.SysResCtrl.delete', 79, '1568793417548000', NULL, '资源删除', '测试项目，无法修改admin角色关联的资源', 1569403466000, b'0');
INSERT INTO `sys_operation_log` VALUES (1569403471414000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/res/update', 'cn.inslee.adminback.ctrl.sys.SysResCtrl.update', 123, '{\"id\":1568793473396000,\"name\":\"日志管理\",\"parentId\":0,\"permChar\":\"log:page\",\"seq\":1,\"url\":\"/log\"}', NULL, '资源修改', '测试项目，无法修改admin角色关联的资源', 1569403471000, b'0');
INSERT INTO `sys_operation_log` VALUES (1569403477457000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/user/delete', 'cn.inslee.adminback.ctrl.sys.SysUserCtrl.delete', 3, '1', NULL, '用户删除', '测试项目，无法修改admin用户', 1569403477000, b'0');
INSERT INTO `sys_operation_log` VALUES (1569468302284000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/role/add', 'cn.inslee.adminback.ctrl.sys.SysRoleCtrl.add', 66, '{\"name\":\"test\",\"resIds\":[1568793053657000,1569039988688001,1568793034295000,1568793473396000,1569039944773001]}', '{\"code\":200,\"msg\":\"角色添加成功\",\"status\":true}', '角色添加', NULL, 1569468302000, b'1');
INSERT INTO `sys_operation_log` VALUES (1569468312325000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/role/add', 'cn.inslee.adminback.ctrl.sys.SysRoleCtrl.add', 35, '{\"name\":\"test1\",\"resIds\":[1569036746501000,1569036597841001,1569036891266001,1568793417548000,1568792818721000,1569035253007000]}', '{\"code\":200,\"msg\":\"角色添加成功\",\"status\":true}', '角色添加', NULL, 1569468312000, b'1');
INSERT INTO `sys_operation_log` VALUES (1569468327468000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/group/add', 'cn.inslee.adminback.ctrl.sys.SysGroupCtrl.add', 45, '{\"name\":\"测试用户组\",\"roleIds\":[1569468302284001,1569468312325001]}', '{\"code\":200,\"msg\":\"用户组添加成功\",\"status\":true}', '用户组添加', NULL, 1569468327000, b'1');
INSERT INTO `sys_operation_log` VALUES (1569468639303000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/user/add', 'cn.inslee.adminback.ctrl.sys.SysUserCtrl.add', 54, '{\"email\":\"shadow4lee@163.com\",\"groupIds\":[1569468327468001],\"roleIds\":[],\"username\":\"test\"}', NULL, '用户添加', '邮箱已经存在', 1569468639000, b'0');
INSERT INTO `sys_operation_log` VALUES (1569468665446000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/user/add', 'cn.inslee.adminback.ctrl.sys.SysUserCtrl.add', 93, '{\"email\":\"dean.lee@aliyun.com\",\"groupIds\":[1569468327468001],\"roleIds\":[],\"username\":\"test\"}', '{\"code\":200,\"msg\":\"用户添加成功\",\"status\":true}', '用户添加', NULL, 1569468665000, b'1');
INSERT INTO `sys_operation_log` VALUES (1570837707879000, 1, 'admin', '111.43.19.6', 'http://admin.inslee.cn/sys/user/status', 'cn.inslee.adminback.ctrl.sys.SysUserCtrl.status', 28, '{\"id\":1569468665446001,\"status\":true}', '{\"code\":200,\"msg\":\"用户状态修改成功\",\"status\":true}', '用户状态修改', NULL, 1570837708000, b'1');
INSERT INTO `sys_operation_log` VALUES (1570837709507000, 1, 'admin', '111.43.19.6', 'http://admin.inslee.cn/sys/user/status', 'cn.inslee.adminback.ctrl.sys.SysUserCtrl.status', 19, '{\"id\":1569468665446001,\"status\":false}', '{\"code\":200,\"msg\":\"用户状态修改成功\",\"status\":true}', '用户状态修改', NULL, 1570837710000, b'1');
INSERT INTO `sys_operation_log` VALUES (1571107570164000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/role/delete', 'cn.inslee.admin.ctrl.sys.SysRoleCtrl.delete', 60, '1568772939750000', NULL, '角色删除', '当前角色有用户关联，请先解除', 1571107570000, b'0');
INSERT INTO `sys_operation_log` VALUES (1571107573591000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/role/update', 'cn.inslee.admin.ctrl.sys.SysRoleCtrl.update', 135, '{\"id\":1568772939750000,\"name\":\"admin\",\"remark\":\"最高权限\",\"resIds\":[1568881517753000,1568881786217000,1571021786935001,1569025948597000,1568793417548000,1568944480258000,1568792818721000,1568968597143000,1569039944773001,1571019039453001,1571019065711001,1568793053657000,1568792740183000,1569036891266001,1568793034295000,1568793473396000,1569036746501000,1569036597841001,1571021728820001,1569039988688001,1568876389002000,1571021754612001,1568881776302000,1568792934828000,1568968652358000,1569033453895000,1571021835594001,1568792652982000,1568862344386000,1568942634425000,1569031805602000,1571021817630001,1569033417472000,1569035253007000],\"roleChar\":\"root\"}', '{\"code\":200,\"msg\":\"角色修改成功\",\"status\":true}', '角色修改', NULL, 1571107574000, b'1');
INSERT INTO `sys_operation_log` VALUES (1571107586590000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/user/update', 'cn.inslee.admin.ctrl.sys.SysUserCtrl.update', 69, '{\"deptId\":1569659656619000,\"email\":\"shadow4lee@163.com\",\"groupIds\":[],\"id\":1,\"nickname\":\"gm\",\"phone\":\"17777777777\",\"remark\":\"系统管理员\",\"roleIds\":[1568772939750000],\"sex\":1}', '{\"code\":200,\"msg\":\"用户修改成功\",\"status\":true}', '用户修改', NULL, 1571107587000, b'1');
INSERT INTO `sys_operation_log` VALUES (1571108106578000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/user/update', 'cn.inslee.admin.ctrl.sys.SysUserCtrl.update', 258, '{\"deptId\":1569659656619000,\"email\":\"shadow4lee@163.com\",\"groupIds\":[],\"id\":1,\"nickname\":\"gm\",\"phone\":\"17777777777\",\"remark\":\"系统管理员\",\"roleIds\":[1568772939750000],\"sex\":1}', NULL, '用户修改', '演示项目，无法操作admin', 1571108107000, b'0');
INSERT INTO `sys_operation_log` VALUES (1571108113974000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/role/delete', 'cn.inslee.admin.ctrl.sys.SysRoleCtrl.delete', 37, '1568772939750000', NULL, '角色删除', '当前角色有用户关联，请先解除', 1571108114000, b'0');
INSERT INTO `sys_operation_log` VALUES (1571108116625000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/role/update', 'cn.inslee.admin.ctrl.sys.SysRoleCtrl.update', 10, '{\"id\":1568772939750000,\"name\":\"admin\",\"remark\":\"最高权限\",\"resIds\":[1568881517753000,1568881786217000,1571021786935001,1569025948597000,1568793417548000,1568944480258000,1568792818721000,1568968597143000,1569039944773001,1571019039453001,1571019065711001,1568793053657000,1568792740183000,1569036891266001,1568793034295000,1568793473396000,1569036746501000,1569036597841001,1571021728820001,1569039988688001,1568876389002000,1571021754612001,1568881776302000,1568792934828000,1568968652358000,1569033453895000,1571021835594001,1568792652982000,1568862344386000,1568942634425000,1569031805602000,1571021817630001,1569033417472000,1569035253007000],\"roleChar\":\"root\"}', NULL, '角色修改', '演示项目，无法操作admin关联的角色', 1571108117000, b'0');
INSERT INTO `sys_operation_log` VALUES (1571108121486000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/user/status', 'cn.inslee.admin.ctrl.sys.SysUserCtrl.status', 3, '{\"id\":1,\"status\":true}', NULL, '用户状态修改', '演示项目，无法操作admin', 1571108121000, b'0');
INSERT INTO `sys_operation_log` VALUES (1571108126000000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/res/update', 'cn.inslee.admin.ctrl.sys.SysResCtrl.update', 6, '{\"id\":1568793473396000,\"name\":\"日志管理\",\"parentId\":0,\"permChar\":\"log:page\",\"seq\":1,\"url\":\"/log\"}', NULL, '资源修改', '演示项目，无法操作admin关联的资源', 1571108126000, b'0');
INSERT INTO `sys_operation_log` VALUES (1571130530640000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/user/add', 'cn.inslee.admin.ctrl.sys.SysUserCtrl.add', 135, '{\"deptId\":1569729083883000,\"email\":\"aaaa@163.com\",\"groupIds\":[],\"roleIds\":[1569468302284001],\"username\":\"aaaa\"}', '{\"code\":200,\"msg\":\"用户添加成功\",\"status\":true}', '用户添加', NULL, 1571130531000, b'1');
INSERT INTO `sys_operation_log` VALUES (1571131229839000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/user/update', 'cn.inslee.admin.ctrl.sys.SysUserCtrl.update', 62, '{\"deptId\":1569729083883000,\"email\":\"dean444@aliyun.com\",\"groupIds\":[1569468327468001],\"id\":1569468665446001,\"roleIds\":[],\"sex\":0}', '{\"code\":200,\"msg\":\"用户修改成功\",\"status\":true}', '用户修改', NULL, 1571131230000, b'1');
INSERT INTO `sys_operation_log` VALUES (1571131667586000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/user/status', 'cn.inslee.admin.ctrl.sys.SysUserCtrl.status', 22, '{\"id\":1571130530641000,\"status\":true}', '{\"code\":200,\"msg\":\"用户状态修改成功\",\"status\":true}', '用户状态修改', NULL, 1571131668000, b'1');
INSERT INTO `sys_operation_log` VALUES (1571131670532000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/user/status', 'cn.inslee.admin.ctrl.sys.SysUserCtrl.status', 30, '{\"id\":1571130530641000,\"status\":false}', '{\"code\":200,\"msg\":\"用户状态修改成功\",\"status\":true}', '用户状态修改', NULL, 1571131671000, b'1');
INSERT INTO `sys_operation_log` VALUES (1571131672982000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/user/status', 'cn.inslee.admin.ctrl.sys.SysUserCtrl.status', 21, '{\"id\":1571021639110001,\"status\":true}', '{\"code\":200,\"msg\":\"用户状态修改成功\",\"status\":true}', '用户状态修改', NULL, 1571131673000, b'1');
INSERT INTO `sys_operation_log` VALUES (1571131673925000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/user/status', 'cn.inslee.admin.ctrl.sys.SysUserCtrl.status', 20, '{\"id\":1571021639110001,\"status\":false}', '{\"code\":200,\"msg\":\"用户状态修改成功\",\"status\":true}', '用户状态修改', NULL, 1571131674000, b'1');
INSERT INTO `sys_operation_log` VALUES (1571213894930000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/dept/delete', 'cn.inslee.admin.ctrl.sys.SysDeptCtrl.delete', 146, '1569659656619000', NULL, '部门删除', '当前部门有用户关联，请先解除', 1571213895000, b'0');
INSERT INTO `sys_operation_log` VALUES (1571620396650000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/user/delete', 'cn.inslee.admin.ctrl.sys.SysUserCtrl.delete', 2, '1', NULL, '用户删除', '演示项目，无法操作admin', 1571620397000, b'0');
INSERT INTO `sys_operation_log` VALUES (1571633805632000, 1, 'admin', '113.16.66.137', 'http://admin.inslee.cn/sys/user/update', 'cn.inslee.admin.ctrl.sys.SysUserCtrl.update', 184, '{\"age\":11,\"deptId\":1569661157895000,\"email\":\"dean33@aliyun.com\",\"groupIds\":[1569468327468001],\"id\":1571211442495001,\"nickname\":\"11\",\"phone\":\"13151515151\",\"remark\":\"11\",\"roleIds\":[1569468302284001],\"sex\":1}', '{\"code\":200,\"msg\":\"用户修改成功\",\"status\":true}', '用户修改', NULL, 1571633806000, b'1');
INSERT INTO `sys_operation_log` VALUES (1571633811225000, 1, 'admin', '113.16.66.137', 'http://admin.inslee.cn/sys/user/status', 'cn.inslee.admin.ctrl.sys.SysUserCtrl.status', 82, '{\"id\":1571211442495001,\"status\":true}', '{\"code\":200,\"msg\":\"用户状态修改成功\",\"status\":true}', '用户状态修改', NULL, 1571633811000, b'1');
INSERT INTO `sys_operation_log` VALUES (1571633813497000, 1, 'admin', '113.16.66.137', 'http://admin.inslee.cn/sys/user/status', 'cn.inslee.admin.ctrl.sys.SysUserCtrl.status', 73, '{\"id\":1571211442495001,\"status\":false}', '{\"code\":200,\"msg\":\"用户状态修改成功\",\"status\":true}', '用户状态修改', NULL, 1571633813000, b'1');
INSERT INTO `sys_operation_log` VALUES (1572245734359000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/dept/add', 'cn.inslee.admin.ctrl.sys.SysDeptCtrl.add', 47, '{\"name\":\"aa\"}', '{\"code\":200,\"msg\":\"部门添加成功\",\"status\":true}', '部门添加', NULL, 1572245734359, b'1');
INSERT INTO `sys_operation_log` VALUES (1572309021452000, 1, 'admin', '123.235.65.206', 'http://admin.inslee.cn/sys/dept/update', 'cn.inslee.admin.ctrl.sys.SysDeptCtrl.update', 36, '{\"id\":1569659656619000,\"name\":\"第一层\",\"pid\":1569729083883000,\"seq\":0}', '{\"code\":200,\"msg\":\"部门修改成功\",\"status\":true}', '部门修改', NULL, 1572309021452, b'1');

-- ----------------------------
-- Table structure for sys_res
-- ----------------------------
DROP TABLE IF EXISTS `sys_res`;
CREATE TABLE `sys_res`  (
  `id` bigint(20) NOT NULL,
  `name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源名称',
  `type` int(1) NOT NULL COMMENT '类型（1-菜单，2-权限）',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '路径',
  `pid` bigint(20) NOT NULL DEFAULT 0 COMMENT '父级id，0-根菜单',
  `level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '层级，所有父级id',
  `perm_char` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限字符',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `seq` int(5) NOT NULL DEFAULT 0 COMMENT '排序',
  `del_flag` bit(1) NOT NULL DEFAULT b'0' COMMENT '删除状态（0-正常，1-删除）',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `creation_time` bigint(13) NOT NULL COMMENT '创建时间',
  `modifier` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `modify_time` bigint(13) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`, `pid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_res
-- ----------------------------
INSERT INTO `sys_res` VALUES (1568792652982000, '用户管理', 1, '/sys/user', 1568793417548000, '0,1568793417548000', 'sys:user:page', NULL, 0, b'0', 1, 1568792735000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1568792740183000, '角色管理', 1, '/sys/role', 1568793417548000, '0,1568793417548000', 'sys:role:page', NULL, 1, b'0', 1, 1568792776000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1568792818721000, '资源管理', 1, '/sys/res', 1568793417548000, '0,1568793417548000', 'sys:res:page', NULL, 3, b'0', 1, 1568792848000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1568792934828000, '用户组管理', 1, '/sys/group', 1568793417548000, '0,1568793417548000', 'sys:group:page', NULL, 2, b'0', 1, 1568792987000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1568793034295000, '登陆日志', 1, '/log/login', 1568793473396000, '0,1568793473396000', 'log:login:page', NULL, 0, b'0', 1, 1568793051000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1568793053657000, '操作日志', 1, '/log/operation', 1568793473396000, '0,1568793473396000', 'log:operation:page', NULL, 1, b'0', 1, 1568793084000, 1, 1569037853000);
INSERT INTO `sys_res` VALUES (1568793417548000, '系统管理', 1, '/sys', 0, '0', 'sys:page', NULL, 0, b'0', 1, 1568793452000, 1, 1572311741124);
INSERT INTO `sys_res` VALUES (1568793473396000, '日志管理', 1, '/log', 0, '0', 'log:page', NULL, 1, b'0', 1, 1568793509000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1568862344386000, '用户新增', 2, '/sys/user/add', 1568792652982000, '0,1568793417548000,1568792652982000', 'sys:user:add', NULL, 0, b'0', 1, 1568862376000, 1, 1571101004000);
INSERT INTO `sys_res` VALUES (1568876389002000, '用户修改', 2, '/sys/user/update', 1568792652982000, '0,1568793417548000,1568792652982000', 'sys:user:update', NULL, 1, b'0', 1, 1568876426000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1568881517753000, '用户删除', 2, '/sys/userdelete', 1568792652982000, '0,1568793417548000,1568792652982000', 'sys:user:delete', NULL, 2, b'0', 1, 1568881500000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1568881776302000, '用户状态', 2, '/sys/user/status', 1568792652982000, '0,1568793417548000,1568792652982000', 'sys:user:status', NULL, 3, b'0', 1, 1568881767000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1568881786217000, '用户查询', 2, '/sys/user/list', 1568792652982000, '0,1568793417548000,1568792652982000', 'sys:user:list', NULL, 4, b'0', 1, 1568881820000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1568942634425000, '角色新增', 2, '/sys/role/add', 1568792740183000, '0,1568793417548000,1568792740183000', 'sys:role:add', NULL, 0, b'0', 1, 1568942664000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1568944480258000, '角色修改', 2, '/sys/role/update', 1568792740183000, '0,1568793417548000,1568792740183000', 'sys:role:update', NULL, 1, b'0', 1, 1568944511000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1568968597143000, '角色删除', 2, '/sys/role/delete', 1568792740183000, '0,1568793417548000,1568792740183000', 'sys:role:delete', NULL, 2, b'0', 1, 1568968628000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1568968652358000, '角色查询', 2, '/sys/role/list', 1568792740183000, '0,1568793417548000,1568792740183000', 'sys:role:list', NULL, 3, b'0', 1, 1568968682000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1569025948597000, '用户组新增', 2, '/sys/group/add', 1568792934828000, '0,1568793417548000,1568792934828000', 'sys:group:add', NULL, 0, b'0', 1, 1569025985000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1569031805602000, '用户组修改', 2, '/sys/group/update', 1568792934828000, '0,1568793417548000,1568792934828000', 'sys:group:update', NULL, 1, b'0', 1, 1569031864000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1569033417472000, '用户组删除', 2, '/sys/group/delete', 1568792934828000, '0,1568793417548000,1568792934828000', 'sys:group:delete', NULL, 2, b'0', 1, 1569033449000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1569033453895000, '用户组查询', 2, '/sys/group/list', 1568792934828000, '0,1568793417548000,1568792934828000', 'sys:group:list', NULL, 3, b'0', 1, 1569033502000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1569035253007000, '资源添加', 2, '/sys/res:add', 1568792818721000, '0,1568793417548000,1568792818721000', 'sys:res:add', NULL, 0, b'0', 1, 1569035287000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1569036597841001, '资源修改', 2, '/sys/res/update', 1568792818721000, '0,1568793417548000,1568792818721000', 'sys:res:update', NULL, 1, b'0', 1, 1569036598000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1569036746501000, '资源删除', 2, '/sys/res/delete', 1568792818721000, '0,1568793417548000,1568792818721000', 'sys:res:delete', NULL, 2, b'0', 1, 1569036747000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1569036891266001, '资源查询', 2, '/sys/res/list', 1568792818721000, '0,1568793417548000,1568792818721000', 'sys:res:list', NULL, 3, b'0', 1, 1569036891000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1569039944773001, '登录日志查询', 2, '/log/login/list', 1568793034295000, '0,1568793473396000,1568793034295000', 'log:login:list', NULL, 0, b'0', 1, 1569039945000, 1, 1569056855000);
INSERT INTO `sys_res` VALUES (1569039988688001, '操作日志查询', 2, '/log/operation/list', 1568793053657000, '0,1568793473396000,1568793053657000', 'log:operation:list', NULL, 0, b'0', 1, 1569039989000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1571019039453001, '数据监控', 1, '/log/druid', 1568793473396000, '0,1568793473396000', 'log:druid:page', NULL, 2, b'0', 1, 1571019039000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1571019065711001, '数据监控查询', 2, '/log/druid/list', 1571019039453001, '0,1568793473396000,1571019039453001', 'log:druid:list', NULL, 0, b'0', 1, 1571019066000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1571021728820001, '部门管理', 1, '/sys/dept', 1568793417548000, '0,1568793417548000', 'sys:dept:page', NULL, 4, b'0', 1, 1571021729000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1571021754612001, '部门添加', 2, '/sys/dept/add', 1571021728820001, '0,1568793417548000,1571021728820001', 'sys:dept:add', NULL, 0, b'0', 1, 1571021755000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1571021786935001, '部门修改', 2, '/sys/dept/update', 1571021728820001, '0,1568793417548000,1571021728820001', 'sys:dept:update', NULL, 1, b'0', 1, 1571021787000, NULL, NULL);
INSERT INTO `sys_res` VALUES (1571021817630001, '部门删除', 2, '/sys/dept/delete', 1571021728820001, '0,1568793417548000,1571021728820001', 'sys:dept:delete', NULL, 2, b'0', 1, 1571021818000, 1, 1571022007000);
INSERT INTO `sys_res` VALUES (1571021835594001, '部门查询', 2, '/sys/dept/list', 1571021728820001, '0,1568793417548000,1571021728820001', 'sys:dept:list', NULL, 3, b'0', 1, 1571021836000, NULL, NULL);

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
  `creation_time` bigint(13) NOT NULL COMMENT '创建时间',
  `modifier` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `modify_time` bigint(13) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1568772939750000, 'admin', 'root', '最高权限', b'0', 1, 1568773000000, 1, 1571107574000);
INSERT INTO `sys_role` VALUES (1569468302284001, 'test', NULL, NULL, b'0', 1, 1569468302000, NULL, NULL);
INSERT INTO `sys_role` VALUES (1569468312325001, 'test1', NULL, NULL, b'0', 1, 1569468312000, NULL, NULL);

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
  `creation_time` bigint(13) NOT NULL COMMENT '创建时间',
  `modifier` bigint(20) NULL DEFAULT NULL COMMENT '修改时间',
  `modify_time` bigint(13) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`id`, `dept_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'admin', '9867a651e0b92d41c0f6c1f9ec509e52', '91e75166-e6db-45be-9fc7-a175be776860', 1569659656619000, 'gm', '17777777778', 'shadow4lee@163.com', 1, 18, '系统管理员', b'0', b'0', 1, 1568771651000, 1, 1572248280111);
INSERT INTO `sys_user` VALUES (1569468665446001, 'test', 'c96b52e8653d95e6aa7eb0d8a0fc4ff6', 'e3f8f00a-2f79-4ffd-8a36-c680d53795c3', 1569729083883000, NULL, NULL, 'dean41@aliyun.com', 0, NULL, NULL, b'0', b'0', 1, 1569468665000, 1, 1571211424000);
INSERT INTO `sys_user` VALUES (1571021639110001, 'test1', '8ff525d3185489218cf05875efdd1df2', 'f67d2c4c-1d53-4d08-b692-44b25320244d', 1569661157895000, NULL, NULL, 'aaa@163.com', 0, NULL, NULL, b'0', b'0', 1, 1571021639000, 1, 1571984438234);
INSERT INTO `sys_user` VALUES (1571130530641000, 'aaaa', '1febe3737b6009d0c3a1d2a0d95e0881', '4cf9069a-b5cf-4ca0-839f-57a5f5377aa8', 1569729083883000, NULL, NULL, 'aaaa@163.com', 0, NULL, NULL, b'0', b'0', 1, 1571130531000, 1, 1571984122379);
INSERT INTO `sys_user` VALUES (1571211442495001, 'test22', '4af83aa735930b101ba765e37b483c13', '3b613e8b-c876-42b7-b0fe-5cf601879ce9', 1569661157895000, '11', '13151515151', 'dean33@aliyun.com', 1, 11, '11', b'0', b'0', 1, 1571211442000, 1, 1571633813000);

-- ----------------------------
-- Table structure for sys_user_group
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_group`;
CREATE TABLE `sys_user_group`  (
  `uid` bigint(20) NOT NULL,
  `group_id` bigint(20) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户与用户组关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_group
-- ----------------------------
INSERT INTO `sys_user_group` VALUES (1569468665446001, 1569468327468001);
INSERT INTO `sys_user_group` VALUES (1571211442495001, 1569468327468001);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `uid` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户与角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1568772939750000);
INSERT INTO `sys_user_role` VALUES (1571130530641000, 1569468302284001);
INSERT INTO `sys_user_role` VALUES (1571021639110001, 1569468302284001);
INSERT INTO `sys_user_role` VALUES (1571021639110001, 1569468312325001);
INSERT INTO `sys_user_role` VALUES (1571211442495001, 1569468302284001);

SET FOREIGN_KEY_CHECKS = 1;
