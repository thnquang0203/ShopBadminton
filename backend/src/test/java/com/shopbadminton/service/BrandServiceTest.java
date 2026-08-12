package com.shopbadminton.service;

import com.shopbadminton.dto.request.BrandRequest;
import com.shopbadminton.entity.Brand;
import com.shopbadminton.exception.DuplicateResourceException;
import com.shopbadminton.mapper.BrandMapper;
import com.shopbadminton.repository.BrandRepository;
import com.shopbadminton.service.impl.BrandServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BrandServiceTest {

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private BrandMapper brandMapper;

    @InjectMocks
    private BrandServiceImpl brandService;

    @Test
    void taoMoi_TrungTen_NemDuplicateException() {
        BrandRequest request = new BrandRequest();
        request.setTenThuongHieu("Yonex");

        when(brandRepository.existsByTenThuongHieu("Yonex")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> brandService.taoMoi(request));
        verify(brandRepository, never()).save(any(Brand.class));
    }

    @Test
    void taoMoi_TenMoi_TaoThanhCong() {
        BrandRequest request = new BrandRequest();
        request.setTenThuongHieu("Lining");

        when(brandRepository.existsByTenThuongHieu("Lining")).thenReturn(false);

        brandService.taoMoi(request);

        verify(brandRepository, times(1)).save(any(Brand.class));
    }
}