package com.shopbadminton.repository;

import com.shopbadminton.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN FETCH u.vaiTro WHERE u.tenDangNhap = :tenDangNhap")
    Optional<User> findByTenDangNhap(String tenDangNhap);
}