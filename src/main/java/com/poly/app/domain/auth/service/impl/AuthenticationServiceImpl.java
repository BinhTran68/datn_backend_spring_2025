package com.poly.app.domain.auth.service.impl;


import com.poly.app.domain.admin.customer.response.CustomerResponse;
import com.poly.app.domain.admin.customer.service.CustomerService;
import com.poly.app.domain.repository.CustomerRepository;
import com.poly.app.domain.repository.StaffRepository;
import com.poly.app.domain.auth.request.ChangeRequest;
import com.poly.app.domain.auth.request.LoginGoogleRequest;
import com.poly.app.domain.auth.request.LoginRequest;
import com.poly.app.domain.auth.request.RegisterRequest;
import com.poly.app.domain.auth.response.TokenPayload;
import com.poly.app.domain.auth.response.UserLoginResponse;
import com.poly.app.domain.auth.service.AuthenticationService;
import com.poly.app.domain.repository.RoleRepository;
import com.poly.app.domain.model.Customer;
import com.poly.app.domain.model.Staff;
import com.poly.app.infrastructure.constant.AccountStatus;
import com.poly.app.infrastructure.email.Email;
import com.poly.app.infrastructure.email.EmailSender;
import com.poly.app.infrastructure.email.EmailService;
import com.poly.app.infrastructure.exception.ApiException;
import com.poly.app.infrastructure.exception.ErrorCode;
import com.poly.app.infrastructure.exception.RestApiException;
import com.poly.app.infrastructure.security.JwtUtilities;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;


@Service
@EnableAsync
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtilities jwtUtilities;

    @Autowired
    private EmailSender emailSender;
    @Autowired
    private CustomerService customerService;

    @Autowired
    EmailService emailService;
    @Autowired
    private HttpSession session;

    @Override
    public Map<String, Object> loginAdmin(LoginRequest request) {
        Staff staff = staffRepository.findByEmail(request.getEmail());
        if (staff == null) {
            throw new ApiException(ErrorCode.TAIKHOAN_NOT_FOUND);
        }
        if (passwordEncoder.matches(request.getPassword(), staff.getPassword())) {
            TokenPayload tokenPayload = new TokenPayload();
            tokenPayload.setEmail(staff.getEmail());
            tokenPayload.setId(staff.getId());
            tokenPayload.setRoleName(staff.getRole().getRoleName());
            UserLoginResponse userLoginResponse = UserLoginResponse.fromStaffEntity(staff);
            return Map.of("token", jwtUtilities.generateToken(tokenPayload), "user", userLoginResponse);
        }
        throw new ApiException(ErrorCode.TAIKHOAN_NOT_FOUND);
    }


    @Override
    public Map<String, Object> login(LoginRequest request) {
        Customer customer = customerRepository.findByEmail(request.getEmail());
        if (customer == null) {
            throw  new RestApiException("Tài khoản không tồn tại", HttpStatus.BAD_REQUEST);
        }
        if(customer.getStatus() == 1) {
            throw  new RestApiException("Tài khoản của quý khách đã bị vô hiệu hóa! Vui lòng liên hệ với cửa hàng để kích họạt trở lại", HttpStatus.BAD_REQUEST);
        }

        if(customer.getStatus() == 2) {
            throw  new RestApiException("Tài khoản của quý khách chưa được kích hooạt!", HttpStatus.BAD_REQUEST);
        }

        if (passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            TokenPayload tokenPayload = new TokenPayload();
            tokenPayload.setEmail(customer.getEmail());
            tokenPayload.setId(customer.getId());
            tokenPayload.setRoleName("ROLE_USER");
            UserLoginResponse userLoginResponse = UserLoginResponse.fromCustomerEntity(customer);
            return Map.of("token", jwtUtilities.generateToken(tokenPayload), "customer", userLoginResponse);
        }
        throw new ApiException(ErrorCode.TAIKHOAN_NOT_FOUND);
    }

    @Override
    public UserLoginResponse userLogin() {
        return null;
    }

    @Transactional
    public Boolean register(RegisterRequest request) {
        boolean existsCustomerByEmail = customerRepository.existsCustomerByEmail(request.getEmail());
        if (existsCustomerByEmail) {
            throw new ApiException(ErrorCode.ACCOUNT_EMAIL_EXISTED);
        }
        String token = UUID.randomUUID().toString();
        Customer customer = new Customer();
        customer.setStatus(2);  // 2 là chưa kích hoạt
        customer.setEmail(request.getEmail());
        customer.setFullName(request.getFullName());
        customer.setTokenActiveAccount(token);
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customerRepository.save(customer);
        // Gửi email ở luồng riêng biệt
        emailService.sendRegistrationEmail(request.getEmail(), token);

        return true;
    }

    @Override
    public Boolean registerStaff(RegisterRequest request) {

        Staff staff = new Staff();
        staff.setStatus(1);  // 2 là chưa kích hoạt
        staff.setEmail(request.getEmail());
        staff.setFullName(request.getFullName());
        staff.setPassword(passwordEncoder.encode(request.getPassword()));
        staffRepository.save(staff);
        return true;
    }

    @Override
    public String sendOtp(String email) {
        return null;
    }

    @Override
    public Boolean change(RegisterRequest request) {
        return null;
    }

    @Override
    public Boolean changePass(ChangeRequest request) {
        return null;
    }

    @Override
    public String loginGoogle(LoginGoogleRequest request) {
        return null;
    }

    @Override
    public Customer getCustomerAuth() {
        UserDetails user = (UserDetails) session.getAttribute("user");

        if (user == null) {
            throw new RestApiException("User not found in session", HttpStatus.UNAUTHORIZED);
        }

        if (user instanceof Customer) {
            return (Customer) user;
        }

        throw new RestApiException("User is not a Customer", HttpStatus.FORBIDDEN);
    }

    @Override
    public Staff getStaffAuth() {
        UserDetails user = (UserDetails) session.getAttribute("user");

        if (user == null) {
            throw new RestApiException("User not found in session", HttpStatus.UNAUTHORIZED);
        }

        if (user instanceof Staff) {
            return (Staff) user;
        }

        throw new RestApiException("User is not a Staff", HttpStatus.FORBIDDEN);
    }

    @Override
    public void activateAccount(String token) {
        Customer customer = customerRepository.findByTokenActiveAccount(token);
        if (customer == null) {
            throw new RestApiException("Token không hợp lệ", HttpStatus.UNAUTHORIZED);
        }
        customer.setStatus(0); // Hoạt động
        customerRepository.save(customer);
    }

    @Override
    public void changePassword(ChangeRequest request) {

    }


    private Authentication authenticateUser(LoginRequest loginRequest) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
    }

}
