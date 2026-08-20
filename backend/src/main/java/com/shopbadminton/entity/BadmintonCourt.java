package com.shopbadminton.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "BadmintonCourts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BadmintonCourt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "court_id")
    private Integer maSan;

    @Column(name = "court_name", nullable = false, length = 50)
    private String tenSan;

    @Column(name = "court_type", length = 50)
    private String loaiSan;

    @Column(name = "price_per_hour", nullable = false)
    private BigDecimal giaTheoGio;

    @Column(name = "status", length = 20)
    @Builder.Default
    private String trangThai = "AVAILABLE";
}