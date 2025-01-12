package com.poly.app.domain.controller;

import com.poly.app.domain.model.Product;
import com.poly.app.domain.request.product.ProductRequest;
import com.poly.app.domain.response.ApiResponse;
import com.poly.app.domain.response.product.ProductResponse;
import com.poly.app.domain.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class ProductController {
    ProductService productService;

    @PostMapping("/add")
    public ApiResponse<Product> create(@RequestBody ProductRequest request) {
        return ApiResponse.<Product>builder()
                .message("create product")
                .data(productService.createProduct(request))
                .build();
    }

    @PostMapping("/update/{id}")
    public ApiResponse<ProductResponse> update(@RequestBody ProductRequest request, @PathVariable int id) {
        return ApiResponse.<ProductResponse>builder()
                .message("update product")
                .data(productService.updateProduct(request,id))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<ProductResponse>> getAllProduct() {
        return ApiResponse.<List<ProductResponse>>builder()
                .message("list product")
                .data(productService.getAllProduct())
                .build();
    }
    @GetMapping("{id}")
    public ApiResponse<ProductResponse> getProduct(@PathVariable int id) {
        return ApiResponse.<ProductResponse>builder()
                .message("get product by id")
                .data(productService.getProduct(id))
                .build();
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<String> delete(@PathVariable int id) {
        return ApiResponse.<String>builder()
                .message("delete by id")
                .data(productService.deleteProduct(id))
                .build();
    }


}
