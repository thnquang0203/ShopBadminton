package com.shopbadminton.service;

import com.shopbadminton.dto.request.CategoryRequest;
import com.shopbadminton.entity.Category;
import com.shopbadminton.exception.DuplicateResourceException;
import com.shopbadminton.exception.ResourceNotFoundException;
import com.shopbadminton.mapper.CategoryMapper;
import com.shopbadminton.repository.CategoryRepository;
import com.shopbadminton.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void taoMoi_TrungTen_NemDuplicateException() {
        CategoryRequest request = new CategoryRequest();
        request.setTenDanhMuc("Vợt cầu lông");

        when(categoryRepository.existsByTenDanhMuc("Vợt cầu lông")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> categoryService.taoMoi(request));
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void taoMoi_TenMoi_TaoThanhCong() {
        CategoryRequest request = new CategoryRequest();
        request.setTenDanhMuc("Giày cầu lông");

        when(categoryRepository.existsByTenDanhMuc("Giày cầu lông")).thenReturn(false);

        categoryService.taoMoi(request);

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void xoa_KhongTonTai_NemResourceNotFound() {
        when(categoryRepository.existsById(999)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> categoryService.xoa(999));
        verify(categoryRepository, never()).deleteById(anyInt());
    }
}