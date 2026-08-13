package com.shopbadminton.repository;

import com.shopbadminton.entity.PurchaseDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PurchaseDetailRepository extends JpaRepository<PurchaseDetail, Long> {
    List<PurchaseDetail> findByPhieuNhap_MaPhieuNhap(Long maPhieuNhap);
}