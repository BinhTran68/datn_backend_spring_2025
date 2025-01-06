package com.poly.app.domain.admin.product.response.product;

import com.poly.app.infrastructure.constant.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Integer id;
    String code;
    String productName;
    Long updateAt;
    Status status;
}
