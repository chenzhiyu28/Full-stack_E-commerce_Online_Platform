package com.leopold.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "t_product", schema = "store")
public class Product extends BaseEntity implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "item_type", length = 100)
    private String itemType;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "sell_point", length = 150)
    private String sellPoint;

    @Column(name = "price")
    private Long price;

    @Column(name = "num")
    private Integer num;

    @Column(name = "image", length = 500)
    private String image;

    @ColumnDefault("1")
    @Column(name = "status")
    private Integer status;

    @Column(name = "priority")
    private Integer priority;

}