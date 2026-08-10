package com.shopbadminton.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@AllArgsConstructor
public class ProductResponse {
    private Long maSanPham;
    private String tenSanPham;
    private String moTa;
    private BigDecimal gia;
    private String tenDanhMuc;
    private String tenThuongHieu;
    private Boolean dangHoatDong;
}