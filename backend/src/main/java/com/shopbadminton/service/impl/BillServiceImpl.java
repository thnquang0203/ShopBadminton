package com.shopbadminton.service.impl;

import com.shopbadminton.dto.request.BillProductItemRequest;
import com.shopbadminton.dto.request.BillRequest;
import com.shopbadminton.dto.response.BillResponse;
import com.shopbadminton.entity.*;
import com.shopbadminton.exception.ResourceNotFoundException;
import com.shopbadminton.repository.*;
import com.shopbadminton.service.BillService;
import com.shopbadminton.service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;
    private final BillDetailRepository billDetailRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final InventoryService inventoryService;

    public BillServiceImpl(BillRepository billRepository, BillDetailRepository billDetailRepository,
                            CustomerRepository customerRepository, EmployeeRepository employeeRepository,
                            ProductRepository productRepository, UserRepository userRepository,
                            InventoryService inventoryService) {
        this.billRepository = billRepository;
        this.billDetailRepository = billDetailRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.inventoryService = inventoryService;
    }

    @Override
    public Page<BillResponse> layDanhSach(Pageable pageable) {
        return billRepository.findAllByOrderByNgayTaoDesc(pageable).map(this::toResponse);
    }

    @Override
    public BillResponse layChiTiet(Long id) {
        Bill hoaDon = billRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy hóa đơn"));
        return toResponse(hoaDon);
    }

    @Override
    @Transactional
    public BillResponse taoHoaDonBanSanPham(BillRequest request, String tenDangNhapNguoiTao) {
        Customer khachHang = customerRepository.findById(request.getMaKhachHang())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khách hàng"));

        User nguoiDung = userRepository.findByTenDangNhap(tenDangNhapNguoiTao)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản"));

        Employee nhanVien = employeeRepository.findByNguoiDung_MaNguoiDung(nguoiDung.getMaNguoiDung())
                .orElseThrow(() -> new ResourceNotFoundException("Tài khoản không phải tài khoản nhân viên"));

        Bill hoaDon = Bill.builder()
                .khachHang(khachHang)
                .nhanVien(nhanVien)
                .trangThai("UNPAID")
                .build();
        billRepository.save(hoaDon);

        BigDecimal tongTien = BigDecimal.ZERO;

        for (BillProductItemRequest item : request.getDanhSachSanPham()) {
            Product sanPham = productRepository.findById(item.getMaSanPham())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm: " + item.getMaSanPham()));

            BigDecimal thanhTien = sanPham.getGia().multiply(BigDecimal.valueOf(item.getSoLuong()));

            BillDetail chiTiet = BillDetail.builder()
                    .hoaDon(hoaDon)
                    .sanPham(sanPham)
                    .soLuong(item.getSoLuong())
                    .donGia(sanPham.getGia())
                    .thanhTien(thanhTien)
                    .build();
            billDetailRepository.save(chiTiet);

            tongTien = tongTien.add(thanhTien);

            // Tu dong tru ton kho (dung lai method tu Day 34)
            inventoryService.truKho(sanPham.getMaSanPham(), item.getSoLuong());
        }

        hoaDon.setTongTien(tongTien);
        billRepository.save(hoaDon);

        return toResponse(hoaDon);
    }

    private BillResponse toResponse(Bill hoaDon) {
        List<BillDetail> danhSachChiTiet = billDetailRepository.findByHoaDon_MaHoaDon(hoaDon.getMaHoaDon());

        List<BillResponse.ChiTietItem> chiTietItems = danhSachChiTiet.stream()
                .map(ct -> {
                    if (ct.getSanPham() != null) {
                        return BillResponse.ChiTietItem.builder()
                                .loai("SAN_PHAM")
                                .tenMuc(ct.getSanPham().getTenSanPham())
                                .soLuong(ct.getSoLuong())
                                .donGia(ct.getDonGia())
                                .thanhTien(ct.getThanhTien())
                                .build();
                    } else {
                        return BillResponse.ChiTietItem.builder()
                                .loai("DAT_SAN")
                                .tenMuc(ct.getDatSan().getSan().getTenSan() + " - " + ct.getDatSan().getNgayDat())
                                .soLuong(1)
                                .donGia(ct.getDonGia())
                                .thanhTien(ct.getThanhTien())
                                .build();
                    }
                })
                .collect(Collectors.toList());

        return BillResponse.builder()
                .maHoaDon(hoaDon.getMaHoaDon())
                .tenKhachHang(hoaDon.getKhachHang().getHoTen())
                .tenNhanVien(hoaDon.getNhanVien().getHoTen())
                .tongTien(hoaDon.getTongTien())
                .trangThai(hoaDon.getTrangThai())
                .ngayTao(hoaDon.getNgayTao())
                .chiTiet(chiTietItems)
                .build();
    }
}