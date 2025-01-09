package com.poly.app.domain.admin.bill.service.impl;

import com.poly.app.domain.admin.bill.request.UpdateStatusBillRequest;
import com.poly.app.domain.admin.bill.response.BillResponse;
import com.poly.app.domain.admin.bill.service.BillHistoryService;
import com.poly.app.domain.admin.bill.service.BillService;
import com.poly.app.domain.model.Bill;
import com.poly.app.domain.model.BillHistory;
import com.poly.app.domain.model.Staff;
import com.poly.app.domain.repository.BillHistoryRepository;
import com.poly.app.domain.repository.BillRepository;
import com.poly.app.domain.repository.CustomerRepository;
import com.poly.app.domain.repository.StaffRepository;
import com.poly.app.infrastructure.constant.BillStatus;
import com.poly.app.infrastructure.exception.ApiException;
import com.poly.app.infrastructure.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    BillHistoryService billHistoryService;

    @Override
    public Page<BillResponse> getPageBill(Integer size, Integer page, BillStatus statusBill) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Bill> billPage;

        if (statusBill == null) {
            billPage = billRepository.findAll(pageable);
        } else {
            billPage = billRepository.findByStatus(statusBill, pageable);
        }
        List<Bill> bills = billPage.getContent();
        List<BillResponse> billResponses = bills.stream().map(
                bill -> convertBillToBillResponse(bill)
        ).collect(Collectors.toList());


        return new PageImpl<>(billResponses, PageRequest.of(billPage.getNumber(), billPage.getSize()), billPage.getTotalElements());
    }

    @Override
    public BillResponse getBillResponseByBillCode(String billCode) {
        Bill bill = billRepository.findByCode(billCode);
        return convertBillToBillResponse(bill);
    }

    @Override
    public Map<String, ?> updateStatusBill(String billCode, UpdateStatusBillRequest request) {

        System.out.println(request);

        Bill bill = billRepository.findByCode(billCode);

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


    private BillResponse convertBillToBillResponse(Bill bill) {
        return BillResponse.builder()
                .billCode(bill.getCode())
                .customerName(bill.getCustomer() != null ? bill.getCustomer().getFullName() : "")
                .customerPhone(bill.getCustomer() != null ?  bill.getCustomer().getPhoneNumber() : "")
                .customerMoney(bill.getCustomerMoney())
                .discountMoney(bill.getDiscountMoney())
                .shipMoney(bill.getShipMoney())
                .totalMoney(bill.getTotalMoney())
                .billType(bill.getBillType())
                .completeDate(bill.getCompleteDate())
                .confirmDate(bill.getConfirmDate())
                .desiredDateOfReceipt(bill.getDesiredDateOfReceipt())
                .shipDate(bill.getShipDate())
                .shippingAddress(bill.getShippingAddress())
                .email(bill.getEmail())
                .status(bill.getStatus().toString())
                .createAt(bill.getCreatedAt())
                .build();
    }
}
