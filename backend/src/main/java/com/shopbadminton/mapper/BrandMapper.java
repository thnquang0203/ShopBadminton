package com.shopbadminton.mapper;

import com.shopbadminton.dto.response.BrandResponse;
import com.shopbadminton.entity.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper {
    public BrandResponse toResponse(Brand thuongHieu) {
        return BrandResponse.builder()
                .maThuongHieu(thuongHieu.getMaThuongHieu())
                .tenThuongHieu(thuongHieu.getTenThuongHieu())
                .build();
    }
}