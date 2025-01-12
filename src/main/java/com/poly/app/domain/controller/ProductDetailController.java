package com.poly.app.domain.controller;

import com.poly.app.domain.common.PageReponse;
import com.poly.app.domain.model.ProductDetail;
import com.poly.app.domain.model.Type;
import com.poly.app.domain.repository.ProductDetailRepository;
import com.poly.app.domain.request.productdetail.ProductDetailRequest;
import com.poly.app.domain.request.type.TypeRequest;
import com.poly.app.domain.response.ApiResponse;
import com.poly.app.domain.response.productdetail.ProductDetailResponse;
import com.poly.app.domain.response.type.TypeResponse;
import com.poly.app.domain.service.ProductDetailService;
import com.poly.app.domain.service.ProductService;
import com.poly.app.domain.service.TypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/productdetail")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class ProductDetailController {
    ProductDetailService productDetailService;

    ProductDetailRepository productDetailRepository;

    @PostMapping("/add")
    public ApiResponse<ProductDetail> create(@RequestBody ProductDetailRequest request) {
        return ApiResponse.<ProductDetail>builder()
                .message("create productDetail")
                .data(productDetailService.createProductDetail(request))
                .build();
    }

    @PostMapping("/update/{id}")
    public ApiResponse<ProductDetailResponse> update(@RequestBody ProductDetailRequest request, @PathVariable int id) {
        return ApiResponse.<ProductDetailResponse>builder()
                .message("update productdetail")
                .data(productDetailService.updateProductDetail(request,id))
                .build();
    }


//teest
//    @GetMapping()
//    public List<ProductDetail> getAllType() {
//        return productDetailService.getAllProductDetail();
//    }
    @GetMapping()
    public ApiResponse<PageReponse<ProductDetailResponse>> getAllProductPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                @RequestParam(value = "size", defaultValue = "1") int Size) {
        return ApiResponse.<PageReponse<ProductDetailResponse>>builder()
                .message("list product detail page")
                .data(new PageReponse<ProductDetailResponse>(productDetailService.getAllProductDetailPage(page,Size)))
                .build();
    }
    @GetMapping("{id}")
    public ApiResponse<ProductDetailResponse> getProductDetail(@PathVariable int id) {
        return ApiResponse.<ProductDetailResponse>builder()
                .message("get product detail by id")
                .data(productDetailService.getProductDetail(id))
                .build();
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<String> delete(@PathVariable int id) {
        return ApiResponse.<String>builder()
                .message("delete product detail by id")
                .data(productDetailService.deleteProductDetail(id))
                .build();
    }


}
