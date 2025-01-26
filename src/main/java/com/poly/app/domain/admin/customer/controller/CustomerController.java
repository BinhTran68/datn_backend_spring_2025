
package com.poly.app.domain.admin.customer.controller;


import com.poly.app.domain.admin.customer.request.AddressRequest;
import com.poly.app.domain.admin.customer.request.CustomerRequest;
import com.poly.app.domain.admin.customer.response.AddressResponse;
import com.poly.app.domain.admin.customer.response.CustomerResponse;
import com.poly.app.domain.admin.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;


    @PostMapping("/add")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.createCustomer(customerRequest);
        return ResponseEntity.ok(customerResponse);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Integer id, @RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.updateCustomer(id, customerRequest);
        return ResponseEntity.ok(customerResponse);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("detail/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Integer id) {
        CustomerResponse customerResponse = customerService.getCustomerById(id);
        return ResponseEntity.ok(customerResponse);
    }

    @GetMapping("/")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @GetMapping("detail/email")
    public ResponseEntity<CustomerResponse> getCustomerByEmail(@RequestParam String email) {
        CustomerResponse customerResponse = customerService.getCustomerByEmail(email);
        return ResponseEntity.ok(customerResponse);
    }






    @PutMapping("/update-address/{addressId}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Integer addressId, @RequestBody AddressRequest addressRequest) {
        AddressResponse addressResponse = customerService.updateAddress(addressId, addressRequest);
        return ResponseEntity.ok(addressResponse);
    }

    @DeleteMapping("/delete-address/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer addressId) {
        customerService.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add-address/{customerId}")
    public ResponseEntity<AddressResponse> addAddress(@PathVariable Integer customerId, @RequestBody AddressRequest addressRequest) {
        AddressResponse addressResponse = customerService.addAddress(customerId, addressRequest);
        return ResponseEntity.ok(addressResponse);
    }

    @PutMapping("/set-default-address/{addressId}")
    public ResponseEntity<Void> setDefaultAddress(@PathVariable Integer addressId) {
        customerService.setDefaultAddress(addressId);
        return ResponseEntity.ok().build();
    }
}