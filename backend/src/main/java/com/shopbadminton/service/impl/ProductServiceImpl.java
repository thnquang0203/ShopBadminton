package com.shopbadminton.service.impl;

import com.shopbadminton.dto.request.ProductRequest;
import com.shopbadminton.repository.ProductSpecification;
import com.shopbadminton.dto.response.ProductResponse;
import com.shopbadminton.entity.Brand;
import com.shopbadminton.entity.Category;
import com.shopbadminton.entity.Product;
import com.shopbadminton.exception.ResourceNotFoundException;
import com.shopbadminton.mapper.ProductMapper;
import com.shopbadminton.repository.BrandRepository;
import com.shopbadminton.repository.CategoryRepository;
import com.shopbadminton.repository.ProductRepository;
import com.shopbadminton.service.ProductService;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.jpa.domain.Specification;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository,
                               BrandRepository brandRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Page<ProductResponse> layDanhSach(Pageable pageable) {
        return productRepository.findAllActiveWithDetails(pageable).map(productMapper::toResponse);
    }

    @Override
    public ProductResponse layChiTiet(Long id) {
        Product sanPham = productRepository.findActiveByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm"));
        return productMapper.toResponse(sanPham);
    }

    @Override
    public ProductResponse taoMoi(ProductRequest request) {
        Category danhMuc = categoryRepository.findById(request.getMaDanhMuc())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục"));
        Brand thuongHieu = brandRepository.findById(request.getMaThuongHieu())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thương hiệu"));

        Product sanPham = Product.builder()
                .tenSanPham(request.getTenSanPham())
                .moTa(request.getMoTa())
                .gia(request.getGia())
                .danhMuc(danhMuc)
                .thuongHieu(thuongHieu)
                .build();

        productRepository.save(sanPham);
        return productMapper.toResponse(sanPham);
    }

    @Override
    public ProductResponse capNhat(Long id, ProductRequest request) {
        Product sanPham = productRepository.findById(id)
        		.filter(Product::getDangHoatDong)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm hoặc đã bị xóa"));

        Category danhMuc = categoryRepository.findById(request.getMaDanhMuc())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục"));
        Brand thuongHieu = brandRepository.findById(request.getMaThuongHieu())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy thương hiệu"));

        sanPham.setTenSanPham(request.getTenSanPham());
        sanPham.setMoTa(request.getMoTa());
        sanPham.setGia(request.getGia());
        sanPham.setDanhMuc(danhMuc);
        sanPham.setThuongHieu(thuongHieu);

        productRepository.save(sanPham);
        return productMapper.toResponse(sanPham);
    }

    @Override
    public void xoaMem(Long id) {
        Product sanPham = productRepository.findById(id)
                .filter(Product::getDangHoatDong)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm"));
        sanPham.setDangHoatDong(false);
        productRepository.save(sanPham);
    }
    @Override
    public Page<ProductResponse> timKiemVaLoc(String tuKhoa, Integer maDanhMuc, Integer maThuongHieu,
                                               BigDecimal giaTu, BigDecimal giaDen, Pageable pageable) {
        Specification<Product> spec = Specification
                .where(ProductSpecification.dangHoatDong())
                .and(ProductSpecification.theoTenSanPham(tuKhoa))
                .and(ProductSpecification.theoDanhMuc(maDanhMuc))
                .and(ProductSpecification.theoThuongHieu(maThuongHieu))
                .and(ProductSpecification.theoKhoangGia(giaTu, giaDen));

        return productRepository.findAll(spec, pageable).map(productMapper::toResponse);
    }
}