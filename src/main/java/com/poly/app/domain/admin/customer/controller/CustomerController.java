//package com.poly.app.domain.admin.customer.controller;
//
//
//import com.poly.app.domain.admin.customer.request.CustomerRequest;
//import com.poly.app.domain.admin.customer.response.CustomerResponse;
//import com.poly.app.domain.admin.customer.service.CustomerService;
//import com.poly.app.domain.model.Address;
//import com.poly.app.domain.model.Customer;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/customers")
//public class CustomerController {
//
//    @Autowired
//    private CustomerService customerService;
//    public List<Address> getAddresses() {
//        if (getAddresses() == null) {
//            return new ArrayList<>();  // Trả về danh sách rỗng thay vì null
//        }
//        return getAddresses();
//    }
//
//    // Create new customer
//    @PostMapping("/add")
//    public CustomerResponse createCustomer(@RequestBody CustomerRequest customerRequest) {
//        Customer customer = customerService.createCustomer(customerRequest);
//        return new CustomerResponse(customer);
//
//
//    }
//
//    // Update customer
//    @PutMapping("update/{id}")
//    public CustomerResponse updateCustomer(@PathVariable Long id, @RequestBody CustomerRequest customerRequest) {
//        Customer customer = customerService.updateCustomer(id, customerRequest);
//        return new CustomerResponse(customer);
//    }
//
//    // Delete customer
//    @DeleteMapping("delete/{id}")
//    public void deleteCustomer(@PathVariable Long id) {
//        customerService.deleteCustomer(id);
//    }
//
//    // Get customer by ID
//    @GetMapping("detail/{id}")
//    public CustomerResponse getCustomer(@PathVariable Long id) {
//        Customer customer = customerService.getCustomerById(id);
//        return new CustomerResponse(customer);
//    }
//
//    // Search customers
//    @GetMapping("/")
//    public List<CustomerResponse> getAllCustomers() {
//        List<Customer> customers = customerService.getAllCustomers();
//        return CustomerResponse.convertToList(customers);
//    }
//}





//package com.poly.app.domain.admin.customer.controller;
//
//import com.poly.app.domain.admin.customer.request.CustomerRequest;
//import com.poly.app.domain.admin.customer.response.CustomerResponse;
//import com.poly.app.domain.admin.customer.service.CustomerService;
//import com.poly.app.domain.model.Address;
//import com.poly.app.domain.model.Customer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/customers")
//
//public class CustomerController {
//    public ResponseEntity<?> someMethod(@RequestParam(value = "id", required = false) String id) {
//        Long validId = null;
//
//        if (id != null && !id.equals("undefined")) {
//            try {
//                validId = Long.valueOf(id);
//            } catch (NumberFormatException e) {
//                // Xử lý khi giá trị id không phải là số hợp lệ
//                return ResponseEntity.badRequest().body("ID không hợp lệ.");
//            }
//        }
//
//        if (validId == null) {
//            // Xử lý khi không có id hoặc id không hợp lệ
//            return ResponseEntity.badRequest().body("ID không hợp lệ hoặc không được cung cấp.");
//        }
//
//        // Tiếp tục logic khi validId hợp lệ
//        return ResponseEntity.ok("ID hợp lệ: " + validId);
//    }
//
//    @Autowired
//    private CustomerService customerService;
//
//    // Giả sử bạn có một phương thức để lấy danh sách địa chỉ
//    public List<Address> getAddresses() {
//        List<Address> addresses = someMethodThatFetchesAddresses(); // Phương thức này có thể lấy từ DB hoặc API
//        if (addresses == null) {
//            return new ArrayList<>();  // Trả về danh sách rỗng nếu null
//        }
//        return addresses;
//    }
//
//    // Create new customer
//    @PostMapping("/add")
//    public CustomerResponse createCustomer(@RequestBody CustomerRequest customerRequest) {
//        Customer customer = customerService.createCustomer(customerRequest);
//        return new CustomerResponse(customer);
//    }
//
//    // Update customer
//    @PutMapping("/update/{id}")
//    public CustomerResponse updateCustomer(@PathVariable Long id, @RequestBody CustomerRequest customerRequest) {
//        Customer customer = customerService.updateCustomer(id, customerRequest);
//        return new CustomerResponse(customer);
//    }
//
//    // Delete customer
//    @DeleteMapping("/delete/{id}")
//    public void deleteCustomer(@PathVariable Long id) {
//        customerService.deleteCustomer(id);
//    }
//
//    // Get customer by ID
//    @GetMapping("/detail/{id}")
//    public CustomerResponse getCustomer(@PathVariable Long id) {
//        Customer customer = customerService.getCustomerById(id);
//        return new CustomerResponse(customer);
//    }
//
//    // Search customers
//    @GetMapping("/")
//    public List<CustomerResponse> getAllCustomers() {
//        List<Customer> customers = customerService.getAllCustomers();
//        return CustomerResponse.convertToList(customers);
//    }
//
//    // Xử lý ngoại lệ MethodArgumentTypeMismatchException
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public ResponseEntity<String> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
//        return ResponseEntity.badRequest().body("Invalid input for ID. Expected a valid number.");
//    }
//
//    // Giả sử có phương thức lấy danh sách địa chỉ
//    private List<Address> someMethodThatFetchesAddresses() {
//        // Lấy địa chỉ từ cơ sở dữ liệu hoặc API
//        return null;  // Ví dụ trả về null, sẽ được xử lý là danh sách rỗng
//    }
//}






package com.poly.app.domain.admin.customer.controller;


import com.poly.app.domain.admin.customer.request.CustomerRequest;
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
}