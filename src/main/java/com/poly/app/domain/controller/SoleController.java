package com.poly.app.domain.controller;

import com.poly.app.domain.model.Sole;
import com.poly.app.domain.request.sole.SoleRequest;
import com.poly.app.domain.response.sole.SoleResponse;
import com.poly.app.domain.service.SoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sole")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class SoleController {
    SoleService soleService;

    @PostMapping("/add")
    public Sole create(@RequestBody SoleRequest request) {
        return soleService.createSole(request);
    }

    @PostMapping("/update/{id}")
    public SoleResponse update(@RequestBody SoleRequest request, @PathVariable int id) {
        return soleService.updateSole(request,id);
    }

    @GetMapping()
    public List<SoleResponse> getAllSole() {
        return soleService.getAllSole();
    }
    @GetMapping("{id}")
    public SoleResponse getSole(@PathVariable int id) {
        return soleService.getSole(id);
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable int id) {
        return soleService.deleteSole(id);
    }


}
