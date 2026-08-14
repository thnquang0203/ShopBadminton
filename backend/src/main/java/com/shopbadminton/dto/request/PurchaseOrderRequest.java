package com.shopbadminton.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PurchaseOrderRequest {

    @NotNull(message = "Phải chọn nhà cung cấp")
    private Integer maNhaCungCap;

    @NotEmpty(message = "Phiếu nhập phải có ít nhất 1 sản phẩm")
    private List<@Valid PurchaseDetailRequest> chiTiet;
}