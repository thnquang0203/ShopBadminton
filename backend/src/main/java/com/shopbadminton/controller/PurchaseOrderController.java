package com.shopbadminton.controller;

import com.shopbadminton.dto.request.PurchaseOrderRequest;
import com.shopbadminton.dto.response.PurchaseOrderResponse;
import com.shopbadminton.service.PurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee/purchase-orders")
@PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService) {
        this.purchaseOrderService = purchaseOrderService;
    }

    @GetMapping
    public ResponseEntity<Page<PurchaseOrderResponse>> layDanhSach(Pageable pageable) {
        return ResponseEntity.ok(purchaseOrderService.layDanhSach(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponse> layChiTiet(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseOrderService.layChiTiet(id));
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderResponse> taoMoi(@Valid @RequestBody PurchaseOrderRequest request,
                                                          Authentication authentication) {
        String tenDangNhap = authentication.getName();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(purchaseOrderService.taoPhieuNhap(request, tenDangNhap));
    }
}