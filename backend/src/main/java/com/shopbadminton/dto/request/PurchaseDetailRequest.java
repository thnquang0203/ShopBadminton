package com.shopbadminton.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PurchaseDetailRequest {

    @NotNull(message = "Phải chọn sản phẩm")
    private Long maSanPham;

    @NotNull(message = "Số lượng không được trống")
    @Positive(message = "Số lượng phải lớn hơn 0")
    private Integer soLuong;

    @NotNull(message = "Đơn giá không được trống")
    @Positive(message = "Đơn giá phải lớn hơn 0")
    private BigDecimal donGia;
}