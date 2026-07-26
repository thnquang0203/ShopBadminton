package com.shopbadminton.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long maNguoiDung;
	
	@Column(name = "username", nullable = false, unique = true, length = 50)
	private String tenDangNhap;
	
	@Column(name = "password", nullable = false)
	private String matKhau;
	
	@Column(name = "email", unique = true, length = 100)
	private String email;
	
	@Column(name = "phone", length = 20)
	private String soDienThoai;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	private Role vaiTro;
	
	@Column(name = "is_active")
	@Builder.Default
	private Boolean dangHoatDong = true;
	
	@Column(name = "created_at", updatable = false)
	private LocalDateTime ngayTao;
	
	@PrePersist
	protected void truocKhiLuu() {
		this.ngayTao = LocalDateTime.now();
	}
}
