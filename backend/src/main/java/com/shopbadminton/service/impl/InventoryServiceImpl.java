package com.shopbadminton.service.impl;

import com.shopbadminton.dto.response.InventoryResponse;
import com.shopbadminton.entity.Inventory;
import com.shopbadminton.exception.BadRequestException;
import com.shopbadminton.exception.ResourceNotFoundException;
import com.shopbadminton.mapper.InventoryMapper;
import com.shopbadminton.repository.InventoryRepository;
import com.shopbadminton.service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    public InventoryServiceImpl(InventoryRepository inventoryRepository, InventoryMapper inventoryMapper) {
        this.inventoryRepository = inventoryRepository;
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    public Page<InventoryResponse> layDanhSach(Pageable pageable) {
        return inventoryRepository.findAllWithProduct(pageable).map(inventoryMapper::toResponse);
    }

    @Override
    public InventoryResponse layTheoSanPham(Long maSanPham) {
        Inventory tonKho = inventoryRepository.findBySanPham_MaSanPham(maSanPham)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm chưa có dữ liệu tồn kho"));
        return inventoryMapper.toResponse(tonKho);
    }

    @Override
    public List<InventoryResponse> layTonKhoThap() {
        return inventoryRepository.findAllTonKhoThap().stream()
                .map(inventoryMapper::toResponse)
                .collect(Collectors.toList());
    }
    @Override
    public void truKho(Long maSanPham, Integer soLuongTru) {
        Inventory tonKho = inventoryRepository.findBySanPham_MaSanPham(maSanPham)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm chưa có dữ liệu tồn kho"));

        if (tonKho.getSoLuong() < soLuongTru) {
            throw new BadRequestException("Tồn kho không đủ để xuất: còn lại" + tonKho.getSoLuong());
        }

        tonKho.setSoLuong(tonKho.getSoLuong() - soLuongTru);
        inventoryRepository.save(tonKho);
    }
}