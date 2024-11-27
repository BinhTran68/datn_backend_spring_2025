package com.poly.app.domain.model;


import com.poly.app.domain.model.base.PrimaryEntity;
import com.poly.app.infrastructure.constant.EntityProperties;
import com.poly.app.infrastructure.constant.Status;
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
@Table(name = "material")
public class Material extends PrimaryEntity {

    @Column(columnDefinition = EntityProperties.DEFINITION_NAME)
    private String name;

    private Status deleted = Status.HOAT_DONG;

    public Integer getDeleted() {
        return deleted.ordinal();
    }

    public void setDeleted(Integer deleted) {
        this.deleted = Status.values()[deleted];
    }
}
