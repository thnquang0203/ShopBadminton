package com.shopbadminton.repository;

import com.shopbadminton.entity.CourtBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CourtBookingRepository extends JpaRepository<CourtBooking, Long> {

    List<CourtBooking> findByKhachHang_MaKhachHang(Long maKhachHang);

    @Query(value = """
    	    SELECT * FROM CourtBookings
    	    WHERE court_id = :maSan
    	    AND booking_date = :ngayDat
    	    AND status <> 'CANCELLED'
    	    AND CAST(start_time AS TIME) < CAST(:gioKetThuc AS TIME)
    	    AND CAST(end_time AS TIME) > CAST(:gioBatDau AS TIME)
    	    """, nativeQuery = true)
    List<CourtBooking> timBookingTrungGio(Integer maSan, LocalDate ngayDat, LocalTime gioBatDau, LocalTime gioKetThuc);
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
}