package com.poly.app.domain.admin.product.controller;

import com.poly.app.domain.admin.product.response.product.IProductResponse;
import com.poly.app.domain.admin.product.response.product.ProductResponseSelect;
import com.poly.app.domain.admin.product.service.ProductListService;
import com.poly.app.domain.admin.voucher.response.VoucherReponse;
import com.poly.app.domain.common.Meta;
import com.poly.app.domain.model.Product;
import com.poly.app.domain.admin.product.request.product.ProductRequest;
import com.poly.app.domain.common.ApiResponse;
import com.poly.app.domain.admin.product.response.product.ProductResponse;
import com.poly.app.domain.admin.product.service.ProductService;
import com.poly.app.infrastructure.constant.Status;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin/product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class ProductController {
    ProductService productService;
    ProductListService productListService;

    @PostMapping("/add")
    public ApiResponse<Product> create(@RequestBody @Valid ProductRequest request) {
        return ApiResponse.<Product>builder()
                .message("craete product")
                .data(productService.createProduct(request))
                .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<ProductResponse> update(@RequestBody ProductRequest request, @PathVariable int id) {
        return ApiResponse.<ProductResponse>builder()
                .message("update product")
                .data(productService.updateProduct(request, id))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<IProductResponse>> getAllProduct(@RequestParam(value = "page", defaultValue = "1") int page,
                                                      @RequestParam(value = "size", defaultValue = "5") int product
    ) {

        Page<IProductResponse> page1 = productService.getAllProduct(page - 1, product);
        return ApiResponse.<List<IProductResponse>>builder()
                .message("list product")
                .data(page1.getContent())
                .meta(Meta.builder()
                        .totalElement(page1.getTotalElements())
                        .currentPage(page1.getNumber() + 1)
                        .totalPages(page1.getTotalPages())
                        .build())
                .build();
    }
    @GetMapping("{id}")
    public ApiResponse<ProductResponse> getProduct(@PathVariable int id) {
        return ApiResponse.<ProductResponse>builder()
                .message("get product by id")
                .data(productService.getProduct(id))
                .build();
    }

    @GetMapping("/search")
    public ApiResponse<List<IProductResponse>> getProduct(@RequestParam("name") String name,
                                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                                   @RequestParam(value = "size", defaultValue = "5") int product) {

        Page<IProductResponse> page1 = productService.fillbyProductName(page - 1, product, name);
        return ApiResponse.<List<IProductResponse>>builder()
                .message("get product by id")
                .data(page1.getContent())
                .meta(Meta.builder()
                        .totalElement(page1.getTotalElements())
                        .currentPage(page1.getNumber() + 1)
                        .totalPages(page1.getTotalPages())
                        .build())
                .build();
    }

    @DeleteMapping("{id}")
    public ApiResponse<String> delete(@PathVariable int id) {
        return ApiResponse.<String>builder()
                .message("delete by id")
                .data(productService.delete(id))
                .build();
    }

    @GetMapping("/existsbyproductname")
    public ApiResponse<Boolean> existsByProductName(@RequestParam String productName) {
        return ApiResponse.<Boolean>builder()
                .message("existsByProductName")
                .data(productService.existsByProductName(productName))
                .build();
    }

    @GetMapping("/existsbyproductnameandidnot")
    public ApiResponse<Boolean> existsByProductNameAndIdNot(@RequestParam String productName, @RequestParam Integer id) {
        return ApiResponse.<Boolean>builder()
                .message("existsByProductName")
                .data(productService.existsByProductNameAndIdNot(productName, id))
                .build();
    }
    @GetMapping("/getallselect")
    public ApiResponse<List<ProductResponseSelect>> getAllSelect() {
        return ApiResponse.<List<ProductResponseSelect>>builder()
                .message("get all selected")
                .data(productService.getAll())
                .build();
    }
    @GetMapping("/switchstatus")
    public ApiResponse<?> getAllSelect(@RequestParam("status") Status status,
                                       @RequestParam("id") int id
    ) {
        return ApiResponse.<String>builder()
                .message("get all selected")
                .data(productService.switchStatus(id,status))
                .build();
    }


    //

    @GetMapping("/hien")
    public ApiResponse<List<ProductResponse>> getAllVoucher() {
        return ApiResponse.<List<ProductResponse>>builder()
                .message("list voucher")
                .data(productListService.getAllIn())
                .build();
    }
    @GetMapping("/page")
    public ApiResponse<Page<ProductResponse>> phanTrang(@RequestParam(value = "page") Integer page,
                                                        @RequestParam(value = "size") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        Page<ProductResponse> list = productListService.getAllIn(pageable);
        return ApiResponse.<Page<ProductResponse>>builder()
                .message("list voucher")
                .data(list)
                .build();
    }

    //
}
