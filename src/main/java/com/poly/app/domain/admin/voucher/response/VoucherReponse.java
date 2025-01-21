package com.poly.app.domain.admin.voucher.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.poly.app.domain.model.StatusVoucher;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime endDate;
    StatusVoucher statusVoucher;

}
