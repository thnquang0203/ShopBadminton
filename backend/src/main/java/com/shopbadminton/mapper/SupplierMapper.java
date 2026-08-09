package com.shopbadminton.mapper;

import com.shopbadminton.dto.response.SupplierResponse;
import com.shopbadminton.entity.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {
    public SupplierResponse toResponse(Supplier nhaCungCap) {
        return SupplierResponse.builder()
                .maNhaCungCap(nhaCungCap.getMaNhaCungCap())
                .tenNhaCungCap(nhaCungCap.getTenNhaCungCap())
                .soDienThoai(nhaCungCap.getSoDienThoai())
                .email(nhaCungCap.getEmail())
                .diaChi(nhaCungCap.getDiaChi())
                .dangHoatDong(nhaCungCap.getDangHoatDong())
                .build();
    }
}