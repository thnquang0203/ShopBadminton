package com.shopbadminton.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CategoryResponse {
    private Integer maDanhMuc;
    private String tenDanhMuc;
}