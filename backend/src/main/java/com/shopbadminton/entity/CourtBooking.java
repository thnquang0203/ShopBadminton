package com.shopbadminton.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "CourtBookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourtBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long maDatSan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "court_id", nullable = false)
    private BadmintonCourt san;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer khachHang;

    @Column(name = "booking_date", nullable = false)
    private LocalDate ngayDat;

    @Column(name = "start_time", nullable = false)
    private LocalTime gioBatDau;

    @Column(name = "end_time", nullable = false)
    private LocalTime gioKetThuc;

    @Column(name = "status", length = 20)
    @Builder.Default
    private String trangThai = "PENDING";

    @Column(name = "created_at", updatable = false)
    private LocalDateTime ngayTao;

    @PrePersist
    protected void truocKhiLuu() {
        this.ngayTao = LocalDateTime.now();
    }
}