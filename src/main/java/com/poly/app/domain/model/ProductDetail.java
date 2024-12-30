package com.poly.app.domain.model;

import com.poly.app.domain.model.base.PrimaryEntity;
import com.poly.app.infrastructure.constant.EntityProperties;
import jakarta.persistence.*;
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
    Sex sexId;

    String productDetailCode;

    Integer quantity;

    Double price;

    Double weight;

    @Column(columnDefinition = EntityProperties.DEFINITION_DESCRIPTION)
    String descrition;

    Integer status;


}
