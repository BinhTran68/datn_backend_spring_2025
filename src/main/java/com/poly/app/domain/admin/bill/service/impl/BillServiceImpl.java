package com.poly.app.domain.admin.bill.service.impl;

import com.poly.app.domain.admin.bill.response.BillResponse;
import com.poly.app.domain.admin.bill.service.BillService;
import com.poly.app.domain.common.PageReponse;
import com.poly.app.domain.common.PageableRequest;
import com.poly.app.domain.model.Bill;
import com.poly.app.domain.repository.BillRepository;
import com.poly.app.domain.response.ApiResponse;
import com.poly.app.infrastructure.constant.StatusBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    BillRepository billRepository;


    @Override
    public Page<BillResponse> getPageBill(Integer size, Integer page, StatusBill statusBill) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Bill> billPage;

        if(statusBill == null) {
            billPage = billRepository.findAll(pageable);
        }else  {
            billPage = billRepository.findByStatus(statusBill, pageable);
        }


        List<Bill> bills = billPage.getContent();
        List<BillResponse> billResponses = bills.stream().map(
                bill -> BillResponse.builder()
                        .billCode(bill.getBillCode())
                        .customerName(bill.getCustomer().getFullName())
                        .customerPhone(bill.getCustomer().getPhoneNumber())
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
                        .build()
        ).collect(Collectors.toList());

        Page<BillResponse> pageResponse = new PageImpl<>(billResponses, PageRequest.of(billPage.getNumber(), billPage.getSize()), billPage.getTotalElements());


        return  pageResponse;
    }
}
