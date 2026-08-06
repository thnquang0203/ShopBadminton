package com.shopbadminton.service;

import com.shopbadminton.dto.request.RegisterRequest;
import com.shopbadminton.dto.response.AuthResponse;
import com.shopbadminton.entity.Role;
import com.shopbadminton.entity.User;
import com.shopbadminton.exception.DuplicateResourceException;
import com.shopbadminton.repository.RoleRepository;
import com.shopbadminton.repository.UserRepository;
import com.shopbadminton.security.JwtTokenProvider;
import com.shopbadminton.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void dangKy_ThanhCong_TraVeToken() {
        RegisterRequest request = new RegisterRequest();
        request.setTenDangNhap("test1");
        request.setMatKhau("123456");
        request.setEmail("test1@gmail.com");
        request.setHoTen("Nguyen Van A");

        Role vaiTroCustomer = Role.builder().maVaiTro(3).tenVaiTro("CUSTOMER").build();

        when(userRepository.findByTenDangNhap("test1")).thenReturn(Optional.empty());
        when(roleRepository.findByTenVaiTro("CUSTOMER")).thenReturn(Optional.of(vaiTroCustomer));
        when(passwordEncoder.encode("123456")).thenReturn("hashed_password");
        when(jwtTokenProvider.taoToken("test1", "CUSTOMER")).thenReturn("fake-jwt-token");

        AuthResponse response = authService.dangKy(request);

        assertNotNull(response);
        assertEquals("fake-jwt-token", response.getToken());
        assertEquals("CUSTOMER", response.getVaiTro());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void dangKy_TrungUsername_NemException() {
        RegisterRequest request = new RegisterRequest();
        request.setTenDangNhap("test1");
        request.setMatKhau("123456");

        when(userRepository.findByTenDangNhap("test1"))
                .thenReturn(Optional.of(new User()));

        assertThrows(DuplicateResourceException.class, () -> authService.dangKy(request));
        verify(userRepository, never()).save(any(User.class));
    }
}