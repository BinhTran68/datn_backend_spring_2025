package com.poly.app.domain.admin.voucher.request.voucher;

import com.poly.app.domain.model.StatusVoucher;
import com.poly.app.infrastructure.constant.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VoucherRequest {
    //phiếu giảm giá
        Integer id;
        //mã phiếu
        String voucherCode;
        //số lượng
        Integer quantity;
        //loại giảm
        String voucherType;
        //giá trị giảm
        Double discountValue;
        //giá trị giảm tối đa
        Double discountMaxValue;
    //giá trị giảm tối thiểu
        Double billMinValue;
        LocalDateTime startDate;
        LocalDateTime endDate;
        StatusVoucher statusVoucher;
}
