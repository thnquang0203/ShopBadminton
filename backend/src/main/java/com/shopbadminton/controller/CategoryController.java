package com.shopbadminton.controller;

import com.shopbadminton.dto.request.CategoryRequest;
import com.shopbadminton.dto.response.CategoryResponse;
import com.shopbadminton.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> layTatCa() {
        return ResponseEntity.ok(categoryService.layTatCa());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<CategoryResponse> taoMoi(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.taoMoi(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> capNhat(@PathVariable Integer id, @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.capNhat(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoa(@PathVariable Integer id) {
        categoryService.xoa(id);
        return ResponseEntity.noContent().build();
    }
}