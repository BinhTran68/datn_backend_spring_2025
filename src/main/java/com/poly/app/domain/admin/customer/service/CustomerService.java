    package com.poly.app.domain.admin.customer.service;
    import com.poly.app.domain.admin.customer.request.AddressRequest;
    import com.poly.app.domain.admin.customer.request.CustomerRequest;
    import com.poly.app.domain.admin.customer.response.AddressResponse;
    import com.poly.app.domain.admin.customer.response.CustomerResponse;

    import java.util.List;

    public interface CustomerService {
        CustomerResponse createCustomer(CustomerRequest customerRequest);
        CustomerResponse updateCustomer(Integer id, CustomerRequest customerRequest);
        void deleteCustomer(Integer id);
        CustomerResponse getCustomerById(Integer id);
        List<CustomerResponse> getAllCustomers();
        CustomerResponse getCustomerByEmail(String email);





        AddressResponse updateAddress(Integer addressId, AddressRequest addressRequest);
        void deleteAddress(Integer addressId);
        AddressResponse addAddress(Integer customerId, AddressRequest addressRequest);
        void setDefaultAddress(Integer addressId);
    }
