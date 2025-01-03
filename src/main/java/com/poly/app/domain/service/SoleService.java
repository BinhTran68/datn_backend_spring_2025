package com.poly.app.domain.service;

import com.poly.app.domain.model.Sole;
import com.poly.app.domain.request.sole.SoleRequest;
import com.poly.app.domain.response.sole.SoleResponse;

import java.util.List;

public interface SoleService {
     List<SoleResponse> getAllSole();
     Sole createSole(SoleRequest request);
     SoleResponse updateSole(SoleRequest request, int id);
     String deleteSole (int id);
     SoleResponse getSole(int id);
}
