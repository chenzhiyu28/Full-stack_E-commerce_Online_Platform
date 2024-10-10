Drop Table if exists t_address ;
CREATE TABLE t_address (
	aid INT AUTO_INCREMENT COMMENT 'address_id',
	uid INT COMMENT 'user_id' NOT NULL,
	name VARCHAR(20) COMMENT 'receiver_name',
	province_name VARCHAR(15) COMMENT 'province_name',
	province_code CHAR(6) COMMENT 'province_code',
	city_name VARCHAR(15) COMMENT 'city_name',
	city_code CHAR(6) COMMENT 'city_code',
	area_name VARCHAR(15) COMMENT 'area_name',
	area_code CHAR(6) COMMENT 'area_code',
	zip CHAR(6) COMMENT 'zip',
	address VARCHAR(50) COMMENT 'address',
	phone VARCHAR(20) COMMENT 'phone',
	tel VARCHAR(20) COMMENT 'tel',
	tag VARCHAR(6) COMMENT 'tag',
	is_default INT COMMENT 'is_default: 0-no 1-yes',
	created_user VARCHAR(20) COMMENT 'created_user',
	created_time DATETIME COMMENT 'created_time',
	modified_user VARCHAR(20) COMMENT 'modified_user',
	modified_time DATETIME COMMENT 'modified_time',
	CONSTRAINT pk_aid PRIMARY KEY (aid),
	CONSTRAINT fk_uid FOREIGN KEY (uid) REFERENCES t_user(uid) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
