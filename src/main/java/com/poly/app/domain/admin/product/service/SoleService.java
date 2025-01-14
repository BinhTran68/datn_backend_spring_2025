package com.poly.app.domain.admin.product.service;

import com.poly.app.domain.model.Sole;
import com.poly.app.domain.admin.product.request.sole.SoleRequest;
import com.poly.app.domain.admin.product.response.sole.SoleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SoleService {

     Sole createSole(SoleRequest request);

     SoleResponse updateSole(SoleRequest request, int id);

     Page<SoleResponse> getAllSole(int page, int sole);

     Page<SoleResponse> fillbySoleName(int page, int sole, String name);

     String delete(int id);

     SoleResponse getSole(int id);

     boolean existsBySoleName(String soleName);

     boolean existsBySoleNameAndIdNot (String soleName, Integer id);
}
