package com.poly.app.domain.auth.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserLoginResponse {
    private String id;
    private String email;
    private String name;
    private String avatar;
    private Integer role;
}
