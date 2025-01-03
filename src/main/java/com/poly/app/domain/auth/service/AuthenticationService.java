package com.poly.app.domain.auth.service;



import com.poly.app.domain.auth.request.ChangeRequest;
import com.poly.app.domain.auth.request.LoginGoogleRequest;
import com.poly.app.domain.auth.request.LoginRequest;
import com.poly.app.domain.auth.request.RegisterRequest;
import com.poly.app.domain.auth.response.UserLoginResponse;

public interface AuthenticationService {
    String loginAdmin(LoginRequest request);

    String login(LoginRequest request);

    UserLoginResponse userLogin();

    Boolean register(RegisterRequest request);

    Boolean registerStaff(RegisterRequest request);





    String sendOtp(String email);

    Boolean change(RegisterRequest request);

    Boolean changePass(ChangeRequest request);

    String loginGoogle(LoginGoogleRequest request);
}
