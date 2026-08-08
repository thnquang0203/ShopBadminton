package com.shopbadminton.service;

import com.shopbadminton.dto.request.CategoryRequest;
import com.shopbadminton.dto.response.CategoryResponse;
import java.util.List;

public interface CategoryService {
    List<CategoryResponse> layTatCa();
    CategoryResponse taoMoi(CategoryRequest request);
    CategoryResponse capNhat(Integer id, CategoryRequest request);
    void xoa(Integer id);
}