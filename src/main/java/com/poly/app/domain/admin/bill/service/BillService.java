package com.poly.app.domain.admin.bill.service;

import com.poly.app.domain.common.PageReponse;
import com.poly.app.domain.model.Bill;
import org.springframework.data.domain.Page;

public interface BillService {

    PageReponse getPageBill(Integer size, Integer page);

}
