package com.shopbadminton.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
public class BadmintonCourtRequest {
	
	@NotBlank(message= "Tên Sân không được trống")
	private String tenSan;
	
	private String loaiSan;
	
	@NotNull(message = "Giá theo giờ không được trống")
	@Positive(message = "Giá phải lớn hơn 0")
	private BigDecimal giaTheoGio;
}
