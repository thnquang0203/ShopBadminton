package com.shopbadminton.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PurchaseOrderResponse {
    private Long maPhieuNhap;
    private String tenNhaCungCap;
    private String tenNhanVien;
    private LocalDateTime ngayNhap;
    private BigDecimal tongTien;
    private String trangThai;
    private List<ChiTietItem> chiTiet;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ChiTietItem {
        private Long maSanPham;
        private String tenSanPham;
        private Integer soLuong;
        private BigDecimal donGia;
    }
}