package com.shopbadminton.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long maSanPham;

    @Column(name = "product_name", nullable = false, length = 150)
    private String tenSanPham;

    @Column(name = "description", columnDefinition = "NVARCHAR(MAX)")
    private String moTa;

    @Column(name = "price", nullable = false)
    private BigDecimal gia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category danhMuc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand thuongHieu;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean dangHoatDong = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime ngayTao;

    @PrePersist
    protected void truocKhiLuu() {
        this.ngayTao = LocalDateTime.now();
    }
}