package com.shopbadminton.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class InventoryResponse {
    private Long maTonKho;
    private Long maSanPham;
    private String tenSanPham;
    private Integer soLuong;
    private Integer soLuongToiThieu;
    private Boolean tonKhoThap;
    private LocalDateTime ngayCapNhat;
}