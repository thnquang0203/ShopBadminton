package com.shopbadminton.controller;

import com.shopbadminton.dto.request.SupplierRequest;
import com.shopbadminton.dto.response.SupplierResponse;
import com.shopbadminton.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/suppliers")
@PreAuthorize("hasRole('ADMIN')")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<Page<SupplierResponse>> layDanhSach(Pageable pageable) {
        return ResponseEntity.ok(supplierService.layDanhSach(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponse> layChiTiet(@PathVariable Integer id) {
        return ResponseEntity.ok(supplierService.layChiTiet(id));
    }

    @PostMapping
    public ResponseEntity<SupplierResponse> taoMoi(@Valid @RequestBody SupplierRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(supplierService.taoMoi(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponse> capNhat(@PathVariable Integer id, @Valid @RequestBody SupplierRequest request) {
        return ResponseEntity.ok(supplierService.capNhat(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoa(@PathVariable Integer id) {
        supplierService.xoaMem(id);
        return ResponseEntity.noContent().build();
    }
}