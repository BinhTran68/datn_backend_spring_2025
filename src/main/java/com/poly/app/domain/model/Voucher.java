package com.poly.app.domain.model;

import com.poly.app.domain.model.base.PrimaryEntity;
import com.poly.app.infrastructure.constant.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "voucher")
//phiếu giảm giá
public class Voucher extends PrimaryEntity implements Serializable {
    Integer id;
    //mã phiếu
    String voucherCode;
    String voucherName;
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
//    Status status;
    StatusVoucher statusVoucher;

}
