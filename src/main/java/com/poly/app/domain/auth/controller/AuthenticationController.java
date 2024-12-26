package com.poly.app.domain.auth.controller;


import com.poly.app.domain.auth.request.LoginRequest;
import com.poly.app.domain.auth.request.RegisterRequest;
import com.poly.app.domain.auth.service.AuthenticationService;
import com.poly.app.domain.common.ObjectResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/register")
    public ObjectResponse  register(@Valid @RequestBody RegisterRequest request) {
        return new ObjectResponse(authenticationService.register(request));
    }

    @PostMapping("/admin-register")
    public ObjectResponse  staffRegister(@Valid @RequestBody RegisterRequest request) {
        return new ObjectResponse(authenticationService.registerStaff(request));
    }

    @PostMapping("/login")
    public ObjectResponse  login(@Valid @RequestBody LoginRequest request) {
        return new ObjectResponse(authenticationService.login(request));
    }




}
