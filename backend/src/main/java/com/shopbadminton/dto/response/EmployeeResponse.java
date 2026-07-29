package com.shopbadminton.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
public class EmployeeResponse {
    private Long maNhanVien;
    private String hoTen;
    private String chucVu;
    private BigDecimal luong;
    private LocalDate ngayVaoLam;
    private Boolean dangHoatDong;
}