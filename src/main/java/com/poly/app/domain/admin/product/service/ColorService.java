package com.poly.app.domain.admin.product.service;

import com.poly.app.domain.model.Color;
import com.poly.app.domain.admin.product.request.color.ColorRequest;
import com.poly.app.domain.admin.product.response.color.ColorResponse;

import java.util.List;

public interface ColorService {
     List<ColorResponse> getAllColor();
     Color createColor(ColorRequest request);
     ColorResponse updateColor(ColorRequest request, int id);
     String deleteColor (int id);
     ColorResponse getColor(int id);
}
