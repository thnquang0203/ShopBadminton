package com.shopbadminton.repository;

import com.shopbadminton.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findByDangHoatDongTrue(Pageable pageable);
    Optional<Employee> findByNguoiDung_MaNguoiDung(Long maNguoiDung);
}