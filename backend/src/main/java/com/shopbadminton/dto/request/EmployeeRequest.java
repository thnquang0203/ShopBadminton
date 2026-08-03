package com.shopbadminton.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PastOrPresent;
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

    @Positive(message = "Luong phai lon hon 0")
    private BigDecimal luong;

    @PastOrPresent(message = "Ngay vao lam khong duoc o tuong lai")
    private LocalDate ngayVaoLam;

    @NotNull(message = "Phai chi dinh User lien ket")
    private Long maNguoiDung;
}