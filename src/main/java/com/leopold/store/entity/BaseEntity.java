package com.leopold.store.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass  //不是一个完整的实体类，但它的fields将被extended & mapped to 数据库。
public abstract class BaseEntity implements Serializable {

    @Column(name = "created_user", length = 20)
    private String createdUser;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "modified_user", length = 20)
    private String modifiedUser;

    @Column(name = "modified_time")
    private Date modifiedTime;
}
