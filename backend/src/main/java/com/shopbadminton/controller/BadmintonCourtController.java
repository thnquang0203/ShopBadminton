package com.shopbadminton.controller;

import com.shopbadminton.dto.request.BadmintonCourtRequest;
import com.shopbadminton.dto.response.BadmintonCourtResponse;
import com.shopbadminton.service.BadmintonCourtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courts")
public class BadmintonCourtController {

    private final BadmintonCourtService badmintonCourtService;

    public BadmintonCourtController(BadmintonCourtService badmintonCourtService) {
        this.badmintonCourtService = badmintonCourtService;
    }

    @GetMapping
    public ResponseEntity<List<BadmintonCourtResponse>> layTatCa() {
        return ResponseEntity.ok(badmintonCourtService.layTatCa());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BadmintonCourtResponse> layChiTiet(@PathVariable Integer id) {
        return ResponseEntity.ok(badmintonCourtService.layChiTiet(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<BadmintonCourtResponse> taoMoi(@Valid @RequestBody BadmintonCourtRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(badmintonCourtService.taoMoi(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<BadmintonCourtResponse> capNhat(@PathVariable Integer id,
                                                           @Valid @RequestBody BadmintonCourtRequest request) {
        return ResponseEntity.ok(badmintonCourtService.capNhat(id, request));
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @PatchMapping("/{id}/trang-thai")
    public ResponseEntity<BadmintonCourtResponse> capNhatTrangThai(@PathVariable Integer id,
                                                                    @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(badmintonCourtService.capNhatTrangThai(id, body.get("trangThai")));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> xoa(@PathVariable Integer id) {
        badmintonCourtService.xoa(id);
        return ResponseEntity.noContent().build();
    }
}