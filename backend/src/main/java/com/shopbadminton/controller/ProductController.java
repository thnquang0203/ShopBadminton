package com.shopbadminton.controller;

import com.shopbadminton.dto.request.ProductRequest;
import com.shopbadminton.dto.response.ProductResponse;
import com.shopbadminton.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> layDanhSach(Pageable pageable) {
        return ResponseEntity.ok(productService.layDanhSach(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> layChiTiet(@PathVariable Long id) {
        return ResponseEntity.ok(productService.layChiTiet(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ProductResponse> taoMoi(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.taoMoi(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> capNhat(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.capNhat(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoa(@PathVariable Long id) {
        productService.xoaMem(id);
        return ResponseEntity.noContent().build();
    }
}