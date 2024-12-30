package com.poly.app.domain.model;

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
    Customer customerId;

    Boolean isAddressDefault;

    String addressDetail;



}
