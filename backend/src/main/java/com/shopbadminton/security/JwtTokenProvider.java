package com.shopbadminton.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String matBiMat;

    @Value("${jwt.expiration}")
    private long thoiGianHetHan;

    private SecretKey layKhoaKy() {
        return Keys.hmacShaKeyFor(matBiMat.getBytes());
    }

    public String taoToken(String tenDangNhap, String vaiTro) {
        Date ngayTao = new Date();
        Date ngayHetHan = new Date(ngayTao.getTime() + thoiGianHetHan);

        return Jwts.builder()
                .subject(tenDangNhap)
                .claim("vaiTro", vaiTro)
                .issuedAt(ngayTao)
                .expiration(ngayHetHan)
                .signWith(layKhoaKy())
                .compact();
    }

    public String layTenDangNhapTuToken(String token) {
        return Jwts.parser()
                .verifyWith(layKhoaKy())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public boolean kiemTraToken(String token) {
        try {
            Jwts.parser().verifyWith(layKhoaKy()).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}