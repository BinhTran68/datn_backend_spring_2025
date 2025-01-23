//package com.poly.app.domain.admin.customer.response;
//import com.poly.app.domain.model.Customer;
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class CustomerResponse {
//    private String code;
//    private String fullName;
//    private LocalDateTime dateBirth;
//    private String CitizenId;
//    private String phoneNumber;
//    private String email;
//    private Boolean gender;
//    private String avatar;
//    private String password;
//    private List<AddressResponse> addresses;
//
//    public CustomerResponse(Customer customer) {
//        this.code = customer.getCode();
//        this.fullName = customer.getFullName();
//        this.dateBirth = customer.getDateBirth();
//        this.CitizenId = customer.getCitizenId();
//        this.phoneNumber = customer.getPhoneNumber();
//        this.email = customer.getEmail();
//        this.gender = customer.getGender();
//        this.avatar = customer.getAvatar();
//        this.password = customer.getPassword();
//        this.addresses = customer.getAddresses().stream()
//                .map(AddressResponse::new)
//                .collect(Collectors.toList());
//    }
//
//    public static List<CustomerResponse> convertToList(List<Customer> customers) {
//        return customers.stream()
//                .map(CustomerResponse::new)
//                .collect(Collectors.toList());
//    }
//}




package com.poly.app.domain.admin.customer.response;
import com.poly.app.domain.model.Address;
import com.poly.app.domain.model.Customer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CustomerResponse {
    private Integer id;
    private String fullName;
    private String CitizenId;
    private String email;
    private Boolean gender;
    private String phoneNumber;
    private String password;
    private String avatar;
    private Integer status;
    private LocalDateTime dateBirth;
    // List of addresses
    private List<Address> addresses;

    public CustomerResponse(Customer customer) {
        this.id = customer.getId();
        this.fullName = customer.getFullName();
        this.CitizenId = customer.getCitizenId();
        this.email = customer.getEmail();
        this.gender = customer.getGender();
        this.phoneNumber = customer.getPhoneNumber();
        this.password = customer.getPassword();
        this.dateBirth = customer.getDateBirth();
        this.avatar = customer.getAvatar();
        this.status = customer.getStatus();
        this.addresses = customer.getAddresses();
    }
}