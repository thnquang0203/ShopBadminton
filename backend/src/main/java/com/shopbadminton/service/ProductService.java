package com.shopbadminton.service;

import com.shopbadminton.dto.request.ProductRequest;
import com.shopbadminton.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductResponse> layDanhSach(Pageable pageable);
    ProductResponse layChiTiet(Long id);
    ProductResponse taoMoi(ProductRequest request);
    ProductResponse capNhat(Long id, ProductRequest request);
    void xoaMem(Long id);
}