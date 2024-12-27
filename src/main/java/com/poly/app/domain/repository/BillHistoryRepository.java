package com.poly.app.domain.repository;

import com.poly.app.domain.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillHistoryRepository extends JpaRepository<com.poly.app.domain.model.BillHistory,Integer> {
}
