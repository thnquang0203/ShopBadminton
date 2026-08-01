package com.shopbadminton.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long maKhachHang;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private User nguoiDung; // nullable - khach vang lai co the chua co tai khoan

    @Column(name = "full_name", nullable = false, length = 100)
    private String hoTen;

    @Column(name = "phone", length = 20)
    private String soDienThoai;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "address", length = 255)
    private String diaChi;

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