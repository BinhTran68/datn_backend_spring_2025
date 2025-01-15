package com.poly.app.domain.admin.product.service;

import com.poly.app.domain.model.Type;
import com.poly.app.domain.admin.product.request.type.TypeRequest;
import com.poly.app.domain.admin.product.response.type.TypeResponse;

import java.util.List;

public interface TypeService {
     List<TypeResponse> getAllType();
     Type createType(TypeRequest request);
     TypeResponse updateType(TypeRequest request, int id);
     String deleteType (int id);
     TypeResponse getType(int id);
}
