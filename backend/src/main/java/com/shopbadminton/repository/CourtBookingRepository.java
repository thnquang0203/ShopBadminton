package com.shopbadminton.repository;

import com.shopbadminton.entity.CourtBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CourtBookingRepository extends JpaRepository<CourtBooking, Long> {

    List<CourtBooking> findByKhachHang_MaKhachHang(Long maKhachHang);

    @Query("""
        SELECT cb FROM CourtBooking cb
        WHERE cb.san.maSan = :maSan
        AND cb.ngayDat = :ngayDat
        AND cb.trangThai <> 'CANCELLED'
        AND cb.gioBatDau < :gioKetThuc
        AND cb.gioKetThuc > :gioBatDau
        """)
    List<CourtBooking> timBookingTrungGio(Integer maSan, LocalDate ngayDat, LocalTime gioBatDau, LocalTime gioKetThuc);
}