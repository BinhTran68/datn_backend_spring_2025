package com.poly.app.domain.admin.staff.controller;

import com.poly.app.domain.admin.address.AddressRequest;
import com.poly.app.domain.admin.customer.response.AddressResponse;
import com.poly.app.domain.admin.staff.request.StaffRequest;
import com.poly.app.domain.admin.staff.response.StaffReponse;
import com.poly.app.domain.admin.staff.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/staff")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @PostMapping("/add")
    public ResponseEntity<StaffReponse> createStaff(@RequestBody StaffRequest staffRequest) {
        StaffReponse staffReponse = staffService.createStaff(staffRequest);
        return ResponseEntity.ok(staffReponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<StaffReponse> updateStaff(@PathVariable Integer id, @RequestBody StaffRequest staffRequest) {
        StaffReponse staffReponse = staffService.updateStaff(id, staffRequest);
        return ResponseEntity.ok(staffReponse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Integer id) {
        staffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<StaffReponse> getStaffById(@PathVariable Integer id) {
        StaffReponse staffReponse = staffService.getStaffById(id);
        return ResponseEntity.ok(staffReponse);
    }

    @GetMapping("/hienthi")
    public ResponseEntity<List<StaffReponse>> getAllStaffs() {
        List<StaffReponse> staffs = staffService.getAllStaff();
        return ResponseEntity.ok(staffs);
    }

    @GetMapping("/detail/email")
    public ResponseEntity<StaffReponse> getStaffByEmail(@RequestParam String email) {
        StaffReponse staffReponse = staffService.getStaffByEmail(email);
        return ResponseEntity.ok(staffReponse);
    }

    @PutMapping("/update-address/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Integer addressId, @RequestBody AddressRequest addressRequest) {
        AddressResponse addressResponse = staffService.updateAddress(addressId, addressRequest);
        return ResponseEntity.ok(addressResponse);
    }

    @DeleteMapping("/delete-address/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer addressId) {
        staffService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add-address/{customerId}")
    public ResponseEntity<AddressResponse> addAddress(@PathVariable Integer customerId, @RequestBody AddressRequest addressRequest) {
        AddressResponse addressResponse = staffService.addAddress(customerId, addressRequest);
        return ResponseEntity.ok(addressResponse);
    }

    @PutMapping("/set-default-address/{addressId}")
    public ResponseEntity<Void> setDefaultAddress(@PathVariable Integer addressId) {
        staffService.setDefaultAddress(addressId);
        return ResponseEntity.ok().build();
    }

}
