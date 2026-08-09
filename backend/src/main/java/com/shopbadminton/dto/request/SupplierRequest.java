package com.shopbadminton.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierRequest {

    @NotBlank(message = "Tên nhà cung cấp không được trống")
    private String tenNhaCungCap;

    private String soDienThoai;

    @Email(message = "Email không hợp lệ")
    private String email;

    private String diaChi;
}