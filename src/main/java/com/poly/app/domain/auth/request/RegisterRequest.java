package com.poly.app.domain.auth.request;

import com.poly.app.domain.auth.AuthMessageConstants;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = AuthMessageConstants.nameRequired)
    private String name;

    @NotBlank(message = AuthMessageConstants.emailRequired)
    private String email;

    @NotBlank(message =  AuthMessageConstants.passwordRequired)
    private String password;
}
