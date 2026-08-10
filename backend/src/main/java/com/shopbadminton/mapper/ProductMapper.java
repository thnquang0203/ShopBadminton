package com.shopbadminton.mapper;

import com.shopbadminton.dto.response.ProductResponse;
import com.shopbadminton.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductResponse toResponse(Product sanPham) {
        return ProductResponse.builder()
                .maSanPham(sanPham.getMaSanPham())
                .tenSanPham(sanPham.getTenSanPham())
                .moTa(sanPham.getMoTa())
                .gia(sanPham.getGia())
                .tenDanhMuc(sanPham.getDanhMuc().getTenDanhMuc())
                .tenThuongHieu(sanPham.getThuongHieu().getTenThuongHieu())
                .dangHoatDong(sanPham.getDangHoatDong())
                .build();
    }
}