package com.poly.app.domain.admin.bill.service;

import com.poly.app.domain.model.Bill;
import org.springframework.data.domain.Page;

public interface BillService {

    Page<Bill> getPageBill();

}
