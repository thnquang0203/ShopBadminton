package com.shopbadminton.service;

import com.shopbadminton.dto.request.CourtBookingRequest;
import com.shopbadminton.dto.response.CourtBookingResponse;

import java.time.LocalDate;
import java.util.List;

public interface CourtBookingService {
    CourtBookingResponse datSan(CourtBookingRequest request, String tenDangNhapKhachHang);
    List<CourtBookingResponse> layTheoKhachHang(String tenDangNhapKhachHang);
    List<CourtBookingResponse> layLichTheoNgay(LocalDate ngayDat);
    List<CourtBookingResponse> layLichTheoSanVaNgay(Integer maSan, LocalDate ngayDat);
    List<CourtBookingResponse> layLichTheoTrangThai(LocalDate ngayDat, String trangThai);
    CourtBookingResponse xacNhanDatSan(Long id);
    CourtBookingResponse layChiTiet(Long id);
    void huyDatSan(Long id, String tenDangNhapKhachHang);
}