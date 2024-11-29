package com.poly.app.domain.model;


import com.poly.app.domain.model.base.PrimaryEntity;
import com.poly.app.infrastructure.constant.EntityProperties;
import com.poly.app.infrastructure.constant.StatusBill;
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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "bill_history")
public class BillHistory extends PrimaryEntity {
    private StatusBill statusBill;

    @Column(columnDefinition = EntityProperties.DEFINITION_DESCRIPTION)
    private String note;

    @ManyToOne
    @JoinColumn(name = "id_bill", referencedColumnName = "id")
    private Bill bill;

    @ManyToOne
    @JoinColumn(name = "id_account", referencedColumnName = "id")
    private User account;

    @ManyToOne
    @JoinColumn(name = "id_reception_staff", referencedColumnName = "id")
    private User receptionStaff;

    public Integer getStatusBill() {
        return statusBill.ordinal();
    }

    public void setStatusBill(Integer statusBill) {
        this.statusBill = StatusBill.values()[statusBill];
    }
}
