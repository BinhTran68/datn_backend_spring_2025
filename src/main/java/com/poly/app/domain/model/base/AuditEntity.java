package com.poly.app.domain.model.base;

import com.poly.app.infrastructure.listener.AuditEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditEntityListener.class)
public abstract class AuditEntity {

    @Column(updatable = false, name = "ngay_tao")
    private Long createdAt;

    @Column(name = "ngay_cap_nhat")
    private Long updatedAt;

    @Column(name = "nguoi_tao")
    private String createdBy;

    @Column(name = "nguoi_cap_nhat")
    private String updatedBy;







}
