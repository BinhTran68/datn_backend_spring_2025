
package com.poly.app.domain.admin.address;

import lombok.Data;

@Data
public class AddressRequest {
    private String provinceId;
    private String districtId;
    private String wardId;
    private Boolean isAddressDefault;
    private String specificAddress;
}