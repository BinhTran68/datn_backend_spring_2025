package com.poly.app.domain.admin.bill.service;

import com.poly.app.domain.admin.bill.request.UpdateStatusBillRequest;
import com.poly.app.domain.admin.bill.response.BillResponse;
import com.poly.app.domain.admin.bill.response.UpdateBillRequest;
import com.poly.app.infrastructure.constant.BillStatus;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface BillService {

    Page<BillResponse> getPageBill(Integer size, Integer page, BillStatus statusBill);

    BillResponse getBillResponseByBillCode(String billCode);


    Map<String,?> updateStatusBill(String billCode, UpdateStatusBillRequest request);

     BillResponse updateBillInfo(String billCode, UpdateBillRequest request);



}
