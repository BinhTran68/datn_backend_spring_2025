package com.poly.app.domain.auth.service.impl;


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
import com.poly.app.infrastructure.email.Email;
import com.poly.app.infrastructure.email.EmailSender;
import com.poly.app.infrastructure.security.JwtUtilities;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtilities jwtUtilities;

    @Autowired
    private EmailSender emailSender;

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
        customer.setFullName(request.getFullName());
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customerRepository.save(customer);
        // Tạo 1 account
        // gửi mail ở đây
        Email email = new Email();
        String[] emailSend = {request.getEmail()};
        email.setToEmail(emailSend);
        email.setSubject("Tạo tài khoản thành công");
        email.setTitleEmail("");
        email.setBody("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; margin: 50px;\">\n" +
                "\n" +
                "    <div class=\"success-message\" style=\"background-color: #FFFFF; color: black; padding: 20px; border-radius: 10px; margin-top: 50px;\">\n" +
                "        <h2 style=\"color: #333;\">Tài khoản đã được tạo thành công!</h2>\n" +
                "        <p style=\"color: #555;\">Cảm ơn bạn đã đăng ký tại TheHands. Dưới đây là thông tin đăng nhập của bạn:</p>\n" +
                "        <p><strong>Email:</strong> " + request.getEmail() + "</p>\n" +
                "        <p><strong>Mật khẩu:</strong> " + request.getPassword() + "</p>\n" +
                "        <p style=\"color: #555;\">Đăng nhập ngay để trải nghiệm!</p>\n" +
                "    </div>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n");



        emailSender.sendEmail(email);
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

    private Authentication authenticateUser(LoginRequest loginRequest) {
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
    }

}
