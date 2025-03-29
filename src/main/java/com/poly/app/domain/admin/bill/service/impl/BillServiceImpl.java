package com.poly.app.domain.admin.bill.service.impl;

import com.poly.app.domain.admin.bill.request.BillDetailRequest;
import com.poly.app.domain.admin.bill.request.BillProductDetailRequest;
import com.poly.app.domain.admin.bill.request.CreateBillRequest;
import com.poly.app.domain.admin.bill.request.UpdateQuantityVoucherRequest;
import com.poly.app.domain.admin.bill.request.UpdateStatusBillRequest;
import com.poly.app.domain.admin.bill.response.BillResponse;
import com.poly.app.domain.admin.bill.response.UpdateBillRequest;
import com.poly.app.domain.admin.bill.service.BillHistoryService;
import com.poly.app.domain.admin.bill.service.BillService;
import com.poly.app.domain.admin.bill.service.WebSocketService;
import com.poly.app.domain.admin.product.response.productdetail.ProductDetailResponse;
import com.poly.app.domain.admin.staff.response.AddressReponse;
import com.poly.app.domain.admin.voucher.response.VoucherReponse;
import com.poly.app.domain.auth.service.AuthenticationService;
import com.poly.app.domain.model.Address;
import com.poly.app.domain.model.Bill;
import com.poly.app.domain.model.BillDetail;
import com.poly.app.domain.model.BillHistory;
import com.poly.app.domain.model.Customer;
import com.poly.app.domain.model.CustomerVoucher;
import com.poly.app.domain.model.PaymentBill;
import com.poly.app.domain.model.PaymentMethods;
import com.poly.app.domain.model.ProductDetail;
import com.poly.app.domain.model.Staff;
import com.poly.app.domain.model.Voucher;
import com.poly.app.domain.repository.AddressRepository;
import com.poly.app.domain.repository.BillDetailRepository;
import com.poly.app.domain.repository.BillHistoryRepository;
import com.poly.app.domain.repository.BillRepository;
import com.poly.app.domain.repository.CustomerRepository;
import com.poly.app.domain.repository.CustomerVoucherRepository;
import com.poly.app.domain.repository.PaymentBillRepository;
import com.poly.app.domain.repository.PaymentMethodsRepository;
import com.poly.app.domain.repository.ProductDetailRepository;
import com.poly.app.domain.repository.StaffRepository;
import com.poly.app.domain.repository.VoucherRepository;
import com.poly.app.infrastructure.constant.BillStatus;
import com.poly.app.infrastructure.constant.PaymentMethodEnum;
import com.poly.app.infrastructure.constant.PaymentMethodsType;
import com.poly.app.infrastructure.constant.TypeBill;
import com.poly.app.infrastructure.constant.VoucherType;
import com.poly.app.infrastructure.exception.ApiException;
import com.poly.app.infrastructure.exception.ErrorCode;
import com.poly.app.infrastructure.security.Auth;
import com.poly.app.infrastructure.util.DateUtil;
import com.poly.app.infrastructure.util.GenHoaDon;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BillServiceImpl implements BillService {

    @Autowired
    BillRepository billRepository;


    @Autowired
    BillHistoryRepository billHistoryRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    AddressRepository addressRepository;


    @Autowired
    BillHistoryService billHistoryService;

    @Autowired
    GenHoaDon genHoaDon;
    @Autowired
    private BillDetailRepository billDetailRepository;
    @Autowired
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private PaymentMethodsRepository paymentMethodsRepository;
    @Autowired
    private PaymentBillRepository paymentBillRepository;
    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private Auth auth;
    @Autowired
    private CustomerVoucherRepository customerVoucherRepository;

    @Autowired
    private WebSocketService webSocketService;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public Page<BillResponse> getPageBill(Integer size, Integer page,
                                          BillStatus statusBill,
                                          TypeBill typeBill,
                                          String search,
                                          String startDate,
                                          String endDate
    ) {
        Sort sort = null;
        if (statusBill == BillStatus.CHO_XAC_NHAN) {
            sort = Sort.by(Sort.Direction.DESC, "createdAt");
        } else {
            sort = Sort.by(Sort.Direction.DESC, "createdAt");
        }

        Pageable pageable = PageRequest.of(page, size, sort);
        // Sử dụng Specification hoặc Predicate để xây dựng các điều kiện động
        Specification<Bill> spec = Specification.where(null);

        // Thêm điều kiện lọc theo `statusBill`
        if (statusBill != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("status"), statusBill));
        }

        // Thêm điều kiện lọc theo `typeBill`
        if (typeBill != null) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("typeBill"), typeBill));
        }

        if (search != null && !search.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                // Thực hiện join tới thực thể Customer
                Join<Object, Object> customerJoin = root.join("customer", JoinType.LEFT);
                // Thêm điều kiện tìm kiếm
                return criteriaBuilder.or(
                        criteriaBuilder.like(customerJoin.get("fullName"), "%" + search + "%"),
                        criteriaBuilder.like(root.get("billCode"), "%" + search + "%")
                );
            });
        }

        if (startDate != null && !startDate.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), DateUtil.parseDateLong(startDate)));
        }
        if (endDate != null && !endDate.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), DateUtil.parseDateLong(endDate)));
        }
        Page<Bill> billPage = billRepository.findAll(spec, pageable);
        List<BillResponse> billResponses = billPage.getContent().stream()
                .map(this::convertBillToBillResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(billResponses, pageable, billPage.getTotalElements());
    }


    @Override
    public BillResponse getBillResponseByBillCode(String billCode) {
        Bill bill = billRepository.findByBillCode(billCode);
        return convertBillToBillResponse(bill);
    }

    @Override
    public Map<String, ?> updateStatusBill(String billCode, UpdateStatusBillRequest request) {

        System.out.println(request);

        Bill bill = billRepository.findByBillCode(billCode);

        Staff staff = staffRepository.findById(1).orElse(null);
        if (bill == null) {
            throw new ApiException(ErrorCode.HOA_DON_NOT_FOUND);
        }
        bill.setStatus(request.getStatus());
        Bill billUpdate = billRepository.save(bill);

        BillHistory billHistory = BillHistory.builder()
                .status(billUpdate.getStatus()).
                description(request.getNote()).
                staff(staff)
                .bill(billUpdate).build();
        billHistoryRepository.save(billHistory);

        return Map.of(
                "bill", billUpdate,
                "billHistory", billHistoryService.findBillHistoryResponseBuilderListByBillCode(billCode));

    }

    @Override
    @Transactional // Bên admin
    public BillResponse updateBillInfo(String billCode, UpdateBillRequest request) {
        Bill bill = billRepository.findByBillCode(billCode);
        bill.setNumberPhone(request.getCustomerPhone());
        bill.setNotes(request.getNote());
        bill.setCustomerName(request.getCustomerName());
        bill.setEmail(request.getEmail());

        Address newAddress = bill.getShippingAddress();
        if (newAddress == null) {
            newAddress = new Address();
        }
        newAddress.setDistrictId(request.getDistrictId());
        newAddress.setProvinceId(request.getProvinceId());
        newAddress.setSpecificAddress(request.getSpecificAddress());
        newAddress.setWardId(request.getWardId());
        bill.setShippingAddress(newAddress);

        if (newAddress.getId() == null) {
            addressRepository.save(newAddress);  // Lưu mới nếu chưa có ID
        }
        Staff staff = authenticationService.getStaffAuth();
        BillHistory billHistory = BillHistory
                .builder()
                .staff(staff)
                .bill(bill)
                .status(bill.getStatus())
                .description("Cập nhật thông tin khách hàng")
                .build();
        billHistoryRepository.save(billHistory);
        billRepository.save(bill);
        return convertBillToBillResponse(bill);
    }

    @Override
    public File printBillById(String billCode) {
        Bill bill = billRepository.findByBillCode(billCode);
        BillHistory billHistory = billHistoryRepository.findDistinctFirstByBillOrderByCreatedAtDesc(bill);
        List<BillDetail> lstBillDetail = billDetailRepository.findByBill(bill);
        return genHoaDon.genHoaDon(bill, lstBillDetail, billHistory);
    }

    @Override
    @Transactional
    public BillResponse createBill(CreateBillRequest request) {
        Customer customer = null;

        Staff staffAuth = auth.getStaffAuth();
        Customer customerAuth = auth.getCustomerAuth();

        if (request.getCustomerId() != null) {
            customer = customerRepository.findById(request.getCustomerId()).orElse(null);
        }
        Voucher voucher = null;
        if (request.getVoucherId() != null) {
            voucher = voucherRepository.findById(request.getVoucherId()).orElse(null);
            if (voucher != null) {
                voucher.setQuantity(voucher.getQuantity() - 1);
                voucherRepository.save(voucher);
            }
        }
        Address address = null;
        if (request.getShippingAddressId() != null) {
            address = addressRepository.findById(request.getShippingAddressId()).orElseThrow(()
                    -> new ApiException(ErrorCode.HOA_DON_NOT_FOUND));
        }

        if (request.getAddress() != null) {
            Address newAddress = Address
                    .builder()
                    .wardId(request.getAddress().getWardId())
                    .specificAddress(request.getAddress().getSpecificAddress())
                    .provinceId(request.getAddress().getProvinceId())
                    .districtId(request.getAddress().getDistrictId())
                    .customer(customer) // Khách hàng -> có thể k phải là khách hàng đăng nhập
                    .build();
            address = addressRepository.save(newAddress);
        }

        Bill bill = Bill
                .builder()
                .typeBill(request.getTypeBill())
                .customer(customer) // Cũng có thể là khách hàng đang đăng nhập hoặc chưa đăng nhập
                .customerMoney(request.getCustomerMoney()) // Tiền khách đưa cho cửa hàng => lấy ra tiền thừa = tiền khách đưa trừ cho tiền cần thanh toán
                .discountMoney(request.getDiscountMoney())
                .moneyAfter(request.getMoneyAfter())
                .totalMoney(request.getTotalMoney() - request.getDiscountMoney()) // Sẽ là tổng tiền hàng trừ cho giảm giá
                .moneyBeforeDiscount(request.getTotalMoney() + request.getDiscountMoney()) // Tiền trước khi được giảm giá
                .shipDate(request.getShipDate())
                .shipMoney(request.getShipMoney())
                .totalMoney(request.getTotalMoney())
                .completeDate(request.getCompleteDate())
                .shippingAddress(address)
                .shipDate(request.getShipDate())
                .numberPhone(request.getNumberPhone())
                .shipMoney(request.getShipMoney())
                .voucher(voucher)
                .build();
        // Nếu đặt hàng online thì ===


        // Trường hợp người dùng ship
        if (request.getIsShipping()) {
            if(request.getIsCOD()) {
                bill.setStatus(BillStatus.DA_XAC_NHAN);
            }else {
                bill.setStatus(BillStatus.CHO_VAN_CHUYEN);
            }
        } else {
            bill.setStatus(BillStatus.DA_HOAN_THANH);
        }

        Bill billSave = billRepository.save(bill);

        for (BillDetailRequest billDetailRequest : request.getBillDetailRequests()) {
            ProductDetail productDetail = productDetailRepository.findById(billDetailRequest.getProductDetailId()).orElse(null);
            if (productDetail != null) {
                BillDetail billDetail = BillDetail
                        .builder()
                        .productDetail(productDetail)
                        .bill(billSave)
                        .price(billDetailRequest.getPrice())
                        .totalMoney(billDetailRequest.getPrice() * billDetailRequest.getQuantity())
                        .quantity(billDetailRequest.getQuantity())
                        .build();
                billDetailRepository.save(billDetail);
            }
        }

        if (request.getIsCashAndBank()) {
            PaymentMethods cashPaymentMethods = createAndSavePaymentMethod(
                    PaymentMethodEnum.TIEN_MAT
            );
            PaymentMethods bankPaymentMethods = createAndSavePaymentMethod(
                    PaymentMethodEnum.CHUYEN_KHOAN);
            savePaymentBill(
                    billSave,
                    cashPaymentMethods,
                    request.getCashCustomerMoney(),
                    "",
                    request.getNotes());
            savePaymentBill(billSave,
                    bankPaymentMethods,
                    request.getBankCustomerMoney(),
                    request.getTransactionCode(),
                    request.getNotes());
        } else if (request.getCashCustomerMoney() != null) {
            PaymentMethods cashPaymentMethods =
                    createAndSavePaymentMethod( PaymentMethodEnum.TIEN_MAT);
            savePaymentBill(
                    billSave,
                    cashPaymentMethods,
                    request.getCashCustomerMoney(), "", request.getNotes()
            );
        } else if (request.getBankCustomerMoney() != null) {
            PaymentMethods bankPayment =
                    createAndSavePaymentMethod(PaymentMethodEnum.CHUYEN_KHOAN);
            savePaymentBill(billSave, bankPayment,
                    request.getCashCustomerMoney(), "",
                    request.getNotes());
        }
        handleSaveBillHistory(billSave, customer, staffAuth, request, customerAuth);


        return convertBillToBillResponse(billSave);
    }

    private void handleSaveBillHistory(Bill billSave,
                                       Customer customer,
                                       Staff staff,
                                       CreateBillRequest request,
                                       Customer customerAuth
    ) {
        // TH 1 Đặt hàng online
        // TH 2 MUA HÀNG TẠI QUẦY
        if (request.getIsCOD()) {
            BillHistory billHistory = BillHistory
                    .builder()
                    .customer(customer)
                    .staff(staff)
                    .bill(billSave)
                    .status(BillStatus.CHO_XAC_NHAN)
                    .build();

            billHistoryRepository.save(billHistory);
            return;
        }

        BillHistory billHistory_1 = BillHistory
                .builder()
                .customer(customer)
                .staff(staff)
                .bill(billSave)
                .status(BillStatus.DA_THANH_TOAN)
                .build();
        BillHistory billHistory_2;
        if (request.getIsShipping()) {
            billHistory_2 = BillHistory
                    .builder()
                    .customer(customer)
                    .staff(staff)
                    .bill(billSave)
                    .status(BillStatus.CHO_VAN_CHUYEN)
                    .build();
        } else {
            billHistory_2 = BillHistory
                    .builder()
                    .customer(customer)
                    .staff(staff)
                    .bill(billSave)
                    .status(BillStatus.DA_HOAN_THANH)
                    .build();
        }
        billHistoryRepository.saveAll(List.of(billHistory_1, billHistory_2));

    }

    @Override
    @Transactional
    public void updateProductQuantity(List<BillProductDetailRequest> requests) {
        log.info("updateProductQuantity {}", requests.toString());
        requests.forEach(request -> {
            ProductDetail productDetail = productDetailRepository.
                    findById(request.getId()).orElse(null);
            if (productDetail != null) {
                productDetail.setQuantity(request.getQuantity());
              ProductDetail productDetailSave =  productDetailRepository.save(productDetail);
              webSocketService.sendProductUpdate(ProductDetailResponse.fromEntity(productDetailSave));
            }
        });

    }

    @Override
    public List<VoucherReponse> getAllVoucherResponse() {
        LocalDateTime now = LocalDateTime.now();
        List<Voucher> vouchers = voucherRepository
                .findByStartDateBeforeAndEndDateAfterAndQuantityGreaterThan(now, now, 0);
        List<VoucherReponse> voucherReponses = vouchers.stream().map(voucher -> VoucherReponse.formEntity(voucher)).toList();
        return voucherReponses;
    }

    @Override
    public List<VoucherReponse> getAllVoucherResponseByCustomerId(Integer customerId) {
        List<CustomerVoucher> customerVouchers = customerVoucherRepository.findCustomerVouchersByCustomerId(customerId);
        List<Voucher> vouchers = customerVouchers.stream().map(customerVoucher -> customerVoucher.getVoucher()).toList();
        List<VoucherReponse> voucherReponses = vouchers.stream().map(voucher -> VoucherReponse.formEntity(voucher)).toList();
        return voucherReponses;
    }

    @Override
    public VoucherReponse updateQuantityVoucher(UpdateQuantityVoucherRequest request) {
        Voucher voucher = voucherRepository.findById(request.getId()).orElseThrow(
                () -> new RuntimeException("voucher not found"));
        voucher.setQuantity(request.getQuantity());
        Voucher voucherSave = voucherRepository.save(voucher);
        VoucherReponse voucherReponse = VoucherReponse.formEntity(voucher);
        return voucherReponse;
    }

    // Để nguyên
    private PaymentMethods createAndSavePaymentMethod(PaymentMethodEnum methodEnum) {
        PaymentMethods paymentMethods = PaymentMethods.builder()
                .paymentMethodsType(PaymentMethodsType.THANH_TOAN_TRUOC)
                .paymentMethod(methodEnum)
                .build();
        return paymentMethodsRepository.save(paymentMethods);
    }

    private void savePaymentBill(Bill bill, PaymentMethods paymentMethods,
                                 Double totalMoney,
                                 String transactionCode,
                                 String notesPayment
    ) {
        if (paymentMethods == null) return;

        PaymentBill paymentBill = PaymentBill.builder()
                .bill(bill)
                .totalMoney(totalMoney)
                .transactionCode(transactionCode)
                .notes(notesPayment)
                .paymentMethods(paymentMethods)
                .build();

        paymentBillRepository.save(paymentBill);
    }


    private BillResponse convertBillToBillResponse(Bill bill) {
        AddressReponse addressReponse;
        if(bill.getShippingAddress() == null) {
            addressReponse = null;
        }else {
             addressReponse   = new AddressReponse(bill.getShippingAddress());
        }
        VoucherReponse voucher = null;
        if(bill.getVoucher() != null) {
            voucher  = VoucherReponse.formEntity(bill.getVoucher());
        }

        return BillResponse.builder()
                .billCode(bill.getBillCode())
                .customerName(bill.getCustomerName())
                .customerPhone(bill.getNumberPhone())
                .customerMoney(bill.getCustomerMoney())
                .discountMoney(bill.getDiscountMoney())
                .shipMoney(bill.getShipMoney())
                .totalMoney(bill.getTotalMoney())
                .billType(bill.getTypeBill().toString())
                .notes(bill.getNotes())
                .completeDate(bill.getCompleteDate())
                .confirmDate(bill.getConfirmDate())
                .desiredDateOfReceipt(bill.getDesiredDateOfReceipt())
                .shipDate(bill.getShipDate())
                .address(bill.getShippingAddress())
                .addressReponse(addressReponse)
                .email(bill.getEmail())
                .voucherReponse(voucher)
                .status(bill.getStatus() != null ? bill.getStatus().toString() : null)
                .createAt(bill.getCreatedAt())
                .build();
    }

    @Override
    public List<Map<String, Object>> getBillCountByStatus() {
        List<Object[]> results = billRepository.countOrdersByStatus();
        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] result : results) {
            BillStatus status = (BillStatus) result[0];
            Long count = (Long) result[1];

            Map<String, Object> map = new HashMap<>();
            map.put("name", status.name());
            map.put("value", count);
            response.add(map);
        }
        Long countAll = billRepository.count();
        response.add(Map.of("name", "all", "value", countAll));
        return response;
    }

}
