package com.poly.app.domain.service;

import com.poly.app.domain.model.Material;
import com.poly.app.domain.request.material.MaterialRequest;
import com.poly.app.domain.response.material.MaterialResponse;

import java.util.List;

public interface MaterialService {
     List<MaterialResponse> getAllMaterial();
     Material createMaterial(MaterialRequest request);
     MaterialResponse updateMaterial(MaterialRequest request, int id);
     String deleteMaterial (int id);
     MaterialResponse getMaterial(int id);
}
