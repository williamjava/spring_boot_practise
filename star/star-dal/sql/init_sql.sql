DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `loginname` varchar(20) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(40) NOT NULL DEFAULT '' COMMENT '密码',
  `email` varchar(40) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(15) DEFAULT NULL COMMENT '联系电话',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '0 否 1 是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户表';

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '角色名称',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '0 否 1 是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='角色表';

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(11) NOT NULL COMMENT '用户ID',
  `role_id` bigint(11) NOT NULL COMMENT '角色ID',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='用户角色关联表';

DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(20) NOT NULL DEFAULT '' COMMENT '菜单名称',
  `url` varchar(20) DEFAULT '' COMMENT '菜单请求路径',
  `parent_id` bigint(11) COMMENT '父菜单ID',
  `sort` bigint(11) COMMENT '菜单排序编号',
  `icon_class` varchar(20) DEFAULT '' COMMENT '菜单图标',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '0 否 1 是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='菜单表';

DROP TABLE IF EXISTS `menu_role`;
CREATE TABLE `menu_role` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `menu_id` bigint(11) NOT NULL COMMENT '菜单ID',
  `role_id` bigint(11) NOT NULL COMMENT '角色ID',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='菜单角色关联表';

DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` bigint(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT '权限资源名称',
  `res_value` varchar(150) NOT NULL DEFAULT '' COMMENT '请求接口路径',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '0 否 1 是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8  COMMENT='权限资源表';

DROP TABLE IF EXISTS `permission_role`;
CREATE TABLE `permission_role` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `permission_id` bigint(11) NOT NULL COMMENT '权限资源ID',
  `role_id` bigint(11) NOT NULL COMMENT '角色ID',
  `created_at` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_at` datetime DEFAULT NULL COMMENT '修改时间',
  `deleted` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='权限资源角色关联表';