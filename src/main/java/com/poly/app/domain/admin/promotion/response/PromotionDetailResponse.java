package com.poly.app.domain.admin.promotion.response;

import com.poly.app.domain.model.ProductDetail;
import com.poly.app.domain.model.Promotion;
import com.poly.app.infrastructure.constant.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionDetailResponse {
    Integer id;
//    Integer productId;
    String productDetailName;
    String promotionName;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Status status;

}
