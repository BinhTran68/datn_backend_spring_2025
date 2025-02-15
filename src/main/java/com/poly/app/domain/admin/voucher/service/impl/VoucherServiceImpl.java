package com.poly.app.domain.admin.voucher.service.impl;

import com.poly.app.domain.admin.voucher.request.voucher.VoucherRequest;
import com.poly.app.domain.admin.voucher.response.VoucherReponse;
import com.poly.app.domain.admin.voucher.response.VoucherResponse;
import com.poly.app.domain.admin.voucher.service.VoucherService;
import com.poly.app.domain.auth.request.RegisterRequest;
import com.poly.app.domain.model.Customer;
import com.poly.app.domain.admin.customer.service.impl.CustomerServiceImpl;
import com.poly.app.domain.admin.customer.service.CustomerService;

import com.poly.app.domain.model.StatusVoucher;
import com.poly.app.domain.model.Voucher;
import com.poly.app.domain.repository.VoucherRepository;
import com.poly.app.infrastructure.email.Email;
import com.poly.app.infrastructure.email.EmailSender;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class VoucherServiceImpl implements VoucherService {
    @Autowired
            CustomerService customerService;
//    @Autowired
//            CustomerServiceImpl customerServiceImpl;
    VoucherRepository voucherRepository;

    @Override
    public List<VoucherReponse> getAllVoucher() {

//        List<VoucherReponse> voucherReponses = voucherRepository.getAllVou();

        return voucherRepository.findAll().stream()
                .map(voucher -> new VoucherReponse(voucher.getId(), voucher.getVoucherCode(),voucher.getVoucherName(),
                        voucher.getQuantity(), voucher.getVoucherType(), voucher.getDiscountValue(),
                        voucher.getDiscountMaxValue(), voucher.getBillMinValue(), voucher.getStartDate(),
                        voucher.getEndDate(), voucher.getStatusVoucher(),voucher.getDiscountValueType())).toList();
    }

    public StatusVoucher checkVoucherStatus(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime currentDate = LocalDateTime.now(); // Lấy thời gian hiện tại

        if (currentDate.isBefore(startDate)) {
            return StatusVoucher.chua_kich_hoat; // Chưa kích hoạt
        } else if (currentDate.isAfter(endDate)) {
            return StatusVoucher.ngung_kich_hoat; // Đã ngừng kích hoạt
        } else {
            return StatusVoucher.dang_kich_hoat; // Đang kích hoạt
        }
    }

    @Override
    public Voucher createVoucher(VoucherRequest request) {

        StatusVoucher saStatusVoucher = checkVoucherStatus(request.getStartDate(), request.getEndDate());
        // Sinh mã voucher tự động (định nghĩa logic trong createVoucher)
        String generatedVoucherCode = "MGG" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();



        Voucher voucher = Voucher.builder()
                .id(request.getId())
                .voucherCode(generatedVoucherCode)
                .voucherName((request.getVoucherName()))
                .quantity(request.getQuantity())
                .voucherType(request.getVoucherType())
                .discountValue(request.getDiscountValue())
                .discountMaxValue(request.getDiscountMaxValue())
                .billMinValue(request.getBillMinValue())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .statusVoucher(saStatusVoucher)
                .discountValueType(request.getDiscountValueType())
                .build();


        // Kiểm tra nếu là loại voucher dành riêng cho khách hàng
        // đây nè anh cái phần này nè

        if (request.getLoaivoucher() != null && request.getLoaivoucher() == 1  && request.getGmailkh() != null) {
            for (String emailKH : request.getGmailkh()) {
                Customer customer = customerService.getEntityCustomerByEmail(emailKH);

                if (customer != null) {
                    Email email = new Email();
                    String[] emailSend = {customer.getEmail()};
                    email.setToEmail(emailSend);
                    email.setSubject("Bạn đã nhận được phiếu giảm giá từ TheHands!");
                    email.setTitleEmail("Mã giảm giá đặc biệt dành cho bạn!");
                    email.setBody("<!DOCTYPE html>\n" +
                            "<html lang=\"vi\">\n" +
                            "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; padding: 20px;\">\n" +
                            "    <div style=\"background-color: white; padding: 20px; border-radius: 10px;\">\n" +
                            "        <h2 style=\"color: #333;\">Xin chào, " + customer.getFullName() + "!</h2>\n" +
                            "        <p style=\"color: #555;\">Cảm ơn bạn đã tin tưởng và sử dụng dịch vụ của TheHands. Chúng tôi dành tặng bạn một mã giảm giá đặc biệt!</p>\n" +
                            "        <p><strong>Mã voucher:</strong> " + generatedVoucherCode + "</p>\n" +
                            "        <p><strong>Giá trị giảm:</strong> " + request.getDiscountValue() + " " + request.getDiscountValueType() + "</p>\n" +
                            "        <p><strong>Giá trị giảm tối đa:</strong> " + request.getDiscountMaxValue() + "</p>\n" +
                            "        <p><strong>Áp dụng cho đơn hàng từ:</strong> " + request.getBillMinValue() + " VNĐ</p>\n" +
                            "        <p><strong>Thời gian sử dụng:</strong> " + request.getStartDate() + " - " + request.getEndDate() + "</p>\n" +
                            "        <p style=\"color: #555;\">Hãy mua hàng để sử dụng phiếu giảm giá!</p>\n" +
                            "    </div>\n" +
                            "</body>\n" +
                            "</html>\n");

                    emailSender.sendEmail(email);

                }
            }
        }
        return voucherRepository.save(voucher);
    }

    @Override
    public VoucherReponse updateVoucher(VoucherRequest request, int id) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
        StatusVoucher saStatusVoucher = checkVoucherStatus(request.getStartDate(), request.getEndDate());
        // Giữ nguyên mã voucher cũ
        String existingVoucherCode = voucher.getVoucherCode();
//        voucher.setId(request.getId());
//        voucher.setVoucherCode(request.getVoucherCode());
        voucher.setVoucherName(request.getVoucherName());
        voucher.setQuantity(request.getQuantity());
        voucher.setVoucherType(request.getVoucherType());
        voucher.setDiscountValue(request.getDiscountValue());
        voucher.setDiscountMaxValue(request.getDiscountMaxValue());
        voucher.setBillMinValue(request.getBillMinValue());
        voucher.setStartDate(request.getStartDate());
        voucher.setEndDate(request.getEndDate());
        voucher.setStatusVoucher(saStatusVoucher);
        voucher.setVoucherType(request.getVoucherType());
        voucher.setDiscountValueType(request.getDiscountValueType());
        // Nếu là voucher dành riêng cho khách hàng (loaivoucher == 1), gửi email thông báo
        if (request.getLoaivoucher() != null && request.getLoaivoucher() == 1 && request.getGmailkh() != null) {
            for (String emailKH : request.getGmailkh()) {
                Customer customer = customerService.getEntityCustomerByEmail(emailKH);

                if (customer != null) {
                    Email email = new Email();
                    String[] emailSend = {customer.getEmail()};
                    email.setToEmail(emailSend);
                    email.setSubject("Phiếu giảm giá của bạn có thay đổi từ TheHands!");
                    email.setTitleEmail("Mã giảm giá đặc biệt dành cho bạn!");
                    email.setBody("<!DOCTYPE html>\n" +
                            "<html lang=\"vi\">\n" +
                            "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; padding: 20px;\">\n" +
                            "    <div style=\"background-color: white; padding: 20px; border-radius: 10px;\">\n" +
                            "        <h2 style=\"color: #333;\">Xin chào, " + customer.getFullName() + "!</h2>\n" +
                            "        <p style=\"color: #555;\">Cảm ơn bạn đã tin tưởng và sử dụng dịch vụ của TheHands. Chúng tôi dành tặng bạn một mã giảm giá đặc biệt!</p>\n" +
                            "        <p><strong>Mã voucher:</strong> " + existingVoucherCode + "</p>\n" + // Giữ nguyên mã cũ
                            "        <p><strong>Giá trị giảm:</strong> " + request.getDiscountValue() + " " + request.getDiscountValueType() + "</p>\n" +
                            "        <p><strong>Giá trị giảm tối đa:</strong> " + request.getDiscountMaxValue() + "</p>\n" +
                            "        <p><strong>Áp dụng cho đơn hàng từ:</strong> " + request.getBillMinValue() + " VNĐ</p>\n" +
                            "        <p><strong>Thời gian sử dụng:</strong> " + request.getStartDate() + " - " + request.getEndDate() + "</p>\n" +
                            "        <p style=\"color: #555;\">Hãy mua hàng để sử dụng phiếu giảm giá!</p>\n" +
                            "    </div>\n" +
                            "</body>\n" +
                            "</html>\n");

                    emailSender.sendEmail(email);
                }
            }
        }

        voucherRepository.save(voucher);

        return VoucherReponse.builder()
                .id(voucher.getId())
                .voucherCode(existingVoucherCode) // Giữ nguyên mã cũ
                .voucherName(voucher.getVoucherName())
                .quantity(voucher.getQuantity())
                .voucherType(voucher.getVoucherType())
                .discountValue(voucher.getDiscountValue())
                .discountMaxValue(voucher.getDiscountMaxValue())
                .billMinValue(voucher.getBillMinValue())
                .startDate(voucher.getStartDate())
                .endDate(voucher.getEndDate())
                .statusVoucher(voucher.getStatusVoucher())
                .discountValueType(request.getDiscountValueType())
                .build();
    }



    @Override
    public String deleteVoucher(int id) {
        if (!voucherRepository.findById(id).isEmpty()) {
            voucherRepository.deleteById(id);
            return "Xoa voucher thanh cong";
        } else {
            return "voucher khong ton tai";
        }
    }

    @Override
    public VoucherReponse getVoucherDetail(int id) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
        return VoucherReponse.builder()
                .id(voucher.getId())
                .voucherCode(voucher.getVoucherCode())
                .voucherName(voucher.getVoucherName())
                .quantity(voucher.getQuantity())
                .voucherType(voucher.getVoucherType())
                .discountValue(voucher.getDiscountValue())
                .discountMaxValue(voucher.getDiscountMaxValue())
                .billMinValue(voucher.getBillMinValue())
                .startDate(voucher.getStartDate())
                .endDate(voucher.getEndDate())
                .statusVoucher(voucher.getStatusVoucher())
                .build();
    }
    public Page<VoucherReponse> getAllVoucher(Pageable pageable) {



        return voucherRepository.findAll(pageable).map(voucher ->
                VoucherReponse.builder()
                        .id(voucher.getId())
                        .voucherCode(voucher.getVoucherCode())
                        .voucherName(voucher.getVoucherName())
                        .quantity(voucher.getQuantity())
                        .voucherType(voucher.getVoucherType())
                        .discountValue(voucher.getDiscountValue())
                        .discountMaxValue(voucher.getDiscountMaxValue())
                        .billMinValue(voucher.getBillMinValue())
                        .startDate(voucher.getStartDate())
                        .endDate(voucher.getEndDate())
                        .statusVoucher(voucher.getStatusVoucher())
                        .discountValueType(voucher.getDiscountValueType())
                        .build()
        );
    }

    @Autowired
    EmailSender emailSender;
    @Override
    public Boolean register(RegisterRequest request) {



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
        //        if (request.getLoaivoucher()!=null) {
//
//
//            if (request.getLoaivoucher() == 1) {
//                for (String lkh : request.getGmailkh()
//                ) {
//                    Email email = new Email();
//                    String[] emailSend = {lkh};
//                    email.setToEmail(emailSend);
//                    email.setSubject("Tạo tài khoản thành công");
//                    email.setTitleEmail("");
//                    email.setBody("<!DOCTYPE html>\n" +
//                            "<html lang=\"en\">\n" +
//                            "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; margin: 50px;\">\n" +
//                            "\n" +
//                            "    <div class=\"success-message\" style=\"background-color: #FFFFF; color: black; padding: 20px; border-radius: 10px; margin-top: 50px;\">\n" +
//                            "        <h2 style=\"color: #333;\">Chúng tôi tặng bạn 1 phiếu giảm giá</h2>\n" +
//                            "        <p style=\"color: #555;\">Cảm ơn bạn đã mua hàng tại TheHands. Dưới đây là thông tin phiếu giảm giá của bạn của bạn:</p>\n" +
//                            "        <p><strong>Min:</strong> " + lkh + "</p>\n" +
//                            "        <p><strong>Max:</strong> " + lkh + "</p>\n" +
//                            "        <p style=\"color: #555;\">Đăng nhập ngay để trải nghiệm!</p>\n" +
//                            "    </div>\n" +
//                            "\n" +
//                            "</body>\n" +
//                            "</html>\n");


        emailSender.sendEmail(email);
        return true;
    }

}

