package com.poly.app.domain.repository;

import com.poly.app.domain.model.Brand;
import com.poly.app.domain.model.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail,Integer> {
}
