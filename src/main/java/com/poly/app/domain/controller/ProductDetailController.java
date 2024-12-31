package com.poly.app.domain.controller;

import com.poly.app.domain.common.PageReponse;
import com.poly.app.domain.model.ProductDetail;
import com.poly.app.domain.model.Type;
import com.poly.app.domain.repository.ProductDetailRepository;
import com.poly.app.domain.request.productdetail.ProductDetailRequest;
import com.poly.app.domain.request.type.TypeRequest;
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
@RequestMapping("/productdetail")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class ProductDetailController {
    ProductDetailService productDetailService;

    ProductDetailRepository productDetailRepository;

    @PostMapping("/add")
    public ProductDetail create(@RequestBody ProductDetailRequest request) {
        return productDetailService.createProductDetail(request);
    }

    @PostMapping("/update/{id}")
    public ProductDetailResponse update(@RequestBody ProductDetailRequest request, @PathVariable int id) {
        return productDetailService.updateProductDetail(request,id);
    }


//teest
//    @GetMapping()
//    public List<ProductDetail> getAllType() {
//        return productDetailService.getAllProductDetail();
//    }
    @GetMapping()
    public PageReponse<ProductDetailResponse> getAllProductPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                                                @RequestParam(value = "size", defaultValue = "1") int Size) {
        return new PageReponse<ProductDetailResponse>(productDetailService.getAllProductDetailPage(page,Size));
    }
    @GetMapping("{id}")
    public ProductDetailResponse getProductDetail(@PathVariable int id) {
        return productDetailService.getProductDetail(id);
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable int id) {
        return productDetailService.deleteProductDetail(id);
    }


}
