package com.shopbadminton.service;

import com.shopbadminton.dto.request.BrandRequest;
import com.shopbadminton.dto.response.BrandResponse;
import java.util.List;

public interface BrandService {
    List<BrandResponse> layTatCa();
    BrandResponse taoMoi(BrandRequest request);
    BrandResponse capNhat(Integer id, BrandRequest request);
    void xoa(Integer id);
}