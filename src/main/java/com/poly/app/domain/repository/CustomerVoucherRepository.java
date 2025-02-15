package com.poly.app.domain.repository;

import com.poly.app.domain.model.Customer;
import com.poly.app.domain.model.CustomerVoucher;
import com.poly.app.domain.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerVoucherRepository extends JpaRepository<CustomerVoucher,Integer> {

    List<CustomerVoucher> findCustomerVouchersByVoucher(Voucher voucher);

}
