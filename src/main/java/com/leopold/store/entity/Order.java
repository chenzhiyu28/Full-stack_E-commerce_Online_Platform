package com.leopold.store.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "t_order", schema = "store")
public class Order extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oid", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "uid", nullable = false)
    private User user;

    @Column(name = "recv_name", nullable = false, length = 20)
    private String recvName;

    @Column(name = "recv_phone", length = 20)
    private String recvPhone;

    @Column(name = "recv_province", length = 15)
    private String recvProvince;

    @Column(name = "recv_city", length = 15)
    private String recvCity;

    @Column(name = "recv_area", length = 15)
    private String recvArea;

    @Column(name = "recv_address", length = 50)
    private String recvAddress;

    @Column(name = "total_price")
    private Long totalPrice;

    @Column(name = "status")
    private Integer status;

    @Column(name = "order_time")
    private Date orderTime;

    @Column(name = "pay_time")
    private Date payTime;
}