package com.shopbadminton.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
@AllArgsConstructor
public class CourtBookingResponse {
    private Long maDatSan;
    private Integer maSan;
    private String tenSan;
    private String tenKhachHang;
    private LocalDate ngayDat;
    private LocalTime gioBatDau;
    private LocalTime gioKetThuc;
    private String trangThai;
    private LocalDateTime ngayTao;
}