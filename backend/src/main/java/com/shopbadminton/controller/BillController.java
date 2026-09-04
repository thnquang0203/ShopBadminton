package com.shopbadminton.controller;

import com.shopbadminton.dto.request.BillRequest;
import com.shopbadminton.dto.response.BillResponse;
import com.shopbadminton.service.BillService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee/bills")
@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping
    public ResponseEntity<Page<BillResponse>> layDanhSach(Pageable pageable) {
        return ResponseEntity.ok(billService.layDanhSach(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillResponse> layChiTiet(@PathVariable Long id) {
        return ResponseEntity.ok(billService.layChiTiet(id));
    }

    @PostMapping("/ban-san-pham")
    public ResponseEntity<BillResponse> taoHoaDonBanSanPham(@Valid @RequestBody BillRequest request,
                                                             Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(billService.taoHoaDonBanSanPham(request, authentication.getName()));
    }
}