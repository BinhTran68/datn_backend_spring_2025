package com.poly.app.domain.controller;

import com.poly.app.domain.model.Gender;
import com.poly.app.domain.request.gender.GenderRequest;
import com.poly.app.domain.common.ApiResponse;
import com.poly.app.domain.response.gender.GenderResponse;
import com.poly.app.domain.service.GenderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/gender")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class GenderController {
    GenderService genderService;

    @PostMapping("/add")
    public ApiResponse<Gender> create(@RequestBody GenderRequest request) {
        return ApiResponse.<Gender>builder()
                .message("create gender")
                .data(genderService.createGender(request))
                .build();
    }

    @PostMapping("/update/{id}")
    public ApiResponse<GenderResponse> update(@RequestBody GenderRequest request, @PathVariable int id) {
        return ApiResponse.<GenderResponse>builder()
                .message("update gender")
                .data(genderService.updateGender(request,id))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<GenderResponse>> getAllGender() {
        return ApiResponse.<List<GenderResponse>>builder()
                .message("list gender")
                .data(genderService.getAllGender())
                .build();
    }
    @GetMapping("{id}")
    public ApiResponse<GenderResponse> getGender(@PathVariable int id) {
        return ApiResponse.<GenderResponse>builder()
                .message("get gender by id")
                .data(genderService.getGender(id))
                .build();
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<String> delete(@PathVariable int id) {
        return ApiResponse.<String>builder()
                .message("delete gender by id")
                .data(genderService.deleteGender(id))
                .build();
    }


}
