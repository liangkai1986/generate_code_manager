/*
Navicat MySQL Data Transfer

Source Server         : 本地数据库
Source Server Version : 50556
Source Host           : 127.0.0.1:3306
Source Database       : jfinal_demo

Target Server Type    : MYSQL
Target Server Version : 50556
File Encoding         : 65001

Date: 2018-06-19 20:28:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for jdbc_config
-- ----------------------------
DROP TABLE IF EXISTS `jdbc_config`;
CREATE TABLE `jdbc_config` (
  `jdbc_config_id` int(11) NOT NULL AUTO_INCREMENT,
  `jdbcUrl` varchar(255) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`jdbc_config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='jdbc_config';

-- ----------------------------
-- Records of jdbc_config
-- ----------------------------
INSERT INTO `jdbc_config` VALUES ('1', 'jdbc:mysql://localhost/jfinal_demo?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull', 'root', 'root');

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
  `project_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`project_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='项目';

-- ----------------------------
-- Records of project
-- ----------------------------

-- ----------------------------
-- Table structure for template
-- ----------------------------
DROP TABLE IF EXISTS `template`;
CREATE TABLE `template` (
  `template_id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text,
  PRIMARY KEY (`template_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of template
-- ----------------------------
INSERT INTO `template` VALUES ('1', '欢迎：${user}${user}');
