package com.poly.app.domain.repository;

import com.poly.app.domain.admin.product.response.brand.BrandResponse;
import com.poly.app.domain.admin.product.response.color.ColorResponse;
import com.poly.app.domain.model.Brand;
import com.poly.app.domain.model.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color,Integer> {
    @Query(value = "select new com.poly.app.domain.admin.product.response.color.ColorResponse(c.id,c.code,c.colorName,c.updatedAt,c.status) from Color c where c.colorName like :name order by c.createdAt desc")
    Page<ColorResponse> fillbyname(String name, Pageable pageable);

    @Query(value = "select new com.poly.app.domain.admin.product.response.color.ColorResponse(c.id,c.code,c.colorName,c.updatedAt,c.status) from Color c order by c.createdAt desc")
    Page<ColorResponse> getAll(Pageable pageable);

    boolean existsByColorName(String name);

    boolean existsByColorNameAndIdNot(String name, Integer id);
}
