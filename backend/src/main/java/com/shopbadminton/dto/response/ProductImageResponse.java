package com.shopbadminton.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductImageResponse {
    private Long maAnh;
    private String duongDanAnh;
    private Boolean laAnhDaiDien;
}