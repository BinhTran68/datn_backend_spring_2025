package com.poly.app.domain.response.productdetail;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@SqlResultSetMapping(
        name = "FilterProductDetailMapping",
        classes = @ConstructorResult(
                targetClass = FilterProductDetailDTO1.class,
                columns = {
                        @ColumnResult(name = "id", type = Integer.class),
                        @ColumnResult(name = "code", type = String.class),
                        @ColumnResult(name = "product_name", type = String.class),
                        @ColumnResult(name = "brand_name", type = String.class),
                        @ColumnResult(name = "type_name", type = String.class),
                        @ColumnResult(name = "color_name", type = String.class),
                        @ColumnResult(name = "material_name", type = String.class),
                        @ColumnResult(name = "size_name", type = String.class),
                        @ColumnResult(name = "sole_name", type = String.class),
                        @ColumnResult(name = "gender_name", type = String.class),
                        @ColumnResult(name = "quantity", type = Integer.class),
                        @ColumnResult(name = "price", type = Double.class),
                        @ColumnResult(name = "weight", type = Double.class),
                        @ColumnResult(name = "descrition", type = String.class),
                        @ColumnResult(name = "status", type = String.class),
                        @ColumnResult(name = "updatedAt", type = Long.class),
                        @ColumnResult(name = "updatedBy", type = String.class)
                }
        )
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterProductDetailDTO1 {

    @Id
    private Integer id;  // Thêm @Id để đánh dấu trường id là khóa chính

    private String code;
    private String productName;
    private String brandName;
    private String typeName;
    private String colorName;
    private String materialName;
    private String sizeName;
    private String soleName;
    private String genderName;
    private Integer quantity;
    private Double price;
    private Double weight;
    private String descrition;
    private String status;
    private Long updatedAt;
    private String updatedBy;


}
