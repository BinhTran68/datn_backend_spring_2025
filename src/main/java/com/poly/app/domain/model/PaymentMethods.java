package com.poly.app.domain.model;

import com.poly.app.domain.model.base.PrimaryEntity;
import com.poly.app.infrastructure.constant.PaymentMethodEnum;
import com.poly.app.infrastructure.constant.PaymentMethodsType;
import com.poly.app.infrastructure.constant.Status;
import jakarta.persistence.*;
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
public class PaymentMethods extends PrimaryEntity implements Serializable {


    @Enumerated(EnumType.STRING)
    PaymentMethodsType paymentMethodsType; // Loại thanh toán (enum)

    Double totalMoney; // Tổng tiền

    @Enumerated(EnumType.STRING)
    PaymentMethodEnum paymentMethod; // Phương thước thanh toán

    String notes; // Ghi chú

    String transactionCode; // Mã giao dịch

    @Enumerated(EnumType.STRING)
    Status status; // Trạng thái (enum)
}
