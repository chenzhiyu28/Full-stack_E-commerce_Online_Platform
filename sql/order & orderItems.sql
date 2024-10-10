CREATE TABLE t_order (
	oid INT AUTO_INCREMENT,
	uid INT NOT NULL,
	recv_name VARCHAR(20) NOT NULL,
	recv_phone VARCHAR(20),
	recv_province VARCHAR(15),
	recv_city VARCHAR(15),
	recv_area VARCHAR(15),
	recv_address VARCHAR(50),
	total_price BIGINT,
	status INT COMMENT '状态：0-未支付，1-已支付，2-已取消，3-已关闭，4-已完成',
	order_time DATETIME,
	pay_time DATETIME,
	created_user VARCHAR(20),
	created_time DATETIME,
	modified_user VARCHAR(20),
	modified_time DATETIME,
	constraint pk_oid_order PRIMARY KEY (oid),
    constraint fk_uid_order FOREIGN KEY (uid) REFERENCES t_user(uid) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE t_order_item (
	id INT AUTO_INCREMENT,
	oid INT NOT NULL,
	pid INT NOT NULL,
	title VARCHAR(100) NOT NULL,
	image VARCHAR(500),
	price BIGINT,
	num INT,
	created_user VARCHAR(20),
	created_time DATETIME,
	modified_user VARCHAR(20),
	modified_time DATETIME,
	constraint pk_id_order_item PRIMARY KEY (id),
    constraint fk_oid_order_item FOREIGN KEY (oid) REFERENCES t_order(oid) ON DELETE CASCADE,
    constraint fk_pid_order_item FOREIGN KEY (pid) REFERENCES t_product(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;