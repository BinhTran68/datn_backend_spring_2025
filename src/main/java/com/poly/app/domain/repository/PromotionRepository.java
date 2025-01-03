package com.poly.app.domain.repository;

import com.poly.app.domain.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion,Integer> {
}
