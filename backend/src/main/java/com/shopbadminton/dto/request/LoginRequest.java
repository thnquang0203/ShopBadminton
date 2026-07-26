package com.shopbadminton.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Ten dang nhap khong duoc trong")
    private String tenDangNhap;

    @NotBlank(message = "Mat khau khong duoc trong")
    private String matKhau;
}