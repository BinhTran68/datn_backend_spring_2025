package com.poly.app.domain.auth.controller;
import com.poly.app.domain.auth.Repo.KhachHangRepository;
import com.poly.app.domain.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/khachhang")

public class KhachHangController {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @GetMapping("/hienthi")
    public List<Customer> getAllKhachHang() {
        return khachHangRepository.findAll();
    }

    @PostMapping("/add")
    public Customer addKhachHang(@RequestBody Customer khachHang) {
        return khachHangRepository.save(khachHang);
    }

    @PutMapping("update/{id}")
    public Customer updateKhachHang(@PathVariable String id, @RequestBody Customer khachHang) {
        Customer existing = khachHangRepository.findById(id).orElseThrow();
        existing.setFullName(khachHang.getFullName());
        existing.setCitizenId(khachHang.getCitizenId());
        existing.setPhoneNumber(khachHang.getPhoneNumber());
        existing.setDateBirth(khachHang.getDateBirth());
        existing.setStatus(khachHang.getStatus());
        return khachHangRepository.save(existing);
    }

    @DeleteMapping("delete/{id}")
    public String deleteKhachHang(@PathVariable String id) {
        khachHangRepository.deleteById(id);
        return "xoa thanh cong";

    }
    @GetMapping("/detail/{id}")
    public List<Customer> detail(@PathVariable("id") Integer code){
        return khachHangRepository.findAll().stream().filter(item -> item.getId().equals(code)).toList();
    }
}