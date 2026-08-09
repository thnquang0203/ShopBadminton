package com.shopbadminton.service;

import com.shopbadminton.dto.request.SupplierRequest;
import com.shopbadminton.dto.response.SupplierResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SupplierService {
    Page<SupplierResponse> layDanhSach(Pageable pageable);
    SupplierResponse layChiTiet(Integer id);
    SupplierResponse taoMoi(SupplierRequest request);
    SupplierResponse capNhat(Integer id, SupplierRequest request);
    void xoaMem(Integer id);
}