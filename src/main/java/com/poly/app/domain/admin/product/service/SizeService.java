package com.poly.app.domain.admin.product.service;

import com.poly.app.domain.model.Size;
import com.poly.app.domain.admin.product.request.size.SizeRequest;
import com.poly.app.domain.admin.product.response.size.SizeResponse;

import java.util.List;

public interface SizeService {
     List<SizeResponse> getAllSize();
     Size createSize(SizeRequest request);
     SizeResponse updateSize(SizeRequest request, int id);
     String deleteSize (int id);
     SizeResponse getSize(int id);
}
