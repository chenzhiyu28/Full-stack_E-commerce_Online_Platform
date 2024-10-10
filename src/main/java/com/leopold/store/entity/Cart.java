package com.leopold.store.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "t_cart", schema = "store")
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cid", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "uid", referencedColumnName = "uid", nullable = false, foreignKey = @ForeignKey(name = "fk_uid_cart"))
    private User user;

    // (Many-to-One) 关系中，"many" 是 Cart，"one" 是 User。
    // optional 表示JPA中, 数据关联是强制的 (对应foreign key的约束)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "pid", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(name = "fk_pid_cart"))
    private Product product;

    @Column(name = "price")
    private Long price;

    @Column(name = "num")
    private Integer num;
}