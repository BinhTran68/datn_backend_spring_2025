package com.poly.app.domain.response.staff;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffResponse {
    String code;
    String fullName;
    LocalDateTime dateBirth;
    String CitizenId;
    String phoneNumber;
    String email;
    Boolean gender;
    String password;
    String avatar;
}
