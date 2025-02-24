package com.poly.app.domain.admin.bill.service.impl;

import com.poly.app.domain.admin.bill.request.BillDetailRequest;
import com.poly.app.domain.admin.bill.request.CreateBillRequest;
import com.poly.app.domain.admin.bill.request.UpdateStatusBillRequest;
import com.poly.app.domain.admin.bill.response.BillResponse;
import com.poly.app.domain.admin.bill.response.UpdateBillRequest;
import com.poly.app.domain.admin.bill.service.BillHistoryService;
import com.poly.app.domain.admin.bill.service.BillService;
import com.poly.app.domain.admin.product.request.productdetail.ProductDetailRequest;
import com.poly.app.domain.model.Address;
import com.poly.app.domain.model.Bill;
import com.poly.app.domain.model.BillDetail;
import com.poly.app.domain.model.BillHistory;
import com.poly.app.domain.model.Customer;
import com.poly.app.domain.model.PaymentBill;
import com.poly.app.domain.model.PaymentMethods;
import com.poly.app.domain.model.Product;
import com.poly.app.domain.model.ProductDetail;
import com.poly.app.domain.model.Staff;
import com.poly.app.domain.model.Voucher;
import com.poly.app.domain.repository.AddressRepository;
import com.poly.app.domain.repository.BillDetailRepository;
import com.poly.app.domain.repository.BillHistoryRepository;
import com.poly.app.domain.repository.BillRepository;
import com.poly.app.domain.repository.CustomerRepository;
import com.poly.app.domain.repository.PaymentBillRepository;
import com.poly.app.domain.repository.PaymentMethodsRepository;
import com.poly.app.domain.repository.ProductDetailRepository;
import com.poly.app.domain.repository.StaffRepository;
import com.poly.app.domain.repository.VoucherRepository;
import com.poly.app.infrastructure.constant.BillStatus;
import com.poly.app.infrastructure.constant.PaymentMethod;
import com.poly.app.infrastructure.constant.PaymentMethodEnum;
import com.poly.app.infrastructure.constant.PaymentMethodsType;
import com.poly.app.infrastructure.constant.TypeBill;
import com.poly.app.infrastructure.exception.ApiException;
import com.poly.app.infrastructure.exception.ErrorCode;
import com.poly.app.infrastructure.util.DateUtil;
import com.poly.app.infrastructure.util.GenHoaDon;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public Page<BillResponse> getPageBill(Integer size, Integer page,
                                          BillStatus statusBill,
                                          TypeBill typeBill,
                                          String search,
                                          String startDate,
                                          String endDate
    ) {
        Pageable pageable = PageRequest.of(page, size);
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
    public BillResponse updateBillInfo(String billCode, UpdateBillRequest request) {
        System.out.println(request.toString());
        Bill bill = billRepository.findByBillCode(billCode);
        bill.setNumberPhone(request.getCustomerPhone());
        bill.setNotes(request.getNote());

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

        billRepository.save(bill);
        return convertBillToBillResponse(bill);
    }

    @Override
    public File printBillById(String billCode) {
        Bill bill = billRepository.findByBillCode(billCode);
        BillHistory billHistory = billHistoryRepository.findDistinctFirstByBillOrderByCreatedAtDesc(bill);
        Customer account = bill.getCustomer();
        List<BillDetail> lstBillDetail = billDetailRepository.findByBill(bill);
        return genHoaDon.genHoaDon(bill, lstBillDetail, billHistory, account);
    }

    @Override
    @Transactional
    public BillResponse createBill(CreateBillRequest request) {
        Customer customer = null;
        if (request.getCustomerId() != null) {
            customer = customerRepository.findById(request.getCustomerId()).orElse(null);
        }
        Voucher voucher = null;
        if (request.getVoucherId() != null) {
            voucher = voucherRepository.findById(request.getVoucherId()).orElse(null);
        }
        Address address = null;
        if(request.getShippingAddressId() != null) {
            address = addressRepository.findById(request.getShippingAddressId()).orElseThrow(()
                    -> new ApiException(ErrorCode.HOA_DON_NOT_FOUND));
        }
        if(request.getAddress() != null && address == null) {
            Address newAddress =  Address
                    .builder()
                    .wardId(request.getAddress().getWardId())
                    .specificAddress(request.getAddress().getSpecificAddress())
                    .provinceId(request.getAddress().getProvinceId())
                    .districtId(request.getAddress().getDistrictId())
                    .customer(customer)
                    .build();
            address =  addressRepository.save(newAddress);
        }

        Bill bill = Bill
                .builder()
                .typeBill(request.getTypeBill())
                .customer(customer)
                .customerMoney(request.getCustomerMoney())
                .discountMoney(request.getDiscountMoney())
                .moneyAfter(request.getMoneyAfter())
                .shipDate(request.getShipDate())
                .shipMoney(request.getShipMoney())
                .totalMoney(request.getTotalMoney())
                .completeDate(request.getCompleteDate())
                .shippingAddress(address)
                .shipDate(request.getShipDate())
                .numberPhone(request.getNumberPhone())
                .shipMoney(request.getShipMoney())
                .voucher(voucher)
                .status(request.getStatus())
                .build();
        Bill billSave = billRepository.save(bill);



        for (BillDetailRequest billDetailRequest : request.getBillDetailRequests()) {
            ProductDetail productDetail = productDetailRepository.findById(billDetailRequest.getProductDetailId()).orElse(null);

            if (productDetail != null) {
                productDetail.setQuantity(productDetail.getQuantity() - billDetailRequest.getQuantity());
                productDetailRepository.save(productDetail);
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
                    request.getCashCustomerMoney(), PaymentMethodEnum.TIEN_MAT);
            PaymentMethods bankPaymentMethods = createAndSavePaymentMethod(
                    request.getBankCustomerMoney(), PaymentMethodEnum.CHUYEN_KHOAN);

            savePaymentBill(billSave, cashPaymentMethods);
            savePaymentBill(billSave, bankPaymentMethods);
        } else if (request.getCashCustomerMoney() != null) {
            createAndSavePaymentMethod(request.getCashCustomerMoney(), PaymentMethodEnum.TIEN_MAT);
        } else if (request.getBankCustomerMoney() != null) {
            createAndSavePaymentMethod(request.getBankCustomerMoney(), PaymentMethodEnum.CHUYEN_KHOAN);
        }
        BillHistory billHistory_1 = BillHistory
                .builder()
                .customer(customer)
                .bill(billSave)
                .status(BillStatus.DA_THANH_TOAN)
                .build();
        BillHistory billHistory_2;
        if(request.getIsShipping()) {
            billHistory_2  = BillHistory
                    .builder()
                    .customer(customer)
                    .bill(billSave)
                    .status(BillStatus.CHO_VAN_CHUYEN)
                    .build();
        }else {
          billHistory_2 = BillHistory
                    .builder()
                    .customer(customer)
                    .bill(billSave)
                    .status(BillStatus.DA_HOAN_THANH)
                    .build();
        }



        billHistoryRepository.saveAll(List.of(billHistory_1, billHistory_2));

        return convertBillToBillResponse(billSave);
    }

    private PaymentMethods createAndSavePaymentMethod(Double amount, PaymentMethodEnum methodEnum) {
        if (amount == null) return null;

        PaymentMethods paymentMethods = PaymentMethods.builder()
                .totalMoney(amount)
                .paymentMethodsType(PaymentMethodsType.THANH_TOAN_TRUOC)
                .paymentMethod(methodEnum)
                .build();

        return paymentMethodsRepository.save(paymentMethods);
    }

    private void savePaymentBill(Bill bill, PaymentMethods paymentMethods) {
        if (paymentMethods == null) return;

        PaymentBill paymentBill = PaymentBill.builder()
                .bill(bill)
                .paymentMethods(paymentMethods)
                .build();

        paymentBillRepository.save(paymentBill);
    }


    private BillResponse convertBillToBillResponse(Bill bill) {
        return BillResponse.builder()
                .billCode(bill.getBillCode())
                .customerName(bill.getCustomer() != null ? bill.getCustomer().getFullName() : "")
                .customerPhone(bill.getCustomer() != null ? bill.getCustomer().getPhoneNumber() : "")
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
                .email(bill.getEmail())
                .status(bill.getStatus() != null ? bill.getStatus().toString() : null)
                .createAt(bill.getCreatedAt())
                .build();
    }
}
