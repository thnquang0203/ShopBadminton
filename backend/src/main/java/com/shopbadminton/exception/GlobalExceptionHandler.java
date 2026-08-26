package com.shopbadminton.exception;

import com.shopbadminton.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> xuLyBadRequest(BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .maLoi(HttpStatus.BAD_REQUEST.value())
                        .thongBao(ex.getMessage())
                        .thoiGian(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> xuLySaiMatKhau(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ErrorResponse.builder()
                        .maLoi(HttpStatus.UNAUTHORIZED.value())
                        .thongBao("Sai ten dang nhap hoac mat khau")
                        .thoiGian(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> xuLyKhongCoQuyen(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ErrorResponse.builder()
                        .maLoi(HttpStatus.FORBIDDEN.value())
                        .thongBao("Ban khong co quyen truy cap chuc nang nay")
                        .thoiGian(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> xuLyValidation(MethodArgumentNotValidException ex) {
        String thongBaoLoi = ex.getBindingResult().getFieldErrors().stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .maLoi(HttpStatus.BAD_REQUEST.value())
                        .thongBao(thongBaoLoi)
                        .thoiGian(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> xuLyLoiChung(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ErrorResponse.builder()
                        .maLoi(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .thongBao("Loi he thong: " + ex.getMessage())
                        .thoiGian(LocalDateTime.now())
                        .build()
        );
    }
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> xuLyTrungDuLieu(DuplicateResourceException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ErrorResponse.builder()
                        .maLoi(HttpStatus.CONFLICT.value())
                        .thongBao(ex.getMessage())
                        .thoiGian(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> xuLyKhongTimThay(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponse.builder()
                        .maLoi(HttpStatus.NOT_FOUND.value())
                        .thongBao(ex.getMessage())
                        .thoiGian(LocalDateTime.now())
                        .build()
        );
    }
    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<ErrorResponse> xuLyLoiUploadFile(FileUploadException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ErrorResponse.builder()
                        .maLoi(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .thongBao(ex.getMessage())
                        .thoiGian(LocalDateTime.now())
                        .build()
        );
    }
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> xuLyViPhamRangBuocDuLieu(org.springframework.dao.DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ErrorResponse.builder()
                        .maLoi(HttpStatus.CONFLICT.value())
                        .thongBao("Dữ liệu bị trùng lặp hoặc vi phạm ràng buộc, vui lòng thử lại")
                        .thoiGian(LocalDateTime.now())
                        .build()
        );
    }
}