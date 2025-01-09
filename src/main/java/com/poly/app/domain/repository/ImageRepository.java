package com.poly.app.domain.repository;

import com.poly.app.domain.model.Image;
import com.poly.app.domain.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image,Integer> {

    Image getImagesByProductDetailAndIsDefault(ProductDetail productDetail, Boolean isDefault);

}
