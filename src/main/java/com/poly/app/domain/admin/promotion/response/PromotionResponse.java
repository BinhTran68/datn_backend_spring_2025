package com.poly.app.domain.admin.promotion.response;

import com.poly.app.infrastructure.constant.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionResponse {
    Integer id;
    String promotionCode;
    String promotionName;
    String promotionType;
    Double discountValue;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Status status;
}
