DROP table if exists t_cart;
CREATE TABLE t_cart (
	cid INT AUTO_INCREMENT,
	uid INT NOT NULL,
	pid INT NOT NULL,
	price BIGINT,
	num INT,
	created_user VARCHAR(20),
	created_time DATETIME,
	modified_user VARCHAR(20),
	modified_time DATETIME,
	constraint pk_cid_cart Primary KEY (cid),
    constraint fk_uid_cart foreign key (uid) references t_user(uid) ON DELETE CASCADE,
    constraint fk_pid_cart foreign key (pid) references t_product(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;