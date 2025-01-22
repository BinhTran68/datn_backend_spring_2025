//package com.poly.app.domain.admin.customer.service;
//
//
//import com.poly.app.domain.admin.customer.request.CustomerRequest;
//import com.poly.app.domain.model.Customer;
//
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public interface CustomerService {
//    Customer createCustomer(CustomerRequest customerRequest);
//    Customer updateCustomer(Long id, CustomerRequest customerRequest);
//    void deleteCustomer(Long id);
//    Customer getCustomerById(Long id);
//    List<Customer> getAllCustomers();
//}




package com.poly.app.domain.admin.customer.service;


import com.poly.app.domain.admin.customer.request.CustomerRequest;
import com.poly.app.domain.admin.customer.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    CustomerResponse createCustomer(CustomerRequest customerRequest);
    CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest);
    void deleteCustomer(Long id);
    CustomerResponse getCustomerById(Long id);
    List<CustomerResponse> getAllCustomers();
    CustomerResponse getCustomerByEmail(String email);
}