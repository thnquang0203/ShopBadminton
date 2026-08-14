package com.shopbadminton.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long maTonKho;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, unique = true)
    private Product sanPham;

    @Column(name = "quantity")
    @Builder.Default
    private Integer soLuong = 0;

    @Column(name = "min_quantity")
    @Builder.Default
    private Integer soLuongToiThieu = 5;

    @Column(name = "updated_at")
    private LocalDateTime ngayCapNhat;

    @PreUpdate
    @PrePersist
    protected void truocKhiLuu() {
        this.ngayCapNhat = LocalDateTime.now();
    }
}