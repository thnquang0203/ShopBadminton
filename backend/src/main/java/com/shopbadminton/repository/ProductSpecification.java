package com.shopbadminton.repository;

import com.shopbadminton.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {

    public static Specification<Product> dangHoatDong() {
        return (root, query, cb) -> cb.isTrue(root.get("dangHoatDong"));
    }

    public static Specification<Product> theoTenSanPham(String tuKhoa) {
        return (root, query, cb) -> {
            if (tuKhoa == null || tuKhoa.isBlank()) return cb.conjunction();
            return cb.like(cb.lower(root.get("tenSanPham")), "%" + tuKhoa.toLowerCase() + "%");
        };
    }

    public static Specification<Product> theoDanhMuc(Integer maDanhMuc) {
        return (root, query, cb) -> {
            if (maDanhMuc == null) return cb.conjunction();
            return cb.equal(root.get("danhMuc").get("maDanhMuc"), maDanhMuc);
        };
    }

    public static Specification<Product> theoThuongHieu(Integer maThuongHieu) {
        return (root, query, cb) -> {
            if (maThuongHieu == null) return cb.conjunction();
            return cb.equal(root.get("thuongHieu").get("maThuongHieu"), maThuongHieu);
        };
    }

    public static Specification<Product> theoKhoangGia(BigDecimal giaTu, BigDecimal giaDen) {
        return (root, query, cb) -> {
            if (giaTu == null && giaDen == null) return cb.conjunction();
            if (giaTu != null && giaDen != null) return cb.between(root.get("gia"), giaTu, giaDen);
            if (giaTu != null) return cb.greaterThanOrEqualTo(root.get("gia"), giaTu);
            return cb.lessThanOrEqualTo(root.get("gia"), giaDen);
        };
    }
}