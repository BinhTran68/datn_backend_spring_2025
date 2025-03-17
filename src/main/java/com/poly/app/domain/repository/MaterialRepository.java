package com.poly.app.domain.repository;

import com.poly.app.domain.admin.product.response.material.MaterialResponseSelect;
import com.poly.app.domain.admin.product.response.material.MaterialResponse;
import com.poly.app.domain.model.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material,Integer> {
    @Query(value = "select new com.poly.app.domain.admin.product.response.material.MaterialResponse(c.id,c.code,c.materialName,c.updatedAt,c.status) from Material c where c.materialName like :name order by c.createdAt desc")
    Page<MaterialResponse> fillbyname(String name, Pageable pageable);

    @Query(value = "select new com.poly.app.domain.admin.product.response.material.MaterialResponse(c.id,c.code,c.materialName,c.updatedAt,c.status) from Material c order by c.createdAt desc")
    Page<MaterialResponse> getAll(Pageable pageable);

    boolean existsByMaterialName(String name);

    boolean existsByMaterialNameAndIdNot(String name, Integer id);
    @Query(value = "select new com.poly.app.domain.admin.product.response.material.MaterialResponseSelect(b.id,b.materialName,b.status) from Material b order by b.createdAt desc ")
    List<MaterialResponseSelect> dataSelect();
    @Query(value = "select new com.poly.app.domain.admin.product.response.material.MaterialResponseSelect(b.id,b.materialName,b.status) from Material b where b.status=0 order by b.createdAt desc ")
    List<MaterialResponseSelect> dataSelecthd();
}
