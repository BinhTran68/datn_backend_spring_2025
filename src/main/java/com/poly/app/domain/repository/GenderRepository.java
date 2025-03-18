package com.poly.app.domain.repository;

import com.poly.app.domain.admin.product.response.gender.GenderResponseSelect;
import com.poly.app.domain.admin.product.response.gender.GenderResponse;
import com.poly.app.domain.model.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Integer> {
    @Query(value = "select new com.poly.app.domain.admin.product.response.gender.GenderResponse(c.id,c.code,c.genderName,c.updatedAt,c.status) from Gender c where c.genderName like :name order by c.createdAt desc")
    Page<GenderResponse> fillbyname(String name, Pageable pageable);

    @Query(value = "select new com.poly.app.domain.admin.product.response.gender.GenderResponse(c.id,c.code,c.genderName,c.updatedAt,c.status) from Gender c order by c.createdAt desc")
    Page<GenderResponse> getAll(Pageable pageable);

    boolean existsByGenderName(String name);

    boolean existsByGenderNameAndIdNot(String name, Integer id);
    @Query(value = "select new com.poly.app.domain.admin.product.response.gender.GenderResponseSelect(b.id,b.genderName,b.status) from Gender b order by b.createdAt desc ")
    List<GenderResponseSelect> dataSelect();
    @Query(value = "select new com.poly.app.domain.admin.product.response.gender.GenderResponseSelect(b.id,b.genderName,b.status) from Gender b where b.status=0 order by b.createdAt desc ")
    List<GenderResponseSelect> dataSelecthd();
}
