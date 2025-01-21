package com.poly.app.domain.repository;

import com.poly.app.domain.admin.promotion.response.PromotionResponse;
import com.poly.app.domain.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
    @Query("""
                SELECT new com.poly.app.domain.admin.promotion.response.PromotionResponse
                (p.id,p.promotionCode,p.promotionName,p.promotionType,p.discountValue,p.quantity,p.startDate,p.endDate,p.status)
                FROM Promotion  p
            """)
//    ,p.quantity
    List<PromotionResponse> getAllPro();
    @Query("""
    SELECT new com.poly.app.domain.admin.promotion.response.PromotionResponse
    (p.id, p.promotionCode, p.promotionName, p.promotionType, p.discountValue, p.quantity, p.startDate, p.endDate, p.status)
    FROM Promotion p
    WHERE (:promotionCode IS NULL OR p.promotionCode LIKE %:promotionCode%)
    AND (:promotionName IS NULL OR p.promotionName LIKE %:promotionName%)
    AND (:promotionType IS NULL OR p.promotionType LIKE %:promotionType%)
    AND (:status IS NULL OR p.status = :status)
""")
    List<PromotionResponse> searchPromotions(String promotionCode, String promotionName, String promotionType, String status);

}
