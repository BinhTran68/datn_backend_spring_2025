package com.poly.app.domain.model;

import com.poly.app.domain.model.base.PrimaryEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(name = "voucher")
//phiếu giảm giá
public class Voucher extends PrimaryEntity implements Serializable {

    String voucherCode;

    String quatity;

    Double discountValue;
//loại
    String voucherType;
//giá trị giảm tối đa
    Double discountMaxValue;
//giá trị tối thiểu của háo đơn
    Double billMinValue;

    Date startDate;

    Date endDate;

    Integer status;

}
