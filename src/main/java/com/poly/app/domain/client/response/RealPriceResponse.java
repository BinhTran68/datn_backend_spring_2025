package com.poly.app.domain.client.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RealPriceResponse {
    Integer cartDetailId;
    Integer productDetailId;
    String productName;
    Double price;
    Integer quantityAddCart;
    String image;
    String note;
}
