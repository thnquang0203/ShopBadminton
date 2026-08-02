package com.shopbadminton.controller;

import com.shopbadminton.dto.request.CustomerRequest;
import com.shopbadminton.dto.response.CustomerResponse;
import com.shopbadminton.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> layDanhSach(
            @RequestParam(required = false) String tuKhoa, Pageable pageable) {
        if (tuKhoa != null && !tuKhoa.isBlank()) {
            return ResponseEntity.ok(customerService.timKiem(tuKhoa, pageable));
        }
        return ResponseEntity.ok(customerService.layDanhSach(pageable));
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> layChiTiet(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.layChiTiet(id));
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @PostMapping
    public ResponseEntity<CustomerResponse> taoMoi(@Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.taoMoi(request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> capNhat(@PathVariable Long id, @Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.ok(customerService.capNhat(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoa(@PathVariable Long id) {
        customerService.xoaMem(id);
        return ResponseEntity.noContent().build();
    }
}