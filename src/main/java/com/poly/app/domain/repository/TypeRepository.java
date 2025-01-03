package com.poly.app.domain.repository;

import com.poly.app.domain.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type,Integer> {
}
