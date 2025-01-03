package com.poly.app.domain.model;

import com.poly.app.domain.model.base.PrimaryEntity;
import com.poly.app.infrastructure.constant.Status;
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
@Table(name = "product_detail")
//sản phẩm jchi tiết
public class ProductDetail extends PrimaryEntity implements Serializable {
    @ManyToOne
    @JoinColumn
    Product productId;

    @ManyToOne
    @JoinColumn
    Brand brandId;

    @ManyToOne
    @JoinColumn
    Type typeId;

    @ManyToOne
    @JoinColumn
    Color colorId;

    @ManyToOne
    @JoinColumn
    Material materialId;

    @ManyToOne
    @JoinColumn
    Size sizeId;

    @ManyToOne
    @JoinColumn
    Sole soleId;

    @ManyToOne
    @JoinColumn
    Gender genderId;

    String productDetailCode;

    Integer quantity;

    Double price;

    Double weight;

    String description;

    Status status;





}
