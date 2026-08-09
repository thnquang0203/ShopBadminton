package com.shopbadminton.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Suppliers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Integer maNhaCungCap;

    @Column(name = "supplier_name", nullable = false, length = 150)
    private String tenNhaCungCap;

    @Column(name = "phone", length = 20)
    private String soDienThoai;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "address", length = 255)
    private String diaChi;

    @Column(name = "is_active")
    @Builder.Default
    private Boolean dangHoatDong = true;
}