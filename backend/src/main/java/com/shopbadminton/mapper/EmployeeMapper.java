package com.shopbadminton.mapper;

import com.shopbadminton.dto.response.EmployeeResponse;
import com.shopbadminton.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeResponse toResponse(Employee nhanVien) {
        return EmployeeResponse.builder()
                .maNhanVien(nhanVien.getMaNhanVien())
                .hoTen(nhanVien.getHoTen())
                .chucVu(nhanVien.getChucVu())
                .luong(nhanVien.getLuong())
                .ngayVaoLam(nhanVien.getNgayVaoLam())
                .dangHoatDong(nhanVien.getDangHoatDong())
                .build();
    }
}