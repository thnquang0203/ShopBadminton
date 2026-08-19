package com.shopbadminton.service.impl;

import com.shopbadminton.dto.request.PurchaseDetailRequest;
import com.shopbadminton.dto.request.PurchaseOrderRequest;
import com.shopbadminton.dto.response.PurchaseOrderResponse;
import com.shopbadminton.entity.*;
import com.shopbadminton.exception.BadRequestException;
import com.shopbadminton.exception.ResourceNotFoundException;
import com.shopbadminton.repository.*;
import com.shopbadminton.service.PurchaseOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PurchaseDetailRepository purchaseDetailRepository;
    private final SupplierRepository supplierRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;
    private final InventoryRepository inventoryRepository;
    private final UserRepository userRepository;
    
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(PurchaseOrderServiceImpl.class);

    public PurchaseOrderServiceImpl(PurchaseOrderRepository purchaseOrderRepository,
                                     PurchaseDetailRepository purchaseDetailRepository,
                                     SupplierRepository supplierRepository,
                                     EmployeeRepository employeeRepository,
                                     ProductRepository productRepository,
                                     InventoryRepository inventoryRepository,
                                     UserRepository userRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.purchaseDetailRepository = purchaseDetailRepository;
        this.supplierRepository = supplierRepository;
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
        this.inventoryRepository = inventoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<PurchaseOrderResponse> layDanhSach(Pageable pageable) {
        return purchaseOrderRepository.findAllByOrderByNgayNhapDesc(pageable).map(this::toResponse);
    }

    @Override
    public PurchaseOrderResponse layChiTiet(Long id) {
        PurchaseOrder phieuNhap = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy phiếu nhập"));
        return toResponse(phieuNhap);
    }

    @Override
    @Transactional
    public PurchaseOrderResponse taoPhieuNhap(PurchaseOrderRequest request, String tenDangNhapNguoiTao) {
    	
    	
    	// Kiểm tra trùng sản phẩm trong cungf phiếu nhập
        long soSanPhamKhongTrung = request.getChiTiet().stream()
                .map(PurchaseDetailRequest::getMaSanPham)
                .distinct()
                .count();
        if (soSanPhamKhongTrung != request.getChiTiet().size()) {
            throw new BadRequestException("Không được nhập trùng sản phẩm trong cùng 1 phiếu nhập");
        }
    	Supplier nhaCungCap = supplierRepository.findById(request.getMaNhaCungCap())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà cung cấp"));

        User nguoiDung = userRepository.findByTenDangNhap(tenDangNhapNguoiTao)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản"));

        Employee nhanVien = employeeRepository.findByNguoiDung_MaNguoiDung(nguoiDung.getMaNguoiDung())
                .orElseThrow(() -> new ResourceNotFoundException("Tài khoản không phải nhân viên"));
        
        PurchaseOrder phieuNhap = PurchaseOrder.builder()
                .nhaCungCap(nhaCungCap)
                .nhanVien(nhanVien)
                .trangThai("COMPLETED")
                .build();
        purchaseOrderRepository.save(phieuNhap);

        BigDecimal tongTien = BigDecimal.ZERO;

        for (PurchaseDetailRequest ct : request.getChiTiet()) {
            Product sanPham = productRepository.findById(ct.getMaSanPham())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm: " + ct.getMaSanPham()));

            PurchaseDetail chiTiet = PurchaseDetail.builder()
                    .phieuNhap(phieuNhap)
                    .sanPham(sanPham)
                    .soLuong(ct.getSoLuong())
                    .donGia(ct.getDonGia())
                    .build();
            purchaseDetailRepository.save(chiTiet);

            tongTien = tongTien.add(ct.getDonGia().multiply(BigDecimal.valueOf(ct.getSoLuong())));

            // Tự động cộng tồn kho
            Inventory tonKho = inventoryRepository.findBySanPham_MaSanPham(sanPham.getMaSanPham())
                    .orElseGet(() -> Inventory.builder().sanPham(sanPham).soLuong(0).build());
            tonKho.setSoLuong(tonKho.getSoLuong() + ct.getSoLuong());
            inventoryRepository.save(tonKho);
        }

        phieuNhap.setTongTien(tongTien);
        purchaseOrderRepository.save(phieuNhap);
        
        log.info("Tạo phiếu nhập thành công: maPhieuNhap={}, nhaCungCap={}, tongTien={}",
                phieuNhap.getMaPhieuNhap(), nhaCungCap.getTenNhaCungCap(), tongTien);

        return toResponse(phieuNhap);
    }

    private PurchaseOrderResponse toResponse(PurchaseOrder phieuNhap) {
        List<PurchaseDetail> danhSachChiTiet = purchaseDetailRepository.findByPhieuNhap_MaPhieuNhap(phieuNhap.getMaPhieuNhap());

        List<PurchaseOrderResponse.ChiTietItem> chiTietItems = danhSachChiTiet.stream()
                .map(ct -> PurchaseOrderResponse.ChiTietItem.builder()
                        .maSanPham(ct.getSanPham().getMaSanPham())
                        .tenSanPham(ct.getSanPham().getTenSanPham())
                        .soLuong(ct.getSoLuong())
                        .donGia(ct.getDonGia())
                        .build())
                .collect(Collectors.toList());

        return PurchaseOrderResponse.builder()
                .maPhieuNhap(phieuNhap.getMaPhieuNhap())
                .tenNhaCungCap(phieuNhap.getNhaCungCap().getTenNhaCungCap())
                .tenNhanVien(phieuNhap.getNhanVien().getHoTen())
                .ngayNhap(phieuNhap.getNgayNhap())
                .tongTien(phieuNhap.getTongTien())
                .trangThai(phieuNhap.getTrangThai())
                .chiTiet(chiTietItems)
                .build();
    }
}