package com.shopbadminton.service.impl;

import com.shopbadminton.dto.request.CourtBookingRequest;
import com.shopbadminton.dto.response.CourtBookingResponse;
import com.shopbadminton.entity.BadmintonCourt;
import com.shopbadminton.entity.Customer;
import com.shopbadminton.entity.CourtBooking;
import com.shopbadminton.entity.User;
import com.shopbadminton.exception.BadRequestException;
import com.shopbadminton.exception.ResourceNotFoundException;
import com.shopbadminton.mapper.CourtBookingMapper;
import com.shopbadminton.repository.BadmintonCourtRepository;
import com.shopbadminton.repository.CourtBookingRepository;
import com.shopbadminton.repository.CustomerRepository;
import com.shopbadminton.repository.UserRepository;
import com.shopbadminton.service.CourtBookingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourtBookingServiceImpl implements CourtBookingService {

    private final CourtBookingRepository courtBookingRepository;
    private final BadmintonCourtRepository badmintonCourtRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final CourtBookingMapper courtBookingMapper;

    public CourtBookingServiceImpl(CourtBookingRepository courtBookingRepository,
                                    BadmintonCourtRepository badmintonCourtRepository,
                                    CustomerRepository customerRepository,
                                    UserRepository userRepository,
                                    CourtBookingMapper courtBookingMapper) {
        this.courtBookingRepository = courtBookingRepository;
        this.badmintonCourtRepository = badmintonCourtRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.courtBookingMapper = courtBookingMapper;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public CourtBookingResponse datSan(CourtBookingRequest request, String tenDangNhapKhachHang) {
        BadmintonCourt san = badmintonCourtRepository.findById(request.getMaSan())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sân"));

        if ("MAINTENANCE".equals(san.getTrangThai())) {
            throw new BadRequestException("Sân đang bảo trì, không thể đặt");
        }

        if (!request.getGioBatDau().isBefore(request.getGioKetThuc())) {
            throw new BadRequestException("Giờ bắt đầu phải trước giờ kết thúc");
        }

        List<CourtBooking> trungLich = courtBookingRepository.timBookingTrungGioCoKhoa(
                request.getMaSan(), request.getNgayDat(), request.getGioBatDau(), request.getGioKetThuc());

        if (!trungLich.isEmpty()) {
            throw new BadRequestException("Khung giờ này đã có người đặt, vui lòng đặt giờ khác");
        }

        User nguoiDung = userRepository.findByTenDangNhap(tenDangNhapKhachHang)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản"));

        Customer khachHang = customerRepository.findByNguoiDung_MaNguoiDung(nguoiDung.getMaNguoiDung())
                .orElseThrow(() -> new ResourceNotFoundException("Tài khoảng không phải khách hàng"));

        CourtBooking datSan = CourtBooking.builder()
                .san(san)
                .khachHang(khachHang)
                .ngayDat(request.getNgayDat())
                .gioBatDau(request.getGioBatDau())
                .gioKetThuc(request.getGioKetThuc())
                .trangThai("PENDING")
                .build();

        courtBookingRepository.save(datSan);
        return courtBookingMapper.toResponse(datSan);
    }

    @Override
    public List<CourtBookingResponse> layTheoKhachHang(String tenDangNhapKhachHang) {
        User nguoiDung = userRepository.findByTenDangNhap(tenDangNhapKhachHang)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản"));

        Customer khachHang = customerRepository.findByNguoiDung_MaNguoiDung(nguoiDung.getMaNguoiDung())
                .orElseThrow(() -> new ResourceNotFoundException("Tài khoản không phải khách hàng"));

        return courtBookingRepository.findByKhachHang_MaKhachHang(khachHang.getMaKhachHang()).stream()
                .map(courtBookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CourtBookingResponse layChiTiet(Long id) {
        CourtBooking datSan = courtBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy lịch đặt sân"));
        return courtBookingMapper.toResponse(datSan);
    }
    @Override
    public void huyDatSan(Long id, String tenDangNhapKhachHang) {
        CourtBooking datSan = courtBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy lịch đặt sân"));

        User nguoiDung = userRepository.findByTenDangNhap(tenDangNhapKhachHang)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy tài khoản"));

        if (!datSan.getKhachHang().getNguoiDung().getMaNguoiDung().equals(nguoiDung.getMaNguoiDung())) {
            throw new BadRequestException("Bạn không có quyền hủy lịch đặt này");
        }

        if ("CANCELLED".equals(datSan.getTrangThai())) {
            throw new BadRequestException("Lịch đặt đã bị hủy trước đó");
        }

        datSan.setTrangThai("CANCELLED");
        courtBookingRepository.save(datSan);
    }
    @Override
    public List<CourtBookingResponse> layLichTheoNgay(LocalDate ngayDat) {
        return courtBookingRepository.findByNgayDatOrderBySan_MaSanAscGioBatDauAsc(ngayDat).stream()
                .map(courtBookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourtBookingResponse> layLichTheoSanVaNgay(Integer maSan, LocalDate ngayDat) {
        return courtBookingRepository.findBySan_MaSanAndNgayDatOrderByGioBatDauAsc(maSan, ngayDat).stream()
                .map(courtBookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourtBookingResponse> layLichTheoTrangThai(LocalDate ngayDat, String trangThai) {
        return courtBookingRepository.findByNgayDatAndTrangThaiOrderByGioBatDauAsc(ngayDat, trangThai).stream()
                .map(courtBookingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CourtBookingResponse xacNhanDatSan(Long id) {
        CourtBooking datSan = courtBookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy lịch đặt sân"));

        if (!"PENDING".equals(datSan.getTrangThai())) {
            throw new BadRequestException("Chỉ có thể xác nhận lịch đặt ở trong trạng thái PENDING");
        }

        datSan.setTrangThai("CONFIRMED");
        courtBookingRepository.save(datSan);
        return courtBookingMapper.toResponse(datSan);
    }
}