package com.poly.app.domain.admin.bill.response;


import com.poly.app.domain.model.base.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BillProductDetailResponse extends AuditEntity {

    String urlImage;
    Integer quantity;
    String productName;
    Double price;
    String size;
    String color;
    Double totalPrice;

}
