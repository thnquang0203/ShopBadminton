package com.shopbadminton.service.impl;

import com.shopbadminton.dto.request.BrandRequest;
import com.shopbadminton.dto.response.BrandResponse;
import com.shopbadminton.entity.Brand;
import com.shopbadminton.exception.DuplicateResourceException;
import com.shopbadminton.exception.ResourceNotFoundException;
import com.shopbadminton.mapper.BrandMapper;
import com.shopbadminton.repository.BrandRepository;
import com.shopbadminton.service.BrandService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public BrandServiceImpl(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public List<BrandResponse> layTatCa() {
        return brandRepository.findAll().stream()
                .map(brandMapper::toResponse)
                .toList();
    }

    @Override
    public BrandResponse taoMoi(BrandRequest request) {
        if (brandRepository.existsByTenThuongHieu(request.getTenThuongHieu())) {
            throw new DuplicateResourceException("Thương hiệu đã tồn tại");
        }
        Brand thuongHieu = Brand.builder().tenThuongHieu(request.getTenThuongHieu()).build();
        brandRepository.save(thuongHieu);
        return brandMapper.toResponse(thuongHieu);
    }

    @Override
    public BrandResponse capNhat(Integer id, BrandRequest request) {
        Brand thuongHieu = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thương hiệu"));
        thuongHieu.setTenThuongHieu(request.getTenThuongHieu());
        brandRepository.save(thuongHieu);
        return brandMapper.toResponse(thuongHieu);
    }

    @Override
    public void xoa(Integer id) {
        if (!brandRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy thương hiệu");
        }
        brandRepository.deleteById(id);
    }
}