package com.poly.app.domain.admin.voucher.service.impl;

import com.poly.app.domain.admin.bill.service.BillService;
import com.poly.app.domain.admin.voucher.request.voucher.VoucherRequest;
import com.poly.app.domain.admin.voucher.response.VoucherReponse;
import com.poly.app.domain.admin.voucher.service.VoucherService;
import com.poly.app.domain.auth.request.RegisterRequest;
import com.poly.app.domain.model.Customer;
import com.poly.app.domain.admin.customer.service.CustomerService;

import com.poly.app.domain.model.CustomerVoucher;
import com.poly.app.domain.model.StatusEnum;
import com.poly.app.domain.model.Voucher;
import com.poly.app.domain.repository.CustomerVoucherRepository;
import com.poly.app.domain.repository.VoucherRepository;
import com.poly.app.infrastructure.constant.DiscountType;
import com.poly.app.infrastructure.constant.VoucherType;
import com.poly.app.infrastructure.email.Email;
import com.poly.app.infrastructure.email.EmailSender;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VoucherServiceImpl implements VoucherService {
    @Autowired
    CustomerService customerService;
    VoucherRepository voucherRepository;
    private CustomerVoucherRepository customerVoucherRepository;
    private final BillService billService;

    @Override
    public List<VoucherReponse> getAllVoucher() {
        return voucherRepository.findAll().stream()
                .map(voucher -> VoucherReponse.formEntity(voucher)).toList();
    }

    public StatusEnum checkVoucherStatus(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime currentDate = LocalDateTime.now(); // Lấy thời gian hiện tại

        if (currentDate.isBefore(startDate)) {
            return StatusEnum.chua_kich_hoat; // Chưa kích hoạt
        } else if (currentDate.isAfter(endDate)) {
            return StatusEnum.ngung_kich_hoat; // Đã ngừng kích hoạt
        } else {
            return StatusEnum.dang_kich_hoat; // Đang kích hoạt
        }
    }

    @Override
    public Voucher createVoucher(VoucherRequest request) {

        StatusEnum saStatusVoucher = checkVoucherStatus(request.getStartDate(), request.getEndDate());
        // Sinh mã voucher tự động (định nghĩa logic trong createVoucher)
        String generatedVoucherCode = "MGG" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Voucher voucher = Voucher.builder()
                .id(request.getId())
                .voucherCode(generatedVoucherCode)
                .quantity(request.getQuantity())
                .voucherName(request.getVoucherName())
                .voucherType(request.getVoucherType())
                .discountType(request.getDiscountType())
                .discountValue(request.getDiscountValue())
                .discountMaxValue(request.getDiscountMaxValue())
                .billMinValue(request.getBillMinValue())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .statusVoucher(saStatusVoucher)
                .build();
        voucherRepository.save(voucher);

        if (request.getVoucherType() != null && request.getVoucherType() == VoucherType.PRIVATE && request.getGmailkh() != null) {
            for (String emailKH : request.getGmailkh()) {
                Customer customer = customerService.getEntityCustomerByEmail(emailKH);
                CustomerVoucher customerVoucher = CustomerVoucher.builder()
                        .customer(customer)
                        .quantity(request.getQuantity())
                        .voucher(voucher).build();
                customerVoucherRepository.save(customerVoucher);

                // Đưa ra luồng khác
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
                            "        <p><strong>Giá trị giảm:</strong> " + request.getDiscountValue() + " " + request.getDiscountType() + "</p>\n" +
                            "        <p><strong>Giá trị giảm tối đa:</strong> " + request.getDiscountMaxValue() + "Đ</p>\n" +
                            "        <p><strong>Áp dụng cho đơn hàng từ:</strong> " + request.getBillMinValue() + " Đ</p>\n" +
                            "        <p><strong>Thời gian sử dụng:</strong> " + request.getStartDate() + " - " + request.getEndDate() + "</p>\n" +
                            "        <p style=\"color: #555;\">Hãy mua hàng để sử dụng phiếu giảm giá!</p>\n" +
                            "    </div>\n" +
                            "</body>\n" +
                            "</html>\n");

                    emailSender.sendEmail(email);

                }
            }
        }

        return voucher;

    }
    //phần test update

    @Override
    @Transactional
    public VoucherReponse updateVoucher(VoucherRequest request, int id) {
        String generatedVoucherCode = "MGG" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID không tồn tại"));

        // Giữ nguyên mã voucher cũ
        String existingVoucherCode = voucher.getVoucherCode();

        // Danh sách khách hàng trước khi cập nhật
        List<CustomerVoucher> oldCustomerVouchers = customerVoucherRepository.findCustomerVouchersByVoucher(voucher);
        Set<String> oldCustomerEmails = oldCustomerVouchers.stream()
                .map(cv -> cv.getCustomer().getEmail())
                .collect(Collectors.toSet());


        // Cập nhật thông tin voucher
        voucher.setVoucherName(request.getVoucherName());
        voucher.setQuantity(request.getQuantity());
        voucher.setVoucherType(request.getVoucherType());
        voucher.setDiscountType(request.getDiscountType());
        voucher.setDiscountValue(request.getDiscountValue());
        voucher.setDiscountMaxValue(request.getDiscountMaxValue());
        voucher.setBillMinValue(request.getBillMinValue());
        voucher.setStartDate(request.getStartDate());
        voucher.setEndDate(request.getEndDate());
        voucher.setStatusVoucher(checkVoucherStatus(request.getStartDate(), request.getEndDate()));
        voucher.setDiscountValueType(request.getDiscountValueType());

        // Danh sách khách hàng mới
        Set<String> newCustomerEmails = request.getGmailkh() != null ? new HashSet<>(request.getGmailkh()) : new HashSet<>();

        // Tìm khách hàng mới được thêm vào
        Set<String> addedCustomers = new HashSet<>(newCustomerEmails);
        addedCustomers.removeAll(oldCustomerEmails);

        // Tìm khách hàng bị xóa khỏi danh sách
        Set<String> removedCustomers = new HashSet<>(oldCustomerEmails);
        removedCustomers.removeAll(newCustomerEmails);

        // Gửi email cho khách hàng mới được thêm vào danh sách
        for (String emailKH : addedCustomers) {
            Customer customer = customerService.getEntityCustomerByEmail(emailKH);
            if (customer != null) {
                CustomerVoucher customerVoucher = CustomerVoucher.builder()
                        .customer(customer)
                        .quantity(request.getQuantity())
                        .voucher(voucher)
                        .build();
                customerVoucherRepository.save(customerVoucher);

                Email email = new Email();
                email.setToEmail(new String[]{customer.getEmail()});
                email.setSubject("Bạn đã nhận được phiếu giảm giá từ TheHands!");
                email.setTitleEmail("Mã giảm giá đặc biệt dành cho bạn!");
                email.setBody("<!DOCTYPE html>\n" +
                        "<html lang=\"vi\">\n" +
                        "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; padding: 20px;\">\n" +
                        "    <div style=\"background-color: white; padding: 20px; border-radius: 10px;\">\n" +
                        "        <h2 style=\"color: #333;\">Xin chào, " + customer.getFullName() + "!</h2>\n" +
                        "        <p style=\"color: #555;\">Cảm ơn bạn đã tin tưởng và sử dụng dịch vụ của TheHands. Chúng tôi dành tặng bạn một mã giảm giá đặc biệt!</p>\n" +
                        "        <p><strong>Mã voucher:</strong> " + generatedVoucherCode + "</p>\n" +
                        "        <p><strong>Giá trị giảm:</strong> " + request.getDiscountValue() + " " + request.getDiscountType() + "</p>\n" +
                        "        <p><strong>Giá trị giảm tối đa:</strong> " + request.getDiscountMaxValue() + "Đ</p>\n" +
                        "        <p><strong>Áp dụng cho đơn hàng từ:</strong> " + request.getBillMinValue() + " Đ</p>\n" +
                        "        <p><strong>Thời gian sử dụng:</strong> " + request.getStartDate() + " - " + request.getEndDate() + "</p>\n" +
                        "        <p style=\"color: #555;\">Hãy mua hàng để sử dụng phiếu giảm giá!</p>\n" +
                        "    </div>\n" +
                        "</body>\n" +
                        "</html>\n");
                emailSender.sendEmail(email);
            }
        }


        // Gửi email thông báo thay đổi cho khách hàng cũ (không bao gồm khách hàng mới)
        for (CustomerVoucher customerVoucher : oldCustomerVouchers) {
            Customer customer = customerVoucher.getCustomer();
            if (customer != null && !addedCustomers.contains(customer.getEmail()) && !removedCustomers.contains(customer.getEmail())) {
                Email email = new Email();
                email.setToEmail(new String[]{customer.getEmail()});
                email.setSubject("Phiếu giảm giá của bạn có thay đổi từ TheHands!");
                email.setTitleEmail("Mã giảm đã sửa biệt dành cho bạn!");
                email.setBody(
                        "<!DOCTYPE html>\n" +
                                "<html lang=\"vi\">\n" +
                                "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; padding: 20px;\">\n" +
                                "    <div style=\"background-color: white; padding: 20px; border-radius: 10px;\">\n" +
                                "        <h2 style=\"color: #333;\">Xin chào, " + customer.getFullName() + "!</h2>\n" +
                                "        <p style=\"color: #555;\"> Chúng tôi có thay đổi về mã giảm giá của bạn!</p>\n" +
                                "        <p><strong>Mã voucher:</strong> " + existingVoucherCode + "</p>\n" +
                                "        <p><strong>Giá trị giảm:</strong> " + request.getDiscountValue() + " " + request.getDiscountType() + "</p>\n" +
                                "        <p><strong>Giá trị giảm tối đa:</strong> " + request.getDiscountMaxValue() + "</p>\n" +
                                "        <p><strong>Áp dụng cho đơn hàng từ:</strong> " + request.getBillMinValue() + " VNĐ</p>\n" +
                                "        <p><strong>Thời gian sử dụng:</strong> " + request.getStartDate() + " - " + request.getEndDate() + "</p>\n" +

                                "    </div>\n" +
                                "</body>\n" +
                                "</html>\n");
                emailSender.sendEmail(email);
            }
        }

        // Gửi email hủy voucher cho khách hàng bị xóa khỏi danh sách
        for (String emailKH : removedCustomers) {
            Customer customer = customerService.getEntityCustomerByEmail(emailKH);
            if (customer != null) {
                customerVoucherRepository.deleteByCustomerAndVoucher(customer, voucher);

                Email email = new Email();
                email.setToEmail(new String[]{customer.getEmail()});
                email.setSubject("Phiếu giảm giá của bạn đã bị hủy từ TheHands!");
                email.setTitleEmail("Mã giảm giá đã bị hủy");
                email.setBody(
                        "<!DOCTYPE html>\n" +
                                "<html lang=\"vi\">\n" +
                                "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; padding: 20px;\">\n" +
                                "    <div style=\"background-color: white; padding: 20px; border-radius: 10px;\">\n" +
                                "        <h2 style=\"color: #333;\">Xin chào, " + customer.getFullName() + "!</h2>\n" +
                                "        <p style=\"color: #555;\"> Chúng tôi rất tiếc phải hủy phiếu giảm giá của bạn!</p>\n" +
                                "        <p><strong>Mã voucher:</strong> " + existingVoucherCode + "</p>\n" +
                                "        <p><strong>Giá trị giảm:</strong> " + request.getDiscountValue() + " " + request.getDiscountType() + "</p>\n" +
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

        // Lưu voucher đã cập nhật
        voucherRepository.save(voucher);

        return VoucherReponse.builder()
                .id(voucher.getId())
                .voucherCode(existingVoucherCode) // Giữ nguyên mã cũ
                .voucherName(voucher.getVoucherName())
                .quantity(voucher.getQuantity())
                .voucherType(voucher.getVoucherType())
                .discountType(voucher.getDiscountType())
                .discountValue(voucher.getDiscountValue())
                .discountMaxValue(voucher.getDiscountMaxValue())
                .billMinValue(voucher.getBillMinValue())
                .startDate(voucher.getStartDate())
                .endDate(voucher.getEndDate())
                .statusVoucher(voucher.getStatusVoucher())
                .discountValueType(request.getDiscountValueType())
                .build();
    }

    //hết

    @Override
    @Transactional
    public String deleteVoucher(int id) {
        // Kiểm tra voucher có tồn tại không
        Optional<Voucher> optionalVoucher = voucherRepository.findById(id);
        if (optionalVoucher.isPresent()) {
            // Xóa tất cả bản ghi trong bảng customer_voucher có voucher_id = id
            customerVoucherRepository.deleteByVoucherId(id);

            // Xóa voucher sau khi đã xóa quan hệ
            voucherRepository.deleteById(id);

            return "Xóa voucher thành công";
        } else {
            return "Voucher không tồn tại";
        }
    }


    @Override
    public VoucherReponse getVoucherDetail(int id) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));

        List<CustomerVoucher> customers = customerVoucherRepository.findCustomerVouchersByVoucher(voucher);
        List<Customer> customerList = customers.stream().map(CustomerVoucher::getCustomer).collect(Collectors.toList());
        List<Integer> customerIds = customerList.stream().map(customer -> customer.getId()).collect(Collectors.toList());

        VoucherReponse voucherReponse = VoucherReponse.formEntity(voucher);
        voucherReponse.setCustomerIds(customerIds);

        return voucherReponse;
    }

    public Page<VoucherReponse> getAllVoucher(Pageable pageable) {

        return voucherRepository.findAll(pageable).map(VoucherReponse::formEntity
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
        emailSender.sendEmail(email);
        return true;
    }

    @Override
    public String switchStatus(Integer id, StatusEnum status) {
        LocalDateTime currentDate = LocalDateTime.now(); // Lấy thời gian hiện tại
        Voucher voucher = voucherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID không tồn tại"));

        if (status == StatusEnum.ngung_kich_hoat) {
            voucher.setStatusVoucher(StatusEnum.ngung_kich_hoat);
            voucher.setEndDate(currentDate);
            voucherRepository.save(voucher);
            return "ngung_kich_hoat";
        } else {
            voucher.setStatusVoucher(StatusEnum.dang_kich_hoat);
            voucher.setStartDate(currentDate);
            voucherRepository.save(voucher);
            return "dang_kich_hoat";
        }


    }

    @Override
    public List<VoucherReponse> getAllVouchersWithCustomer(Integer customerId) {
        List<CustomerVoucher> customerVouchers = Optional.ofNullable(
                customerVoucherRepository.findCustomerVouchersByCustomerId(customerId)
        ).orElse(List.of()); // Nếu null thì trả về danh sách rỗng tránh NullPointerException

        List<Voucher> validVouchers = customerVouchers.stream()
                .map(CustomerVoucher::getVoucher)
                .filter(voucher -> voucher.getQuantity() > 0)
                .filter(voucher -> voucher.getStartDate().isBefore(LocalDateTime.now()) || voucher.getStartDate().isEqual(LocalDateTime.now()))
                .filter(voucher -> voucher.getEndDate().isAfter(LocalDateTime.now()) || voucher.getEndDate().isEqual(LocalDateTime.now()))
                .filter(voucher -> voucher.getStatusVoucher() == StatusEnum.dang_kich_hoat)
                .collect(Collectors.toList());

        List<VoucherReponse> voucherReponses = validVouchers.stream()
                .map(VoucherReponse::formEntity)
                .collect(Collectors.toList());

        List<VoucherReponse> voucherReponsePublic = Optional.ofNullable(
                billService.getAllVoucherResponse()
        ).orElse(List.of()); // Xử lý trường hợp billService trả về null

        // Hợp hai danh sách
        List<VoucherReponse> mergedVouchers = new ArrayList<>(voucherReponses);
        mergedVouchers.addAll(voucherReponsePublic);

        return mergedVouchers;
    }

    // Tìm voucher theo tên
    @Override
    public List<VoucherReponse> searchVoucherByName(String voucherName) {
        return voucherRepository.findByVoucherNameContainingIgnoreCase(voucherName)
                .stream()
                .map(VoucherReponse::formEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<VoucherReponse> searchVoucherByStatus(StatusEnum statusVoucher) {
        return voucherRepository.findByStatusVoucher(statusVoucher)
                .stream()
                .map(VoucherReponse::formEntity)
                .collect(Collectors.toList());
    }

    // 🔍 Tìm voucher theo số lượng
    @Override
    public List<VoucherReponse> searchVoucherByQuantity(Integer quantity) {
        return voucherRepository.findByQuantity(quantity)
                .stream()
                .map(VoucherReponse::formEntity)
                .collect(Collectors.toList());
    }

    // 🔍 Tìm voucher theo loại
    @Override
    public List<VoucherReponse> searchVoucherByType(VoucherType voucherType) {
        return voucherRepository.findByVoucherType(voucherType)
                .stream()
                .map(VoucherReponse::formEntity)
                .collect(Collectors.toList());
    }

    // 🔍 Tìm voucher theo khoảng giá trị giảm tối đa
    @Override
    public List<VoucherReponse> searchVoucherByDiscountMaxRange(Double minDiscount, Double maxDiscount) {
        return voucherRepository.findByDiscountMaxValueBetween(minDiscount, maxDiscount)
                .stream()
                .map(VoucherReponse::formEntity)
                .collect(Collectors.toList());
    }

    // 🔍 Tìm voucher theo khoảng giá trị hóa đơn tối thiểu
    @Override
    public List<VoucherReponse> searchVoucherByBillMinRange(Double minBill, Double maxBill) {
        return voucherRepository.findByBillMinValueBetween(minBill, maxBill)
                .stream()
                .map(VoucherReponse::formEntity)
                .collect(Collectors.toList());
    }


    // 🔍 Tìm voucher theo khoảng ngày bắt đầu và kết thúc
    @Override
    public List<VoucherReponse> searchVoucherByStartDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return voucherRepository.findByStartDateBetween(startDate, endDate)
                .stream()
                .map(VoucherReponse::formEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Page<VoucherReponse> getPageVoucher(int size, int page, StatusEnum statusVoucher, String search, String startDate, String endDate, VoucherType voucherType, DiscountType discountType) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageable = PageRequest.of(page, size, sort);

        Specification<Voucher> spec = Specification.where(null);

        // Lọc theo trạng thái voucher
        if (statusVoucher != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("statusVoucher"), statusVoucher));
        }

        // Lọc theo loại voucher
        if (voucherType != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("voucherType"), voucherType));
        }

        // Lọc theo loại giảm giá
        if (discountType != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("discountType"), discountType));
        }

        // Tìm kiếm theo mã hoặc tên voucher
        if (search != null && !search.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.like(root.get("voucherCode"), "%" + search + "%"),
                            criteriaBuilder.like(root.get("voucherName"), "%" + search + "%")
                    ));
        }

        // Lọc theo ngày bắt đầu
        if (startDate != null && !startDate.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"),
                            parseDateTime(startDate)));
        }

        // Lọc theo ngày kết thúc
        if (endDate != null && !endDate.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("endDate"),
                            parseDateTime(endDate))
            );
        }

        Page<Voucher> voucherPage = voucherRepository.findAll(spec, pageable);
        List<VoucherReponse> voucherResponses = voucherPage.getContent().stream()
                .map(VoucherReponse::formEntity)
                .collect(Collectors.toList());

        return new PageImpl<>(voucherResponses, pageable, voucherPage.getTotalElements());
    }

    private  LocalDateTime parseDateTime(String dateTime) {
        return LocalDate.parse(dateTime, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                .atStartOfDay(); // Đặt giờ thành 00:00:0
    }
}

