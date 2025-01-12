package com.poly.app.domain.repository;

import com.poly.app.domain.model.PromotionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionDetailRepository extends JpaRepository<PromotionDetail,Integer> {
//    @Query("""
//select  new com.poly.app.domain.admin.promotion.response.PromotionDetailResponse
//(pd.id,pd.productDetail.) from PromotionDetail pd
//
//""")
}
