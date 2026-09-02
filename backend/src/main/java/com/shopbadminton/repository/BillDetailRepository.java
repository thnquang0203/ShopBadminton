package com.shopbadminton.repository;

import com.shopbadminton.entity.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {
    List<BillDetail> findByHoaDon_MaHoaDon(Long maHoaDon);
}