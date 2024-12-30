package com.poly.app.domain.repository;

import com.poly.app.domain.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill,Integer> {
}
