package com.poly.app.domain.controller;

import com.poly.app.domain.model.Material;
import com.poly.app.domain.request.material.MaterialRequest;
import com.poly.app.domain.response.ApiResponse;
import com.poly.app.domain.response.material.MaterialResponse;
import com.poly.app.domain.service.MaterialService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/material")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class MaterialController {
    MaterialService materialService;

    @PostMapping("/add")
    public ApiResponse<Material> create(@RequestBody MaterialRequest request) {
        return ApiResponse.<Material>builder()
                .message("create material")
                .data(materialService.createMaterial(request))
                .build();
    }

    @PostMapping("/update/{id}")
    public ApiResponse<MaterialResponse> update(@RequestBody MaterialRequest request, @PathVariable int id) {
        return ApiResponse.<MaterialResponse>builder()
                .message("update material")
                .data(materialService.updateMaterial(request,id))
                .build()  ;
    }

    @GetMapping()
    public ApiResponse<List<MaterialResponse>> getAllMaterial() {
        return ApiResponse.<List<MaterialResponse>>builder()
                .message("list material")
                .data(materialService.getAllMaterial())
                .build() ;
    }
    @GetMapping("{id}")
    public ApiResponse<MaterialResponse> getMaterial(@PathVariable int id) {
        return ApiResponse.<MaterialResponse>builder()
                .message("get material by id")
                .data(materialService.getMaterial(id))
                .build();
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<String> delete(@PathVariable int id) {
        return ApiResponse.<String>builder()
                .message("delete by id")
                .data(materialService.deleteMaterial(id))
                .build();
    }


}
