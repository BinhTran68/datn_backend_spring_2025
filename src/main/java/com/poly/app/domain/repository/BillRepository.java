package com.poly.app.domain.repository;

import com.poly.app.domain.model.Bill;
import com.poly.app.infrastructure.constant.BillStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer>, JpaSpecificationExecutor<Bill> {

    Page<Bill> findByStatus(BillStatus status, Pageable pageable);


    Bill findByBillCode(String billCode);

    Boolean existsByBillCode(String billCode);

    @Query(value = "select b from Bill b where b.customer.id = :customerId order by b.createdAt desc")
    Page<Bill> findByCustomerId(Integer customerId,Pageable pageable);
    @Query("SELECT o.status, COUNT(o) FROM Bill o GROUP BY o.status")
    List<Object[]> countOrdersByStatus();


}
