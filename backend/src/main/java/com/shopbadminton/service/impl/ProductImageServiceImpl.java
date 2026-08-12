package com.shopbadminton.service.impl;

import com.shopbadminton.dto.response.ProductImageResponse;
import com.shopbadminton.entity.Product;
import com.shopbadminton.entity.ProductImage;
import com.shopbadminton.exception.BadRequestException;
import com.shopbadminton.exception.FileUploadException;
import com.shopbadminton.exception.ResourceNotFoundException;
import com.shopbadminton.repository.ProductImageRepository;
import com.shopbadminton.repository.ProductRepository;
import com.shopbadminton.service.ProductImageService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Value("${app.upload.dir}")
    private String thuMucUpload;

    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;

    public ProductImageServiceImpl(ProductImageRepository productImageRepository, ProductRepository productRepository) {
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
    }

    @Override
    public ProductImageResponse upload(Long maSanPham, MultipartFile file, boolean laAnhDaiDien) {
        Product sanPham = productRepository.findById(maSanPham)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm"));

        String duoiFile = layDuoiFile(file.getOriginalFilename());
        if (!List.of(".jpg", ".jpeg", ".png").contains(duoiFile.toLowerCase())) {
            throw new BadRequestException("Chỉ chấp nhận file jpg, jpeg, png");
        }
        
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new BadRequestException("Dung lượng file không được vượt quá 5MB");
        }
        if (laAnhDaiDien) {
            List<ProductImage> anhCu = productImageRepository.findBySanPham_MaSanPham(maSanPham);
            anhCu.forEach(anh -> {
                if (Boolean.TRUE.equals(anh.getLaAnhDaiDien())) {
                    anh.setLaAnhDaiDien(false);
                    productImageRepository.save(anh);
                }
            });
        }

        String tenFileMoi = UUID.randomUUID() + duoiFile;

        try {
            Path thuMuc = Paths.get(thuMucUpload);
            if (!Files.exists(thuMuc)) {
                Files.createDirectories(thuMuc);
            }
            Path duongDanLuu = thuMuc.resolve(tenFileMoi);
            Files.copy(file.getInputStream(), duongDanLuu);
        } catch (IOException e) {
            throw new FileUploadException("Lỗi khi lưu file ảnh: " + e.getMessage());
        }

        ProductImage anh = ProductImage.builder()
                .sanPham(sanPham)
                .duongDanAnh("/images/products/" + tenFileMoi)
                .laAnhDaiDien(laAnhDaiDien)
                .build();

        productImageRepository.save(anh);

        return ProductImageResponse.builder()
                .maAnh(anh.getMaAnh())
                .duongDanAnh(anh.getDuongDanAnh())
                .laAnhDaiDien(anh.getLaAnhDaiDien())
                .build();
    }

    @Override
    public List<ProductImageResponse> layTheoSanPham(Long maSanPham) {
        return productImageRepository.findBySanPham_MaSanPham(maSanPham).stream()
                .map(anh -> ProductImageResponse.builder()
                        .maAnh(anh.getMaAnh())
                        .duongDanAnh(anh.getDuongDanAnh())
                        .laAnhDaiDien(anh.getLaAnhDaiDien())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public void xoa(Long maAnh) {
        ProductImage anh = productImageRepository.findById(maAnh)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy ảnh"));

        try {
            Path duongDanFile = Paths.get(thuMucUpload).resolve(Paths.get(anh.getDuongDanAnh()).getFileName());
            Files.deleteIfExists(duongDanFile);
        } catch (IOException e) {
            // bỏ qua lỗi xóa file vật lí
        }

        productImageRepository.delete(anh);
    }

    private String layDuoiFile(String tenFile) {
        if (tenFile == null || !tenFile.contains(".")) {
            throw new BadRequestException("Tên file không hợp lệ");
        }
        return tenFile.substring(tenFile.lastIndexOf("."));
    }
}