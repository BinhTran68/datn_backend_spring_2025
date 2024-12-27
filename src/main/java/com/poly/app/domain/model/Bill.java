package com.poly.app.domain.model;

import com.poly.app.domain.model.base.PrimaryEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;

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
    Customer customerid;

    @ManyToOne
    @JoinColumn
    Staff staffId;


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
    Date completeDate;
//    nagyf xác nhận
    Date confirmDate;
//    ngày mong muốn nhận hàng

    Date desiredDateOfReceipt;
//    ngày ship
    Date shipDate;

//    dịa chỉ giao hàng
    String shippingAddress;

    String numberPhone;

    String email;

    Integer status;



}
