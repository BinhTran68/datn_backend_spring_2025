package com.poly.app.domain.admin.product.controller;

import com.poly.app.domain.model.Type;
import com.poly.app.domain.admin.product.request.type.TypeRequest;
import com.poly.app.domain.common.ApiResponse;
import com.poly.app.domain.admin.product.response.type.TypeResponse;
import com.poly.app.domain.admin.product.service.TypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/type")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class TypeController {
    TypeService typeService;

    @PostMapping("/add")
    public ApiResponse<Type> create(@RequestBody TypeRequest request) {
        return ApiResponse.<Type>builder()
                .message("create type")
                .data(typeService.createType(request))
                .build();
    }

    @PostMapping("/update/{id}")
    public ApiResponse<TypeResponse> update(@RequestBody TypeRequest request, @PathVariable int id) {
        return ApiResponse.<TypeResponse>builder()
                .message("update type")
                .data(typeService.updateType(request,id))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<TypeResponse>> getAllType() {
        return ApiResponse.<List<TypeResponse>>builder()
                .message("list type")
                .data(typeService.getAllType())
                .build();
    }
    @GetMapping("{id}")
    public ApiResponse<TypeResponse> getType(@PathVariable int id) {
        return ApiResponse.<TypeResponse>builder()
                .message("get type by id")
                .data(typeService.getType(id))
                .build();
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<String> delete(@PathVariable int id) {
        return ApiResponse.<String>builder()
                .message("delete type by id")
                .data(typeService.deleteType(id))
                .build();
    }


}
