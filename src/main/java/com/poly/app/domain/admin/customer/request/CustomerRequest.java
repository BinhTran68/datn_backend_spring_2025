package com.poly.app.domain.admin.customer.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerRequest {
    private String code;
    private String fullName;
    private LocalDateTime dateBirth;
    private String CitizenId;
    private String phoneNumber;
    private String email;
    private Boolean gender;
    private String password;
    private List<AddressRequest> addresses;
}