package com.shopbadminton.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "BillDetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bill_detail_id")
    private Long maChiTietHoaDon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id", nullable = false)
    private Bill hoaDon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product sanPham; // nullable - dong san pham

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private CourtBooking datSan; // nullable - dong dat san

    @Column(name = "quantity")
    @Builder.Default
    private Integer soLuong = 1;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal donGia;

    @Column(name = "subtotal", nullable = false)
    private BigDecimal thanhTien;
}