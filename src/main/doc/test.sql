/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50538
Source Host           : localhost:3306
Source Database       : aphrodite

Target Server Type    : MYSQL
Target Server Version : 50538
File Encoding         : 65001

Date: 2015-11-02 15:00:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `ID` varchar(36) NOT NULL,
  `NAME` varchar(255) DEFAULT NULL,
  `URL` varchar(255) DEFAULT NULL,
  `PARENT_ID` varchar(36) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO SYS_MENU(ID,NAME,URL,PARENT_ID) VALUES('1','系统管理','sys',null);
INSERT INTO SYS_MENU(ID,NAME,URL,PARENT_ID) VALUES('2','用户管理','sys/menu','1');
INSERT INTO SYS_MENU(ID,NAME,URL,PARENT_ID) VALUES('3','角色管理','sys/role','1');
INSERT INTO SYS_MENU(ID,NAME,URL,PARENT_ID) VALUES('4','权限管理','sys/access','1')