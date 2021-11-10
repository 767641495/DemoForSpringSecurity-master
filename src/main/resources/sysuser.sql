/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : localhost:3306
 Source Schema         : SpringSecurity

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 07/11/2021 09:59:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sysuser
-- ----------------------------
DROP TABLE IF EXISTS `sysuser`;
CREATE TABLE `sysuser` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `status` int DEFAULT NULL,
  `login_ip` varchar(255) DEFAULT NULL,
  `login_Date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `phone` (`phone`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sysuser
-- ----------------------------
BEGIN;
INSERT INTO `sysuser` VALUES (1, 'root', '$2a$10$e0YyGT6R9KkujS8PQrt.g.Z7MZGumDKq6xaje3AzSw/2maRvPCOgG', '17366636923', 0, NULL, NULL);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
