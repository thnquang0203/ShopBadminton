package com.shopbadminton.service;

import com.shopbadminton.dto.response.ProductImageResponse;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ProductImageService {
    ProductImageResponse upload(Long maSanPham, MultipartFile file, boolean laAnhDaiDien);
    List<ProductImageResponse> layTheoSanPham(Long maSanPham);
    void xoa(Long maAnh);
}