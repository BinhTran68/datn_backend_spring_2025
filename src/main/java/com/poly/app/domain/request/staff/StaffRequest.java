package com.poly.app.domain.request.staff;

import com.poly.app.infrastructure.constant.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StaffRequest {
    private String code;
    private String fullName;
    private LocalDateTime dateBirth;
    private String CitizenId;
    private String phoneNumber;
    private String email;
    private Boolean gender;
    private String password;
    private String avatar;

}
