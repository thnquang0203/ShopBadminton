package com.shopbadminton.service;

import com.shopbadminton.dto.request.CourtBookingRequest;
import com.shopbadminton.dto.response.CourtBookingResponse;
import java.util.List;

public interface CourtBookingService {
    CourtBookingResponse datSan(CourtBookingRequest request, String tenDangNhapKhachHang);
    List<CourtBookingResponse> layTheoKhachHang(String tenDangNhapKhachHang);
    CourtBookingResponse layChiTiet(Long id);
}