//package com.poly.app.domain.admin.customer.service.impl;
//
//import com.poly.app.domain.admin.customer.request.CustomerRequest;
//import com.poly.app.domain.admin.customer.service.CustomerService;
//import com.poly.app.domain.model.Address;
//import com.poly.app.domain.model.Customer;
//
//import com.poly.app.domain.repository.CustomerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class CustomerServiceImpl implements CustomerService {
//
//    @Autowired
//    private CustomerRepository customerRepository;
//
//    @Override
//    public Customer createCustomer(CustomerRequest customerRequest) {
//        Customer customer = new Customer();
//        customer.setCode(customerRequest.getCode());
//        customer.setFullName(customerRequest.getFullName());
//        customer.setDateBirth(customerRequest.getDateBirth());
//        customer.setCitizenId(customerRequest.getCitizenId());
//        customer.setPhoneNumber(customerRequest.getPhoneNumber());
//        customer.setEmail(customerRequest.getEmail());
//        customer.setGender(customerRequest.getGender());
//        customer.setPassword(customerRequest.getPassword());
//
////        List<Address> addresses = customerRequest.getAddresses().stream()
////                .map(addressRequest -> new Address(addressRequest, customer))
////                .toList();
////
////        customer.setAddresses(addresses);
//
//        return customerRepository.save(customer);
//    }
//
//    @Override
//    public Customer updateCustomer(Long id, CustomerRequest customerRequest) {
//        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
//        customer.setFullName(customerRequest.getFullName());
//        customer.setDateBirth(customerRequest.getDateBirth());
//        customer.setCitizenId(customerRequest.getCitizenId());
//        customer.setPhoneNumber(customerRequest.getPhoneNumber());
//        customer.setEmail(customerRequest.getEmail());
//        customer.setGender(customerRequest.getGender());
//        customer.setPassword(customerRequest.getPassword());
//
//
//        // Updating addresses
////        customer.setAddresses(customerRequest.getAddresses().stream()
////                .map(addressRequest -> new Address(addressRequest, customer))
////                .toList());
////        List<Address> addresses = customerRequest.getAddresses().stream()
////                .map(addressRequest -> new Address(addressRequest, customer))
////                .toList();
////        customer.setAddresses(addresses);
////
//
//
//        return customerRepository.save(customer);
//    }
//
//    @Override
//    public void deleteCustomer(Long id) {
//        customerRepository.deleteById(id);
//    }
//
//    @Override
//    public Customer getCustomerById(Long id) {
//        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
//    }
//
//    @Override
//    public List<Customer> getAllCustomers() {
//        return customerRepository.findAll();
//    }
//}






package com.poly.app.domain.admin.customer.service.impl;

import com.poly.app.domain.admin.customer.request.CustomerRequest;
import com.poly.app.domain.admin.customer.service.CustomerService;
import com.poly.app.domain.model.Address;
import com.poly.app.domain.model.Customer;
import com.poly.app.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setCode(customerRequest.getCode());
        customer.setFullName(customerRequest.getFullName());
        customer.setDateBirth(customerRequest.getDateBirth());
        customer.setCitizenId(customerRequest.getCitizenId());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        customer.setEmail(customerRequest.getEmail());
        customer.setGender(customerRequest.getGender());
        customer.setPassword(customerRequest.getPassword());

        // Đảm bảo danh sách addresses được khởi tạo không null
        customer.setAddresses(new ArrayList<>());

        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, CustomerRequest customerRequest) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
        customer.setFullName(customerRequest.getFullName());
        customer.setDateBirth(customerRequest.getDateBirth());
        customer.setCitizenId(customerRequest.getCitizenId());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        customer.setEmail(customerRequest.getEmail());
        customer.setGender(customerRequest.getGender());
        customer.setPassword(customerRequest.getPassword());

        // Đảm bảo danh sách addresses được khởi tạo không null
        if (customer.getAddresses() == null) {
            customer.setAddresses(new ArrayList<>());
        }

        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void processCustomerAddresses(Long customerId) {
        Customer customer = getCustomerById(customerId);

        // Lấy danh sách địa chỉ của khách hàng và đảm bảo không null
        List<Address> addresses = customer.getAddresses() != null ? customer.getAddresses() : Collections.emptyList();

        if (!addresses.isEmpty()) {
            addresses.stream().forEach(address -> {
                // Thực hiện các thao tác với từng address
                System.out.println("Processing address: " + address);
            });
        } else {
            System.out.println("No addresses to process.");
        }
    }
}
