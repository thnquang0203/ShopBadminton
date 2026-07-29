package com.shopbadminton.service;

import com.shopbadminton.dto.request.EmployeeRequest;
import com.shopbadminton.dto.response.EmployeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {
    Page<EmployeeResponse> layDanhSach(Pageable pageable);
    EmployeeResponse layChiTiet(Long id);
    EmployeeResponse taoMoi(EmployeeRequest request);
    EmployeeResponse capNhat(Long id, EmployeeRequest request);
    void xoaMem(Long id);
}