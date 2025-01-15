package com.poly.app.domain.repository;

import com.poly.app.domain.admin.product.response.brand.BrandResponse;
import com.poly.app.domain.admin.product.response.productdetail.ProductDetailResponse;
import com.poly.app.domain.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer> {
    @Query(value = "select new com.poly.app.domain.admin.product.response.brand.BrandResponse(b.id,b.code,b.brandName,b.updatedAt,b.status) from Brand b where b.brandName like :name order by b.createdAt desc")
    Page<BrandResponse> fillbyname(String name, Pageable pageable);

    @Query(value = "select * from brand order by created_at desc", nativeQuery = true)
    Page<Brand> getAll( Pageable pageable);

    boolean existsByBrandName(String brandName);

    boolean existsByBrandNameAndIdNot(String brandName, Integer id);
}
