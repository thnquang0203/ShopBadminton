package com.shopbadminton.service;

import com.shopbadminton.dto.request.PurchaseDetailRequest;
import com.shopbadminton.dto.request.PurchaseOrderRequest;
import com.shopbadminton.entity.*;
import com.shopbadminton.exception.BadRequestException;
import com.shopbadminton.exception.ResourceNotFoundException;
import com.shopbadminton.repository.*;
import com.shopbadminton.service.impl.PurchaseOrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchaseOrderServiceTest {

    @Mock private PurchaseOrderRepository purchaseOrderRepository;
    @Mock private PurchaseDetailRepository purchaseDetailRepository;
    @Mock private SupplierRepository supplierRepository;
    @Mock private EmployeeRepository employeeRepository;
    @Mock private ProductRepository productRepository;
    @Mock private InventoryRepository inventoryRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks
    private PurchaseOrderServiceImpl purchaseOrderService;

    @Test
    void taoPhieuNhap_TrungSanPham_NemBadRequest() {
        PurchaseOrderRequest request = new PurchaseOrderRequest();
        request.setMaNhaCungCap(1);

        PurchaseDetailRequest ct1 = new PurchaseDetailRequest();
        ct1.setMaSanPham(1L);
        ct1.setSoLuong(10);
        ct1.setDonGia(BigDecimal.valueOf(100000));

        PurchaseDetailRequest ct2 = new PurchaseDetailRequest();
        ct2.setMaSanPham(1L); //trùng sản phẩm
        ct2.setSoLuong(5);
        ct2.setDonGia(BigDecimal.valueOf(100000));

        request.setChiTiet(List.of(ct1, ct2));
        assertThrows(BadRequestException.class,
                () -> purchaseOrderService.taoPhieuNhap(request, "test1"));

        verify(purchaseOrderRepository, never()).save(any(PurchaseOrder.class));
    }

    @Test
    void taoPhieuNhap_NhaCungCapKhongTonTai_NemResourceNotFound() {
        PurchaseOrderRequest request = new PurchaseOrderRequest();
        request.setMaNhaCungCap(999);
        request.setChiTiet(List.of());

        when(supplierRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> purchaseOrderService.taoPhieuNhap(request, "test1"));
    }

    @Test
    void taoPhieuNhap_TaiKhoanKhongPhaiNhanVien_NemResourceNotFound() {
        PurchaseOrderRequest request = new PurchaseOrderRequest();
        request.setMaNhaCungCap(1);
        request.setChiTiet(List.of());

        when(supplierRepository.findById(1)).thenReturn(Optional.of(new Supplier()));
        when(userRepository.findByTenDangNhap("khach1")).thenReturn(Optional.of(new User()));
        when(employeeRepository.findByNguoiDung_MaNguoiDung(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> purchaseOrderService.taoPhieuNhap(request, "khach1"));
    }
}