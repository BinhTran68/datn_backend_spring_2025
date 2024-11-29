package com.poly.app.domain.auth.controller;


import com.poly.app.domain.auth.request.ChangeRequest;
import com.poly.app.domain.auth.request.LoginGoogleRequest;
import com.poly.app.domain.auth.request.LoginRequest;
import com.poly.app.domain.auth.request.RegisterRequest;
import com.poly.app.domain.auth.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.poly.app.domain.common.*;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {
    
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login-admin")
    public ObjectResponse loginAdmin(@RequestBody LoginRequest request) {
        return new ObjectResponse(authenticationService.loginAdmin(request));
    }

    @PostMapping("/login")
    public ObjectResponse login(@RequestBody LoginRequest request) {
        return new ObjectResponse(authenticationService.login(request));
    }

    @PostMapping("/login-google")
    public ObjectResponse loginGoogle(@RequestBody LoginGoogleRequest request) {
        return new ObjectResponse(authenticationService.loginGoogle(request));
    }

    @PostMapping("/register")
    public ObjectResponse register(@RequestBody @Valid RegisterRequest request) {
        return new ObjectResponse(authenticationService.register(request));
    }

    @PostMapping("/change-password")
    public ObjectResponse change(@RequestBody RegisterRequest request) {
        return new ObjectResponse(authenticationService.change(request));
    }

    @PostMapping("/doi-mat-khau")
    public ObjectResponse change(@RequestBody ChangeRequest request) {
        return new ObjectResponse(authenticationService.changePass(request));
    }

    @GetMapping
    public ObjectResponse getUserLogin() {
        return new ObjectResponse(authenticationService.userLogin());
    }

    @GetMapping("/check-mail")
    public ObjectResponse checkMail(@RequestParam String email) {
        return new ObjectResponse(authenticationService.checkMail(email));
    }

    @GetMapping("/send-otp")
    public ObjectResponse sendOtp(@RequestParam String email) {
        return new ObjectResponse(authenticationService.sendOtp(email));
    }
}
