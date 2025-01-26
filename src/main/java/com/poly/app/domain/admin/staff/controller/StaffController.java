package com.poly.app.domain.admin.staff.controller;

import com.poly.app.domain.admin.customer.response.CustomerResponse;
import com.poly.app.domain.admin.customer.service.CustomerService;
import com.poly.app.domain.auth.Repo.StaffRepo;
import com.poly.app.domain.model.Staff;
import com.poly.app.domain.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/admin/staff")
public class StaffController {
//    @Autowired
//    private StaffRepo staffRepo;
    @Autowired
    private StaffRepository staffRepository;


    @GetMapping("/hienthi")
    public List<Staff> hienThi(){
        return staffRepository.findAll();
    }

    @PostMapping("/add")
    public String add(@RequestBody Staff staff){
        staffRepository.save(staff);
        return "Thêm thành công!";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        staffRepository.deleteById(id);
        return "Xóa thành công!";
    }

    @PutMapping("/update/{id}")
    public String edit(@PathVariable Integer id, @RequestBody Staff staff){
        Staff staffUpdate = staffRepository.findById(id).orElseThrow();
            staffUpdate.setFullName(staff.getFullName());
            staffUpdate.setDateBirth(staff.getDateBirth());
            staffUpdate.setPhoneNumber(staff.getPhoneNumber());
            staffUpdate.setEmail(staff.getEmail());
            staffUpdate.setGender(staff.getGender());
            staffUpdate.setPassword(staff.getPassword());
            staffUpdate.setStatus(staff.getStatus());
            staffRepository.save(staffUpdate);
            return "Cập nhật thành công!";

    }
//    @GetMapping("/detail/{fullName}")
//    public List<Staff> getDetail(@PathVariable ("fullName") String fullName){
//        return staffRepo.findAll().stream().filter(item -> item.getFullName().equals(fullName)).toList();
//    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Staff> getDetail(@PathVariable Integer id) {
        Staff staff = staffRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(staff);
    }
}
