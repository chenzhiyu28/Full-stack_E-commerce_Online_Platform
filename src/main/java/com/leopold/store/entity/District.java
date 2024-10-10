package com.leopold.store.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "t_dict_district", schema = "store")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "parent", length = 6)
    private String parent;

    @Column(name = "code", length = 6)
    private String code;

    @Column(name = "name", length = 16)
    private String name;

}