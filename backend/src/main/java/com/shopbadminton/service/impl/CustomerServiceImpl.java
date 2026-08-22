package com.shopbadminton.service.impl;

import com.shopbadminton.dto.request.CustomerRequest;
import com.shopbadminton.dto.response.CustomerResponse;
import com.shopbadminton.entity.Customer;
import com.shopbadminton.entity.User;
import com.shopbadminton.exception.ResourceNotFoundException;
import com.shopbadminton.mapper.CustomerMapper;
import com.shopbadminton.repository.CustomerRepository;
import com.shopbadminton.repository.UserRepository;
import com.shopbadminton.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final UserRepository userRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper,
            UserRepository userRepository) {
    			this.customerRepository = customerRepository;
    			this.customerMapper = customerMapper;
    			this.userRepository = userRepository;
    }

    @Override
    public Page<CustomerResponse> layDanhSach(Pageable pageable) {
        return customerRepository.findByDangHoatDongTrue(pageable).map(customerMapper::toResponse);
    }

    @Override
    public Page<CustomerResponse> timKiem(String tuKhoa, Pageable pageable) {
        return customerRepository
                .findByDangHoatDongTrueAndHoTenContainingIgnoreCaseOrDangHoatDongTrueAndSoDienThoaiContaining(
                        tuKhoa, tuKhoa, pageable)
                .map(customerMapper::toResponse);
    }

    @Override
    public CustomerResponse layChiTiet(Long id) {
        return customerMapper.toResponse(timTheoId(id));
    }

    @Override
    public CustomerResponse taoMoi(CustomerRequest request) {
        Customer.CustomerBuilder builder = Customer.builder()
                .hoTen(request.getHoTen())
                .soDienThoai(request.getSoDienThoai())
                .email(request.getEmail())
                .diaChi(request.getDiaChi());

        if (request.getMaNguoiDung() != null) {
            User nguoiDung = userRepository.findById(request.getMaNguoiDung())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy user tương ứng"));
            builder.nguoiDung(nguoiDung);
        }
        Customer khachHang = builder.build();
        customerRepository.save(khachHang);
        return customerMapper.toResponse(khachHang);
    }
    @Override
    public CustomerResponse capNhat(Long id, CustomerRequest request) {
        Customer khachHang = timTheoId(id);
        khachHang.setHoTen(request.getHoTen());
        khachHang.setSoDienThoai(request.getSoDienThoai());
        khachHang.setEmail(request.getEmail());
        khachHang.setDiaChi(request.getDiaChi());

        customerRepository.save(khachHang);
        return customerMapper.toResponse(khachHang);
    }

    @Override
    public void xoaMem(Long id) {
        Customer khachHang = timTheoId(id);
        khachHang.setDangHoatDong(false);
        customerRepository.save(khachHang);
    }

    private Customer timTheoId(Long id) {
        return customerRepository.findById(id)
                .filter(Customer::getDangHoatDong)
                .orElseThrow(() -> new ResourceNotFoundException("Khong tim thay khach hang"));
    }
}