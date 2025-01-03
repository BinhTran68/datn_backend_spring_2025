package com.poly.app.domain.model;

import com.poly.app.domain.model.base.PrimaryEntity;
import com.poly.app.infrastructure.constant.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "payment_methods")
//phương thức thanh toán
public class PaymentMethods extends PrimaryEntity implements Serializable {
//mã
    String paymentMethodsCode;
//phương thwudcs thanh toán
    String paymentMethods;
//tổng tiền
    Double totalMoney;
//loại
    String paymentMethodsType;
//    ghi chứ
    String notes;
//    mã giao dịch

    String dealCode;

    Status status;

}
