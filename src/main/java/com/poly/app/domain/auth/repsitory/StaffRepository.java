package com.poly.app.domain.auth.repsitory;

import com.poly.app.domain.model.Customer;
import com.poly.app.domain.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Integer> {
    Staff findByEmail(String email);
}
