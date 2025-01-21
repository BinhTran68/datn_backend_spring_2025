package com.poly.app.domain.repository;

import com.poly.app.domain.admin.product.response.product.ProductResponseSelect;
import com.poly.app.domain.admin.product.response.product.ProductResponse;
import com.poly.app.domain.admin.product.response.product.ProductResponse;
import com.poly.app.domain.model.Product;
import com.poly.app.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query(value = "select new com.poly.app.domain.admin.product.response.product.ProductResponse(c.id,c.code,c.productName,c.updatedAt,c.status) from Product c where c.productName like :name order by c.createdAt desc")
    Page<ProductResponse> fillbyname(String name, Pageable pageable);

    @Query(value = "select new com.poly.app.domain.admin.product.response.product.ProductResponse(c.id,c.code,c.productName,c.updatedAt,c.status) from Product c order by c.createdAt desc")
    Page<ProductResponse> getAll(Pageable pageable);

    boolean existsByProductName(String name);

    boolean existsByProductNameAndIdNot(String name, Integer id);
    @Query(value = "select new com.poly.app.domain.admin.product.response.product.ProductResponseSelect(b.id,b.productName,b.status) from Product b order by b.createdAt desc ")
    List<ProductResponseSelect> dataSelect();
}
