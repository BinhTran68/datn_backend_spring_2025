package com.poly.app.domain.model;

import com.poly.app.domain.model.base.PrimaryEntity;
import com.poly.app.infrastructure.constant.BillStatus;
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

    String billType;
    //nagyf hoàn thành
    LocalDateTime completeDate;
    //    nagyf xác nhận
    LocalDateTime confirmDate;
//    ngày mong muốn nhận hàng

    LocalDateTime desiredDateOfReceipt;
    //    ngày ship
    LocalDateTime shipDate;

    //    dịa chỉ giao hàng
    String shippingAddress;

    String numberPhone;

    String email;

    @Enumerated(EnumType.STRING)
    BillStatus status;


}
