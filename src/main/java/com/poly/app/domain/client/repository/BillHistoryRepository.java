package com.poly.app.domain.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillHistoryRepository extends JpaRepository<com.poly.app.domain.model.BillHistory,Integer> {
}
