package com.poly.app.domain.admin.bill.service;

import com.poly.app.domain.admin.bill.response.BillProductDetailResponse;
import com.poly.app.domain.admin.bill.response.BillProductResponse;
import com.poly.app.domain.model.Product;
import com.poly.app.domain.response.productdetail.ProductDetailResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BillProductDetailService {

    List<BillProductDetailResponse> getBillProductDetailResponse(String billCode);

    Page<BillProductResponse> getBillProductResponsePage(int page, int size);
}
