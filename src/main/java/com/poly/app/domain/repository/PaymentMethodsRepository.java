package com.poly.app.domain.repository;

import com.poly.app.domain.model.PaymentMethods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentMethodsRepository extends JpaRepository<PaymentMethods,Integer> {
}
