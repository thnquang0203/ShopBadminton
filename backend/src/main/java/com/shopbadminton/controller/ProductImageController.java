package com.shopbadminton.controller;

import com.shopbadminton.dto.response.ProductImageResponse;
import com.shopbadminton.service.ProductImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/products/{maSanPham}/images")
public class ProductImageController {

    private final ProductImageService productImageService;

    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    @GetMapping
    public ResponseEntity<List<ProductImageResponse>> layDanhSach(@PathVariable Long maSanPham) {
        return ResponseEntity.ok(productImageService.layTheoSanPham(maSanPham));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ProductImageResponse> upload(
            @PathVariable Long maSanPham,
            @RequestParam("file") MultipartFile file,
            @RequestParam(defaultValue = "false") boolean laAnhDaiDien) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productImageService.upload(maSanPham, file, laAnhDaiDien));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{maAnh}")
    public ResponseEntity<Void> xoa(@PathVariable Long maSanPham, @PathVariable Long maAnh) {
        productImageService.xoa(maAnh);
        return ResponseEntity.noContent().build();
    }
}