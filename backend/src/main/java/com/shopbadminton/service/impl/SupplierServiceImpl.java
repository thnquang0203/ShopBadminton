package com.shopbadminton.service.impl;

import com.shopbadminton.dto.request.SupplierRequest;
import com.shopbadminton.dto.response.SupplierResponse;
import com.shopbadminton.entity.Supplier;
import com.shopbadminton.exception.ResourceNotFoundException;
import com.shopbadminton.mapper.SupplierMapper;
import com.shopbadminton.repository.SupplierRepository;
import com.shopbadminton.service.SupplierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    @Override
    public Page<SupplierResponse> layDanhSach(Pageable pageable) {
        return supplierRepository.findByDangHoatDongTrue(pageable).map(supplierMapper::toResponse);
    }

    @Override
    public SupplierResponse layChiTiet(Integer id) {
        return supplierMapper.toResponse(timTheoId(id));
    }

    @Override
    public SupplierResponse taoMoi(SupplierRequest request) {
        Supplier nhaCungCap = Supplier.builder()
                .tenNhaCungCap(request.getTenNhaCungCap())
                .soDienThoai(request.getSoDienThoai())
                .email(request.getEmail())
                .diaChi(request.getDiaChi())
                .build();
        supplierRepository.save(nhaCungCap);
        return supplierMapper.toResponse(nhaCungCap);
    }

    @Override
    public SupplierResponse capNhat(Integer id, SupplierRequest request) {
        Supplier nhaCungCap = timTheoId(id);
        nhaCungCap.setTenNhaCungCap(request.getTenNhaCungCap());
        nhaCungCap.setSoDienThoai(request.getSoDienThoai());
        nhaCungCap.setEmail(request.getEmail());
        nhaCungCap.setDiaChi(request.getDiaChi());
        supplierRepository.save(nhaCungCap);
        return supplierMapper.toResponse(nhaCungCap);
    }

    @Override
    public void xoaMem(Integer id) {
        Supplier nhaCungCap = timTheoId(id);
        nhaCungCap.setDangHoatDong(false);
        supplierRepository.save(nhaCungCap);
    }

    private Supplier timTheoId(Integer id) {
        return supplierRepository.findById(id)
                .filter(Supplier::getDangHoatDong)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà cung cấp"));
    }
}