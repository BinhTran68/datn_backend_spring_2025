package com.poly.app.domain.repository;

import com.poly.app.domain.model.Bill;
import com.poly.app.infrastructure.constant.StatusBill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill,Integer> {

    Page<Bill> findByStatus(StatusBill status, Pageable pageable);

}
