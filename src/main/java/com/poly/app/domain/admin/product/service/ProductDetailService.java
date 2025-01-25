package com.poly.app.domain.admin.product.service;

import com.poly.app.domain.admin.product.response.color.ColorResponse;
import com.poly.app.domain.model.ProductDetail;
import com.poly.app.domain.admin.product.request.productdetail.FilterRequest;
import com.poly.app.domain.admin.product.request.productdetail.ProductDetailRequest;
import com.poly.app.domain.admin.product.response.productdetail.FilterProductDetailResponse;
import com.poly.app.domain.admin.product.response.productdetail.ProductDetailResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductDetailService {

     ProductDetail createProductDetail(ProductDetailRequest request);
     ProductDetailResponse updateProductDetail(ProductDetailRequest request, int id);

//     chua phan trang
     List<ProductDetail> getAllProductDetail();
     String deleteProductDetail (int id);
     ProductDetailResponse getProductDetail(int id);

     Page<ProductDetailResponse> getAllProductDetailPage(int page, int size);
     List<FilterProductDetailResponse> filterProductDetail(int page, int size, FilterRequest request);

     Integer getFillterElement (FilterRequest request);

     Page<ColorResponse> fillbyName(int page, int size, String name);

     boolean existsByColorName(String brandName);

     boolean existsByColorNameAndIdNot (String brandName, Integer id);

     List<ProductDetailResponse> createProductDetailList(List<ProductDetailRequest> request);

}
