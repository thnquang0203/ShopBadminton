package com.shopbadminton.mapper;

import com.shopbadminton.dto.response.CustomerResponse;
import com.shopbadminton.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerResponse toResponse(Customer khachHang) {
        return CustomerResponse.builder()
                .maKhachHang(khachHang.getMaKhachHang())
                .hoTen(khachHang.getHoTen())
                .soDienThoai(khachHang.getSoDienThoai())
                .email(khachHang.getEmail())
                .diaChi(khachHang.getDiaChi())
                .dangHoatDong(khachHang.getDangHoatDong())
                .build();
    }
}