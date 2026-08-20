package com.shopbadminton.mapper;

import com.shopbadminton.dto.response.BadmintonCourtResponse;
import com.shopbadminton.entity.BadmintonCourt;
import org.springframework.stereotype.Component;

@Component
public class BadmintonCourtMapper {
    public BadmintonCourtResponse toResponse(BadmintonCourt san) {
        return BadmintonCourtResponse.builder()
                .maSan(san.getMaSan())
                .tenSan(san.getTenSan())
                .loaiSan(san.getLoaiSan())
                .giaTheoGio(san.getGiaTheoGio())
                .trangThai(san.getTrangThai())
                .build();
    }
}