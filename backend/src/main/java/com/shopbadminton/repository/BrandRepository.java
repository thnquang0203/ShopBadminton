package com.shopbadminton.repository;

import com.shopbadminton.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Integer> {
    boolean existsByTenThuongHieu(String tenThuongHieu);
}