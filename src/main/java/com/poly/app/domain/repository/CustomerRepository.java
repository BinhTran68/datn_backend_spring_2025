
package com.poly.app.domain.repository;

import com.poly.app.domain.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByEmail(String email);
    Optional<Customer> findCustomerByEmailAndPassword(String email, String password);


}





