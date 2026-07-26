package com.shopbadminton.service;

import com.shopbadminton.dto.request.LoginRequest;
import com.shopbadminton.dto.request.RegisterRequest;
import com.shopbadminton.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse dangKy(RegisterRequest request);
    AuthResponse dangNhap(LoginRequest request);
}