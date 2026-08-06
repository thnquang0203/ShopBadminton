package com.shopbadminton.service;

import com.shopbadminton.dto.request.EmployeeRequest;
import com.shopbadminton.dto.response.EmployeeResponse;
import com.shopbadminton.entity.Employee;
import com.shopbadminton.exception.ResourceNotFoundException;
import com.shopbadminton.mapper.EmployeeMapper;
import com.shopbadminton.repository.EmployeeRepository;
import com.shopbadminton.repository.UserRepository;
import com.shopbadminton.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void layChiTiet_KhongTonTai_NemResourceNotFound() {
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.layChiTiet(999L));
    }

    @Test
    void layChiTiet_DaBiXoaMem_NemResourceNotFound() {
        Employee nhanVienDaXoa = Employee.builder()
                .maNhanVien(1L)
                .hoTen("Nguyen Van A")
                .dangHoatDong(false)
                .build();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(nhanVienDaXoa));

        assertThrows(ResourceNotFoundException.class, () -> employeeService.layChiTiet(1L));
    }

    @Test
    void layChiTiet_TonTai_TraVeResponse() {
        Employee nhanVien = Employee.builder()
                .maNhanVien(1L)
                .hoTen("Nguyen Van A")
                .dangHoatDong(true)
                .build();

        EmployeeResponse response = EmployeeResponse.builder()
                .maNhanVien(1L)
                .hoTen("Nguyen Van A")
                .dangHoatDong(true)
                .build();

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(nhanVien));
        when(employeeMapper.toResponse(nhanVien)).thenReturn(response);

        EmployeeResponse ketQua = employeeService.layChiTiet(1L);

        assertEquals("Nguyen Van A", ketQua.getHoTen());
    }
}