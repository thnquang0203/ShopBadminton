package com.shopbadminton.repository;

import com.shopbadminton.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findBySanPham_MaSanPham(Long maSanPham);
}