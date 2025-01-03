package com.poly.app.domain.admin.voucher.response;

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
public class VoucherReponse {
    Integer id;
    String voucherCode;
    Integer quantity;
    String voucherType;
    Double discountValue;
    Double discountMaxValue;
    Double billMinValue;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Status status;

}
