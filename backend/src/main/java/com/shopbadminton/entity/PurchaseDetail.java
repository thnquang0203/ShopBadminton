package com.shopbadminton.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "PurchaseDetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_detail_id")
    private Long maChiTietNhap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrder phieuNhap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product sanPham;

    @Column(name = "quantity", nullable = false)
    private Integer soLuong;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal donGia;
}