package com.shopbadminton.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "Employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long maNhanVien;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User nguoiDung;

    @Column(name = "full_name", nullable = false, length = 100)
    private String hoTen;

    @Column(name = "position", length = 50)
    private String chucVu;

    @Column(name = "salary")
    private java.math.BigDecimal luong;

    @Column(name = "hire_date")
    private LocalDate ngayVaoLam;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean dangHoatDong = true;
}