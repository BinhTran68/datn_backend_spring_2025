package com.poly.app.domain.client.repository;

import com.poly.app.domain.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material,Integer> {
}
