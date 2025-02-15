package com.poly.app.domain.admin.promotion.service.impl;

import com.poly.app.domain.admin.promotion.request.PromotionRequest;
import com.poly.app.domain.admin.promotion.response.PromotionResponse;
import com.poly.app.domain.admin.promotion.service.PromotionService;
import com.poly.app.domain.model.ProductDetail;
import com.poly.app.domain.model.Promotion;
import com.poly.app.domain.model.PromotionDetail;
import com.poly.app.domain.repository.ProductDetailRepository;
import com.poly.app.domain.repository.PromotionDetailRepository;
import com.poly.app.domain.repository.PromotionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PromotionServiceImpl implements PromotionService {
    PromotionRepository promotionRepository;
    private final ProductDetailRepository productDetailRepository;
    private final PromotionDetailRepository promotionDetailRepository;

    @Override
    public List<PromotionResponse> getAllPromotion() {
        return promotionRepository.findAll().stream()
                .map(promotion -> new PromotionResponse(promotion.getId(), promotion.getPromotionCode(),
                        promotion.getPromotionName(), promotion.getPromotionType(), promotion.getDiscountValue(),promotion.getQuantity(),
                        promotion.getStartDate(), promotion.getEndDate(), promotion.getStatus())).toList();
    }

    @Override
    public Promotion createPromotion(PromotionRequest request) {
        Promotion promotion = Promotion.builder()
                .id(request.getId())
                .promotionCode(request.getPromotionCode())
                .promotionName(request.getPromotionName())
                .promotionType(request.getPromotionType())
                .discountValue(request.getDiscountValue())
                .quantity(request.getQuantity())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(request.getStatus())
                .build();
        Promotion promotionSave =  promotionRepository.save(promotion);

        for (Integer productId : request.getProductIds()) {
            ProductDetail productDetail = productDetailRepository.findById(productId).orElse(null);
            if(productDetail != null) {
                PromotionDetail promotionDetail = PromotionDetail.builder()
                        .productDetail(productDetail)
                        .promotion(promotionSave)
                        .build();
                promotionDetailRepository.save(promotionDetail);
            }
        }

        return promotion;
    }

    @Override
    public PromotionResponse updatePromotion(PromotionRequest request, int id) {
        Promotion promotion = promotionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
        promotion.setPromotionCode(request.getPromotionCode());
        promotion.setPromotionName(request.getPromotionName());
        promotion.setPromotionType(request.getPromotionType());
        promotion.setDiscountValue(request.getDiscountValue());
        promotion.setQuantity(request.getQuantity());
        promotion.setStartDate(request.getStartDate());
        promotion.setEndDate(request.getEndDate());
        promotion.setStatus(request.getStatus());
        promotionRepository.save(promotion);

        return PromotionResponse.builder()
                .id(promotion.getId())
                .promotionCode(promotion.getPromotionCode())
                .promotionName(promotion.getPromotionName())
                .promotionType(promotion.getPromotionType())
                .discountValue(promotion.getDiscountValue())
                .quantity(promotion.getQuantity())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .status(promotion.getStatus())
                .build();
    }

    @Override
    public String deletePromotion(int id) {
        if (!promotionRepository.findById(id).isEmpty()) {
            promotionRepository.deleteById(id);
            return "Xoa promotion thanh cong";
        } else {
            return "promotion khong ton tai";
        }
    }

    @Override
    public PromotionResponse getPromotionDetail(int id) {
        Promotion promotion = promotionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
        return PromotionResponse.builder()
                .id(promotion.getId())
                .promotionCode(promotion.getPromotionCode())
                .promotionName(promotion.getPromotionName())
                .promotionType(promotion.getPromotionType())
                .discountValue(promotion.getDiscountValue())
                .startDate(promotion.getStartDate())
                .endDate(promotion.getEndDate())
                .status(promotion.getStatus())
                .build();
    }

    public Page<PromotionResponse> getAllPromotion(Pageable pageable) {
        return promotionRepository.findAll(pageable).map(promotion ->
                PromotionResponse.builder()
                        .id(promotion.getId())
                        .promotionCode(promotion.getPromotionCode())
                        .promotionName(promotion.getPromotionName())
                        .promotionType(promotion.getPromotionType())
                        .discountValue(promotion.getDiscountValue())
                        .quantity(promotion.getQuantity())
                        .startDate(promotion.getStartDate())
                        .endDate(promotion.getEndDate())
                        .status(promotion.getStatus())
                        .build()
        );
    }
    @Override
    public List<PromotionResponse> searchPromotions(String promotionCode, String promotionName, String promotionType, String status) {
        return promotionRepository.searchPromotions(promotionCode, promotionName, promotionType, status);
    }


}
