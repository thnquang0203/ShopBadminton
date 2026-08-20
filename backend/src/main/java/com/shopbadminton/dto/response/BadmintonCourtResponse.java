package com.shopbadminton.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class BadmintonCourtResponse {
	private Integer maSan;
	private String tenSan;
	private String loaiSan;
	private BigDecimal giaTheoGio;
	private String trangThai;
}
