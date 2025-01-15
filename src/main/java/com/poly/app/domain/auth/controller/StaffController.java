package com.poly.app.domain.auth.controller;

import com.poly.app.domain.auth.Repo.StaffRepo;
import com.poly.app.domain.model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/admin/staff")
public class StaffController {
    @Autowired
    private StaffRepo staffRepo;

    @GetMapping("/hienthi")
    public List<Staff> hienThi(){
        return staffRepo.findAll();
    }

    @PostMapping("/add")
    public String add(@RequestBody Staff staff){
        staffRepo.save(staff);
        return "Thêm thành công!";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable String id){
        staffRepo.deleteById(id);
        return "Xóa thành công!";
    }

    @PutMapping("/update/{id}")
    public String edit(@PathVariable String id, @RequestBody Staff staff){
        Staff staffUpdate = staffRepo.findById(id).orElseThrow();
            staffUpdate.setFullName(staff.getFullName());
            staffUpdate.setDateBirth(staff.getDateBirth());
            staffUpdate.setPhoneNumber(staff.getPhoneNumber());
            staffUpdate.setEmail(staff.getEmail());
            staffUpdate.setGender(staff.getGender());
            staffUpdate.setPassword(staff.getPassword());
            staffUpdate.setStatus(staff.getStatus());
            staffRepo.save(staffUpdate);
            return "Cập nhật thành công!";

    }
    @GetMapping("/detail/{fullName}")
    public List<Staff> getDetail(@PathVariable ("fullName") String fullName){
        return staffRepo.findAll().stream().filter(item -> item.getFullName().equals(fullName)).toList();
    }
}
