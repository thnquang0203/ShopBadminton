package com.shopbadminton.mapper;

import com.shopbadminton.dto.response.CategoryResponse;
import com.shopbadminton.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryResponse toResponse(Category danhMuc) {
        return CategoryResponse.builder()
                .maDanhMuc(danhMuc.getMaDanhMuc())
                .tenDanhMuc(danhMuc.getTenDanhMuc())
                .build();
    }
}