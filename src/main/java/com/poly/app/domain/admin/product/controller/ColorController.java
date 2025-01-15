package com.poly.app.domain.admin.product.controller;
import com.poly.app.domain.model.Color;
import com.poly.app.domain.admin.product.request.color.ColorRequest;
import com.poly.app.domain.common.ApiResponse;
import com.poly.app.domain.admin.product.response.color.ColorResponse;
import com.poly.app.domain.admin.product.service.ColorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/color")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class ColorController {
    ColorService colorService;

    @PostMapping("/add")
    public ApiResponse<Color> create(@RequestBody ColorRequest request) {
        return ApiResponse.<Color>builder()
                .message("create color")
                .data(colorService.createColor(request))
                .build();
    }

    @PostMapping("/update/{id}")
    public ApiResponse<ColorResponse> update(@RequestBody ColorRequest request, @PathVariable int id) {
        return ApiResponse.<ColorResponse>builder()
                .message("update color")
                .data(colorService.updateColor(request,id))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<ColorResponse>> getAllColor() {
        return ApiResponse.<List<ColorResponse>>builder()
                .message("list color")
                .data(colorService.getAllColor())
                .build();
    }
    @GetMapping("{id}")
    public ApiResponse<ColorResponse> getColor(@PathVariable int id) {
        return ApiResponse.<ColorResponse>builder()
                .message("get color by id")
                .data(colorService.getColor(id))
                .build();
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<String> delete(@PathVariable int id) {
        return ApiResponse.<String>builder()
                .message("delete color by id")
                .data(colorService.deleteColor(id))
                .build();
    }


}
