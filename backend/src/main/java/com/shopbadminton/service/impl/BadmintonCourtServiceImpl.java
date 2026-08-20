package com.shopbadminton.service.impl;

import com.shopbadminton.dto.request.BadmintonCourtRequest;
import com.shopbadminton.dto.response.BadmintonCourtResponse;
import com.shopbadminton.entity.BadmintonCourt;
import com.shopbadminton.exception.BadRequestException;
import com.shopbadminton.exception.ResourceNotFoundException;
import com.shopbadminton.mapper.BadmintonCourtMapper;
import com.shopbadminton.repository.BadmintonCourtRepository;
import com.shopbadminton.service.BadmintonCourtService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BadmintonCourtServiceImpl implements BadmintonCourtService {

    private static final List<String> TRANG_THAI_HOP_LE = List.of("AVAILABLE", "MAINTENANCE");

    private final BadmintonCourtRepository badmintonCourtRepository;
    private final BadmintonCourtMapper badmintonCourtMapper;

    public BadmintonCourtServiceImpl(BadmintonCourtRepository badmintonCourtRepository,
                                      BadmintonCourtMapper badmintonCourtMapper) {
        this.badmintonCourtRepository = badmintonCourtRepository;
        this.badmintonCourtMapper = badmintonCourtMapper;
    }

    @Override
    public List<BadmintonCourtResponse> layTatCa() {
        return badmintonCourtRepository.findAll().stream()
                .map(badmintonCourtMapper::toResponse)
                .toList();
    }

    @Override
    public BadmintonCourtResponse layChiTiet(Integer id) {
        return badmintonCourtMapper.toResponse(timTheoId(id));
    }

    @Override
    public BadmintonCourtResponse taoMoi(BadmintonCourtRequest request) {
        BadmintonCourt san = BadmintonCourt.builder()
                .tenSan(request.getTenSan())
                .loaiSan(request.getLoaiSan())
                .giaTheoGio(request.getGiaTheoGio())
                .trangThai("AVAILABLE")
                .build();
        badmintonCourtRepository.save(san);
        return badmintonCourtMapper.toResponse(san);
    }

    @Override
    public BadmintonCourtResponse capNhat(Integer id, BadmintonCourtRequest request) {
        BadmintonCourt san = timTheoId(id);
        san.setTenSan(request.getTenSan());
        san.setLoaiSan(request.getLoaiSan());
        san.setGiaTheoGio(request.getGiaTheoGio());
        badmintonCourtRepository.save(san);
        return badmintonCourtMapper.toResponse(san);
    }

    @Override
    public BadmintonCourtResponse capNhatTrangThai(Integer id, String trangThai) {
        if (!TRANG_THAI_HOP_LE.contains(trangThai)) {
            throw new BadRequestException("Trạng thái không hợp lệ, chỉ nhận: AVAILABLE, MAINTENANCE");
        }
        BadmintonCourt san = timTheoId(id);
        san.setTrangThai(trangThai);
        badmintonCourtRepository.save(san);
        return badmintonCourtMapper.toResponse(san);
    }

    @Override
    public void xoa(Integer id) {
        if (!badmintonCourtRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy sân");
        }
        badmintonCourtRepository.deleteById(id);
    }

    private BadmintonCourt timTheoId(Integer id) {
        return badmintonCourtRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sân"));
    }
}