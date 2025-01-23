////package com.poly.app.domain.admin.customer.service.impl;
////
////import com.poly.app.domain.admin.customer.request.CustomerRequest;
////import com.poly.app.domain.admin.customer.service.CustomerService;
////import com.poly.app.domain.model.Address;
////import com.poly.app.domain.model.Customer;
////import com.poly.app.domain.repository.CustomerRepository;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.stereotype.Service;
////
////import java.util.ArrayList;
////import java.util.Collections;
////import java.util.List;
////
////@Service
////public class CustomerServiceImpl implements CustomerService {
////
////    @Autowired
////    private CustomerRepository customerRepository;
////
////    @Override
////    public Customer createCustomer(CustomerRequest customerRequest) {
////        Customer customer = new Customer();
////        customer.setCode(customerRequest.getCode());
////        customer.setFullName(customerRequest.getFullName());
////        customer.setDateBirth(customerRequest.getDateBirth());
////        customer.setCitizenId(customerRequest.getCitizenId());
////        customer.setPhoneNumber(customerRequest.getPhoneNumber());
////        customer.setEmail(customerRequest.getEmail());
////        customer.setGender(customerRequest.getGender());
////        customer.setPassword(customerRequest.getPassword());
////
////        // Đảm bảo danh sách addresses được khởi tạo không null
////        customer.setAddresses(new ArrayList<>());
////
////        return customerRepository.save(customer);
////    }
////
////    @Override
////    public Customer updateCustomer(Long id, CustomerRequest customerRequest) {
////        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
////        customer.setFullName(customerRequest.getFullName());
////        customer.setDateBirth(customerRequest.getDateBirth());
////        customer.setCitizenId(customerRequest.getCitizenId());
////        customer.setPhoneNumber(customerRequest.getPhoneNumber());
////        customer.setEmail(customerRequest.getEmail());
////        customer.setGender(customerRequest.getGender());
////        customer.setPassword(customerRequest.getPassword());
////
////        // Đảm bảo danh sách addresses được khởi tạo không null
////        if (customer.getAddresses() == null) {
////            customer.setAddresses(new ArrayList<>());
////        }
////
////        return customerRepository.save(customer);
////    }
////
////    @Override
////    public void deleteCustomer(Long id) {
////        customerRepository.deleteById(id);
////    }
////
////    @Override
////    public Customer getCustomerById(Long id) {
////        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
////    }
////
////    @Override
////    public List<Customer> getAllCustomers() {
////        return customerRepository.findAll();
////    }
////
////    public void processCustomerAddresses(Long customerId) {
////        Customer customer = getCustomerById(customerId);
////
////        // Lấy danh sách địa chỉ của khách hàng và đảm bảo không null
////        List<Address> addresses = customer.getAddresses() != null ? customer.getAddresses() : Collections.emptyList();
////
////        if (!addresses.isEmpty()) {
////            addresses.stream().forEach(address -> {
////                // Thực hiện các thao tác với từng address
////                System.out.println("Processing address: " + address);
////            });
////        } else {
////            System.out.println("No addresses to process.");
////        }
////    }
////}
//
//
//
//
//package com.poly.app.domain.admin.customer.service.impl;
//
//import com.poly.app.domain.admin.customer.request.CustomerRequest;
//import com.poly.app.domain.admin.customer.service.CustomerService;
//import com.poly.app.domain.model.Address;
//import com.poly.app.domain.model.Customer;
//import com.poly.app.domain.repository.CustomerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
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
//        // Chuyển AddressRequest sang Address và gán vào danh sách addresses của Customer
//        List<Address> addresses = customerRequest.getAddresses().stream()
//                .map(addressRequest -> {
//                    Address address = new Address();
//                    address.setProvinceId(addressRequest.getProvinceId());
//                    address.setDistrictId(addressRequest.getDistrictId());
//                    address.setWardId(addressRequest.getWardId());
//                    address.setIsAddressDefault(addressRequest.getIsAddressDefault());
//                    address.setSpecificAddress(addressRequest.getSpecificAddress());
//                    address.setCustomer(customer);  // Gán customer vào mỗi address
//                    return address;
//                })
//                .collect(Collectors.toList());
//
//        customer.setAddresses(addresses); // Gán danh sách địa chỉ vào customer
//        return customerRepository.save(customer); // Lưu customer và địa chỉ vào DB
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
//        // Chuyển AddressRequest sang Address và cập nhật danh sách địa chỉ
//        List<Address> addresses = customerRequest.getAddresses().stream()
//                .map(addressRequest -> {
//                    Address address = new Address();
//                    address.setProvinceId(addressRequest.getProvinceId());
//                    address.setDistrictId(addressRequest.getDistrictId());
//                    address.setWardId(addressRequest.getWardId());
//                    address.setIsAddressDefault(addressRequest.getIsAddressDefault());
//                    address.setSpecificAddress(addressRequest.getSpecificAddress());
//                    address.setCustomer(customer);  // Gán customer vào mỗi address
//                    return address;
//                })
//                .collect(Collectors.toList());
//
//        customer.setAddresses(addresses); // Cập nhật danh sách địa chỉ vào customer
//        return customerRepository.save(customer); // Lưu customer và địa chỉ vào DB
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



//package com.poly.app.domain.admin.customer.service.impl;
//
//import com.poly.app.domain.admin.customer.request.CustomerRequest;
//import com.poly.app.domain.admin.customer.service.CustomerService;
//import com.poly.app.domain.model.Address;
//import com.poly.app.domain.model.Customer;
//import com.poly.app.domain.repository.CustomerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
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
//        // Kiểm tra nếu addresses là null, gán nó thành một danh sách rỗng
//        List<Address> addresses = customerRequest.getAddresses() != null
//                ? customerRequest.getAddresses().stream()
//                .map(addressRequest -> {
//                    Address address = new Address();
//                    address.setProvinceId(addressRequest.getProvinceId());
//                    address.setDistrictId(addressRequest.getDistrictId());
//                    address.setWardId(addressRequest.getWardId());
//                    address.setIsAddressDefault(addressRequest.getIsAddressDefault());
//                    address.setSpecificAddress(addressRequest.getSpecificAddress());
//                    address.setCustomer(customer);  // Liên kết với customer
//                    return address;
//                })
//                .collect(Collectors.toList())
//                : new ArrayList<>();  // Tránh NullPointerException nếu addresses là null
//
//        customer.setAddresses(addresses); // Gán danh sách địa chỉ vào customer
//        return customerRepository.save(customer); // Lưu customer và địa chỉ vào DB
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
//        // Chuyển AddressRequest sang Address và cập nhật danh sách địa chỉ
//        List<Address> addresses = customerRequest.getAddresses().stream()
//                .map(addressRequest -> {
//                    Address address = new Address();
//                    address.setProvinceId(addressRequest.getProvinceId());
//                    address.setDistrictId(addressRequest.getDistrictId());
//                    address.setWardId(addressRequest.getWardId());
//                    address.setIsAddressDefault(addressRequest.getIsAddressDefault());
//                    address.setSpecificAddress(addressRequest.getSpecificAddress());
//                    address.setCustomer(customer);  // Liên kết với customer
//                    return address;
//                })
//                .collect(Collectors.toList());
//
//        customer.setAddresses(addresses); // Cập nhật danh sách địa chỉ vào customer
//        return customerRepository.save(customer); // Lưu customer và địa chỉ vào DB
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
import com.poly.app.domain.admin.customer.response.CustomerResponse;
import com.poly.app.domain.admin.customer.service.CustomerService;
import com.poly.app.domain.model.Address;
import com.poly.app.domain.model.Customer;
import com.poly.app.domain.repository.AddressRepository;
import com.poly.app.domain.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setFullName(customerRequest.getFullName());
        customer.setEmail(customerRequest.getEmail());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        customer.setDateBirth(customerRequest.getDateBirth());
        customer.setPassword(customerRequest.getPassword());
        customer.setCitizenId(customerRequest.getCitizenId());
        customer.setGender(customerRequest.getGender());
        customer.setAvatar(customerRequest.getAvatar());
        customer.setStatus(customerRequest.getStatus());
        customerRepository.save(customer);
        Address address = Address.builder()
                .provinceId(customerRequest.getProvinceId())
                .districtId(customerRequest.getDistrictId())
                .wardId(customerRequest.getWardId())
                .specificAddress(customerRequest.getSpecificAddress())
                .build();
        address.setCustomer(customer);
        address.setIsAddressDefault(true);
        addressRepository.save(address);

        Customer customerFromDB = customerRepository.findById(customer.getId()).orElse(null);
        assert customerFromDB != null;
        return new CustomerResponse(customerFromDB);
    }

    @Override
    public CustomerResponse updateCustomer(Integer id, CustomerRequest customerRequest) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            // Update fields
            customer.setFullName(customerRequest.getFullName());
            customer.setEmail(customerRequest.getEmail());
            customer.setPhoneNumber(customerRequest.getPhoneNumber());
            customer.setDateBirth(customerRequest.getDateBirth());
            customer.setPassword(customerRequest.getPassword());
            customer.setCitizenId(customerRequest.getCitizenId());
            customer.setGender(customerRequest.getGender());
            customer.setAvatar(customerRequest.getAvatar());
            customer.setStatus(customerRequest.getStatus());

            // Clear existing addresses
            customer.getAddresses().clear();



            Customer updatedCustomer = customerRepository.save(customer);
            return new CustomerResponse(updatedCustomer);
        } else {
            throw new RuntimeException("Customer not found");
        }
    }

    @Override
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerResponse getCustomerById(Integer id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        return optionalCustomer.map(CustomerResponse::new).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public List<CustomerResponse> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(CustomerResponse::new).collect(Collectors.toList());
    }

    @Override
    public CustomerResponse getCustomerByEmail(String email) {
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.findByEmail(email));
        return optionalCustomer.map(CustomerResponse::new).orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}