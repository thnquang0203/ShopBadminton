package com.shopbadminton.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class CourtBookingRequest {

    @NotNull(message = "Phải chọn sân")
    private Integer maSan;

    @NotNull(message = "Ngày đặt không được trống")
    private LocalDate ngayDat;

    @NotNull(message = "Giờ đặt không được trống")
    private LocalTime gioBatDau;

    @NotNull(message = "Giờ kết thúc không được trống")
    private LocalTime gioKetThuc;
}