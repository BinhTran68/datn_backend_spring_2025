package com.poly.app.domain.controller;

import com.poly.app.domain.model.Sole;
import com.poly.app.domain.request.sole.SoleRequest;
import com.poly.app.domain.common.ApiResponse;
import com.poly.app.domain.response.sole.SoleResponse;
import com.poly.app.domain.service.SoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/sole")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class SoleController {
    SoleService soleService;

    @PostMapping("/add")
    public ApiResponse<Sole> create(@RequestBody SoleRequest request) {
        return ApiResponse.<Sole>builder()
                .message("create sole")
                .data(soleService.createSole(request))
                .build();
    }

    @PostMapping("/update/{id}")
    public ApiResponse<SoleResponse> update(@RequestBody SoleRequest request, @PathVariable int id) {
        return ApiResponse.<SoleResponse>builder()
                .message("update sole")
                .data(soleService.updateSole(request, id))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<SoleResponse>> getAllSole() {
        return ApiResponse.<List<SoleResponse>>builder()
                .message("list sole")
                .data(soleService.getAllSole())
                .build();
    }

    @GetMapping("{id}")
    public ApiResponse<SoleResponse> getSole(@PathVariable int id) {
        return ApiResponse.<SoleResponse>builder()
                .message("get sole by id")
                .data(soleService.getSole(id))
                .build();

    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<String> delete(@PathVariable int id) {
        return ApiResponse.<String>builder()
                .message("delete sole by id")
                .data(soleService.deleteSole(id))
                .build();
    }


}
