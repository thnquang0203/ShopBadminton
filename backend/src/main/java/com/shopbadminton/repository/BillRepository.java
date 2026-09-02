package com.shopbadminton.repository;

import com.shopbadminton.entity.Bill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
    Page<Bill> findAllByOrderByNgayTaoDesc(Pageable pageable);
    Page<Bill> findByKhachHang_MaKhachHangOrderByNgayTaoDesc(Long maKhachHang, Pageable pageable);
}