package com.shopbadminton.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Ten dang nhap khong duoc trong")
    private String tenDangNhap;

    @NotBlank(message = "Mat khau khong duoc trong")
    @Size(min = 6, message = "Mat khau it nhat 6 ky tu")
    private String matKhau;

    @Email(message = "Email khong hop le")
    private String email;

    private String soDienThoai;

    @NotBlank(message = "Ho ten khong duoc trong")
    private String hoTen;
}