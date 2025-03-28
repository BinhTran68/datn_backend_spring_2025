package com.poly.app.domain.model;

import com.poly.app.domain.model.base.PrimaryEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
public class Comments extends PrimaryEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductDetail product;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(columnDefinition = "TEXT")
    private String comment;

}