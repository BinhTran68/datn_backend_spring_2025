package com.poly.app.domain.model;


import com.poly.app.domain.model.base.PrimaryEntity;
import com.poly.app.infrastructure.constant.EntityProperties;
import com.poly.app.infrastructure.constant.StatusBill;
import com.poly.app.infrastructure.constant.TypeBill;
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
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "bill")
public class Bill extends PrimaryEntity {

    @Column(length = EntityProperties.LENGTH_CODE)
    private String code;

    @Column(columnDefinition = EntityProperties.DEFINITION_NAME)
    private String fullName;

    @Column(length = EntityProperties.LENGTH_PHONE)
    private String phoneNumber;

    @Column(columnDefinition = EntityProperties.DEFINITION_ADDRESS)
    private String address;

    private BigDecimal totalMoney;

    private BigDecimal moneyReduced;

    private BigDecimal moneyAfter;

    private Long shipDate;

    private Long receiveDate;

    private BigDecimal moneyShip;

    private Long confirmationDate;

    private TypeBill type;

    @Column(columnDefinition = EntityProperties.DEFINITION_DESCRIPTION)
    private String note;

    private BigDecimal customerAmount;

    private Long desiredReceiptDate;

    private Long completeDate;

    private StatusBill status;

    private Integer receivingMethod; //receivingMethod nếu mua tại quầy set là 0 còn giao hàng là 1 nhé

    private Integer percentMoney;

    @Column(length = EntityProperties.LENGTH_EMAIL)
    private String email;

    @ManyToOne
    @JoinColumn(name = "id_customer", referencedColumnName = "id")
    private Account customer;

    @ManyToOne
    @JoinColumn(name = "id_voucher", referencedColumnName = "id")
    private Voucher voucher;

    public Integer getType() {
        return type.ordinal();
    }

    public void setType(Integer type) {
        this.type = TypeBill.values()[type];
    }

    public Integer getStatus() {
        return status.ordinal();
    }

    public void setStatus(Integer status) {
        this.status = StatusBill.values()[status];
    }
}
