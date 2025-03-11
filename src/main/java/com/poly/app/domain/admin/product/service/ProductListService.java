package com.poly.app.domain.admin.product.service;

import com.poly.app.domain.admin.product.response.product.ProductResponse;
import com.poly.app.domain.admin.voucher.response.VoucherReponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductListService {
    List<ProductResponse> getAllIn();
    Page<ProductResponse> getAllIn (Pageable pageable);

}
