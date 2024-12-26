package com.poly.app.domain.auth.service.impl;



import com.cloudinary.api.exceptions.ApiException;
import com.poly.app.domain.auth.repsitory.CustomerRepository;
import com.poly.app.domain.auth.repsitory.RoleRepository;
import com.poly.app.domain.auth.repsitory.StaffRepository;
import com.poly.app.domain.auth.request.ChangeRequest;
import com.poly.app.domain.auth.request.LoginGoogleRequest;
import com.poly.app.domain.auth.request.LoginRequest;
import com.poly.app.domain.auth.request.RegisterRequest;
import com.poly.app.domain.auth.response.TokenPayload;
import com.poly.app.domain.auth.response.UserLoginResponse;
import com.poly.app.domain.auth.service.AuthenticationService;
import com.poly.app.domain.common.Helpers;
import com.poly.app.domain.model.Customer;
import com.poly.app.domain.model.Role;
import com.poly.app.domain.model.Staff;
import com.poly.app.domain.model.User;
import com.poly.app.infrastructure.exception.ErrorCode;
import com.poly.app.infrastructure.security.JwtUtilities;
import com.poly.app.infrastructure.util.MD5Util;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private  AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtilities jwtUtilities;

    @Override
    public String loginAdmin(LoginRequest request) {
        return null;
    }



    @Override
    public String login(LoginRequest request) {
        // Sử dụng BCryptPasswordEncoder để mã hóa và kiểm tra mật khẩu
        // Lấy customer từ database, với mật khẩu đã mã hóa
        Customer customer = customerRepository.findByEmail(request.getEmail());
        if (customer != null && passwordEncoder.matches(request.getPassword(), customer.getPassword())) {
            // Nếu mật khẩu người dùng nhập đúng, tạo token và trả về
            TokenPayload tokenPayload = new TokenPayload();
            tokenPayload.setEmail(customer.getEmail());
            tokenPayload.setId(customer.getId());
            tokenPayload.setRoleName("ROLE_USER");
            return jwtUtilities.generateToken(tokenPayload);
        }

        return null; // Trả về null nếu không tìm thấy customer hoặc mật khẩu sai
    }

    @Override
    public UserLoginResponse userLogin() {
        return null;
    }

    @Override
    public Boolean register(RegisterRequest request) {

            Customer customer = new Customer();
            customer.setStatus(2);  // 2 là chưa kích hoạt

            customer.setEmail(request.getEmail());
            customer.setFullName(request.getName());
            customer.setPassword(passwordEncoder.encode(request.getPassword()));
            customerRepository.save(customer);
            System.out.println("lỗi");

            // Tạo 1 account


        return true;
    }

    @Override
    public Boolean registerStaff(RegisterRequest request) {

        Staff staff = new Staff();
        staff.setStatus(1);  // 2 là chưa kích hoạt
        staff.setEmail(request.getEmail());
        staff.setFullName(request.getName());
        staff.setPassword(passwordEncoder.encode(request.getPassword()));
        staffRepository.save(staff);
        return true;
    }

    @Override
    public User checkMail(String email) {
        return null;
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
    private Authentication authenticateUser(LoginRequest loginRequest) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
    }

}
