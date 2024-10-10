package com.leopold.store.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)  //  equals 和 hashCode 方法包含superclass的属性
@Table(name = "t_user", schema = "store")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "password", nullable = false, length = 32)
    private String password;

    @Column(name = "salt", length = 36)
    private String salt;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 30)
    private String email;

    @Column(name = "gender")
    private Integer gender;

    @Column(name = "avatar", length = 50)
    private String avatar;

    @Column(name = "is_delete")
    private Integer isDelete;

}