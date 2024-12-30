package com.poly.app.domain.client.repository;

import com.poly.app.domain.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size,Integer> {
}
