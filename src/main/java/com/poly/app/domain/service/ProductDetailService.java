package com.poly.app.domain.service;

import com.poly.app.domain.model.ProductDetail;
import com.poly.app.domain.request.productdetail.ProductDetailRequest;
import com.poly.app.domain.response.productdetail.ProductDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductDetailService {

     ProductDetail createProductDetail(ProductDetailRequest request);
     ProductDetailResponse updateProductDetail(ProductDetailRequest request, int id);

//     chua phan trang
     List<ProductDetail> getAllProductDetail();
     String deleteProductDetail (int id);
     ProductDetailResponse getProductDetail(int id);

     Page<ProductDetailResponse> getAllProductDetailPage(int page, int size);
}
