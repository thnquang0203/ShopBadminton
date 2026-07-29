package com.shopbadminton.repository;

import com.shopbadminton.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Page<Employee> findByDangHoatDongTrue(Pageable pageable);
}