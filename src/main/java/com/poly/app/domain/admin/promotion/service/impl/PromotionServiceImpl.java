package com.poly.app.domain.admin.promotion.service.impl;

import com.poly.app.domain.admin.promotion.request.PromotionRequest;
import com.poly.app.domain.admin.promotion.response.PromotionResponse;
import com.poly.app.domain.admin.promotion.service.PromotionService;
import com.poly.app.domain.model.*;
import com.poly.app.domain.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PromotionServiceImpl implements PromotionService {
    @Autowired
    PromotionRepository promotionRepository;
    @Autowired
    ProductDetailRepository productDetailRepository;
//    @Autowired
//    PromotionDetailRepository promotionDetailRepository;
    @Autowired
    ProductPromotionRepository productPromotionRepository;
    @Autowired
    ProductRepository productRepository;

    public StatusEnum checkPromotionStatus(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime currentDate = LocalDateTime.now(); // Lấy thời gian hiện tại

        if (currentDate.isBefore(startDate)) {
            return StatusEnum.chua_kich_hoat; // Chưa kích hoạt
        } else if (currentDate.isAfter(endDate)) {
            return StatusEnum.ngung_kich_hoat; // Đã ngừng kích hoạt
        } else {
            return StatusEnum.dang_kich_hoat; // Đang kích hoạt
        }
    }

    @Transactional
    @Override
    public Promotion createPromotion(PromotionRequest request) {
        StatusEnum saStatusPromotion = checkPromotionStatus(request.getStartDate(), request.getEndDate());
        String generatedPromotionCode = "DGG" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        Promotion promotion = Promotion.builder()
                .promotionCode(generatedPromotionCode)
                .promotionName(request.getPromotionName())
                .promotionType(request.getPromotionType())
                .discountValue(request.getDiscountValue())
                .discountType(request.getDiscountType())
                .quantity(request.getQuantity())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .statusPromotion(saStatusPromotion)
                .build();

        Promotion promotionSaved = promotionRepository.save(promotion);

        // Lấy danh sách Product cùng lúc
        if (request.getProductIds() != null && !request.getProductIds().isEmpty()) {
            List<Product> products = productRepository.findAllById(request.getProductIds());
            List<ProductPromotion> productPromotions = products.stream()
                    .map(product -> ProductPromotion.builder()
                            .product(product)
                            .promotion(promotionSaved)
                            .quantity(request.getQuantity())
                            .build())
                    .collect(Collectors.toList());
            productPromotionRepository.saveAll(productPromotions);
        }

        // Lấy danh sách ProductDetail cùng lúc
        if (request.getProductDetailIds() != null && !request.getProductDetailIds().isEmpty()) {
            List<ProductDetail> productDetails = productDetailRepository.findAllById(request.getProductDetailIds());
            List<ProductPromotion> productDetailPromotions = productDetails.stream()
                    .map(productDetail -> ProductPromotion.builder()
                            .productDetail(productDetail)
                            .promotion(promotionSaved)
                            .quantity(request.getQuantity())
                            .build())
                    .collect(Collectors.toList());
            productPromotionRepository.saveAll(productDetailPromotions);
        }

        return promotionSaved;
    }



    @Override
    public List<PromotionResponse> getAllPromotion() {
        return promotionRepository.findAll().stream()
                .map(PromotionResponse::fromEntity)
                .toList();
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
                        .statusPromotion(promotion.getStatusPromotion())
                        .build()
        );
    }


    @Transactional
    @Override
    public PromotionResponse updatePromotion(PromotionRequest request, int id) {
        // Lấy thông tin chương trình khuyến mãi từ database
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Promotion not found"));

        // Cập nhật trạng thái chương trình khuyến mãi
        promotion.setStatusPromotion(checkPromotionStatus(request.getStartDate(), request.getEndDate()));

        // Cập nhật thông tin chương trình khuyến mãi
        promotion.setPromotionName(request.getPromotionName());
        promotion.setPromotionType(request.getPromotionType());
        promotion.setDiscountValue(request.getDiscountValue());
        promotion.setDiscountType(request.getDiscountType());
        promotion.setQuantity(request.getQuantity());
        promotion.setStartDate(request.getStartDate());
        promotion.setEndDate(request.getEndDate());

        // Xóa quan hệ cũ giữa promotion và products
        productPromotionRepository.deleteAllByPromotionId(id);

        // Cập nhật danh sách sản phẩm nếu có
        if (request.getProductIds() != null && !request.getProductIds().isEmpty()) {
            List<ProductPromotion> productPromotions = productRepository.findAllById(request.getProductIds()).stream()
                    .map(product -> ProductPromotion.builder()
                            .product(product)
                            .promotion(promotion)
                            .quantity(request.getQuantity())
                            .build())
                    .collect(Collectors.toList());
            productPromotionRepository.saveAll(productPromotions);
        }

        // Cập nhật danh sách chi tiết sản phẩm nếu có
        if (request.getProductDetailIds() != null && !request.getProductDetailIds().isEmpty()) {
            List<ProductPromotion> productDetailPromotions = productDetailRepository.findAllById(request.getProductDetailIds()).stream()
                    .map(productDetail -> ProductPromotion.builder()
                            .productDetail(productDetail)
                            .promotion(promotion)
                            .quantity(request.getQuantity())
                            .build())
                    .collect(Collectors.toList());
            productPromotionRepository.saveAll(productDetailPromotions);
        }

        // Lưu cập nhật vào cơ sở dữ liệu
        return PromotionResponse.fromEntity(promotionRepository.save(promotion));
    }




    @Transactional
    @Override
    public String deletePromotion(int id) {
        // Kiểm tra sự tồn tại của Promotion
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Promotion với ID: " + id));

        // Xóa tất cả ProductPromotion liên quan
        productPromotionRepository.deleteAllByPromotionId(id);

        // Xóa Promotion
        promotionRepository.delete(promotion);

        return "Promotion với ID " + id + " đã được xóa thành công.";
    }




    @Override
    public PromotionResponse getPromotionDetail(int id) {
        // Lấy thông tin Promotion từ DB
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy Promotion với ID: " + id));

        // Lấy danh sách sản phẩm và chi tiết sản phẩm liên quan đến promotion
        List<ProductPromotion> productPromotions = productPromotionRepository.findProductPromotionByPromotion(promotion);

        // Lấy danh sách productIds và productDetailIds, đảm bảo tránh NullPointerException
        List<Integer> productIds = new ArrayList<>();
        List<Integer> productDetailIds = new ArrayList<>();

        for (ProductPromotion pp : productPromotions) {
            if (pp.getProduct() != null) {
                productIds.add(pp.getProduct().getId());
            }
            if (pp.getProductDetail() != null) {
                productDetailIds.add(pp.getProductDetail().getId());
            }
        }

        // Chuyển đổi từ Entity sang Response
        PromotionResponse promotionResponse = PromotionResponse.fromEntity(promotion);
        promotionResponse.setProductIds(productIds);
        promotionResponse.setProductDetailIds(productDetailIds);

        return promotionResponse;
    }


    @Override
    public String switchStatus(Integer id, StatusEnum status) {
        LocalDateTime currentDate = LocalDateTime.now(); // Lấy thời gian hiện tại
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID không tồn tại"));

        if (status == StatusEnum.ngung_kich_hoat) {
            promotion.setStatusPromotion(StatusEnum.ngung_kich_hoat);
            promotion.setEndDate(currentDate);
            promotionRepository.save(promotion);
            return "ngung_kich_hoat";
        } else {
            promotion.setStatusPromotion(StatusEnum.dang_kich_hoat);
            promotion.setStartDate(currentDate);
            promotionRepository.save(promotion);
            return "dang_kich_hoat";
        }
    }

    @Override
    public List<PromotionResponse> getAllPromotionsWithCustomer(Integer productIds) {
        return List.of();
    }

    // 🔍 Tìm kiếm theo tên chương trình khuyến mãi
    @Override
    public List<PromotionResponse> searchPromotionByName(String promotionName) {
        return promotionRepository.findByPromotionNameContainingIgnoreCase(promotionName)
                .stream()
                .map(PromotionResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // 🔍 Tìm kiếm theo khoảng giá trị giảm giá
    @Override
    public List<PromotionResponse> searchPromotionByDiscountRange(Double minDiscount, Double maxDiscount) {
        return promotionRepository.findByDiscountValueBetween(minDiscount, maxDiscount)
                .stream()
                .map(PromotionResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // 🔍 Tìm kiếm theo trạng thái khuyến mãi
    @Override
    public List<PromotionResponse> searchPromotionByStatus(StatusEnum statusPromotion) {
        return promotionRepository.findByStatusPromotion(statusPromotion)
                .stream()
                .map(PromotionResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // 🔍 Tìm kiếm theo khoảng thời gian bắt đầu và kết thúc
    @Override
    public List<PromotionResponse> searchPromotionByEndDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return promotionRepository.findByEndDateBetween(startDate, endDate)
                .stream()
                .map(PromotionResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
