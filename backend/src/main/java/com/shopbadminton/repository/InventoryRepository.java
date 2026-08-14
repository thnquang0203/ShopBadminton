package com.shopbadminton.repository;

import com.shopbadminton.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findBySanPham_MaSanPham(Long maSanPham);
}