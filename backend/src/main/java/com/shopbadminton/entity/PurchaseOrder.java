package com.shopbadminton.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PurchaseOrders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_order_id")
    private Long maPhieuNhap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier nhaCungCap;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee nhanVien;

    @Column(name = "order_date", updatable = false)
    private LocalDateTime ngayNhap;

    @Column(name = "total_amount")
    @Builder.Default
    private BigDecimal tongTien = BigDecimal.ZERO;

    @Column(name = "status", length = 20)
    @Builder.Default
    private String trangThai = "COMPLETED";

    @PrePersist
    protected void truocKhiLuu() {
        this.ngayNhap = LocalDateTime.now();
    }
}