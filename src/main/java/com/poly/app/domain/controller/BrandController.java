package com.poly.app.domain.controller;

import com.poly.app.domain.model.Brand;
import com.poly.app.domain.request.brand.BrandRequest;
import com.poly.app.domain.response.ApiResponse;
import com.poly.app.domain.response.brand.BrandResponse;
import com.poly.app.domain.service.BrandService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/brand")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class BrandController {
    BrandService brandService;

    @PostMapping("/add")
    public ApiResponse<Brand> create(@RequestBody BrandRequest request) {
        return ApiResponse.<Brand>builder()
                .message("craete brand")
                .data(brandService.createBrand(request))
                .build();
    }

    @PostMapping("/update/{id}")
    public ApiResponse<BrandResponse> update(@RequestBody BrandRequest request, @PathVariable int id) {
        return ApiResponse.<BrandResponse>builder()
                .message("update brand")
                .data(brandService.updateBrand(request,id))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<BrandResponse>> getAllBrand() {
        return ApiResponse.<List<BrandResponse>>builder()
                .message("list brand")
                .data(brandService.getAllBrand())
                .build();
    }
    @GetMapping("{id}")
    public ApiResponse<BrandResponse> getBrand(@PathVariable int id) {
        return ApiResponse.<BrandResponse>builder()
                .message("get brand by id")
                .data( brandService.getBrand(id))
                .build();
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<String> delete(@PathVariable int id) {
        return ApiResponse.<String>builder()
                .message("delete by id")
                .data(brandService.delete(id))
                .build();
    }


}
