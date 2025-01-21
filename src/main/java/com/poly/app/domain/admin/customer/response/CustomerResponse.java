package com.poly.app.domain.admin.customer.response;
import com.poly.app.domain.model.Customer;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerResponse {
    private String code;
    private String fullName;
    private LocalDateTime dateBirth;
    private String CitizenId;
    private String phoneNumber;
    private String email;
    private Boolean gender;
    private String avatar;
    private List<AddressResponse> addresses;

    public CustomerResponse(Customer customer) {
        this.code = customer.getCode();
        this.fullName = customer.getFullName();
        this.dateBirth = customer.getDateBirth();
        this.CitizenId = customer.getCitizenId();
        this.phoneNumber = customer.getPhoneNumber();
        this.email = customer.getEmail();
        this.gender = customer.getGender();
        this.avatar = customer.getAvatar();
        this.addresses = customer.getAddresses().stream()
                .map(AddressResponse::new)
                .collect(Collectors.toList());
    }

    public static List<CustomerResponse> convertToList(List<Customer> customers) {
        return customers.stream()
                .map(CustomerResponse::new)
                .collect(Collectors.toList());
    }
}