package com.poly.app.domain.admin.customer.controller;
import com.poly.app.domain.auth.Repo.KhachHangRepository;
import com.poly.app.domain.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/khachhang")
@Slf4j

public class KhachHangController {

    @Autowired
    private KhachHangRepository khachHangRepository;

    @GetMapping("/hienthi")
    public List<Customer> getAllKhachHang() {
        return khachHangRepository.findAll();
    }

    @PostMapping("/add")
    public Object addKhachHang(@RequestBody Customer khachHang) {
        log.info(khachHang.toString() + "");
        // Kiểm tra các trường bắt buộc
        if (khachHang.getFullName() == null || khachHang.getFullName().trim().isEmpty()) {
            return "Tên khách hàng không được để trống.";
        }
//        if (khachHang.getCitizenId() == null || khachHang.getCitizenId().trim().isEmpty()) {
//            return "CCCD không được để trống.";
//        }
        if (khachHang.getPhoneNumber() == null || khachHang.getPhoneNumber().trim().isEmpty()) {
            return "Số điện thoại không được để trống.";
        }
        if (khachHang.getDateBirth() == null) {
            return "Ngày sinh không được để trống.";
        }
        if (khachHang.getStatus() == null || khachHang.getStatus().toString().isEmpty()) {
            return "Trạng thái không được để trống.";
        }


        // Nếu dữ liệu hợp lệ, tiến hành thêm mới
        return khachHangRepository.save(khachHang);
//    public String add(@RequestBody Customer customer){
//        khachHangRepository.save(customer);
//        return "add thanh cong";
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