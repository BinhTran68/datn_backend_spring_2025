package com.poly.app.domain.controller;

import com.poly.app.domain.model.Size;
import com.poly.app.domain.request.size.SizeRequest;
import com.poly.app.domain.response.size.SizeResponse;
import com.poly.app.domain.service.SizeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/size")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class SizeController {
    SizeService sizeService;

    @PostMapping("/add")
    public Size create(@RequestBody SizeRequest request) {
        return sizeService.createSize(request);
    }

    @PostMapping("/update/{id}")
    public SizeResponse update(@RequestBody SizeRequest request, @PathVariable int id) {
        return sizeService.updateSize(request,id);
    }

    @GetMapping()
    public List<SizeResponse> getAllSize() {
        return sizeService.getAllSize();
    }
    @GetMapping("{id}")
    public SizeResponse getSize(@PathVariable int id) {
        return sizeService.getSize(id);
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable int id) {
        return sizeService.deleteSize(id);
    }


}
