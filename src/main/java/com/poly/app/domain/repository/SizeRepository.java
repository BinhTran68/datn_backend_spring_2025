package com.poly.app.domain.repository;

import com.poly.app.domain.admin.product.response.size.SizeResponse;
import com.poly.app.domain.admin.product.response.size.SizeResponse;
import com.poly.app.domain.model.Size;
import com.poly.app.domain.model.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size,Integer> {
    @Query(value = "select new com.poly.app.domain.admin.product.response.size.SizeResponse(c.id,c.code,c.sizeName,c.updatedAt,c.status) from Size c where c.sizeName like :name order by c.createdAt desc")
    Page<SizeResponse> fillbyname(String name, Pageable pageable);

    @Query(value = "select new com.poly.app.domain.admin.product.response.size.SizeResponse(c.id,c.code,c.sizeName,c.updatedAt,c.status) from Size c order by c.createdAt desc")
    Page<SizeResponse> getAll(Pageable pageable);

    boolean existsBySizeName(String name);

    boolean existsBySizeNameAndIdNot(String name, Integer id);
}
