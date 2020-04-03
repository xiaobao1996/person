/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : info_report_v2.000_tb1

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2020-04-03 13:40:00
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for ir_config
-- ----------------------------
DROP TABLE IF EXISTS `ir_config`;
CREATE TABLE `ir_config` (
  `school_id` varchar(64) NOT NULL COMMENT '学校id',
  `temperature_low_limit` decimal(10,2) NOT NULL COMMENT '异常体温下限',
  `no_pass` tinyint(2) NOT NULL COMMENT '禁止通行 0否 1是',
  `device_alarm` tinyint(2) NOT NULL COMMENT '设备报警 0否 1是',
  `notify_continuous_number` int(10) NOT NULL COMMENT '连续异常次数通知',
  `mode` tinyint(2) NOT NULL COMMENT '模式 0假期 1开学',
  PRIMARY KEY (`school_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统设置表';

-- ----------------------------
-- Records of ir_config
-- ----------------------------
INSERT INTO `ir_config` VALUES ('d3746e6564374233bffd66e3612c9924', '37.30', '0', '0', '3', '0');

-- ----------------------------
-- Table structure for ir_duty_person
-- ----------------------------
DROP TABLE IF EXISTS `ir_duty_person`;
CREATE TABLE `ir_duty_person` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `date` date NOT NULL COMMENT '日期',
  `user_id` varchar(64) NOT NULL COMMENT '用户id',
  `school_id` varchar(64) NOT NULL COMMENT '学校id',
  `create_at` datetime(3) NOT NULL COMMENT '创建时间',
  `create_by` varchar(64) NOT NULL COMMENT '创建人',
  `update_at` datetime(3) NOT NULL COMMENT '更新时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新人',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态（0删除 1正常)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `udx_school_id_date_user_id` (`school_id`,`date`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='值班员表';

-- ----------------------------
-- Records of ir_duty_person
-- ----------------------------
INSERT INTO `ir_duty_person` VALUES ('00001', '2020-03-22', '48be7891b50d477a89e00322cd55ca18888', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '1');
INSERT INTO `ir_duty_person` VALUES ('1110', '2020-03-25', '48be7891b50d477a89e00322cd55ca18', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '1');
INSERT INTO `ir_duty_person` VALUES ('1111', '2020-03-15', '48be7891b50d477a89e00322cd55ca18', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '1');
INSERT INTO `ir_duty_person` VALUES ('11111', '2020-03-26', '48be7891b50d477a89e00322cd55ca18', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '1');
INSERT INTO `ir_duty_person` VALUES ('11112', '2020-03-27', '48be7891b50d477a89e00322cd55ca18', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '1');
INSERT INTO `ir_duty_person` VALUES ('11113', '2020-03-28', '48be7891b50d477a89e00322cd55ca18', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '1');
INSERT INTO `ir_duty_person` VALUES ('11114', '2020-03-29', '48be7891b50d477a89e00322cd55ca18', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '1');
INSERT INTO `ir_duty_person` VALUES ('11115', '2020-03-30', '48be7891b50d477a89e00322cd55ca18', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '1');
INSERT INTO `ir_duty_person` VALUES ('11116', '2020-03-31', '48be7891b50d477a89e00322cd55ca18', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '1');
INSERT INTO `ir_duty_person` VALUES ('11117', '2020-04-01', '48be7891b50d477a89e00322cd55ca18', 'd3746e6564374233bffd66e3612c9924', '2020-03-31 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '1');
INSERT INTO `ir_duty_person` VALUES ('11118', '2020-03-01', '48be7891b50d477a89e00322cd55ca18', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '1');
INSERT INTO `ir_duty_person` VALUES ('1112', '2020-03-16', '48be7891b50d477a89e00322cd55ca18', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '1');
INSERT INTO `ir_duty_person` VALUES ('1113', '2020-03-17', '48be7891b50d477a89e00322cd55ca18', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '1');
INSERT INTO `ir_duty_person` VALUES ('1114', '2020-03-18', '48be7891b50d477a89e00322cd55ca18', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '1');
INSERT INTO `ir_duty_person` VALUES ('1115', '2020-03-19', '48be7891b50d477a89e00322cd55ca18', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '1');
INSERT INTO `ir_duty_person` VALUES ('1116', '2020-03-20', '48be7891b50d477a89e00322cd55ca18', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '1');
INSERT INTO `ir_duty_person` VALUES ('1117', '2020-03-21', '48be7891b50d477a89e00322cd55ca18', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '1');
INSERT INTO `ir_duty_person` VALUES ('1118', '2020-03-23', '48be7891b50d477a89e00322cd55ca18', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '1');
INSERT INTO `ir_duty_person` VALUES ('1119', '2020-03-24', '48be7891b50d477a89e00322cd55ca18', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '2020-04-02 09:24:21.000', '48be7891b50d477a89e00322cd55ca18', '1');

-- ----------------------------
-- Table structure for ir_health_monitor
-- ----------------------------
DROP TABLE IF EXISTS `ir_health_monitor`;
CREATE TABLE `ir_health_monitor` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `user_id` varchar(64) NOT NULL COMMENT '用户id',
  `monitor_date` date NOT NULL COMMENT '检测日期',
  `am_temperature` decimal(10,2) DEFAULT NULL COMMENT '上午体温',
  `am_recheck` tinyint(2) DEFAULT NULL COMMENT '上午是否人工复测',
  `am_check_time` datetime(3) DEFAULT NULL COMMENT '上午监测数据的时间',
  `am_human_check` tinyint(2) DEFAULT NULL COMMENT '上午是否是人工测试 0否 1是',
  `pm_temperature` decimal(10,2) DEFAULT NULL COMMENT '下午体温',
  `pm_recheck` tinyint(2) DEFAULT NULL COMMENT '下午是否复测',
  `pm_check_time` datetime(3) DEFAULT NULL COMMENT '下午监测数据的时间',
  `pm_human_check` tinyint(2) DEFAULT NULL COMMENT '下午是否是人工测试',
  `health_status` tinyint(2) NOT NULL DEFAULT '2' COMMENT '体温状态 2异常 1需确认 0正常',
  `check_status` tinyint(2) DEFAULT NULL COMMENT '测温进展 3测试完成 2下午未测 1上午未测 0全天未测 NULL为全天未测',
  `school_id` varchar(64) NOT NULL COMMENT '学校id',
  `create_at` datetime(3) NOT NULL COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `udx_monitor_date_school_id_user_id` (`monitor_date`,`school_id`,`user_id`),
  KEY `idx_school_id_user_id` (`school_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='健康监测表';

-- ----------------------------
-- Records of ir_health_monitor
-- ----------------------------

-- ----------------------------
-- Table structure for ir_monitor_history
-- ----------------------------
DROP TABLE IF EXISTS `ir_monitor_history`;
CREATE TABLE `ir_monitor_history` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `user_id` varchar(64) NOT NULL COMMENT '用户id',
  `monitor_date` date NOT NULL COMMENT '测温日期',
  `monitor_datetime` datetime(3) NOT NULL COMMENT '测试时间',
  `monitor_user_id` varchar(64) DEFAULT NULL COMMENT '测温人id',
  `monitor_user_name` varchar(16) DEFAULT NULL COMMENT '测温人姓名',
  `monitor_device` varchar(16) DEFAULT NULL COMMENT '测温设备',
  `monitor_place` varchar(64) DEFAULT NULL COMMENT '测温地点',
  `temperature` decimal(10,2) NOT NULL COMMENT '体温',
  `source` tinyint(2) NOT NULL COMMENT '数据来源 0人工监测 1机器监测 3天波数据导入',
  `school_id` varchar(64) NOT NULL COMMENT '学校id',
  `create_at` datetime(3) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_school_id_user_id` (`school_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='测温记录表';

-- ----------------------------
-- Records of ir_monitor_history
-- ----------------------------

-- ----------------------------
-- Table structure for ir_msg_log
-- ----------------------------
DROP TABLE IF EXISTS `ir_msg_log`;
CREATE TABLE `ir_msg_log` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `date` date NOT NULL COMMENT '日期',
  `user_id` varchar(64) NOT NULL COMMENT '被监测人用户id',
  `time_area` tinyint(2) DEFAULT NULL COMMENT '时间范围 0上午 1下午',
  `receiver` varchar(10000) DEFAULT NULL COMMENT '接收人 ,分割',
  `ope_type` tinyint(2) NOT NULL COMMENT '操作类型 0自动发送 1手工发送',
  `school_id` varchar(64) NOT NULL COMMENT '学校id',
  `content` varchar(3000) DEFAULT NULL COMMENT '发送内容',
  `result` varchar(1024) DEFAULT NULL COMMENT '发送结果',
  `create_at` datetime(3) NOT NULL COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  KEY `idx_school_id_user_id` (`school_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='消息发送记录表';

-- ----------------------------
-- Records of ir_msg_log
-- ----------------------------

-- ----------------------------
-- Table structure for ir_report_config
-- ----------------------------
DROP TABLE IF EXISTS `ir_report_config`;
CREATE TABLE `ir_report_config` (
  `school_id` varchar(64) NOT NULL COMMENT '学校id',
  `student_report_identify` varchar(2048) DEFAULT NULL COMMENT '学生通知身份',
  `student_report_user_ids` varchar(2048) DEFAULT NULL COMMENT '学生通知用户id集合',
  `staff_report_identify` varchar(2048) DEFAULT NULL COMMENT '教职工通知身份',
  `staff_report_user_ids` varchar(2048) DEFAULT NULL COMMENT '教职工通知用户id集合',
  `create_at` datetime(3) NOT NULL COMMENT '创建时间',
  `update_at` datetime(3) NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`school_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='通知配置表';

-- ----------------------------
-- Records of ir_report_config
-- ----------------------------
INSERT INTO `ir_report_config` VALUES ('d3746e6564374233bffd66e3612c9924', '-INNER:HEAD_TEACHER', '33ee2aa799564e74be3dac2eb17962bd', '', '09dab1c3fe6c45ad849f64d66dd392ab', '2020-04-01 09:45:00.054', '2020-04-01 09:50:40.537');

-- ----------------------------
-- Table structure for ir_temperature_report
-- ----------------------------
DROP TABLE IF EXISTS `ir_temperature_report`;
CREATE TABLE `ir_temperature_report` (
  `id` varchar(64) NOT NULL COMMENT 'id',
  `data_time` datetime DEFAULT NULL COMMENT '填报时间',
  `mobile` varchar(64) DEFAULT NULL COMMENT '手机号',
  `user_name` varchar(64) NOT NULL COMMENT '用户姓名',
  `user_id` varchar(64) NOT NULL COMMENT '用户id',
  `office_name` varchar(255) DEFAULT NULL COMMENT '所在部门名称',
  `office_id` varchar(255) DEFAULT NULL COMMENT '学生为所在班级/教师为所在部门的id',
  `identity` tinyint(3) NOT NULL COMMENT '身份(0 学生 1 教职工)',
  `temperature` decimal(5,1) NOT NULL COMMENT '体温',
  `other_symptom` tinyint(3) DEFAULT NULL COMMENT '其他症状( 0 无 , 1 有 )',
  `current_location` varchar(255) NOT NULL COMMENT '当前所在地',
  `contact` tinyint(3) DEFAULT NULL COMMENT '有无和疫情人员接触( 0:无 ,1: 有)',
  `arrive_other_area` tinyint(3) DEFAULT NULL COMMENT '15天内有无去过其他区域',
  `abnormal` tinyint(3) NOT NULL COMMENT '是否异常( 0 否 1 是)',
  `other_info` varchar(255) DEFAULT NULL COMMENT '其他信息',
  `school_id` varchar(255) NOT NULL COMMENT '学校id',
  `create_at` datetime(3) NOT NULL COMMENT '创建时间',
  `create_by` varchar(255) DEFAULT NULL COMMENT '创建人',
  `update_at` datetime(3) NOT NULL COMMENT '更新时间',
  `update_by` varchar(255) DEFAULT NULL COMMENT '更新人',
  `status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '状态（0删除 1正常  2停用）',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_school_id_user_id` (`school_id`,`user_id`) USING BTREE,
  KEY `idx_data_time` (`data_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ir_temperature_report
-- ----------------------------

-- ----------------------------
-- Table structure for ir_user_arrive_area
-- ----------------------------
DROP TABLE IF EXISTS `ir_user_arrive_area`;
CREATE TABLE `ir_user_arrive_area` (
  `id` varchar(64) NOT NULL COMMENT 'id',
  `report_id` varchar(64) NOT NULL COMMENT '记录id',
  `province` varchar(64) NOT NULL COMMENT '省',
  `city` varchar(64) NOT NULL COMMENT '市',
  `area` varchar(64) NOT NULL COMMENT '区',
  `sort` tinyint(2) DEFAULT NULL COMMENT '排序字段',
  `create_at` datetime(3) NOT NULL COMMENT '创建时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ir_user_arrive_area
-- ----------------------------

-- ----------------------------
-- Table structure for pms_class
-- ----------------------------
DROP TABLE IF EXISTS `pms_class`;
CREATE TABLE `pms_class` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `office_id` varchar(64) NOT NULL COMMENT '机构id',
  `class_name` varchar(256) DEFAULT NULL COMMENT '班级名称',
  `grade` int(10) DEFAULT NULL COMMENT '年级',
  `class_code` varchar(256) DEFAULT NULL COMMENT '班号',
  `school_id` varchar(64) DEFAULT NULL COMMENT '学校id',
  `update_at` datetime(3) NOT NULL COMMENT '更新时间',
  `sync_time` bigint(20) NOT NULL COMMENT '同步时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `udx_school_id_office_id` (`school_id`,`office_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of pms_class
-- ----------------------------
INSERT INTO `pms_class` VALUES ('2f116bfaf03b4f1dad721c63b27b1dac', '614e00ef983d49bcad7c33cf88808daa', '2018级（2）班', '2018', '2', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:18.759', '1585790658759');
INSERT INTO `pms_class` VALUES ('8b78bb98d7c0405d8b4badee42194deb', '0ac7a10bebda4dd0ae48a72a75b1db0c', '2018级（1）班', '2018', '1', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:18.759', '1585790658759');
INSERT INTO `pms_class` VALUES ('e3a51f0b5dd0466ea8be705c211366fc', '02ddc646aa5f4b5aa31ec3fc2a861b3a', '2017级（1）班', '2017', '1', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:18.759', '1585790658759');

-- ----------------------------
-- Table structure for pms_staff
-- ----------------------------
DROP TABLE IF EXISTS `pms_staff`;
CREATE TABLE `pms_staff` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `user_id` varchar(64) NOT NULL COMMENT '用户id',
  `name` varchar(16) NOT NULL COMMENT '教职工姓名',
  `staff_no` varchar(64) DEFAULT NULL COMMENT '工号',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机号',
  `school_id` varchar(64) NOT NULL COMMENT '学校id',
  `update_at` datetime(3) NOT NULL COMMENT '更新时间',
  `sync_time` bigint(20) NOT NULL COMMENT '同步时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `udx_school_id_user_id` (`school_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='教职工信息';

-- ----------------------------
-- Records of pms_staff
-- ----------------------------
INSERT INTO `pms_staff` VALUES ('3ce697e220634188afd03f069ef5b632', '48be7891b50d477a89e00322cd55ca18', '胡教师', null, '18110621033', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.000', '1585790661113');
INSERT INTO `pms_staff` VALUES ('4cd99a7c08ee48fdbc26bc118ad909e9', '066471822d2444a798c8d64c3b6cb1ba', '测试教师', null, '17678627809', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.113', '1585790661113');
INSERT INTO `pms_staff` VALUES ('6afcfd0f58e8402a8736342df969373f', '33ee2aa799564e74be3dac2eb17962bd', '二级管理员', null, '', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.113', '1585790661113');
INSERT INTO `pms_staff` VALUES ('75831a1b747b402aa1dbc4d093d0392c', '334c965754ec49ea84074f82a0e32652', '李四', null, '', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.113', '1585790661113');
INSERT INTO `pms_staff` VALUES ('a738f5da8f4f44c094dd5fafaef08edd', '09dab1c3fe6c45ad849f64d66dd392ab', '3333', null, '', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.113', '1585790661113');
INSERT INTO `pms_staff` VALUES ('ab65505dd1c041088faba1edada50b58', 'fa5cacd47db6418887ecfa07a594486c', '教师3', null, '', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.113', '1585790661113');

-- ----------------------------
-- Table structure for pms_student
-- ----------------------------
DROP TABLE IF EXISTS `pms_student`;
CREATE TABLE `pms_student` (
  `id` varchar(64) NOT NULL COMMENT '主键',
  `user_id` varchar(64) NOT NULL COMMENT '用户id',
  `name` varchar(16) NOT NULL COMMENT '学生姓名',
  `class_id` varchar(64) DEFAULT NULL COMMENT '学生班级id',
  `class_sort` int(10) DEFAULT NULL COMMENT '学生班级排序',
  `class_name` varchar(128) DEFAULT NULL COMMENT '班级名称',
  `grade` int(4) DEFAULT NULL COMMENT '年级',
  `class_code` varchar(16) DEFAULT NULL COMMENT '班号',
  `school_id` varchar(64) NOT NULL COMMENT '学校id',
  `update_at` datetime(3) NOT NULL COMMENT '更新时间',
  `sync_time` bigint(20) NOT NULL COMMENT '同步时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `udx_school_id_user_id` (`school_id`,`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生信息';

-- ----------------------------
-- Records of pms_student
-- ----------------------------
INSERT INTO `pms_student` VALUES ('3c424102d071465ab8729ec666b4c7c8', '58f85f8039ad4666823b61d9458ba2ac', '123123ss', '02ddc646aa5f4b5aa31ec3fc2a861b3a', null, '2017级（1）班', '2017', '1', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.196', '1585790661196');
INSERT INTO `pms_student` VALUES ('69cde21445be457fa625f5fc1b3323a3', '48be7891b50d477a89e00322cd55ca18', '学生张三', null, null, null, null, null, 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.196', '1585790661196');
INSERT INTO `pms_student` VALUES ('9cd7cd46f69243d2beb76ee76d648048', '334c965754ec49ea84074f82a0e32652', '李四', '614e00ef983d49bcad7c33cf88808daa', null, '2018级（2）班', '2018', '2', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.196', '1585790661196');
INSERT INTO `pms_student` VALUES ('aa5a46b9950b4d5daab2c617556a7da1', '9f4e419421be40f7a5c579ade2110ee1', '吴剑', '381e2dfefad24ef9bf6d1b0d401bb116', null, '2019级（1）班', '2019', '1', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 10:01:32.206', '1585792892206');
INSERT INTO `pms_student` VALUES ('cc378f6d8b114a8985a36b5dbb617b51', '866089a87c8643eebfc1d3d52045c431', 'sss', '02ddc646aa5f4b5aa31ec3fc2a861b3a', null, '2017级（1）班', '2017', '1', 'd3746e6564374233bffd66e3612c9924', '2020-04-02 09:24:21.196', '1585790661196');
