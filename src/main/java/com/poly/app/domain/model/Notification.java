package com.poly.app.domain.model;


import com.poly.app.domain.model.base.PrimaryEntity;
import com.poly.app.infrastructure.constant.EntityProperties;
import com.poly.app.infrastructure.constant.Status;
import com.poly.app.infrastructure.constant.TypeNotification;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "notification")
public class Notification extends PrimaryEntity {

    @Column(columnDefinition = EntityProperties.DEFINITION_NAME)
    private String title;

    @Column(columnDefinition = EntityProperties.DEFINITION_NAME)
    private String content;

    private String idRedirect;

    @Enumerated(EnumType.STRING)
    private TypeNotification type;

    @Enumerated(EnumType.STRING)
    private Status status = Status.HOAT_DONG;

    private String image;

    @ManyToOne
    @JoinColumn(name = "id_account", referencedColumnName = "id")
    private User account;
}
