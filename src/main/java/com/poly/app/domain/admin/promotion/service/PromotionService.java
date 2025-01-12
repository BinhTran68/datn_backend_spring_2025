package com.poly.app.domain.admin.promotion.service;

import com.poly.app.domain.admin.promotion.request.PromotionRequest;
import com.poly.app.domain.admin.promotion.response.PromotionResponse;
import com.poly.app.domain.model.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PromotionService {
    List<PromotionResponse> getAllPromotion();
    Promotion createPromotion(PromotionRequest request);
    PromotionResponse updatePromotion(PromotionRequest request, int id);
    String deletePromotion(int id);
    PromotionResponse getPromotionDetail(int id);
    Page<PromotionResponse> getAllPromotion (Pageable pageable);
}
