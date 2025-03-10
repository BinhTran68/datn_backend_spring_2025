package com.poly.app.domain.client.response;

import java.math.BigDecimal;


public interface ProductViewResponse {
    Long getProductId();
    String getProductName();
    Long getProductDetailId();
    BigDecimal getPrice();
    Integer getSold();
    Integer getTag();
    String getImageUrl();
    String getPromotionName();
    BigDecimal getDiscountValue();
    String getPromotionType();
    Long getCreatedAt();
    Long getViews();
}
