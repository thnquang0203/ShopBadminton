package com.shopbadminton.controller;

import com.shopbadminton.dto.request.CourtBookingRequest;
import com.shopbadminton.dto.response.CourtBookingResponse;
import com.shopbadminton.service.CourtBookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class CourtBookingController {

    private final CourtBookingService courtBookingService;

    public CourtBookingController(CourtBookingService courtBookingService) {
        this.courtBookingService = courtBookingService;
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<CourtBookingResponse> datSan(@Valid @RequestBody CourtBookingRequest request,
                                                         Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courtBookingService.datSan(request, authentication.getName()));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/cua-toi")
    public ResponseEntity<List<CourtBookingResponse>> layCuaToi(Authentication authentication) {
        return ResponseEntity.ok(courtBookingService.layTheoKhachHang(authentication.getName()));
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE','CUSTOMER')")
    @GetMapping("/{id}")
    public ResponseEntity<CourtBookingResponse> layChiTiet(@PathVariable Long id) {
        return ResponseEntity.ok(courtBookingService.layChiTiet(id));
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @PatchMapping("/{id}/huy")
    public ResponseEntity<Void> huyDatSan(@PathVariable Long id, Authentication authentication) {
        courtBookingService.huyDatSan(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @GetMapping("/lich")
    public ResponseEntity<List<CourtBookingResponse>> xemLichTheoNgay(
            @RequestParam LocalDate ngayDat) {
        return ResponseEntity.ok(courtBookingService.layLichTheoNgay(ngayDat));
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @GetMapping("/lich/san")
    public ResponseEntity<List<CourtBookingResponse>> xemLichTheoSan(
            @RequestParam Integer maSan, @RequestParam LocalDate ngayDat) {
        return ResponseEntity.ok(courtBookingService.layLichTheoSanVaNgay(maSan, ngayDat));
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @GetMapping("/lich/trang-thai")
    public ResponseEntity<List<CourtBookingResponse>> xemLichTheoTrangThai(
            @RequestParam LocalDate ngayDat, @RequestParam String trangThai) {
        return ResponseEntity.ok(courtBookingService.layLichTheoTrangThai(ngayDat, trangThai));
    }

    @PreAuthorize("hasAnyRole('ADMIN','EMPLOYEE')")
    @PatchMapping("/{id}/xac-nhan")
    public ResponseEntity<CourtBookingResponse> xacNhan(@PathVariable Long id) {
        return ResponseEntity.ok(courtBookingService.xacNhanDatSan(id));
    }
}