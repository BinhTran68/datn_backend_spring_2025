package com.poly.app.domain.admin.bill.service;

import com.poly.app.domain.admin.bill.response.BillResponse;
import com.poly.app.domain.common.PageReponse;
import com.poly.app.domain.model.Bill;
import org.springframework.data.domain.Page;

public interface BillService {

    Page<BillResponse> getPageBill(Integer size, Integer page);

}
