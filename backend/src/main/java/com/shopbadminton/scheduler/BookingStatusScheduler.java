package com.shopbadminton.scheduler;

import com.shopbadminton.entity.CourtBooking;
import com.shopbadminton.repository.CourtBookingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookingStatusScheduler {

    private static final Logger log = LoggerFactory.getLogger(BookingStatusScheduler.class);

    private final CourtBookingRepository courtBookingRepository;

    public BookingStatusScheduler(CourtBookingRepository courtBookingRepository) {
        this.courtBookingRepository = courtBookingRepository;
    }

    @Scheduled(fixedRate = 15 * 60 * 1000)
    public void tuDongHoanThanhBooking() {
        List<CourtBooking> danhSachDaQuaGio = courtBookingRepository.timBookingDaQuaGio();

        if (danhSachDaQuaGio.isEmpty()) {
            return;
        }

        for (CourtBooking booking : danhSachDaQuaGio) {
            booking.setTrangThai("COMPLETED");
        }
        courtBookingRepository.saveAll(danhSachDaQuaGio);

        log.info("Đã tự động cập nhật {} booking sang trạng thái COMPLETED", danhSachDaQuaGio.size());
    }
}