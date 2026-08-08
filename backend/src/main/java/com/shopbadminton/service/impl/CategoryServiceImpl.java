package com.shopbadminton.service.impl;

import com.shopbadminton.dto.request.CategoryRequest;
import com.shopbadminton.dto.response.CategoryResponse;
import com.shopbadminton.entity.Category;
import com.shopbadminton.exception.DuplicateResourceException;
import com.shopbadminton.exception.ResourceNotFoundException;
import com.shopbadminton.mapper.CategoryMapper;
import com.shopbadminton.repository.CategoryRepository;
import com.shopbadminton.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryResponse> layTatCa() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Override
    public CategoryResponse taoMoi(CategoryRequest request) {
        if (categoryRepository.existsByTenDanhMuc(request.getTenDanhMuc())) {
            throw new DuplicateResourceException("Danh mục đã tồn tại");
        }
        Category danhMuc = Category.builder().tenDanhMuc(request.getTenDanhMuc()).build();
        categoryRepository.save(danhMuc);
        return categoryMapper.toResponse(danhMuc);
    }

    @Override
    public CategoryResponse capNhat(Integer id, CategoryRequest request) {
        Category danhMuc = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục"));
        danhMuc.setTenDanhMuc(request.getTenDanhMuc());
        categoryRepository.save(danhMuc);
        return categoryMapper.toResponse(danhMuc);
    }

    @Override
    public void xoa(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy danh mục");
        }
        categoryRepository.deleteById(id);
    }
}