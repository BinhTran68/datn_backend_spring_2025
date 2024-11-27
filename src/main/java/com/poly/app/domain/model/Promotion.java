package com.poly.app.domain.model;


import com.poly.app.domain.model.base.PrimaryEntity;
import com.poly.app.infrastructure.constant.EntityProperties;
import com.poly.app.infrastructure.constant.StatusVoucher;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "promotion")
public class Promotion extends PrimaryEntity {

    @Column(columnDefinition = EntityProperties.DEFINITION_NAME, unique = true)
    private String name;

    private Long timeStart;

    private Long timeEnd;

    private Boolean type;

    private Integer value;

    private StatusVoucher status;

    public Integer getStatus() {
        return status.ordinal();
    }

    public void setStatus(Integer status) {
        this.status = StatusVoucher.values()[status];
    }

}
