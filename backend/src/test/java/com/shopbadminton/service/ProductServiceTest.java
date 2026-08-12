package com.shopbadminton.service;

import com.shopbadminton.dto.request.ProductRequest;
import com.shopbadminton.dto.response.ProductResponse;
import com.shopbadminton.entity.Brand;
import com.shopbadminton.entity.Category;
import com.shopbadminton.entity.Product;
import com.shopbadminton.exception.ResourceNotFoundException;
import com.shopbadminton.mapper.ProductMapper;
import com.shopbadminton.repository.BrandRepository;
import com.shopbadminton.repository.CategoryRepository;
import com.shopbadminton.repository.ProductRepository;
import com.shopbadminton.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void taoMoi_DanhMucKhongTonTai_NemResourceNotFound() {
        ProductRequest request = new ProductRequest();
        request.setTenSanPham("Vợt Yonex");
        request.setGia(BigDecimal.valueOf(3000000));
        request.setMaDanhMuc(999);
        request.setMaThuongHieu(1);

        when(categoryRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.taoMoi(request));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void taoMoi_ThuongHieuKhongTonTai_NemResourceNotFound() {
        ProductRequest request = new ProductRequest();
        request.setTenSanPham("Vợt Yonex");
        request.setGia(BigDecimal.valueOf(3000000));
        request.setMaDanhMuc(1);
        request.setMaThuongHieu(999);

        Category danhMuc = Category.builder().maDanhMuc(1).tenDanhMuc("Vợt").build();
        when(categoryRepository.findById(1)).thenReturn(Optional.of(danhMuc));
        when(brandRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.taoMoi(request));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void taoMoi_HopLe_TaoThanhCong() {
        ProductRequest request = new ProductRequest();
        request.setTenSanPham("Vợt Yonex");
        request.setGia(BigDecimal.valueOf(3000000));
        request.setMaDanhMuc(1);
        request.setMaThuongHieu(1);

        Category danhMuc = Category.builder().maDanhMuc(1).tenDanhMuc("Vợt").build();
        Brand thuongHieu = Brand.builder().maThuongHieu(1).tenThuongHieu("Yonex").build();

        when(categoryRepository.findById(1)).thenReturn(Optional.of(danhMuc));
        when(brandRepository.findById(1)).thenReturn(Optional.of(thuongHieu));
        when(productMapper.toResponse(any(Product.class))).thenReturn(
                ProductResponse.builder().maSanPham(1L).tenSanPham("Vợt Yonex").build()
        );

        ProductResponse response = productService.taoMoi(request);

        assertEquals("Vợt Yonex", response.getTenSanPham());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void layChiTiet_KhongTonTai_NemResourceNotFound() {
        when(productRepository.findActiveByIdWithDetails(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.layChiTiet(999L));
    }
}