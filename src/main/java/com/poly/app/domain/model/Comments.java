package com.poly.app.domain.model;

import com.poly.app.domain.model.base.PrimaryEntity;
import com.poly.app.infrastructure.constant.CommentsStatus;
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
    private ProductDetail product; /// Productchuwsuws nhể ->>> Đổi sang product

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = true) // trường hợp quản lý trang trả lời
    private Customer customer;

    @Column(columnDefinition = "TEXT")
    private String comment;

    // max 5
    // lấy luôn trung bình của rate neeus chưa có rate thì là 5
    private Double rate;

    @Enumerated(EnumType.STRING)
    private CommentsStatus status;

    // Có parentId có nghĩ là tin nhắn này là trả lời
    // Comments nào có parentId  có nghĩa là của amdin trả lời thì email người trả lời trong created_by
    //
    private Integer parentId;


    // Admin cần phải trả lời
    // Admin có quyền ẩn duyệt


}