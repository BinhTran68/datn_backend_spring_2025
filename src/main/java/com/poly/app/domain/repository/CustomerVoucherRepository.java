package com.poly.app.domain.repository;

import com.poly.app.domain.model.Brand;
import com.poly.app.domain.model.CustomerVoucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerVoucherRepository extends JpaRepository<CustomerVoucher,Integer> {
}
