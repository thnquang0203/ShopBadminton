package com.shopbadminton.service.impl;

import com.shopbadminton.dto.request.LoginRequest;
import com.shopbadminton.dto.request.RegisterRequest;
import com.shopbadminton.dto.response.AuthResponse;
import com.shopbadminton.entity.Role;
import com.shopbadminton.entity.User;
import com.shopbadminton.exception.BadRequestException;
import com.shopbadminton.exception.DuplicateResourceException;
import com.shopbadminton.repository.RoleRepository;
import com.shopbadminton.repository.UserRepository;
import com.shopbadminton.security.JwtTokenProvider;
import com.shopbadminton.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                            PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,
                            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse dangKy(RegisterRequest request) {
        if (userRepository.findByTenDangNhap(request.getTenDangNhap()).isPresent()) {
        	throw new DuplicateResourceException("Ten dang nhap da ton tai");
        }

        Role vaiTroKhachHang = roleRepository.findByTenVaiTro("CUSTOMER")
                .orElseThrow(() -> new BadRequestException("Khong tim thay vai tro CUSTOMER"));

        User nguoiDungMoi = User.builder()
                .tenDangNhap(request.getTenDangNhap())
                .matKhau(passwordEncoder.encode(request.getMatKhau()))
                .email(request.getEmail())
                .soDienThoai(request.getSoDienThoai())
                .vaiTro(vaiTroKhachHang)
                .build();

        userRepository.save(nguoiDungMoi);

        String token = jwtTokenProvider.taoToken(nguoiDungMoi.getTenDangNhap(), vaiTroKhachHang.getTenVaiTro());

        return AuthResponse.builder()
                .token(token)
                .tenDangNhap(nguoiDungMoi.getTenDangNhap())
                .vaiTro(vaiTroKhachHang.getTenVaiTro())
                .build();
    }

    @Override
    public AuthResponse dangNhap(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getTenDangNhap(), request.getMatKhau())
        );

        User nguoiDung = userRepository.findByTenDangNhap(request.getTenDangNhap())
                .orElseThrow(() -> new BadRequestException("Tai khoan khong ton tai"));

        String token = jwtTokenProvider.taoToken(nguoiDung.getTenDangNhap(), nguoiDung.getVaiTro().getTenVaiTro());

        return AuthResponse.builder()
                .token(token)
                .tenDangNhap(nguoiDung.getTenDangNhap())
                .vaiTro(nguoiDung.getVaiTro().getTenVaiTro())
                .build();
    }
}