package com.shopbadminton.mapper;

import com.shopbadminton.dto.response.InventoryResponse;
import com.shopbadminton.entity.Inventory;
import org.springframework.stereotype.Component;

@Component
public class InventoryMapper {
    public InventoryResponse toResponse(Inventory tonKho) {
        return InventoryResponse.builder()
                .maTonKho(tonKho.getMaTonKho())
                .maSanPham(tonKho.getSanPham().getMaSanPham())
                .tenSanPham(tonKho.getSanPham().getTenSanPham())
                .soLuong(tonKho.getSoLuong())
                .soLuongToiThieu(tonKho.getSoLuongToiThieu())
                .tonKhoThap(tonKho.getSoLuong() <= tonKho.getSoLuongToiThieu())
                .ngayCapNhat(tonKho.getNgayCapNhat())
                .build();
    }
}