package com.poly.app.domain.model;


import com.poly.app.domain.model.base.PrimaryEntity;
import com.poly.app.infrastructure.constant.EntityProperties;
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
@Table(name = "address")
public class Address extends PrimaryEntity {


    @Column(columnDefinition = EntityProperties.DEFINITION_NAME)
    private String name;

    @Column(length = EntityProperties.LENGTH_PHONE)
    private String phoneNumber;


    @Column(length = EntityProperties.LENGTH_ID)
    private String provinceId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String districtId;

    @Column(length = EntityProperties.LENGTH_ID)
    private String wardId;

    @Column(columnDefinition = EntityProperties.DEFINITION_ADDRESS)
    private String specificAddress;

    private Boolean type;

    @ManyToOne
    @JoinColumn(name = "id_account", referencedColumnName = "id")
    private User account;

}
