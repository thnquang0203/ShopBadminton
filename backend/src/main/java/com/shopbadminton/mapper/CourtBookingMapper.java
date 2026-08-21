package com.shopbadminton.mapper;

import com.shopbadminton.dto.response.CourtBookingResponse;
import com.shopbadminton.entity.CourtBooking;
import org.springframework.stereotype.Component;

@Component
public class CourtBookingMapper {
    public CourtBookingResponse toResponse(CourtBooking datSan) {
        return CourtBookingResponse.builder()
                .maDatSan(datSan.getMaDatSan())
                .maSan(datSan.getSan().getMaSan())
                .tenSan(datSan.getSan().getTenSan())
                .tenKhachHang(datSan.getKhachHang().getHoTen())
                .ngayDat(datSan.getNgayDat())
                .gioBatDau(datSan.getGioBatDau())
                .gioKetThuc(datSan.getGioKetThuc())
                .trangThai(datSan.getTrangThai())
                .ngayTao(datSan.getNgayTao())
                .build();
    }
}