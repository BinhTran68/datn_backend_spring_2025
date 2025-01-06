package com.poly.app.domain.admin.product.service;

import com.poly.app.domain.model.Brand;
import com.poly.app.domain.admin.product.request.brand.BrandRequest;
import com.poly.app.domain.admin.product.response.brand.BrandResponse;

import java.util.List;

public interface BrandService {

     Brand createBrand(BrandRequest request);
     BrandResponse updateBrand(BrandRequest request, int id);
     List<BrandResponse> getAllBrand();
     String delete (int id);
     BrandResponse getBrand(int id);
}
