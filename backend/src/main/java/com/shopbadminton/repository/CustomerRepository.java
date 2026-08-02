package com.shopbadminton.repository;

import com.shopbadminton.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Page<Customer> findByDangHoatDongTrueAndHoTenContainingIgnoreCaseOrDangHoatDongTrueAndSoDienThoaiContaining(
            String hoTen, String soDienThoai, Pageable pageable);

    Page<Customer> findByDangHoatDongTrue(Pageable pageable);
}