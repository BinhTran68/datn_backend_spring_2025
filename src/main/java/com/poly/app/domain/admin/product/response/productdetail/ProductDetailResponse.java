package com.poly.app.domain.admin.product.response.productdetail;

import com.poly.app.infrastructure.constant.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailResponse {
    Integer id;

    String code;

    String productName;

    String brandName;

    String typeName;

    String colorName;

    String materialName;

    String sizeName;

    String soleName;

    String genderName;

    Integer quantity;

    Double price;

    Double weight;
    
    String description;
    
    Status status;

    long updateAt;

    String updateBy;
}
