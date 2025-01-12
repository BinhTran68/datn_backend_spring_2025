package com.poly.app.domain.admin.product.request.productdetail;

import com.poly.app.infrastructure.constant.Status;
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
