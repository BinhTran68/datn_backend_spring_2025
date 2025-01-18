package com.poly.app.domain.repository;

import com.poly.app.domain.admin.product.response.sole.SoleResponseSelect;
import com.poly.app.domain.admin.product.response.sole.SoleResponse;
import com.poly.app.domain.admin.product.response.sole.SoleResponse;
import com.poly.app.domain.model.Sole;
import com.poly.app.domain.model.Sole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoleRepository extends JpaRepository<Sole,Integer> {
    @Query(value = "select new com.poly.app.domain.admin.product.response.sole.SoleResponse(c.id,c.code,c.soleName,c.updatedAt,c.status) from Sole c where c.soleName like :name order by c.createdAt desc")
    Page<SoleResponse> fillbyname(String name, Pageable pageable);

    @Query(value = "select new com.poly.app.domain.admin.product.response.sole.SoleResponse(c.id,c.code,c.soleName,c.updatedAt,c.status) from Sole c order by c.createdAt desc")
    Page<SoleResponse> getAll(Pageable pageable);

    boolean existsBySoleName(String name);

    boolean existsBySoleNameAndIdNot(String name, Integer id);
    @Query(value = "select new com.poly.app.domain.admin.product.response.sole.SoleResponseSelect(b.id,b.soleName,b.status) from Sole b order by b.createdAt desc ")
    List<SoleResponseSelect> dataSelect();
}
