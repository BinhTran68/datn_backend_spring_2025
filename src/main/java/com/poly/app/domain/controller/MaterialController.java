package com.poly.app.domain.controller;

import com.poly.app.domain.model.Material;
import com.poly.app.domain.request.material.MaterialRequest;
import com.poly.app.domain.response.material.MaterialResponse;
import com.poly.app.domain.service.MaterialService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/material")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class MaterialController {
    MaterialService materialService;

    @PostMapping("/add")
    public Material create(@RequestBody MaterialRequest request) {
        return materialService.createMaterial(request);
    }

    @PostMapping("/update/{id}")
    public MaterialResponse update(@RequestBody MaterialRequest request, @PathVariable int id) {
        return materialService.updateMaterial(request,id);
    }

    @GetMapping()
    public List<MaterialResponse> getAllMaterial() {
        return materialService.getAllMaterial();
    }
    @GetMapping("{id}")
    public MaterialResponse getMaterial(@PathVariable int id) {
        return materialService.getMaterial(id);
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable int id) {
        return materialService.deleteMaterial(id);
    }


}
