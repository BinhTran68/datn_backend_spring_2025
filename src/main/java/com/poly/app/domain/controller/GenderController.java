package com.poly.app.domain.controller;

import com.poly.app.domain.model.Gender;
import com.poly.app.domain.request.gender.GenderRequest;
import com.poly.app.domain.response.gender.GenderResponse;
import com.poly.app.domain.service.GenderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/gender")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j

public class GenderController {
    GenderService genderService;

    @PostMapping("/add")
    public Gender create(@RequestBody GenderRequest request) {
        return genderService.createGender(request);
    }

    @PostMapping("/update/{id}")
    public GenderResponse update(@RequestBody GenderRequest request, @PathVariable int id) {
        return genderService.updateGender(request,id);
    }

    @GetMapping()
    public List<GenderResponse> getAllGender() {
        return genderService.getAllGender();
    }
    @GetMapping("{id}")
    public GenderResponse getGender(@PathVariable int id) {
        return genderService.getGender(id);
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable int id) {
        return genderService.deleteGender(id);
    }


}
