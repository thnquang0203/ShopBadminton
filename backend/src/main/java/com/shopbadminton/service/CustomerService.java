package com.shopbadminton.service;

import com.shopbadminton.dto.request.CustomerRequest;
import com.shopbadminton.dto.response.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    Page<CustomerResponse> layDanhSach(Pageable pageable);
    Page<CustomerResponse> timKiem(String tuKhoa, Pageable pageable);
    CustomerResponse layChiTiet(Long id);
    CustomerResponse taoMoi(CustomerRequest request);
    CustomerResponse capNhat(Long id, CustomerRequest request);
    void xoaMem(Long id);
}