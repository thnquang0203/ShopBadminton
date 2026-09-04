package com.shopbadminton.service;

import com.shopbadminton.dto.request.BillRequest;
import com.shopbadminton.dto.response.BillResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BillService {
    Page<BillResponse> layDanhSach(Pageable pageable);
    BillResponse layChiTiet(Long id);
    BillResponse taoHoaDonBanSanPham(BillRequest request, String tenDangNhapNguoiTao);
}