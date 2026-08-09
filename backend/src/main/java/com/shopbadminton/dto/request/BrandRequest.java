package com.shopbadminton.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BrandRequest {

    @NotBlank(message = "Tên thương hiệu không được trống")
    private String tenThuongHieu;
}