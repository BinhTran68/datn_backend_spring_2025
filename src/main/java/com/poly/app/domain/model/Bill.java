package com.poly.app.domain.model;

import com.poly.app.domain.model.base.PrimaryEntity;
import com.poly.app.infrastructure.constant.BillStatus;
import com.poly.app.infrastructure.constant.TypeBill;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "bill")
//hóa đơn
public class Bill extends PrimaryEntity implements Serializable {

    @ManyToOne
    @JoinColumn
    Customer customer;

    @ManyToOne
    @JoinColumn
    Staff staff;

    String billCode;

    //    tiền kahcsh đưa
    Double customerMoney;

//    tiền đưuọc giảm

    Double discountMoney;

    //    tiền ship
    Double shipMoney;

    //    tổng tiền
    Double totalMoney;
//    laoi

    //nagyf hoàn thành
    LocalDateTime completeDate;
    //    nagyf xác nhận
    LocalDateTime confirmDate;
//    ngày mong muốn nhận hàng

    LocalDateTime desiredDateOfReceipt;
    //    ngày ship
    LocalDateTime shipDate;

    @ManyToOne
    @JoinColumn(name = "shipping_address_id")
    Address shippingAddress;

    String numberPhone;

    String email;

    @Enumerated(EnumType.STRING)
    TypeBill typeBill;

    String notes;

    @Enumerated(EnumType.STRING)
    BillStatus status;


}
