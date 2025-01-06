package com.poly.app.domain.admin.product.request.product;

import com.poly.app.infrastructure.constant.Status;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    @NotBlank(message = "NOT_BLANK")
    String ProductName;
    Status status;
}
