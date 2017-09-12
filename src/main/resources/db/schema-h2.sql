
DROP TABLE IF EXISTS user;

CREATE TABLE `user` (
 `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
 `username` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名称',
 `password` char(64) NOT NULL DEFAULT '' COMMENT '密码',
 PRIMARY KEY (`id`),
 UNIQUE KEY `username_UNIQUE` (`username`)
) COMMENT='用户表';

