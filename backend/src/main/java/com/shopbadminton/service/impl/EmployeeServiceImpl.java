package com.shopbadminton.service.impl;

import com.shopbadminton.dto.request.EmployeeRequest;
import com.shopbadminton.dto.response.EmployeeResponse;
import com.shopbadminton.entity.Employee;
import com.shopbadminton.entity.User;
import com.shopbadminton.exception.BadRequestException;
import com.shopbadminton.exception.ResourceNotFoundException;
import com.shopbadminton.mapper.EmployeeMapper;
import com.shopbadminton.repository.EmployeeRepository;
import com.shopbadminton.repository.UserRepository;
import com.shopbadminton.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;
    private final EmployeeMapper employeeMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, UserRepository userRepository,
                                EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public Page<EmployeeResponse> layDanhSach(Pageable pageable) {
        return employeeRepository.findByDangHoatDongTrue(pageable)
                .map(employeeMapper::toResponse);
    }

    @Override
    public EmployeeResponse layChiTiet(Long id) {
        Employee nhanVien = timTheoId(id);
        return employeeMapper.toResponse(nhanVien);
    }

    @Override
    public EmployeeResponse taoMoi(EmployeeRequest request) {
    	User nguoiDung = userRepository.findById(request.getMaNguoiDung())
    	        .orElseThrow(() -> new ResourceNotFoundException("Khong tim thay User tuong ung"));

        Employee nhanVien = Employee.builder()
                .nguoiDung(nguoiDung)
                .hoTen(request.getHoTen())
                .chucVu(request.getChucVu())
                .luong(request.getLuong())
                .ngayVaoLam(request.getNgayVaoLam())
                .build();

        employeeRepository.save(nhanVien);
        return employeeMapper.toResponse(nhanVien);
    }

    @Override
    public EmployeeResponse capNhat(Long id, EmployeeRequest request) {
        Employee nhanVien = timTheoId(id);
        nhanVien.setHoTen(request.getHoTen());
        nhanVien.setChucVu(request.getChucVu());
        nhanVien.setLuong(request.getLuong());
        nhanVien.setNgayVaoLam(request.getNgayVaoLam());

        employeeRepository.save(nhanVien);
        return employeeMapper.toResponse(nhanVien);
    }

    @Override
    public void xoaMem(Long id) {
        Employee nhanVien = timTheoId(id);
        nhanVien.setDangHoatDong(false);
        employeeRepository.save(nhanVien);
    }

    private Employee timTheoId(Long id) {
        return employeeRepository.findById(id)
                .filter(Employee::getDangHoatDong)
                .orElseThrow(() -> new ResourceNotFoundException("Khong tim thay nhan vien"));
    }
}