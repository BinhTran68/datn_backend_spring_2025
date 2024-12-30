package com.poly.app.domain.controller;

import com.poly.app.domain.model.Type;
import com.poly.app.domain.request.type.TypeRequest;
import com.poly.app.domain.response.type.TypeResponse;
import com.poly.app.domain.service.TypeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class TypeController {
    TypeService typeService;

    @PostMapping("/add")
    public Type create(@RequestBody TypeRequest request) {
        return typeService.createType(request);
    }

    @PostMapping("/update/{id}")
    public TypeResponse update(@RequestBody TypeRequest request, @PathVariable int id) {
        return typeService.updateType(request,id);
    }

    @GetMapping()
    public List<TypeResponse> getAllType() {
        return typeService.getAllType();
    }
    @GetMapping("{id}")
    public TypeResponse getType(@PathVariable int id) {
        return typeService.getType(id);
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable int id) {
        return typeService.deleteType(id);
    }


}
