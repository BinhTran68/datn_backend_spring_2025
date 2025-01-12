package com.poly.app.domain.admin.bill.controller;

import com.poly.app.domain.admin.bill.service.BillProductDetailService;
import com.poly.app.domain.admin.bill.service.BillService;
import com.poly.app.domain.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/bill-product-detail")
public class BillProductDetailController {

    @Autowired
    private BillService billService;

    @Autowired
    private BillProductDetailService billProductDetailService;


    @GetMapping("/{billCode}")
    public ApiResponse<?> getProductDetailsByBillCode(@PathVariable String billCode) {
        return ApiResponse.builder().data(billProductDetailService.getBillProductDetailResponse(billCode)).build();
    }

    @GetMapping("")
    public ApiResponse<?> getBillProductDetails(@RequestParam(defaultValue = "10") Integer size,
                                                @RequestParam(defaultValue = "0") Integer page) {
        return ApiResponse.builder().data(billProductDetailService.getBillProductResponsePage(page, size)).build();
    }



}
