package com.poly.app.domain.model;


import com.poly.app.domain.model.base.PrimaryEntity;
import com.poly.app.infrastructure.constant.EntityProperties;
import com.poly.app.infrastructure.constant.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product_detail")
public class ProductDetail extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_CODE)
    private String code;

    private BigDecimal price;

    private Integer weight;

    private Status deleted = Status.HOAT_DONG;

    private Integer amount;

    private Integer quantityReturn = 0;


    @Column(columnDefinition = EntityProperties.DEFINITION_DESCRIPTION)
    private String description;
    @ManyToOne
    @JoinColumn(name = "id_brand", referencedColumnName = "id")
    private Brand brand;
    @ManyToOne
    @JoinColumn(name = "id_sole", referencedColumnName = "id")
    private Sole sole;
    @ManyToOne
    @JoinColumn(name = "id_material", referencedColumnName = "id")
    private Material material;
    @ManyToOne
    @JoinColumn(name = "id_category", referencedColumnName = "id")
    private Category category;
    @ManyToOne
    @JoinColumn(name = "id_product", referencedColumnName = "id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "id_size", referencedColumnName = "id")
    private Size size;
    @ManyToOne
    @JoinColumn(name = "id_color", referencedColumnName = "id")
    private Color color;

    public Integer getDeleted() {
        return deleted.ordinal();
    }

    public void setDeleted(Integer deleted) {
        this.deleted = Status.values()[deleted];
    }
}
