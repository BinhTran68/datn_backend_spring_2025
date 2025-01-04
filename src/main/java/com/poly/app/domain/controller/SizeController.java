package com.poly.app.domain.controller;

import com.poly.app.domain.model.Size;
import com.poly.app.domain.request.size.SizeRequest;
import com.poly.app.domain.common.ApiResponse;
import com.poly.app.domain.response.size.SizeResponse;
import com.poly.app.domain.service.SizeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/size")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class SizeController {
    SizeService sizeService;

    @PostMapping("/add")
    public ApiResponse<Size> create(@RequestBody SizeRequest request) {
        return ApiResponse.<Size>builder()
                .message("create size")
                .data(sizeService.createSize(request))
                .build();
    }

    @PostMapping("/update/{id}")
    public ApiResponse<SizeResponse> update(@RequestBody SizeRequest request, @PathVariable int id) {
        return ApiResponse.<SizeResponse>builder()
                .message("update size")
                .data(sizeService.updateSize(request,id))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<SizeResponse>> getAllSize() {
        return ApiResponse.<List<SizeResponse>>builder()
                .message("list size")
                .data(sizeService.getAllSize())
                .build();
    }
    @GetMapping("{id}")
    public ApiResponse<SizeResponse> getSize(@PathVariable int id) {
        return ApiResponse.<SizeResponse>builder()
                .message("get size by id")
                .data(sizeService.getSize(id))
                .build();
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<String> delete(@PathVariable int id) {
        return ApiResponse.<String>builder()
                .message("delete size by id")
                .data(sizeService.deleteSize(id))
                .build();
    }


}
