package com.poly.app.domain.repository;

import com.poly.app.domain.model.ProductDetail;
import com.poly.app.domain.admin.product.response.productdetail.FilterProductDetailResponse;
import com.poly.app.domain.admin.product.response.productdetail.ProductDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {

    @Query(value = "select  new com.poly.app.domain.admin.product.response.productdetail.ProductDetailResponse" +
            "(pd.id,pd.code,pd.product.productName,pd.brand.brandName,pd.type.typeName,pd.color.colorName" +
            ",pd.material.materialName,pd.size.sizeName,pd.sole.soleName,pd.gender.genderName,pd.quantity" +
            ",pd.price,pd.weight,pd.descrition,pd.status,pd.updatedAt,pd.updatedBy) from ProductDetail pd order by pd.createdAt desc ")
    Page<ProductDetailResponse> getAllProductDetailPage(Pageable pageable);


    @Query(value = "CALL sp_FilterProductDetails(" +
            ":productName, :brandName, :typeName, :colorName, :materialName," +
            " :sizeName, :soleName, :genderName, :status, :sortByQuantity, :sortByPrice, :page, :size);", nativeQuery = true)
    List<FilterProductDetailResponse> getFilterProductDetail(@Param("productName") String productName,
                                                             @Param("brandName") String brandName,
                                                             @Param("typeName") String typeName,
                                                             @Param("colorName") String colorName,
                                                             @Param("materialName") String materialName,
                                                             @Param("sizeName") String sizeName,
                                                             @Param("soleName") String soleName,
                                                             @Param("genderName") String genderName,
                                                             @Param("status") String status,
                                                             @Param("sortByQuantity") String sortByQuantity,
                                                             @Param("sortByPrice") String sortByPrice,
                                                             @Param("page") int page,
                                                             @Param("size") int size

    );

    @Query(value = """
             SELECT COUNT(pd.id)\s
                FROM\s
                    product_detail pd
                    JOIN product p ON pd.product_id = p.id
                    JOIN brand b ON pd.brand_id = b.id
                    JOIN type t ON pd.type_id = t.id
                    JOIN color c ON pd.color_id = c.id
                    JOIN material m ON pd.material_id = m.id
                    JOIN size s ON pd.size_id = s.id
                    JOIN sole so ON pd.sole_id = so.id
                    JOIN gender g ON pd.gender_id = g.id
                WHERE\s
                    (:productName IS NULL OR p.product_name LIKE CONCAT("%", :productName, "%"))
                    AND (:brandName IS NULL OR b.brand_name LIKE CONCAT("%", :brandName, "%"))
                    AND (:typeName IS NULL OR t.type_name LIKE CONCAT("%", :typeName, "%"))
                    AND (:colorName IS NULL OR c.color_name LIKE CONCAT("%", :colorName, "%"))
                    AND (:materialName IS NULL OR m.material_name LIKE CONCAT("%", :materialName, "%"))
                    AND (:sizeName IS NULL OR s.size_name LIKE CONCAT("%", :sizeName, "%"))
                    AND (:soleName IS NULL OR so.sole_name LIKE CONCAT("%", :soleName, "%"))
                    AND (:genderName IS NULL OR g.gender_name LIKE CONCAT("%", :genderName, "%"))
                    AND (:status IS NULL OR pd.status = :status)
            """, nativeQuery = true)
    Integer getInforpage(@Param("productName") String productName,
                         @Param("brandName") String brandName,
                         @Param("typeName") String typeName,
                         @Param("colorName") String colorName,
                         @Param("materialName") String materialName,
                         @Param("sizeName") String sizeName,
                         @Param("soleName") String soleName,
                         @Param("genderName") String genderName,
                         @Param("status") String status

    );
}
