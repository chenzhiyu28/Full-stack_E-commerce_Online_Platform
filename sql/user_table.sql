CREATE TABLE t_user (
	uid INT AUTO_INCREMENT COMMENT 'userID',
	username VARCHAR(20) NOT NULL UNIQUE COMMENT 'userName',
	password CHAR(32) NOT NULL COMMENT 'pwd',
	salt CHAR(36) COMMENT 'salt',
	phone VARCHAR(20) COMMENT 'tel',
	email VARCHAR(30) COMMENT 'email',
	gender INT COMMENT 'gender:0-female，1-male',
	avatar VARCHAR(50) COMMENT 'avatar',
	is_delete INT COMMENT '0-notDeleted，1-deleted',
	created_user VARCHAR(20) COMMENT 'logCreater',
	created_time DATETIME COMMENT 'logCreateTime',
	modified_user VARCHAR(20) COMMENT 'logLastModify',
	modified_time DATETIME COMMENT 'LogModifiedTime',
	PRIMARY KEY (uid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;