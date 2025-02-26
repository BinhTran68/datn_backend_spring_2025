package com.poly.app.domain.client.repository;


import com.poly.app.domain.admin.product.response.productdetail.ProductDetailResponse;
import com.poly.app.domain.client.response.ProductViewResponse;
import com.poly.app.domain.model.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductViewRepository extends JpaRepository<ProductDetail, Integer> {
    //    đây là repo của client
//    hiển thị các sản phẩm đang đưuọc giảm giá
    @Query(
            value = "SELECT " +
                    "p.id AS product_id, " +
                    "CONCAT(p.product_name, ' [', c.color_name, '-', g.gender_name, ']') AS product_name, " +
                    "MAX(pd.id) AS product_detail_id, " +
                    "MIN(pd.price) AS price, " +
                    "MAX(pd.sold) AS sold, " +
                    "MAX(pd.color_id) AS color_id," +
                    "MAX(pd.size_id) AS size_id," +
                    "MAX(pd.tag) AS tag, " +
                    "COALESCE((" +
                    "SELECT i.url " +
                    "FROM image i " +
                    "WHERE i.product_detail_id = MAX(pd.id) " +
                    "ORDER BY i.is_default DESC " +
                    "LIMIT 1" +
                    "), '') AS image_url, " +
                    "COALESCE(MAX(pr.promotion_name), 'Không có khuyến mãi') AS promotion_name, " +
                    "COALESCE(MAX(pr.discount_value), 0) AS discount_value, " +
                    "COALESCE(MAX(pr.promotion_type), 'none') AS promotion_type, " +
                    "MAX(pd.created_at) AS created_at " +
                    "FROM product p " +
                    "JOIN product_detail pd ON p.id = pd.product_id " +
                    "LEFT JOIN color c ON pd.color_id = c.id " +
                    "LEFT JOIN gender g ON pd.gender_id = g.id " +
                    "LEFT JOIN promotion_detail prd ON pd.id = prd.product_detail_id " +
                    "LEFT JOIN promotion pr ON prd.promotion_id = pr.id " +
                    "WHERE p.status = 0 " +
                    "AND pd.status = 0 " +
                    "AND pr.id IS NOT NULL " +
                    "AND NOW() BETWEEN pr.start_date AND pr.end_date " +
                    "GROUP BY p.id, p.product_name, c.color_name, g.gender_name " +
                    "ORDER BY created_at DESC",

            countQuery = "SELECT COUNT(*) FROM (" +
                         "SELECT p.id " +
                         "FROM product p " +
                         "JOIN product_detail pd ON p.id = pd.product_id " +
                         "LEFT JOIN color c ON pd.color_id = c.id " +
                         "LEFT JOIN gender g ON pd.gender_id = g.id " +
                         "LEFT JOIN promotion_detail prd ON pd.id = prd.product_detail_id " +
                         "LEFT JOIN promotion pr ON prd.promotion_id = pr.id " +
                         "WHERE p.status = 0 " +
                         "AND pd.status = 0 " +
                         "AND pr.id IS NOT NULL " +
                         "AND NOW() BETWEEN pr.start_date AND pr.end_date " +
                         "GROUP BY p.id, p.product_name, c.color_name, g.gender_name" +
                         ") AS tmp",
            nativeQuery = true
    )
    Page<ProductViewResponse> getAllProductHadPromotion(Pageable pageable);

//    lấy cacs sản phẩm có lượt bán nhiều nhất

    @Query(
            value = "SELECT " +
                    "p.id AS product_id, " +
                    "CONCAT(p.product_name, ' [', c.color_name, '-', g.gender_name, ']') AS product_name, " +
                    "MAX(pd.id) AS product_detail_id, " +
                    "MIN(pd.price) AS price, " +
                    "MAX(pd.sold) AS sold, " +
                    "MAX(pd.color_id) AS color_id," +
                    "MAX(pd.size_id) AS size_id," +
                    "MAX(pd.tag) AS tag, " +
                    "COALESCE((" +
                    "SELECT i.url " +
                    "FROM image i " +
                    "WHERE i.product_detail_id = MAX(pd.id) " +
                    "ORDER BY i.is_default DESC " +
                    "LIMIT 1" +
                    "), '') AS image_url, " +
                    "COALESCE(MAX(pr.promotion_name), 'Không có khuyến mãi') AS promotion_name, " +
                    "COALESCE(MAX(pr.discount_value), 0) AS discount_value, " +
                    "COALESCE(MAX(pr.promotion_type), 'none') AS promotion_type " +
                    "FROM product p " +
                    "JOIN product_detail pd ON p.id = pd.product_id " +
                    "LEFT JOIN color c ON pd.color_id = c.id " +
                    "LEFT JOIN gender g ON pd.gender_id = g.id " +
                    "LEFT JOIN promotion_detail prd ON pd.id = prd.product_detail_id " +
                    "LEFT JOIN promotion pr ON prd.promotion_id = pr.id " +
                    "WHERE p.status = 0 AND pd.status = 0 " +
                    "GROUP BY p.id, p.product_name, c.color_name, g.gender_name " +
                    "ORDER BY sold DESC ",
            countQuery = "SELECT COUNT(*) FROM ( " +
                         "SELECT p.id " +
                         "FROM product p " +
                         "JOIN product_detail pd ON p.id = pd.product_id " +
                         "LEFT JOIN color c ON pd.color_id = c.id " +
                         "LEFT JOIN gender g ON pd.gender_id = g.id " +
                         "LEFT JOIN promotion_detail prd ON pd.id = prd.product_detail_id " +
                         "LEFT JOIN promotion pr ON prd.promotion_id = pr.id " +
                         "WHERE p.status = 0 AND pd.status = 0 " +
                         "GROUP BY p.id, p.product_name, c.color_name, g.gender_name " +
                         ") AS tmp",
            nativeQuery = true
    )
    Page<ProductViewResponse> getAllProductHadSoleDesc(Pageable pageable);

    //lấy các sản phẩm mới theo ngày tạo
    @Query(
            value = "SELECT " +
                    "p.id AS product_id, " +
                    "CONCAT(p.product_name, ' [', c.color_name, '-', g.gender_name, ']') AS product_name, " +
                    "MAX(pd.id) AS product_detail_id, " +
                    "MIN(pd.price) AS price, " +
                    "MAX(pd.sold) AS sold, " +
                    "MAX(pd.color_id) AS color_id," +
                    "MAX(pd.size_id) AS size_id," +
                    "MAX(pd.tag) AS tag, " +
                    "COALESCE((" +
                    "SELECT i.url " +
                    "FROM image i " +
                    "WHERE i.product_detail_id = MAX(pd.id) " +
                    "ORDER BY i.is_default DESC " +
                    "LIMIT 1" +
                    "), '') AS image_url, " +
                    "COALESCE(MAX(pr.promotion_name), 'Không có khuyến mãi') AS promotion_name, " +
                    "COALESCE(MAX(pr.discount_value), 0) AS discount_value, " +
                    "COALESCE(MAX(pr.promotion_type), 'none') AS promotion_type, " +
                    "MAX(pd.created_at) AS created_at " +
                    "FROM product p " +
                    "JOIN product_detail pd ON p.id = pd.product_id " +
                    "LEFT JOIN color c ON pd.color_id = c.id " +
                    "LEFT JOIN gender g ON pd.gender_id = g.id " +
                    "LEFT JOIN promotion_detail prd ON pd.id = prd.product_detail_id " +
                    "LEFT JOIN promotion pr ON prd.promotion_id = pr.id " +
                    "WHERE p.status = 0 AND pd.status = 0 " +
                    "GROUP BY p.id, p.product_name, c.color_name, g.gender_name " +
                    "ORDER BY created_at DESC ",
            countQuery = "SELECT COUNT(*) FROM ( " +
                         "SELECT p.id " +
                         "FROM product p " +
                         "JOIN product_detail pd ON p.id = pd.product_id " +
                         "LEFT JOIN color c ON pd.color_id = c.id " +
                         "LEFT JOIN gender g ON pd.gender_id = g.id " +
                         "LEFT JOIN promotion_detail prd ON pd.id = prd.product_detail_id " +
                         "LEFT JOIN promotion pr ON prd.promotion_id = pr.id " +
                         "WHERE p.status = 0 AND pd.status = 0 " +
                         "GROUP BY p.id, p.product_name, c.color_name, g.gender_name " +
                         ") AS tmp",
            nativeQuery = true
    )
    Page<ProductViewResponse> getAllProductHadCreatedAtDesc(Pageable pageable);

    //sắp xếp theo lượt vào xem của sản phẩm
    @Query(
            value = "SELECT \n" +
                    "    p.id AS product_id,\n" +
                    "    -- Gộp tên sản phẩm với màu, giới tính\n" +
                    "    CONCAT(p.product_name, ' [', c.color_name, '-', g.gender_name, ']') AS product_name,\n" +
                    "    \n" +
                    "    MAX(pd.id) AS product_detail_id,\n" +
                    "    MIN(pd.price) AS price,\n" +
                    "    MAX(pd.sold) AS sold,  \n" +
                    "MAX(pd.color_id) AS color_id," +
                    "MAX(pd.size_id) AS size_id," +
                    "    MAX(pd.tag) AS tag,    \n" +
                    "\n" +
                    "    -- Lấy ảnh theo màu\n" +
                    "    COALESCE((\n" +
                    "        SELECT i.url \n" +
                    "        FROM image i \n" +
                    "        WHERE i.product_detail_id = MAX(pd.id)  \n" +
                    "        ORDER BY i.is_default DESC\n" +
                    "        LIMIT 1\n" +
                    "    ), '') AS image_url,\n" +
                    "p.views,\n" +
                    "    COALESCE(MAX(pr.promotion_name), 'Không có khuyến mãi') AS promotion_name,\n" +
                    "    COALESCE(MAX(pr.discount_value), 0) AS discount_value,\n" +
                    "    COALESCE(MAX(pr.promotion_type), 'none') AS promotion_type,\n" +
                    "MAX(pd.created_at) AS created_at \n" +
                    "FROM product p\n" +
                    "JOIN product_detail pd ON p.id = pd.product_id\n" +
                    "LEFT JOIN color c ON pd.color_id = c.id  -- Lấy tên màu từ bảng color\n" +
                    "LEFT JOIN gender g ON pd.gender_id = g.id  -- Lấy tên giới tính\n" +
                    "LEFT JOIN promotion_detail prd ON pd.id = prd.product_detail_id\n" +
                    "LEFT JOIN promotion pr ON prd.promotion_id = pr.id\n" +
                    "\n" +
                    "WHERE p.status = 0 AND pd.status = 0\n" +
                    "GROUP BY p.id, p.product_name, c.color_name, g.gender_name\n" +
                    "\n" +
                    "ORDER BY p.views DESC",
            countQuery = "SELECT COUNT(*) FROM ( " +
                         "SELECT p.id " +
                         "FROM product p " +
                         "JOIN product_detail pd ON p.id = pd.product_id " +
                         "LEFT JOIN color c ON pd.color_id = c.id " +
                         "LEFT JOIN gender g ON pd.gender_id = g.id " +
                         "LEFT JOIN promotion_detail prd ON pd.id = prd.product_detail_id " +
                         "LEFT JOIN promotion pr ON prd.promotion_id = pr.id " +
                         "WHERE p.status = 0 AND pd.status = 0 " +
                         "GROUP BY p.id, p.product_name, c.color_name, g.gender_name " +
                         ") AS tmp",
            nativeQuery = true
    )
    Page<ProductViewResponse> getAllProductHadViewsDesc(Pageable pageable);

    @Query(value = "select  new com.poly.app.domain.admin.product.response.productdetail.ProductDetailResponse" +
                   "(pd.id,pd.code,pd.product.productName,pd.brand.brandName,pd.type.typeName,pd.color.colorName" +
                   ",pd.material.materialName,pd.size.sizeName,pd.sole.soleName,pd.gender.genderName,pd.quantity" +
                   ",pd.price,pd.weight,pd.descrition,pd.status,pd.updatedAt,pd.updatedBy) from ProductDetail pd  " +
                   "where pd.product.id = :productId and pd.color.id= :colorId and pd.size.id = :sizeId")
    ProductDetailResponse findByProductAndColorAndSize(int productId, int colorId, int sizeId);


}
