package com.poly.app.domain.repository;

import com.poly.app.domain.model.ProductDetail;
import com.poly.app.domain.response.productdetail.ProductDetailResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Integer> {

    @Query(value = "select  new com.poly.app.domain.response.productdetail.ProductDetailResponse" +
            "(pd.id,pd.code,pd.productId.productName,pd.brandId.brandName,pd.typeId.typeName,pd.colorId.colorName" +
            ",pd.materialId.materialName,pd.sizeId.sizeName,pd.soleId.soleName,pd.genderId.genderName,pd.quantity" +
            ",pd.price,pd.weight,pd.descrition,pd.status,pd.updatedAt,pd.updatedBy) from ProductDetail pd order by pd.createdAt desc ")
    Page<ProductDetailResponse> getAllProductDetailPage(Pageable pageable);

}
