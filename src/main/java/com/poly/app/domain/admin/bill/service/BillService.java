package com.poly.app.domain.admin.bill.service;

import com.poly.app.domain.admin.bill.response.BillResponse;
import com.poly.app.infrastructure.constant.BillStatus;
import org.springframework.data.domain.Page;

public interface BillService {

    Page<BillResponse> getPageBill(Integer size, Integer page, BillStatus statusBill);

    BillResponse getBillResponseByBillCode(String billCode);




}
