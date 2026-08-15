package com.shopbadminton.repository;

import com.shopbadminton.entity.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findBySanPham_MaSanPham(Long maSanPham);

    @Query("SELECT i FROM Inventory i JOIN FETCH i.sanPham")
    Page<Inventory> findAllWithProduct(Pageable pageable);

    @Query("SELECT i FROM Inventory i JOIN FETCH i.sanPham WHERE i.soLuong <= i.soLuongToiThieu")
    List<Inventory> findAllTonKhoThap();
}