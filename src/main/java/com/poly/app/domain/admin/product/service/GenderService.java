package com.poly.app.domain.admin.product.service;

import com.poly.app.domain.model.Gender;
import com.poly.app.domain.admin.product.request.gender.GenderRequest;
import com.poly.app.domain.admin.product.response.gender.GenderResponse;

import java.util.List;

public interface GenderService {
     List<GenderResponse> getAllGender();
     Gender createGender(GenderRequest request);
     GenderResponse updateGender(GenderRequest request, int id);
     String deleteGender (int id);
     GenderResponse getGender(int id);
}
