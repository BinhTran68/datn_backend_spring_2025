package com.poly.app.domain.repository;

import com.poly.app.domain.model.Bill;
import com.poly.app.domain.model.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail,Integer> {

    List<BillDetail> findByBill(Bill bill);

}
