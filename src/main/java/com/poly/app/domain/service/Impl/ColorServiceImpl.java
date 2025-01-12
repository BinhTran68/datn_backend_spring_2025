package com.poly.app.domain.service.Impl;

import com.poly.app.domain.model.Color;
import com.poly.app.domain.repository.ColorRepository;
import com.poly.app.domain.request.color.ColorRequest;
import com.poly.app.domain.response.color.ColorResponse;
import com.poly.app.domain.service.ColorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
                .status(request.getStatus())
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
    public List<ColorResponse> getAllColor() {
        return colorRepository.findAll().stream()
                .map(color -> new ColorResponse(color.getId(), color.getCode(), color.getColorName(), color.getUpdatedAt(), color.getStatus())).toList();
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
}
