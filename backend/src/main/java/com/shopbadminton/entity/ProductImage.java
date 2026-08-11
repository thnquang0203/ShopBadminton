package com.shopbadminton.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ProductImages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long maAnh;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product sanPham;

    @Column(name = "image_url", nullable = false, length = 500)
    private String duongDanAnh;

    @Column(name = "is_thumbnail")
    @Builder.Default
    private Boolean laAnhDaiDien = false;
}