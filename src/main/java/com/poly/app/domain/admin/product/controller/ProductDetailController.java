package com.poly.app.domain.admin.product.controller;

import com.poly.app.domain.common.Meta;
import com.poly.app.domain.model.ProductDetail;
import com.poly.app.domain.repository.ProductDetailRepository;
import com.poly.app.domain.repository.ProductDetailRepositoryDTO;
import com.poly.app.domain.admin.product.request.productdetail.FilterRequest;
import com.poly.app.domain.admin.product.request.productdetail.ProductDetailRequest;
import com.poly.app.domain.common.ApiResponse;
import com.poly.app.domain.admin.product.response.productdetail.FilterProductDetailResponse;
import com.poly.app.domain.admin.product.response.productdetail.ProductDetailResponse;
import com.poly.app.domain.admin.product.service.ProductDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

    ProductDetailRepositoryDTO productDetailRepositoryDTO;

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
                .data(productDetailService.updateProductDetail(request, id))
                .build();
    }


    //teest
//    @GetMapping()
//    public List<ProductDetail> getAllType() {
//        return productDetailService.getAllProductDetail();
//    }
    @GetMapping()
    public ApiResponse<List<ProductDetailResponse>> getAllProductPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                      @RequestParam(value = "size", defaultValue = "1") int size) {

        Page<ProductDetailResponse> page1 = productDetailService.getAllProductDetailPage(page-1, size);
        return ApiResponse.<List<ProductDetailResponse>>builder()
                .message("list product detail page")
                .data(page1.getContent())
                .meta(Meta.builder()
                        .currentPage(page1.getNumber())
                        .totalPages(page1.getTotalPages())
                        .totalElement(page1.getTotalElements())
                        .build())
                .build();
    }

    @GetMapping("/filter")
    public ApiResponse<List<FilterProductDetailResponse>> getFilterPD(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                      @RequestParam(value = "size", defaultValue = "1") int size,
                                                                      @RequestBody FilterRequest request
    ) {

        Integer totalElement = productDetailService.getFillterElement(request);

        return ApiResponse.<List<FilterProductDetailResponse>>builder()
                .message("fillter")
                .data(productDetailService.filterProductDetail(page, size, request))
                .meta(Meta.builder()
                        .currentPage(page)
                        .totalPages((int) Math.ceil(Double.valueOf(totalElement) / size))
                        .totalElement((long) totalElement)
                        .build())
                .build();

    }

//    @GetMapping("/test")
//    public ApiResponse<List<ProductDetailRepositoryDTO>> test(@RequestParam(value = "page", defaultValue = "1") int page,
//                               @RequestParam(value = "size", defaultValue = "1") int size,
//                               @RequestBody FilterRequest request) {
//
//
//
//        List<ProductDetailRepositoryDTO> list = productDetailRepositoryDTO.getFilterProductDetail(request.getProductName(),
//                request.getBrandName(),
//                request.getTypeName(),
//                request.getColorName(),
//                request.getMaterialName(),
//                request.getSizeName(), request.getSoleName(), request.getGenderName(), request.getStatus(), request.getSortByQuantity(), request.getSortByPrice(), page,size);
//        return ApiResponse.<List<ProductDetailRepositoryDTO>>builder()
//                .data(list)
//                .build();
//    }

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
