package com.shopbadminton.controller;

import com.shopbadminton.dto.response.InventoryResponse;
import com.shopbadminton.service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee/inventory")
@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<Page<InventoryResponse>> layDanhSach(Pageable pageable) {
        return ResponseEntity.ok(inventoryService.layDanhSach(pageable));
    }

    @GetMapping("/product/{maSanPham}")
    public ResponseEntity<InventoryResponse> layTheoSanPham(@PathVariable Long maSanPham) {
        return ResponseEntity.ok(inventoryService.layTheoSanPham(maSanPham));
    }

    @GetMapping("/canh-bao")
    public ResponseEntity<List<InventoryResponse>> layTonKhoThap() {
        return ResponseEntity.ok(inventoryService.layTonKhoThap());
    }
}