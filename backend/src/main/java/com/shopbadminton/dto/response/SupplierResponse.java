package com.shopbadminton.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SupplierResponse {
    private Integer maNhaCungCap;
    private String tenNhaCungCap;
    private String soDienThoai;
    private String email;
    private String diaChi;
    private Boolean dangHoatDong;
}