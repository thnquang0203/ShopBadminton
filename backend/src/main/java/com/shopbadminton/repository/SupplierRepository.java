package com.shopbadminton.repository;

import com.shopbadminton.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
    Page<Supplier> findByDangHoatDongTrue(Pageable pageable);
}