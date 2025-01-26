package com.poly.app.domain.admin.product.service.Impl;

import com.poly.app.domain.admin.product.response.brand.BrandResponse;
import com.poly.app.domain.admin.product.response.brand.BrandResponseSelect;
import com.poly.app.domain.admin.product.response.color.ColorResponseSelect;
import com.poly.app.domain.model.Brand;
import com.poly.app.domain.model.Color;
import com.poly.app.domain.repository.ColorRepository;
import com.poly.app.domain.admin.product.request.color.ColorRequest;
import com.poly.app.domain.admin.product.response.color.ColorResponse;
import com.poly.app.domain.admin.product.service.ColorService;
import com.poly.app.infrastructure.constant.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class ColorServiceImpl implements ColorService {

    ColorRepository colorRepository;


    @Override
    public Color createColor(ColorRequest request) {
        Color color = Color.builder()
                .colorName(request.getColorName())
                .status(Status.HOAT_DONG)
                .build();
        return colorRepository.save(color);
    }

    @Override
    public ColorResponse updateColor(ColorRequest request, int id) {
        Color color = colorRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id ko tồn tại"));

        color.setColorName(request.getColorName());
        color.setStatus(request.getStatus());

        colorRepository.save(color);

        return ColorResponse.builder()
                .code(color.getCode())
                .id(color.getId())
                .colorName(color.getColorName())
                .updateAt(color.getCreatedAt())
                .status(color.getStatus())
                .build();
    }

    @Override
//    public List<ColorResponse> getAllColor() {
//        return colorRepository.findAll().stream()
//                .map(color -> new ColorResponse(color.getId(), color.getCode(), color.getColorName(), color.getUpdatedAt(), color.getStatus())).toList();
//    }
    public Page<ColorResponse> getAllColor(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ColorResponse> colorResponses = colorRepository.getAll(pageable);

        // Chuyển đổi từ Page<Brand> sang Page<BrandResponse>
        return colorResponses;

    }

    @Override
    public String deleteColor(int id) {
        if ( !colorRepository.findById(id).isEmpty()){
            colorRepository.deleteById(id);
            return "xóa thành công";
        }else{
            return "id ko tồn tại";
        }


    }

    @Override
    public ColorResponse getColor(int id) {
        Color color = colorRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id ko tồn tại"));

        return ColorResponse.builder()
                .code(color.getCode())
                .id(color.getId())
                .colorName(color.getColorName())
                .updateAt(color.getUpdatedAt())
                .status(color.getStatus())
                .build();
    }

    @Override
    public Page<ColorResponse> fillbyName(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);


        Page<ColorResponse> color = colorRepository.fillbyname(String.format("%%%s%%", name), pageable);
        log.info(name);

        return color;
    }

    @Override
    public boolean existsByColorName(String brandName) {
        return false;
    }

    @Override
    public boolean existsByColorNameAndIdNot(String brandName, Integer id) {
        return false;
    }

    @Override
    public List<ColorResponseSelect> getAll() {
        return colorRepository.dataSelect();
    }

}
