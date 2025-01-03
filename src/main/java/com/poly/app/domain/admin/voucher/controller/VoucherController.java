package com.poly.app.domain.admin.voucher.controller;

import com.poly.app.domain.repository.VoucherRepository;
import com.poly.app.domain.admin.voucher.response.VoucherReponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/voucher")
public class VoucherController {
    @Autowired
    VoucherRepository voucherRepository;

    @GetMapping("/voucher")
    public List<VoucherReponse> hienthi(){
        return null;
    }

}
