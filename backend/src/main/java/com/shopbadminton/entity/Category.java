package com.shopbadminton.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer maDanhMuc;

    @Column(name = "category_name", nullable = false, unique = true, length = 100)
    private String tenDanhMuc;
}