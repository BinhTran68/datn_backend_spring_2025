package com.poly.app.domain.repository;

import com.poly.app.domain.admin.product.response.img.ImgResponse;
import com.poly.app.domain.model.Image;
import com.poly.app.domain.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image,Integer> {

    Image getImagesByProductDetailAndIsDefault(ProductDetail productDetail, Boolean isDefault);
    @Query("SELECT new com.poly.app.domain.admin.product.response.img.ImgResponse(i.id,i.url,i.publicId) " +
           "FROM Image i WHERE i.productDetail.id = :productDetailId")
    List<ImgResponse> findByProductDetailId(@Param("productDetailId") Integer productDetailId);

    Image getImageByPublicId(String publicId);

}
