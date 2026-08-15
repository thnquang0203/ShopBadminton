package com.shopbadminton.service;

import com.shopbadminton.dto.response.InventoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InventoryService {
    Page<InventoryResponse> layDanhSach(Pageable pageable);
    InventoryResponse layTheoSanPham(Long maSanPham);
    List<InventoryResponse> layTonKhoThap();
}