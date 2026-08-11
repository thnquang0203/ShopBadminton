package com.shopbadminton.repository;

import com.shopbadminton.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query("SELECT p FROM Product p JOIN FETCH p.danhMuc JOIN FETCH p.thuongHieu WHERE p.dangHoatDong = true")
    Page<Product> findAllActiveWithDetails(Pageable pageable);
    @Query("SELECT p FROM Product p JOIN FETCH p.danhMuc JOIN FETCH p.thuongHieu WHERE p.maSanPham = :id AND p.dangHoatDong = true")
    Optional<Product> findActiveByIdWithDetails(Long id);
}