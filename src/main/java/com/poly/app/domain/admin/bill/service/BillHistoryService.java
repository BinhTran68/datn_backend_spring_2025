package com.poly.app.domain.admin.bill.service;

import com.poly.app.domain.admin.bill.response.BillHistoryResponse;

import java.util.List;

public interface BillHistoryService {

    List<BillHistoryResponse> findBillHistoryResponseListByBillCode(String billCode);

}
