package com.poly.app.domain.admin.bill.service.impl;

import com.poly.app.domain.admin.bill.response.BillResponse;
import com.poly.app.domain.admin.bill.service.BillService;
import com.poly.app.domain.model.Bill;
import com.poly.app.domain.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    BillRepository billRepository;

    @Override
    public Page<Bill> getPageBill() {

        List<Bill> bills =  billRepository.findAll();

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
                        .shipDate(bill.getShipDate())
                        .build()
        ).collect(Collectors.toList());

        Pageable pageable = Pageable.ofSize(5);


        return billRepository.findAll(pageable);
    }
}
