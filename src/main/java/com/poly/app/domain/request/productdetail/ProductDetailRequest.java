package com.poly.app.domain.request.productdetail;

import com.poly.app.domain.model.*;
import com.poly.app.infrastructure.constant.EntityProperties;
import com.poly.app.infrastructure.constant.Status;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailRequest {
    Integer productId;

    Integer brandId;

    Integer typeId;

    Integer colorId;

    Integer materialId;

    Integer sizeId;

    Integer soleId;

    Integer genderId;

    Integer quantity;

    Double price;

    Double weight;

    String descrition;

    Status status;
}
