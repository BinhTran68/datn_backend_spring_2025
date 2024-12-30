package com.poly.app.domain.client.repository;

import com.poly.app.domain.model.BillDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail,Integer> {
}
