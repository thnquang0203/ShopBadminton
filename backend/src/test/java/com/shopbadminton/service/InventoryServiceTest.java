package com.shopbadminton.service;

import com.shopbadminton.entity.Inventory;
import com.shopbadminton.entity.Product;
import com.shopbadminton.exception.BadRequestException;
import com.shopbadminton.exception.ResourceNotFoundException;
import com.shopbadminton.mapper.InventoryMapper;
import com.shopbadminton.repository.InventoryRepository;
import com.shopbadminton.service.impl.InventoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private InventoryMapper inventoryMapper;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    @Test
    void truKho_KhongDuTonKho_NemBadRequest() {
        Inventory tonKho = Inventory.builder()
                .maTonKho(1L)
                .sanPham(Product.builder().maSanPham(1L).build())
                .soLuong(5)
                .build();

        when(inventoryRepository.findBySanPham_MaSanPham(1L)).thenReturn(Optional.of(tonKho));

        assertThrows(BadRequestException.class, () -> inventoryService.truKho(1L, 10));
        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    void truKho_DuTonKho_TruThanhCong() {
        Inventory tonKho = Inventory.builder()
                .maTonKho(1L)
                .sanPham(Product.builder().maSanPham(1L).build())
                .soLuong(20)
                .build();

        when(inventoryRepository.findBySanPham_MaSanPham(1L)).thenReturn(Optional.of(tonKho));

        inventoryService.truKho(1L, 5);

        assertEquals(15, tonKho.getSoLuong());
        verify(inventoryRepository, times(1)).save(tonKho);
    }

    @Test
    void layTheoSanPham_KhongTonTai_NemResourceNotFound() {
        when(inventoryRepository.findBySanPham_MaSanPham(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> inventoryService.layTheoSanPham(999L));
    }
}