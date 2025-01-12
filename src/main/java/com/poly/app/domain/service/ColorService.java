package com.poly.app.domain.service;

import com.poly.app.domain.model.Brand;
import com.poly.app.domain.model.Color;
import com.poly.app.domain.request.brand.BrandRequest;
import com.poly.app.domain.request.color.ColorRequest;
import com.poly.app.domain.response.brand.BrandResponse;
import com.poly.app.domain.response.color.ColorResponse;

import java.util.List;

public interface ColorService {
     List<ColorResponse> getAllColor();
     Color createColor(ColorRequest request);
     ColorResponse updateColor(ColorRequest request, int id);
     String deleteColor (int id);
     ColorResponse getColor(int id);
}
