package com.poly.app.domain.model;

import com.poly.app.domain.model.base.PrimaryEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "promotion_detail")
//đoẹt giảm giá chi tiết
public class PromotionDetail extends PrimaryEntity implements Serializable {

    @ManyToOne
    @JoinColumn
    ProductDetail productDetailId;

    @ManyToOne
    @JoinColumn
    Promotion promotionId;

    Integer status;

}
