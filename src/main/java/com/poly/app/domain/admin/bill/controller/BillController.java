package com.poly.app.domain.admin.bill.controller;


import com.poly.app.domain.admin.bill.service.BillService;
import com.poly.app.domain.common.ObjectResponse;
import com.poly.app.domain.common.PageReponse;
import com.poly.app.domain.model.Bill;
import com.poly.app.domain.response.ApiResponse;
import com.poly.app.infrastructure.constant.StatusBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin/bill")
public class BillController {

    @Autowired
    BillService billService;

    @GetMapping("/index")
    public PageReponse index(@RequestParam(defaultValue = "10") Integer size,
                             @RequestParam(defaultValue = "0") Integer page,
                             @RequestParam(required = false) StatusBill statusBill) {
        return new PageReponse(billService.getPageBill(size, page, statusBill));
    }



}
