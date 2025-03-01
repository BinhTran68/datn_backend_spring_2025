package com.poly.app.domain.auth.service;



import com.poly.app.domain.auth.request.ChangeRequest;
import com.poly.app.domain.auth.request.LoginGoogleRequest;
import com.poly.app.domain.auth.request.LoginRequest;
import com.poly.app.domain.auth.request.RegisterRequest;
import com.poly.app.domain.auth.response.UserLoginResponse;
import com.poly.app.domain.model.Customer;
import com.poly.app.domain.model.Staff;

import java.util.Map;

public interface AuthenticationService {
    Map<String, Object> loginAdmin(LoginRequest request);

    Map<String, Object> login(LoginRequest request);

    UserLoginResponse userLogin();

    Boolean register(RegisterRequest request);

    Boolean registerStaff(RegisterRequest request);

    String sendOtp(String email);

    Boolean change(RegisterRequest request);

    Boolean changePass(ChangeRequest request);

    String loginGoogle(LoginGoogleRequest request);


    Customer getCustomerAuth();
    Staff getStaffAuth();
}
