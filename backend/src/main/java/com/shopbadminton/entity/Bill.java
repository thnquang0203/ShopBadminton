package com.shopbadminton.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Bills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_id")
    private Long maHoaDon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee nhanVien;

    @Column(name = "total_amount")
    @Builder.Default
    private BigDecimal tongTien = BigDecimal.ZERO;

    @Column(name = "status", length = 20)
    @Builder.Default
    private String trangThai = "UNPAID";

    @Column(name = "created_at", updatable = false)
    private LocalDateTime ngayTao;

    @PrePersist
    protected void truocKhiLuu() {
        this.ngayTao = LocalDateTime.now();
    }
}