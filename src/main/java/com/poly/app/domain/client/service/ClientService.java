package com.poly.app.domain.client.service;

import com.poly.app.domain.admin.product.request.brand.BrandRequest;
import com.poly.app.domain.admin.product.response.brand.BrandResponse;
import com.poly.app.domain.admin.product.response.brand.BrandResponseSelect;
import com.poly.app.domain.client.response.ProductViewResponse;
import com.poly.app.domain.model.Brand;
import com.poly.app.infrastructure.constant.Status;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClientService {
    Page<ProductViewResponse> getAllProductHadPromotion(int page, int size);
    Page<ProductViewResponse> getAllProductHadSoleDesc(int page, int size);
    Page<ProductViewResponse> getAllProductHadCreatedAtDesc(int page, int size);
    Page<ProductViewResponse> getAllProductHadViewsDesc(int page, int size);

}
