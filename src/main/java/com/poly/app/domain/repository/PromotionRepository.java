package com.poly.app.domain.repository;

import com.poly.app.domain.admin.promotion.response.PromotionResponse;
import com.poly.app.domain.model.Promotion;
import com.poly.app.domain.model.StatusEnum;
import com.poly.app.domain.model.Voucher;
import com.poly.app.infrastructure.constant.VoucherType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    // 🔍 Tìm kiếm theo tên chương trình khuyến mãi (promotionName)
    List<Promotion> findByPromotionNameContainingIgnoreCase(String promotionName);
    // 🔍 Tìm kiếm theo khoảng giá trị giảm giá (discountValue)
    List<Promotion> findByDiscountValueBetween(Double minDiscount, Double maxDiscount);
    // 🔍 Tìm kiếm theo trạng thái khuyến mãi (statusPromotion)
    List<Promotion> findByStatusPromotion(StatusEnum statusPromotion);
    // 🔍 Tìm kiếm theo khoảng thời gian bắt đầu (startDate) và kết thúc (endDate)
    List<Promotion> findByEndDateBetween(LocalDateTime startDate, LocalDateTime endDate);



}
