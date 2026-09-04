package com.shopbadminton.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillProductItemRequest {

    @NotNull(message = "Phải chọn sản phẩm")
    private Long maSanPham;

    @NotNull(message = "Số lượng không được trống")
    @Positive(message ="Số lượng phải lớn hơn 0")
    private Integer soLuong;
}