-- todo 测试数据结构 待修改 user表还需新增 微信用户相关信息  ----------------------------
-- Table structure for `sys_auth`
-- ----------------------------
DROP TABLE IF EXISTS `sys_auth`;
CREATE TABLE `sys_auth` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) NOT NULL COMMENT '权限名称',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB COMMENT='权限表';

-- ----------------------------
-- Records of sys_auth
-- ----------------------------
INSERT INTO `sys_auth` VALUES ('1', '查看用户信息', 'sys:user:info');
INSERT INTO `sys_auth` VALUES ('2', '查看所有权限', 'sys:auth:info');
INSERT INTO `sys_auth` VALUES ('3', '查看所有角色', 'sys:role:info');

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB COMMENT='角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'ADMIN');
INSERT INTO `sys_role` VALUES ('2', 'USER');

-- ----------------------------
-- Table structure for `sys_role_auth`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_auth`;
CREATE TABLE `sys_role_auth` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `auth_id` bigint DEFAULT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB COMMENT='角色权限关系表';

-- ----------------------------
-- Records of sys_role_auth
-- ----------------------------
INSERT INTO `sys_role_auth` VALUES ('1', '1', '1');
INSERT INTO `sys_role_auth` VALUES ('2', '1', '2');
INSERT INTO `sys_role_auth` VALUES ('3', '1', '3');
INSERT INTO `sys_role_auth` VALUES ('4', '2', '1');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
                            `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
                            `username` VARCHAR(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
                            `password` VARCHAR(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
                            `status` CHAR(1) COLLATE utf8mb4_unicode_ci DEFAULT '0' COMMENT '状态（0-正常，1-删除，2-禁用）',
                            `wechat_open_id` VARCHAR(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信openid',
                            `wechat_union_id` VARCHAR(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信unionid',
                            `wechat_nickname` VARCHAR(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '微信nickname',
                            `create_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            PRIMARY KEY (`id`) USING BTREE,
                            UNIQUE KEY `u_username` (`username`) USING BTREE
) ENGINE=INNODB COMMENT='系统用户表';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '$2a$10$5T851lZ7bc2U87zjt/9S6OkwmLW62tLeGLB2aCmq3XRZHA7OI7Dqa', '0');
INSERT INTO `sys_user` VALUES ('2', 'user', '$2a$10$szHoqQ64g66PymVJkip98.Fap21Csy8w.RD8v5Dhq08BMEZ9KaSmS', '0');
INSERT INTO `sys_user` VALUES ('3', 'C3Stones', '$2a$10$Z6a7DSehk58ypqyWzfFAbOR0gaqpwVzY9aNXKqf4UhDCSJxsbDqDK', '2');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB COMMENT='用户角色关系表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1', '1', '1');
INSERT INTO `sys_user_role` VALUES ('2', '2', '2');
INSERT INTO `sys_user_role` VALUES ('3', '3', '2');
-- 新增商品表 考虑商品图片可传多张问题
-- 商品信息表
CREATE TABLE `commodity` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT,
                             `barcode` varchar(50) DEFAULT NULL COMMENT '条码号',
                             `name` varchar(50) DEFAULT NULL COMMENT '商品名称',
                             `have_barcode` tinyint(1) DEFAULT '0' COMMENT '商品类型 0:无条码,1:有条码',
                             `prime_price` decimal(16,2) DEFAULT NULL COMMENT '进货价格',
                             `sale_price` decimal(16,2) DEFAULT NULL COMMENT '零售价格',
                             `width` decimal(16,2) DEFAULT NULL COMMENT '商品宽度',
                             `height` decimal(16,2) DEFAULT NULL COMMENT '商品长度',
                             `depth` decimal(16,2) DEFAULT NULL COMMENT '商品深度',
                             `description` varchar(200) DEFAULT NULL COMMENT '商品描述',
                             `brand` varchar(50) DEFAULT NULL COMMENT '商标',
                             `brand_name` varchar(50) DEFAULT NULL COMMENT '商标名称',
                             `manufacturer_name` varchar(200) DEFAULT NULL COMMENT '制造商名称',
                             `manufacturer_address` varchar(200) DEFAULT NULL COMMENT '制造商地址',
                             `attachment_id` bigint(20) DEFAULT NULL COMMENT '附件id',
                             `status` char(2) DEFAULT '0' COMMENT '状态，0:正常，1：删除，2：禁用',
                             `create_person` varchar(50) DEFAULT NULL COMMENT '创建人',
                             `create_date` timestamp NULL DEFAULT NULL COMMENT '创建时间',
                             `update_person` varchar(50) DEFAULT NULL COMMENT '创建人',
                             `update_date` timestamp NULL DEFAULT NULL COMMENT '更新时间',
                             `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
                             `repertory` int(6) DEFAULT '0' COMMENT '商品库存',
                             `order_phone` varchar(50) DEFAULT NULL COMMENT '订货电话',
                             `commodity_type` bigint(20) DEFAULT NULL COMMENT '商品类型id',
                             `units` varchar(50) DEFAULT '个' COMMENT '商品结算单位',
                             PRIMARY KEY (`id`),
                             UNIQUE KEY `key_barcode` (`barcode`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8
-- 新增商品类型字段
ALTER TABLE commodity ADD COLUMN commodity_type BIGINT(20) DEFAULT NULL COMMENT '商品类型id';
-- 附件表
CREATE TABLE attachment (
                            id BIGINT(20) NOT NULL AUTO_INCREMENT,
                            commodity_id BIGINT(20) DEFAULT NULL COMMENT '商品id',
                            attachment_address VARCHAR(200) DEFAULT NULL COMMENT '附件地址',
                            attachment_name VARCHAR(200) DEFAULT NULL COMMENT '附件名称',
                            create_person VARCHAR(20) DEFAULT NULL COMMENT '创建人',
                            create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                            STATUS CHAR(2) DEFAULT '0' COMMENT '状态，0:正常，1：删除',
                            TYPE CHAR(2) DEFAULT '0' COMMENT '附件类型 0:图片',
                            PRIMARY KEY (id)
);

-- 新增商品类型表
CREATE TABLE commodity_type (
                                id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
                                `name` VARCHAR(200) DEFAULT NULL COMMENT '类型名称',
                                description VARCHAR(200) DEFAULT NULL COMMENT '详细描述',
                                `status` CHAR(2) DEFAULT '0' COMMENT '状态，0:正常，1：删除，2：禁用',
                                create_date TIMESTAMP NULL DEFAULT NULL COMMENT '创建时间',
                                PRIMARY KEY (`id`) USING BTREE
) ENGINE=INNODB COMMENT='商品类型表';
-- 测试数据
insert into `commodity` (`id`, `barcode`, `name`, `have_barcode`, `prime_price`, `sale_price`, `width`, `height`, `depth`, `description`, `brand`, `brand_name`, `manufacturer_name`, `manufacturer_address`, `attachment_id`, `status`, `create_person`, `create_date`, `update_person`, `update_date`, `remark`, `repertory`, `order_phone`) values('1','06921734956835','得力deliA4-55mm档案盒','1','12.00','20.00','24.30','32.10','5.80',NULL,'deli','得力','得力集团有限公司','浙江省宁海县得力工业园',NULL,'0','操作员','2021-06-04 16:20:50',NULL,NULL,NULL,'0',NULL);
insert into `commodity` (`id`, `barcode`, `name`, `have_barcode`, `prime_price`, `sale_price`, `width`, `height`, `depth`, `description`, `brand`, `brand_name`, `manufacturer_name`, `manufacturer_address`, `attachment_id`, `status`, `create_person`, `create_date`, `update_person`, `update_date`, `remark`, `repertory`, `order_phone`) values('2','06907992514970','伊利金典有机纯牛奶','1','7.50','8.50','5.20','5.20','13.70',NULL,'伊利','伊利','内蒙古伊利实业集团股份有限公司','内蒙古自治区呼和浩特市金山开发区金山大街1号',NULL,'0',NULL,NULL,NULL,NULL,NULL,'0',NULL);
insert into `commodity` (`id`, `barcode`, `name`, `have_barcode`, `prime_price`, `sale_price`, `width`, `height`, `depth`, `description`, `brand`, `brand_name`, `manufacturer_name`, `manufacturer_address`, `attachment_id`, `status`, `create_person`, `create_date`, `update_person`, `update_date`, `remark`, `repertory`, `order_phone`) values('3','06922266462191','清风牌面巾纸','1',NULL,NULL,NULL,NULL,NULL,NULL,'清风','清风','金红叶纸业集团有限公司','江苏省苏州市苏州工业园区金胜路1号',NULL,'0',NULL,NULL,NULL,NULL,NULL,'0',NULL);

insert into `attachment` (`id`, `commodity_id`, `attachment_address`, `attachment_name`, `create_person`, `create_time`, `status`, `type`) values('1','1','http://www.anccnet.com/userfile/uploada/gra/sj201020162902684827/06921734956835/06921734956835.1.jpg','商品图片','操作员','2021-06-04 16:21:52','0','0');
insert into `attachment` (`id`, `commodity_id`, `attachment_address`, `attachment_name`, `create_person`, `create_time`, `status`, `type`) values('2','1','http://www.anccnet.com/userfile/uploada/gra/sj201020162902684827/06921734956835/06921734956835.2.jpg','商品图片','操作员','2021-06-04 16:27:10','0','0');
insert into `attachment` (`id`, `commodity_id`, `attachment_address`, `attachment_name`, `create_person`, `create_time`, `status`, `type`) values('3','2','http://www.anccnet.com/userfile/uploada/gra/sj200102094358911664/06907992514970/06907992514970.1.jpg','商品图片','操作员','2021-06-04 16:27:11','0','0');
insert into `attachment` (`id`, `commodity_id`, `attachment_address`, `attachment_name`, `create_person`, `create_time`, `status`, `type`) values('4','2','http://www.anccnet.com/userfile/uploada/gra/sj200102094358911664/06907992514970/06907992514970.2.jpg','商品图片','操作员','2021-06-04 16:27:27','0','0');
insert into `attachment` (`id`, `commodity_id`, `attachment_address`, `attachment_name`, `create_person`, `create_time`, `status`, `type`) values('5','2','http://www.anccnet.com/userfile/uploada/gra/sj200102094358911664/06907992514970/06907992514970.9.jpg','商品图片','操作员','2021-06-04 16:27:50','0','0');


-- 测试数据备份


insert  into `attachment`(`id`,`commodity_id`,`attachment_address`,`attachment_name`,`create_person`,`create_time`,`status`,`type`) values (1,1,'http://quq3zw1oz.hd-bkt.clouddn.com/202106180001','商品图片1','操作员','2021-06-22 14:34:53','1','0'),(2,1,'https://www.513210.com/userfiles/images/goods/514/su162624391uqUaws.jpg','商品图片2','操作员','2021-06-22 14:58:34','1','0'),(3,2,'http://hbimg.b0.upaiyun.com/e1b1467beea0a9c7d6a56b32bac6d7e5dcd914f7c3e6-YTwUd6_fw658','商品图片3','操作员','2021-06-22 15:12:57','1','0'),(4,2,'http://hbimg.b0.upaiyun.com/e1b1467beea0a9c7d6a56b32bac6d7e5dcd914f7c3e6-YTwUd6_fw658','商品图片4','操作员','2021-06-22 15:12:57','1','0'),(5,2,'http://hbimg.b0.upaiyun.com/e1b1467beea0a9c7d6a56b32bac6d7e5dcd914f7c3e6-YTwUd6_fw658','商品图片5','操作员','2021-06-22 15:12:57','1','0'),(6,1,'http://quq3zw1oz.hd-bkt.clouddn.com/3R6o3RMkU5l7.jpg','/quq3zw1oz.hd-bkt.clouddn.com/3R6o3RMkU5l7.jpg','admin','2021-06-22 15:09:01','1','0'),(7,1,'http://quq3zw1oz.hd-bkt.clouddn.com/b38Eb7DaLXz1.jpg','/quq3zw1oz.hd-bkt.clouddn.com/b38Eb7DaLXz1.jpg','admin','2021-06-22 15:09:01','1','0'),(8,1,'http://quq3zw1oz.hd-bkt.clouddn.com/NVNESnYf1vtl.jpg','/quq3zw1oz.hd-bkt.clouddn.com/NVNESnYf1vtl.jpg','admin','2021-06-22 15:09:01','1','0'),(9,1,'http://quq3zw1oz.hd-bkt.clouddn.com/qLRQEi76wdGJ.jpg','/quq3zw1oz.hd-bkt.clouddn.com/qLRQEi76wdGJ.jpg','admin','2021-06-22 14:58:34','0','0'),(10,1,'http://quq3zw1oz.hd-bkt.clouddn.com/b4mIBXA8cuuM.jpg','/quq3zw1oz.hd-bkt.clouddn.com/b4mIBXA8cuuM.jpg','admin','2021-06-22 15:09:01','0','0'),(11,1,'http://quq3zw1oz.hd-bkt.clouddn.com/ioV5jtd7P1k7.jpg','/quq3zw1oz.hd-bkt.clouddn.com/ioV5jtd7P1k7.jpg','admin','2021-06-22 15:11:15','0','0'),(12,2,'http://quq3zw1oz.hd-bkt.clouddn.com/MQAFeGJJ5HQN.jpg','/quq3zw1oz.hd-bkt.clouddn.com/MQAFeGJJ5HQN.jpg','admin','2021-06-22 15:12:57','0','0'),(13,3,'http://quq3zw1oz.hd-bkt.clouddn.com/VN0q43txyuKg.jpg','/quq3zw1oz.hd-bkt.clouddn.com/VN0q43txyuKg.jpg','admin','2021-06-22 16:24:17','0','0'),(14,3,'http://quq3zw1oz.hd-bkt.clouddn.com/IA5mb0EvResS.jpg','/quq3zw1oz.hd-bkt.clouddn.com/IA5mb0EvResS.jpg','admin','2021-06-23 15:16:34','0','0'),(15,NULL,'http://quq3zw1oz.hd-bkt.clouddn.com/4c1LnATOjB4K.jpg','/quq3zw1oz.hd-bkt.clouddn.com/4c1LnATOjB4K.jpg','admin','2021-06-24 15:34:00','0','0'),(16,4,'http://quq3zw1oz.hd-bkt.clouddn.com/3CyxGoRXIdbt.jpg','/quq3zw1oz.hd-bkt.clouddn.com/3CyxGoRXIdbt.jpg','admin','2021-06-24 15:37:27','0','0'),(17,5,'http://quq3zw1oz.hd-bkt.clouddn.com/r1nL11XHcqGk.jpg','/quq3zw1oz.hd-bkt.clouddn.com/r1nL11XHcqGk.jpg','admin','2021-06-25 08:52:05','0','0'),(18,6,'http://quq3zw1oz.hd-bkt.clouddn.com/ioEnwEZNUPct.jpg','/quq3zw1oz.hd-bkt.clouddn.com/ioEnwEZNUPct.jpg','admin','2021-06-25 09:44:09','0','0');


insert  into `commodity`(`id`,`barcode`,`name`,`have_barcode`,`prime_price`,`sale_price`,`width`,`height`,`depth`,`description`,`brand`,`brand_name`,`manufacturer_name`,`manufacturer_address`,`attachment_id`,`status`,`create_person`,`create_date`,`update_person`,`update_date`,`remark`,`repertory`,`order_phone`,`commodity_type`) values (1,'6921734956835','公牛图标',1,'12.00','28.00','24.30','32.10','5.80',NULL,'deli','得力','得力集团有限公司','浙江省宁海县得力工业园',NULL,'0','操作员','2021-06-04 16:20:50','admin','2021-06-25 15:41:41',NULL,16,NULL,3),(2,'06907992514970','伊利金典有机纯牛奶',1,'7.50','8.50','5.20','5.20','13.70',NULL,'伊利','伊利','内蒙古伊利实业集团股份有限公司','内蒙古自治区呼和浩特市金山开发区金山大街1号',NULL,'0',NULL,'2021-06-22 15:15:34','admin','2021-06-25 15:52:33',NULL,55,NULL,4),(3,'06922266462191','文件夹',1,'0.80','1.50',NULL,NULL,NULL,NULL,'清风','清风','金红叶纸业集团有限公司','江苏省苏州市苏州工业园区金胜路1号',NULL,'0',NULL,'2021-06-23 15:14:36','admin','2021-06-25 16:07:41',NULL,16,NULL,6),(4,'585800058','电风扇',0,NULL,'68.80',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','admin','2021-06-24 15:37:25','admin','2021-06-24 15:44:19',NULL,19,NULL,7),(5,'9966885500','一次性医用外科口罩',0,NULL,'16.00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','admin','2021-06-25 08:52:05','admin','2021-06-25 16:12:17',NULL,500,NULL,7),(6,'6304444nba','球队队标设计',0,NULL,'5000.00',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'0','admin','2021-06-25 09:44:09','admin','2021-06-25 09:44:09',NULL,100,NULL,8);


insert  into `commodity_type`(`id`,`name`,`description`,`status`,`create_date`) values (1,'休闲食品','糖果、巧克力、蜜饯、膨化食品等','0','2021-06-24 09:49:30'),(2,'果蔬生鲜','水果、蔬菜、生鲜类等','0','2021-06-24 09:49:38'),(3,'香烟及相关商品','香烟、电子烟、火机等','0','2021-06-24 09:49:46'),(4,'非食品日用类商品','日用、美妆、纸品等','0','2021-06-24 09:49:56'),(5,'乳制品','常温奶、低温奶、冷冻冰糕等','0','2021-06-24 09:50:05'),(6,'饮品','咖啡、奶茶等','0','2021-06-24 09:50:13'),(7,'五金百货','电线、卷尺、灯泡、钢钉等','0','2021-06-24 09:50:23'),(8,'运动相关','羽毛球、乒乓球、篮球等运动器材','0','2021-06-24 09:50:40'),(9,'其他','其他类型商品','0','2021-06-24 09:50:42');



insert  into `sys_auth`(`id`,`name`,`permission`) values (1,'查看用户信息','sys:user:info'),(2,'查看所有权限','sys:auth:info'),(3,'查看所有角色','sys:role:info');



insert  into `sys_role`(`id`,`role_name`) values (1,'ADMIN'),(2,'USER');



insert  into `sys_role_auth`(`id`,`role_id`,`auth_id`) values (1,1,1),(2,1,2),(3,1,3),(4,2,1);



insert  into `sys_user`(`id`,`username`,`password`,`status`,`wechat_open_id`,`wechat_union_id`,`wechat_nickname`) values (1,'admin','$2a$10$5T851lZ7bc2U87zjt/9S6OkwmLW62tLeGLB2aCmq3XRZHA7OI7Dqa','0','od6Zi5ak2DxNaNbPzCYQZRiDmwUI',NULL,'光景不待人 须臾发成丝'),(2,'user','$2a$10$szHoqQ64g66PymVJkip98.Fap21Csy8w.RD8v5Dhq08BMEZ9KaSmS','0',NULL,NULL,NULL),(3,'C3Stones','$2a$10$Z6a7DSehk58ypqyWzfFAbOR0gaqpwVzY9aNXKqf4UhDCSJxsbDqDK','2',NULL,NULL,NULL);


insert  into `sys_user_role`(`id`,`user_id`,`role_id`) values (1,1,1),(2,2,2),(3,3,2);

-- commodity 商品表新增 单位字段
ALTER TABLE commodity ADD COLUMN units VARCHAR(50) DEFAULT '个' COMMENT '商品结算单位';

-- -- 清空 商品类型表 重新写入数据
<!-- TRUNCATE TABLE commodity_type -->
<!-- INSERT INTO commodity_type (NAME, description, create_date) VALUES
 ('新鲜果蔬','水果、蔬菜、葱姜蒜类',NOW()),
 ('乳制品','牛奶、酸奶、饮品类',NOW()),
 ('饼干面包','饼干、面包类',NOW()),
 ('休闲零食','薯片、瓜子、辣条类',NOW()),
 ('冰淇淋','雪糕、冰淇淋类',NOW()),
 ('速食卤味','卤菜、速食类',NOW()),
 ('肉蛋水产','肉、蛋、水产类',NOW()),
 ('粮油面点','油、面类',NOW()),
 ('调料','油盐酱醋、榨菜、酸菜类',NOW()),
 ('饮品','矿泉水、饮料',NOW()),
 ('啤酒白酒','酒类',NOW()),
 ('个护清洁','肥皂、洗发露、面霜类',NOW()),
 ('纸类','卫生纸、卫生巾类',NOW()),
 ('家居百货','锅碗瓢盆、筷子、纸杯',NOW()),
 ('五金百货','插座、电线、钉子、铅丝类',NOW()),
 ('其他','未归类的其他商品',NOW());
-->

-- 新增超级管理员角色
INSERT INTO sys_role(role_name) VALUE ('SUPPER');

-- -- 默认密码 123456: $2a$10$szHoqQ64g66PymVJkip98.Fap21Csy8w.RD8v5Dhq08BMEZ9KaSmS
-- 下面sql 可通过数据库新增用户并添加相应权限
-- -- 新增用户 sql
INSERT INTO sys_user(username,PASSWORD,create_date) VALUES('testUser','$2a$10$szHoqQ64g66PymVJkip98.Fap21Csy8w.RD8v5Dhq08BMEZ9KaSmS',NOW());
-- -- 添加用户权限 role_id: 1:ADMIN (管理员 可查看、新增、更新商品信息) 2:USER (可查看) 3:SUPER(超级管理员 拥有全部权限)
INSERT INTO sys_user_role(user_id,role_id) SELECT id,'2' FROM sys_user WHERE username='testUser';
INSERT INTO sys_user_role(user_id,role_id) SELECT id,'1' FROM sys_user WHERE username='testUser';
