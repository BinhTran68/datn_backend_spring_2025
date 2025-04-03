package com.poly.app.domain.admin.product.response.productdetail;
import com.poly.app.domain.client.response.PromotionResponse;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FilterProductDetailWithPromotionDTO {
    private Integer id;
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
    private String description;
    private String status;
    private String updatedAt;
    private String updatedBy;
    private String image;
    private PromotionResponse promotionResponse; // client

    // Constructor nhận từ interface
    public static FilterProductDetailWithPromotionDTO fromEntity(FilterProductDetailResponse response, PromotionResponse promotionResponse) {
        return FilterProductDetailWithPromotionDTO.builder()
                .id(response.getId())
                .code(response.getCode())
                .productName(response.getProductName())
                .brandName(response.getBrandName())
                .typeName(response.getTypeName())
                .colorName(response.getColorName())
                .materialName(response.getMaterialName())
                .sizeName(response.getSizeName())
                .soleName(response.getSoleName())
                .genderName(response.getGenderName())
                .quantity(response.getQuantity())
                .price(response.getPrice())
                .weight(response.getWeight())
                .description(response.getDescrition()) // Chú ý lỗi chính tả: nên là getDescription()
                .status(response.getStatus())
                .updatedAt(response.getUpdatedAt())
                .updatedBy(response.getUpdatedBy())
                .image(response.getImage())
                .promotionResponse(promotionResponse)
                .build();
    }

}
