package com.shopbadminton.service;

import com.shopbadminton.dto.request.CourtBookingRequest;
import com.shopbadminton.entity.*;
import com.shopbadminton.exception.BadRequestException;
import com.shopbadminton.exception.ResourceNotFoundException;
import com.shopbadminton.mapper.CourtBookingMapper;
import com.shopbadminton.repository.*;
import com.shopbadminton.service.impl.CourtBookingServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourtBookingServiceTest {

    @Mock private CourtBookingRepository courtBookingRepository;
    @Mock private BadmintonCourtRepository badmintonCourtRepository;
    @Mock private CustomerRepository customerRepository;
    @Mock private UserRepository userRepository;
    @Mock private CourtBookingMapper courtBookingMapper;

    @InjectMocks
    private CourtBookingServiceImpl courtBookingService;

    @Test
    void datSan_SanDangBaoTri_NemBadRequest() {
        CourtBookingRequest request = new CourtBookingRequest();
        request.setMaSan(1);
        request.setNgayDat(LocalDate.of(2026, 8, 25));
        request.setGioBatDau(LocalTime.of(18, 0));
        request.setGioKetThuc(LocalTime.of(19, 0));

        BadmintonCourt san = BadmintonCourt.builder().maSan(1).trangThai("MAINTENANCE").build();
        when(badmintonCourtRepository.findById(1)).thenReturn(Optional.of(san));

        assertThrows(BadRequestException.class,
                () -> courtBookingService.datSan(request, "khach1"));

        verify(courtBookingRepository, never()).save(any(CourtBooking.class));
    }

    @Test
    void datSan_GioBatDauSauGioKetThuc_NemBadRequest() {
        CourtBookingRequest request = new CourtBookingRequest();
        request.setMaSan(1);
        request.setNgayDat(LocalDate.of(2026, 8, 25));
        request.setGioBatDau(LocalTime.of(19, 0));
        request.setGioKetThuc(LocalTime.of(18, 0)); // sai logic

        BadmintonCourt san = BadmintonCourt.builder().maSan(1).trangThai("AVAILABLE").build();
        when(badmintonCourtRepository.findById(1)).thenReturn(Optional.of(san));

        assertThrows(BadRequestException.class,
                () -> courtBookingService.datSan(request, "khach1"));
    }

    @Test
    void datSan_TrungKhungGio_NemBadRequest() {
        CourtBookingRequest request = new CourtBookingRequest();
        request.setMaSan(1);
        request.setNgayDat(LocalDate.of(2026, 8, 25));
        request.setGioBatDau(LocalTime.of(18, 0));
        request.setGioKetThuc(LocalTime.of(19, 0));

        BadmintonCourt san = BadmintonCourt.builder().maSan(1).trangThai("AVAILABLE").build();
        when(badmintonCourtRepository.findById(1)).thenReturn(Optional.of(san));
        when(courtBookingRepository.timBookingTrungGioCoKhoa(1, request.getNgayDat(),
                request.getGioBatDau(), request.getGioKetThuc()))
                .thenReturn(List.of(new CourtBooking()));

        assertThrows(BadRequestException.class,
                () -> courtBookingService.datSan(request, "khach1"));

        verify(courtBookingRepository, never()).save(any(CourtBooking.class));
    }

    @Test
    void datSan_HopLe_TaoThanhCong() {
        CourtBookingRequest request = new CourtBookingRequest();
        request.setMaSan(1);
        request.setNgayDat(LocalDate.of(2026, 8, 25));
        request.setGioBatDau(LocalTime.of(18, 0));
        request.setGioKetThuc(LocalTime.of(19, 0));

        BadmintonCourt san = BadmintonCourt.builder().maSan(1).trangThai("AVAILABLE").build();
        User nguoiDung = User.builder().maNguoiDung(1L).build();
        Customer khachHang = Customer.builder().maKhachHang(1L).build();

        when(badmintonCourtRepository.findById(1)).thenReturn(Optional.of(san));
        when(courtBookingRepository.timBookingTrungGioCoKhoa(anyInt(), any(), any(), any()))
                .thenReturn(List.of());
        when(userRepository.findByTenDangNhap("khach1")).thenReturn(Optional.of(nguoiDung));
        when(customerRepository.findByNguoiDung_MaNguoiDung(1L)).thenReturn(Optional.of(khachHang));

        courtBookingService.datSan(request, "khach1");

        verify(courtBookingRepository, times(1)).save(any(CourtBooking.class));
    }

    @Test
    void huyDatSan_KhongPhaiChuSoHuu_NemBadRequest() {
        User chuSoHuu = User.builder().maNguoiDung(1L).build();
        User nguoiKhac = User.builder().maNguoiDung(2L).build();
        Customer khachHang = Customer.builder().maKhachHang(1L).nguoiDung(chuSoHuu).build();
        CourtBooking booking = CourtBooking.builder().maDatSan(1L).khachHang(khachHang).trangThai("PENDING").build();

        when(courtBookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(userRepository.findByTenDangNhap("khac")).thenReturn(Optional.of(nguoiKhac));

        assertThrows(BadRequestException.class,
                () -> courtBookingService.huyDatSan(1L, "khac"));
    }

    @Test
    void xacNhanDatSan_KhongPhaiPending_NemBadRequest() {
        CourtBooking booking = CourtBooking.builder().maDatSan(1L).trangThai("CONFIRMED").build();
        when(courtBookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        assertThrows(BadRequestException.class, () -> courtBookingService.xacNhanDatSan(1L));
    }

    @Test
    void layChiTiet_KhongTonTai_NemResourceNotFound() {
        when(courtBookingRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courtBookingService.layChiTiet(999L));
    }
}