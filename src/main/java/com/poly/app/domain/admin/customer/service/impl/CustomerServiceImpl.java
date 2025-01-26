
package com.poly.app.domain.admin.customer.service.impl;

import com.poly.app.domain.admin.customer.request.AddressRequest;
import com.poly.app.domain.admin.customer.request.CustomerRequest;
import com.poly.app.domain.admin.customer.response.AddressResponse;
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






    @Override
    public AddressResponse updateAddress(Integer addressId, AddressRequest addressRequest) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            address.setProvinceId(addressRequest.getProvinceId());
            address.setDistrictId(addressRequest.getDistrictId());
            address.setWardId(addressRequest.getWardId());
            address.setSpecificAddress(addressRequest.getSpecificAddress());
            address.setIsAddressDefault(addressRequest.getIsAddressDefault());
            Address updatedAddress = addressRepository.save(address);
            return new AddressResponse(updatedAddress);
        } else {
            throw new RuntimeException("Address not found");
        }
    }

    @Override
    public void deleteAddress(Integer addressId) {
        addressRepository.deleteById(addressId);
    }

    @Override
    public AddressResponse addAddress(Integer customerId, AddressRequest addressRequest) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            Address address = Address.builder()
                    .provinceId(addressRequest.getProvinceId())
                    .districtId(addressRequest.getDistrictId())
                    .wardId(addressRequest.getWardId())
                    .specificAddress(addressRequest.getSpecificAddress())
                    .isAddressDefault(addressRequest.getIsAddressDefault())
                    .build();
            address.setCustomer(customer);
            Address savedAddress = addressRepository.save(address);
            return new AddressResponse(savedAddress);
        } else {
            throw new RuntimeException("Customer not found");
        }
    }

    @Override
    public void setDefaultAddress(Integer addressId) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            // Set existing default address to false
            List<Address> addresses = addressRepository.findByCustomerId(address.getCustomer().getId());
            addresses.forEach(addr -> {
                if (addr.getIsAddressDefault()) {
                    addr.setIsAddressDefault(false);
                    addressRepository.save(addr);
                }
            });
            // Set the selected address to default
            address.setIsAddressDefault(true);
            addressRepository.save(address);
        } else {
            throw new RuntimeException("Address not found");
        }
    }


}