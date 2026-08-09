package com.shopbadminton.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shopbadminton.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	@Query("SELECT p FROM Product p JOIN FETCH p.danhMuc JOIN FETCH p.thuongHieu WHERE p.dangHoatDong = true")
	Page<Product> findAllActiveWithDetails(Pageable pageable);
	@Query("SELECT p FROM Product p JOIN FETCH p.danhMuc JOIN FETCH p.thuongHieu WHERE p.dangHoatDong = true")
	java.util.Optional<Product> findAllActiveWithDetails(Long id);
}
