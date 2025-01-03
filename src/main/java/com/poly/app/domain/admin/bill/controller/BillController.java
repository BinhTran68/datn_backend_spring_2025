package com.poly.app.domain.admin.bill.controller;


import com.poly.app.domain.admin.bill.service.BillService;
import com.poly.app.domain.common.ObjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bill")
public class BillController {

    @Autowired
    BillService billService;

    @GetMapping("/index")
    public ObjectResponse index () {
            return new ObjectResponse(billService.getPageBill());
    }



}
