package com.shopbadminton.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Brands")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Integer maThuongHieu;

    @Column(name = "brand_name", nullable = false, unique = true, length = 100)
    private String tenThuongHieu;
}