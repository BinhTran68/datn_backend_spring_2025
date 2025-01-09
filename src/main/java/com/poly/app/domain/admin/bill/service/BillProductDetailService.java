package com.poly.app.domain.admin.bill.service;

import com.poly.app.domain.admin.bill.response.BillProductDetailResponse;
import com.poly.app.domain.response.productdetail.ProductDetailResponse;

import java.util.List;

public interface BillProductDetailService {

    List<BillProductDetailResponse> getBillProductDetailResponse(String billCode);

}
