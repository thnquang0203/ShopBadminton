package com.shopbadminton.service;

import com.shopbadminton.dto.request.BadmintonCourtRequest;
import com.shopbadminton.dto.response.BadmintonCourtResponse;
import java.util.List;

public interface BadmintonCourtService {
    List<BadmintonCourtResponse> layTatCa();
    BadmintonCourtResponse layChiTiet(Integer id);
    BadmintonCourtResponse taoMoi(BadmintonCourtRequest request);
    BadmintonCourtResponse capNhat(Integer id, BadmintonCourtRequest request);
    BadmintonCourtResponse capNhatTrangThai(Integer id, String trangThai);
    void xoa(Integer id);
}