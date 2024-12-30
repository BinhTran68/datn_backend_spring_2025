package com.poly.app.domain.controller;

import com.poly.app.domain.model.Color;
import com.poly.app.domain.request.color.ColorRequest;
import com.poly.app.domain.response.color.ColorResponse;
import com.poly.app.domain.service.ColorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/color")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class ColorController {
    ColorService colorService;

    @PostMapping("/add")
    public Color create(@RequestBody ColorRequest request) {
        return colorService.createColor(request);
    }

    @PostMapping("/update/{id}")
    public ColorResponse update(@RequestBody ColorRequest request, @PathVariable int id) {
        return colorService.updateColor(request,id);
    }

    @GetMapping()
    public List<ColorResponse> getAllColor() {
        return colorService.getAllColor();
    }
    @GetMapping("{id}")
    public ColorResponse getColor(@PathVariable int id) {
        return colorService.getColor(id);
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable int id) {
        return colorService.deleteColor(id);
    }


}
