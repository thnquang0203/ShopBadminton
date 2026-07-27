package com.shopbadminton.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private int maLoi;
    private String thongBao;
    private LocalDateTime thoiGian;
}