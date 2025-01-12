package com.poly.app.domain.admin.promotion.request;

import com.poly.app.domain.model.ProductDetail;
import com.poly.app.domain.model.Promotion;
import com.poly.app.infrastructure.constant.Status;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionDetailRequest {
    Integer productDetailId;
    Integer promotionId;
    Status status;
}
