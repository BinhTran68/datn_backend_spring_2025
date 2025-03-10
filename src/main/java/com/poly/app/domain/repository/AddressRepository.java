package com.poly.app.domain.repository;

import com.poly.app.domain.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {
    List<Address> findByCustomerId(Integer id);


    Optional<Object> findByCustomerIdAndIsAddressDefault(Integer id, boolean b);
}
