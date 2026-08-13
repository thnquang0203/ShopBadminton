package com.shopbadminton.repository;

import com.shopbadminton.entity.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    Page<PurchaseOrder> findAllByOrderByNgayNhapDesc(Pageable pageable);
}