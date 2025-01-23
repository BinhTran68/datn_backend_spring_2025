package com.poly.app.domain.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.poly.app.domain.model.base.PrimaryEntity;
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
@Table(name = "address")
//địa chỉ
public class Address extends PrimaryEntity implements Serializable {

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    Customer customer;

    @ManyToOne
    @JoinColumn
    Staff staff;

    String provinceId; // tỉnh

    String districtId; // quận

    String wardId; // xã

    Boolean isAddressDefault;

    String specificAddress; // địa chỉ cụ thể số đường, ngỗ ....


}