package com.leopold.store.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "t_address", schema = "store")
public class Address extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aid", nullable = false)
    private Integer id;

    // (Many-to-One) 关系中，"many" 是 Address，"one" 是 User。
    // optional 表示JPA中, 数据关联是强制的 (对应foreign key的约束)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "uid", referencedColumnName = "uid", nullable = false,
                foreignKey = @ForeignKey(name = "fk_uid"))
    private User user;  // JPA 会自动 map user --> uid

    @Column(name = "name", length = 20)
    private String name;

    @Column(name = "province_name", length = 15)
    private String provinceName;

    @Column(name = "province_code", length = 6)
    private String provinceCode;

    @Column(name = "city_name", length = 15)
    private String cityName;

    @Column(name = "city_code", length = 6)
    private String cityCode;

    @Column(name = "area_name", length = 15)
    private String areaName;

    @Column(name = "area_code", length = 6)
    private String areaCode;

    @Column(name = "zip", length = 6)
    private String zip;

    @Column(name = "address", length = 50)
    private String address;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "tel", length = 20)
    private String tel;

    @Column(name = "tag", length = 6)
    private String tag;

    @Column(name = "is_default")
    private Integer isDefault;
}