package com.shopbadminton.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class EmployeeRequest {

    @NotBlank(message = "Ho ten khong duoc trong")
    private String hoTen;

    private String chucVu;
    private BigDecimal luong;
    private LocalDate ngayVaoLam;
    private Long maNguoiDung; // user_id liên kết
}