package com.poly.app.domain.response.productdetail;

import com.poly.app.infrastructure.constant.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

public interface FilterProductDetailResponse {
    Integer getId();

    String getCode();

    String getProductName();

    String getBrandName();

    String getTypeName();

    String getColorName();

    String getMaterialName();

    String getSizeName();

    String getSoleName();

    String getGenderName();

    Integer getQuantity();

    Double getPrice();

    Double getWeight();

    String getDescrition();

    String getStatus();

    String getUpdatedAt();

    String getUpdatedBy();
}
