package com.poly.app.domain.controller;

import com.poly.app.domain.model.Product;
import com.poly.app.domain.request.product.ProductRequest;
import com.poly.app.domain.response.product.ProductResponse;
import com.poly.app.domain.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class ProductController {
    ProductService productService;

    @PostMapping("/add")
    public Product create(@RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    @PostMapping("/update/{id}")
    public ProductResponse update(@RequestBody ProductRequest request, @PathVariable int id) {
        return productService.updateProduct(request,id);
    }

    @GetMapping()
    public List<ProductResponse> getAllProduct() {
        return productService.getAllProduct();
    }
    @GetMapping("{id}")
    public ProductResponse getProduct(@PathVariable int id) {
        return productService.getProduct(id);
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable int id) {
        return productService.deleteProduct(id);
    }


}
