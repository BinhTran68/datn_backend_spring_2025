package com.poly.app.domain.admin.bill.controller;


import com.poly.app.domain.admin.bill.request.UpdateStatusBillRequest;
import com.poly.app.domain.admin.bill.service.BillService;
import com.poly.app.domain.common.PageReponse;
import com.poly.app.domain.model.Bill;
import com.poly.app.domain.response.ApiResponse;
import com.poly.app.infrastructure.constant.BillStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/bill")
public class BillController {

    // Khi tạo 1 hóa đơn mặc định sẽ là chưa xác nhận

    @Autowired
    BillService billService;

    @GetMapping("/index")
    public PageReponse index(@RequestParam(defaultValue = "10") Integer size,
                             @RequestParam(defaultValue = "0") Integer page,
                             @RequestParam(required = false) BillStatus statusBill) {
        return new PageReponse(billService.getPageBill(size, page, statusBill));
    }


    @GetMapping("/detail/{billCode}")
    public ApiResponse<?> findBillByBillCode(@PathVariable String billCode) {
        return ApiResponse.builder().data(billService.getBillResponseByBillCode(billCode)).build();
    }


    @PutMapping ("/{code}/update")
    public ApiResponse<?> updateBillStatus(@PathVariable String code, @RequestBody UpdateStatusBillRequest request) {
        return ApiResponse.builder().data(billService.updateStatusBill(code, request)).build();
    }





}
