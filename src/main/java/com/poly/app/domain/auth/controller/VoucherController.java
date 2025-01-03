package com.poly.app.domain.auth.controller;

import com.poly.app.domain.repository.VoucherRepository;
import com.poly.app.domain.response.VoucherReponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VoucherController {
    @Autowired
    VoucherRepository voucherRepository;

    @GetMapping("/voucher")
    public List<VoucherReponse> hienthi(){
        return voucherRepository.getAll();
    }

}
