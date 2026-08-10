package com.shopbadminton.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {

    @NotBlank(message = "Tên sản phẩm không được trống")
    private String tenSanPham;

    private String moTa;

    @NotNull(message = "Giá không được trồng")
    @Positive(message = "Giá phải lớn hơn 0")
    private BigDecimal gia;

    @NotNull(message = "Phải chọn danh mục")
    private Integer maDanhMuc;

    @NotNull(message = "Phải chọn thương hiệu")
    private Integer maThuongHieu;
}