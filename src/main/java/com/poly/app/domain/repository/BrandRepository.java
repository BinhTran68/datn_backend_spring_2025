package com.poly.app.domain.repository;

import com.poly.app.domain.model.Address;
import com.poly.app.domain.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand,Integer> {
}
