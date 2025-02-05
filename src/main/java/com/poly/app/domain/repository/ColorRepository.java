package com.poly.app.domain.repository;

import com.poly.app.domain.admin.product.response.color.ColorResponse;
import com.poly.app.domain.admin.product.response.color.ColorResponseSelect;
import com.poly.app.domain.admin.product.response.color.ColorResponse;
import com.poly.app.domain.model.Color;
import com.poly.app.domain.model.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    @Query(value = "select new com.poly.app.domain.admin.product.response.color.ColorResponse(c.id,c.code,c.colorName,c.updatedAt,c.status) from Color c where c.colorName like :name order by c.createdAt desc")
    Page<ColorResponse> fillbyname(String name, Pageable pageable);

    @Query(value = "select new com.poly.app.domain.admin.product.response.color.ColorResponse(c.id,c.code,c.colorName,c.updatedAt,c.status) from Color c order by c.createdAt desc")
    Page<ColorResponse> getAll(Pageable pageable);

    @Query(value = "select new com.poly.app.domain.admin.product.response.color.ColorResponseSelect(b.id,b.colorName,b.status,b.code) from Color b order by b.createdAt desc ")
    List<ColorResponseSelect> dataSelect();

    boolean existsByColorName(String name);

    boolean existsByColorNameAndIdNot(String name, Integer id);
}
