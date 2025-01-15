package com.poly.app.domain.repository;

import com.poly.app.domain.model.Bill;
import com.poly.app.infrastructure.constant.BillStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill,Integer>, JpaSpecificationExecutor<Bill> {

    Page<Bill> findByStatus(BillStatus status, Pageable pageable);





    Bill findByCode(String billCode);



}
