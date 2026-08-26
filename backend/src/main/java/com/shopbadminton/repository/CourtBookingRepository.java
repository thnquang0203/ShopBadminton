package com.shopbadminton.repository;

import com.shopbadminton.entity.CourtBooking;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CourtBookingRepository extends JpaRepository<CourtBooking, Long> {

    List<CourtBooking> findByKhachHang_MaKhachHang(Long maKhachHang);

    @Query("""
    	    SELECT cb FROM CourtBooking cb
    	    WHERE cb.trangThai IN ('PENDING', 'CONFIRMED')
    	    AND (cb.ngayDat < CURRENT_DATE
    	         OR (cb.ngayDat = CURRENT_DATE AND cb.gioKetThuc < CURRENT_TIME))
    	    """)
    List<CourtBooking> timBookingDaQuaGio();
    List<CourtBooking> findBySan_MaSanAndNgayDatOrderByGioBatDauAsc(Integer maSan, LocalDate ngayDat);
    List<CourtBooking> findByNgayDatOrderBySan_MaSanAscGioBatDauAsc(LocalDate ngayDat);
    List<CourtBooking> findByNgayDatAndTrangThaiOrderByGioBatDauAsc(LocalDate ngayDat, String trangThai);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
        SELECT cb FROM CourtBooking cb
        WHERE cb.san.maSan = :maSan
        AND cb.ngayDat = :ngayDat
        AND cb.trangThai <> 'CANCELLED'
        AND cb.gioBatDau < :gioKetThuc
        AND cb.gioKetThuc > :gioBatDau
        """)
    List<CourtBooking> timBookingTrungGioCoKhoa(Integer maSan, LocalDate ngayDat, LocalTime gioBatDau, LocalTime gioKetThuc);
}