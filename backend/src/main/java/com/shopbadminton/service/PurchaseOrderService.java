package com.shopbadminton.service;

import com.shopbadminton.dto.request.PurchaseOrderRequest;
import com.shopbadminton.dto.response.PurchaseOrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PurchaseOrderService {
    Page<PurchaseOrderResponse> layDanhSach(Pageable pageable);
    PurchaseOrderResponse layChiTiet(Long id);
    PurchaseOrderResponse taoPhieuNhap(PurchaseOrderRequest request, String tenDangNhapNguoiTao);
}