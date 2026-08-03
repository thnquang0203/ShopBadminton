package com.shopbadminton.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {

    @NotBlank(message = "Ho ten khong duoc trong")
    private String hoTen;

    @Pattern(regexp = "^0[0-9]{9}$", message = "So dien thoai khong hop le (10 so, bat dau bang 0)")
    private String soDienThoai;

    @Email(message = "Email khong hop le")
    private String email;

    private String diaChi;
}