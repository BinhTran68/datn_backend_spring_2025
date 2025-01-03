package com.poly.app.domain.model;

import com.poly.app.domain.model.base.PrimaryEntity;
import com.poly.app.infrastructure.constant.Status;
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
@Table(name = "promotion")
//dợt giảm giá
public class Promotion extends PrimaryEntity implements Serializable {

    String promotionCode;

    String promotionName;

    String promotionType;

//    giá trị giảm
    Double discountValue;

    Date startDate;

    Date endDate;

    Status status;

}
