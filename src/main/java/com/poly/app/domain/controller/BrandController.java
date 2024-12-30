package com.poly.app.domain.controller;

import com.poly.app.domain.model.Brand;
import com.poly.app.domain.request.brand.BrandRequest;
import com.poly.app.domain.response.brand.BrandResponse;
import com.poly.app.domain.service.BrandService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class BrandController {
    BrandService brandService;

    @PostMapping("/add")
    public Brand create(@RequestBody BrandRequest request) {
        return brandService.createBrand(request);
    }

    @PostMapping("/update/{id}")
    public BrandResponse update(@RequestBody BrandRequest request, @PathVariable int id) {
        return brandService.updateBrand(request,id);
    }

    @GetMapping()
    public List<BrandResponse> getAllBrand() {
        return brandService.getAllBrand();
    }
    @GetMapping("{id}")
    public BrandResponse getBrand(@PathVariable int id) {
        return brandService.getBrand(id);
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable int id) {
        return brandService.delete(id);
    }


}
