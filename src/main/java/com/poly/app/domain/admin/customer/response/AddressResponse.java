

package com.poly.app.domain.admin.customer.response;
import com.poly.app.domain.model.Address;
import lombok.Data;

@Data
public class AddressResponse {
    private String provinceId;
    private String districtId;
    private String wardId;
    private Boolean isAddressDefault;
    private String specificAddress;

//    public AddressResponse(Address address) {
//        this.provinceId = address.getProvinceId();
//        this.districtId = address.getDistrictId();
//        this.wardId = address.getWardId();
//        this.isAddressDefault = address.getIsAddressDefault();
//        this.specificAddress = address.getSpecificAddress();
//    }

    public AddressResponse(Address address) {
        this.provinceId = address.getProvinceId();
        this.districtId = address.getDistrictId();
        this.wardId = address.getWardId();
        this.isAddressDefault = address.getIsAddressDefault() != null ? address.getIsAddressDefault() : false;
        this.specificAddress = address.getSpecificAddress();
    }

}