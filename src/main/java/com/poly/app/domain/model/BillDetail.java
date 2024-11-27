package com.poly.app.domain.model;


import com.poly.app.domain.model.base.PrimaryEntity;
import com.poly.app.infrastructure.constant.EntityProperties;
import com.poly.app.infrastructure.constant.StatusBillDetail;
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
@Table(name = "bill_detail")
public class BillDetail extends PrimaryEntity {
    private Integer quantity;

    private BigDecimal price;

    private StatusBillDetail status;

    @Column(columnDefinition = EntityProperties.DEFINITION_DESCRIPTION)
    private String note;

    @ManyToOne
    @JoinColumn(name = "id_bill", referencedColumnName = "id")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "id_product_detail", referencedColumnName = "id")
    private ProductDetail productDetail;

    public Integer getStatus() {
        return status.ordinal();
    }

    public void setStatus(Integer status) {
        this.status = StatusBillDetail.values()[status];
    }
}
