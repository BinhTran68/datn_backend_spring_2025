package com.poly.app.domain.controller;

import com.poly.app.domain.model.Staff;
import com.poly.app.domain.request.staff.StaffRequest;
import com.poly.app.domain.common.ApiResponse;
import com.poly.app.domain.response.staff.StaffResponse;
import com.poly.app.domain.service.StaffService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/staff")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class StaffController {
    StaffService staffService;

    @PostMapping("/add")
    public ApiResponse<Staff> create(@RequestBody StaffRequest request) {
        return ApiResponse.<Staff>builder()
                .message("create staff")
                .data(staffService.createStaff(request))
                .build();
    }

    @PostMapping("/update/{id}")
    public ApiResponse<StaffResponse> update(@RequestBody StaffRequest request, @PathVariable int id) {
        return ApiResponse.<StaffResponse>builder()
                .message("update staff")
                .data(staffService.updateStaff(request, id))
                .build();
    }

    @GetMapping()
    public ApiResponse<List<StaffResponse>> getAllStaff() {
        return ApiResponse.<List<StaffResponse>>builder()
                .message("list staff")
                .data(staffService.getAllStaff())
                .build();
    }

    @GetMapping("{id}")
    public ApiResponse<StaffResponse> getStaff(@PathVariable int id) {
        return ApiResponse.<StaffResponse>builder()
                .message("get staff by id")
                .data(staffService.getStaff(id))
                .build();
    }

    @DeleteMapping("delete/{id}")
    public ApiResponse<String> delete(@PathVariable int id) {
        return ApiResponse.<String>builder()
                .message("delete by id")
                .data(staffService.deleteStaff(id))
                .build();
    }
}
