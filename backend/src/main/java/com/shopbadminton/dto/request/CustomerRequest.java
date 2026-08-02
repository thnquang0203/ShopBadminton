package com.shopbadminton.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {

    @NotBlank(message = "Họ tên không được trống!")
    private String hoTen;

    private String soDienThoai;
    private String email;
    private String diaChi;
}