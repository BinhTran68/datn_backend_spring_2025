package com.poly.app.domain.admin.bill.service.impl;

import com.poly.app.domain.admin.bill.response.BillHistoryResponse;
import com.poly.app.domain.admin.bill.service.BillHistoryService;
import com.poly.app.domain.model.BillHistory;
import com.poly.app.domain.repository.BillHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillHistoryServiceImpl implements BillHistoryService {

    @Autowired
    BillHistoryRepository billHistoryRepository;

    @Override
    public List<BillHistoryResponse> findBillHistoryResponseListByBillCode(String billCode) {
        return billHistoryRepository.findBillHistoryByBillCode(billCode);
    }
}
