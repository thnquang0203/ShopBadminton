package com.shopbadminton.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BillRequest {

    @NotNull(message = "Phải chọn khách hàng")
    private Long maKhachHang;

    @NotEmpty(message = "Hóa đơn phải có ít nhất 1 sản phẩm")
    private List<@Valid BillProductItemRequest> danhSachSanPham;
}