package com.poly.app.domain.admin.promotion.request;

import com.poly.app.infrastructure.constant.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PromotionRequest {
    Integer id;
    // mã giảm
    String promotionCode;
    // tên loại giảm
    String promotionName;
    // loại giảm
    String promotionType;
    // giá trị giảm
    Double discountValue;
//    Integer quantity;
    // ngày bắt đầu
    LocalDateTime startDate;
    // ngày kết thúc
    LocalDateTime endDate;
    // trạng thái
    Status status;
}
