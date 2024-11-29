package com.poly.app.domain.auth.controller;

import com.poly.app.domain.auth.request.ChangeRequest;
import com.poly.app.domain.common.*;
import com.poly.app.domain.auth.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/admin/authentication")
public class AuthenticationAdminController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/doi-mat-khau")
    public ObjectResponse change(@RequestBody ChangeRequest request) {
        return new ObjectResponse(authenticationService.changePass(request));
    }
}
