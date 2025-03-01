package com.poly.app.domain.auth.controller;


import com.poly.app.domain.auth.request.LoginRequest;
import com.poly.app.domain.auth.request.RegisterRequest;
import com.poly.app.domain.auth.service.AuthenticationService;
import com.poly.app.domain.common.ObjectResponse;
import com.poly.app.domain.model.Customer;
import com.poly.app.domain.repository.BillRepository;
import com.poly.app.infrastructure.security.Auth;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    HttpSession session;

    @Autowired
    Auth auth;


    @PostMapping("/register")
    public ObjectResponse  register(@Valid @RequestBody RegisterRequest request) {
        return new ObjectResponse(authenticationService.register(request));
    }

    @PostMapping("/admin-register")
    public ObjectResponse  staffRegister(@Valid @RequestBody RegisterRequest request) {
        return new ObjectResponse(authenticationService.registerStaff(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }


    @PostMapping("/login-admin")
    public ResponseEntity<?> loginAdmin(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.loginAdmin(request));
    }


    @GetMapping("/user-info")
    public ResponseEntity<?> getUserInfo() {
        Customer customer = auth.getCustomerAuth();

        return ResponseEntity.ok(customer);
    }


}
