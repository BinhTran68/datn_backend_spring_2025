
package com.poly.app.domain.repository;

import com.poly.app.domain.model.Customer;
import com.poly.app.domain.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByEmail(String email);
    Customer findByPhoneNumber(String phoneNumber);
    Optional<Customer> findCustomerByEmailAndPassword(String email, String password);


}





