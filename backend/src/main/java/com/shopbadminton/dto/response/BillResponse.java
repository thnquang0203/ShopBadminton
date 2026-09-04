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
public class BillResponse {
    private Long maHoaDon;
    private String tenKhachHang;
    private String tenNhanVien;
    private BigDecimal tongTien;
    private String trangThai;
    private LocalDateTime ngayTao;
    private List<ChiTietItem> chiTiet;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class ChiTietItem {
        private String loai; // "SAN_PHAM" hoac "DAT_SAN"
        private String tenMuc;
        private Integer soLuong;
        private BigDecimal donGia;
        private BigDecimal thanhTien;
    }
}