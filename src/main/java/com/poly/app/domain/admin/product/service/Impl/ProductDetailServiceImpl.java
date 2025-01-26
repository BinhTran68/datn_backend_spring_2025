package com.poly.app.domain.admin.product.service.Impl;

import com.poly.app.domain.admin.product.response.color.ColorResponse;
import com.poly.app.domain.model.*;
import com.poly.app.domain.repository.*;
import com.poly.app.domain.admin.product.request.productdetail.FilterRequest;
import com.poly.app.domain.admin.product.request.productdetail.ProductDetailRequest;
import com.poly.app.domain.admin.product.response.productdetail.FilterProductDetailResponse;
import com.poly.app.domain.admin.product.response.productdetail.ProductDetailResponse;
import com.poly.app.domain.admin.product.service.ProductDetailService;
import com.poly.app.infrastructure.constant.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class ProductDetailServiceImpl implements ProductDetailService {

    ProductDetailRepository productDetailRepository;

    ProductRepository productRepository;

    BrandRepository brandRepository;

    TypeRepository typeRepository;

    ColorRepository colorRepository;

    MaterialRepository materialRepository;

    SizeRepository sizeRepository;

    SoleRepository soleRepository;

    GenderRepository genderRepository;


    @Override
    public ProductDetail createProductDetail(ProductDetailRequest request) {
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
        Brand brand = brandRepository.findById(request.getBrandId()).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
        Type type = typeRepository.findById(request.getTypeId()).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
        Color color = colorRepository.findById(request.getColorId()).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
        Material material = materialRepository.findById(request.getMaterialId()).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
        Size size = sizeRepository.findById(request.getSizeId()).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
        Sole sole = soleRepository.findById(request.getSoleId()).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
        Gender gender = genderRepository.findById(request.getGenderId()).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));

        ProductDetail productDetail = ProductDetail.builder()
                .product(product)
                .brand(brand)
                .type(type)
                .color(color)
                .material(material)
                .size(size)
                .sole(sole)
                .gender(gender)
                .quantity(request.getQuantity())
                .price(request.getPrice())
                .weight(request.getWeight())
                .descrition(request.getDescription())
                .status(request.getStatus())
                .build();
        return productDetailRepository.save(productDetail);
    }

    @Override
    public ProductDetailResponse updateProductDetail(ProductDetailRequest request, int id) {
        ProductDetail productDetail = productDetailRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id ko ton tai"));

        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
        Brand brand = brandRepository.findById(request.getBrandId()).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
        Type type = typeRepository.findById(request.getTypeId()).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
        Color color = colorRepository.findById(request.getColorId()).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
        Material material = materialRepository.findById(request.getMaterialId()).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
        Size size = sizeRepository.findById(request.getSizeId()).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
        Sole sole = soleRepository.findById(request.getSoleId()).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
        Gender gender = genderRepository.findById(request.getGenderId()).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));


        productDetail.setProduct(product);
        productDetail.setBrand(brand);
        productDetail.setType(type);
        productDetail.setColor(color);
        productDetail.setMaterial(material);
        productDetail.setSize(size);
        productDetail.setSole(sole);
        productDetail.setGender(gender);
        productDetail.setQuantity(request.getQuantity());
        productDetail.setPrice(request.getPrice());
        productDetail.setWeight(request.getWeight());
        productDetail.setDescrition(request.getDescription());
        productDetail.setStatus(request.getStatus());

        productDetailRepository.save(productDetail);
        return ProductDetailResponse.builder()
                .id(productDetail.getId())
                .code(productDetail.getCode())
                .productName(productDetail.getProduct().getProductName())
                .brandName(productDetail.getBrand().getBrandName())
                .typeName(productDetail.getType().getTypeName())
                .colorName(productDetail.getColor().getColorName())
                .materialName(productDetail.getMaterial().getMaterialName())
                .sizeName(productDetail.getSize().getSizeName())
                .soleName(productDetail.getSole().getSoleName())
                .genderName(productDetail.getGender().getGenderName())
                .quantity(productDetail.getQuantity())
                .price(productDetail.getPrice())
                .weight(productDetail.getWeight())
                .description(productDetail.getDescrition())
                .status(productDetail.getStatus())
                .updateAt(productDetail.getUpdatedAt())
                .updateBy(productDetail.getUpdatedBy())
                .build();
    }

//    @Override
//    public ProductDetailResponse updateProductDetail(ProductDetailRequest request, int id) {
//        ProductDetail productDetail = productDetailRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id ko tồn tại"));
//
//        productDetail.setProductDetailName(request.getProductDetailName());
//        productDetail.setStatus(request.getStatus());
//
//        productDetailRepository.save(productDetail);
//
//        return ProductDetailResponse.builder()
//                .code(productDetail.getCode())
//                .id(productDetail.getId())
//                .productDetailName(productDetail.getProductDetailName())
//                .updateAt(productDetail.getCreatedAt())
//                .status(productDetail.getStatus())
//                .build();
//    }

    @Override
    public List<ProductDetail> getAllProductDetail() {
        return productDetailRepository.findAll();
    }


    @Override
    public String deleteProductDetail(int id) {
        if (!productDetailRepository.findById(id).isEmpty()) {
            productDetailRepository.deleteById(id);
            return "xóa thành công";
        } else {
            return "id ko tồn tại";
        }


    }

    @Override
    public ProductDetailResponse getProductDetail(int id) {
        ProductDetail productDetail = productDetailRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id ko ton tai"));

        return ProductDetailResponse.builder()
                .id(productDetail.getId())
                .code(productDetail.getCode())
                .productName(productDetail.getProduct().getProductName())
                .brandName(productDetail.getBrand().getBrandName())
                .typeName(productDetail.getType().getTypeName())
                .colorName(productDetail.getColor().getColorName())
                .materialName(productDetail.getMaterial().getMaterialName())
                .sizeName(productDetail.getSize().getSizeName())
                .soleName(productDetail.getSole().getSoleName())
                .genderName(productDetail.getGender().getGenderName())
                .quantity(productDetail.getQuantity())
                .price(productDetail.getPrice())
                .weight(productDetail.getWeight())
                .description(productDetail.getDescrition())
                .status(productDetail.getStatus())
                .updateAt(productDetail.getUpdatedAt())
                .updateBy(productDetail.getUpdatedBy())
                .build();
    }
//
//    @Override
//    public ProductDetailResponse getProductDetail(int id) {
//        ProductDetail productDetail = productDetailRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id ko tồn tại"));
//
//        return ProductDetailResponse.builder()
//                .code(productDetail.getCode())
//                .id(productDetail.getId())
//                .productDetailName(productDetail.getProductDetailName())
//                .updateAt(productDetail.getUpdatedAt())
//                .status(productDetail.getStatus())
//                .build();
//    }

    @Override
    public Page<ProductDetailResponse> getAllProductDetailPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDetailResponse> page1 = productDetailRepository.getAllProductDetailPage(pageable);
        return page1;
    }

    @Override
    public List<FilterProductDetailResponse> filterProductDetail(int page, int size, FilterRequest request) {

        log.info(request.toString());

        List<FilterProductDetailResponse> list = productDetailRepository.getFilterProductDetail(
                request.getProductName(),
                request.getBrandName(),
                request.getTypeName(),
                request.getColorName(),
                request.getMaterialName(),
                request.getSizeName(),
                request.getSoleName(),
                request.getGenderName(),
                request.getStatus(),
                request.getSortByQuantity(),
                request.getSortByPrice(),
                page, size);

//        List<FilterProductDetailResponse> list = productDetailRepository.getFilterProductDetail(
//                null,
//                "nike",
//                null,
//                null,
//                null,
//                null,
//                null,
//                null,
//                "HOAT_DONG",
//                null,
//                null,
//                1,
//                1
//                );

        return list;
    }

    @Override
    public Integer getFillterElement(FilterRequest request) {
        Integer totalElement = productDetailRepository.getInforpage(
                request.getProductName(),
                request.getBrandName(),
                request.getTypeName(),
                request.getColorName(),
                request.getMaterialName(),
                request.getSizeName(),
                request.getSoleName(),
                request.getGenderName(),
                request.getStatus()

        );
        log.info("tổng element" + Integer.toString(totalElement));
        return totalElement;
    }

    @Override
    public Page<ColorResponse> fillbyName(int page, int size, String name) {
        return null;
    }

    @Override
    public boolean existsByColorName(String brandName) {
        return false;
    }

    @Override
    public boolean existsByColorNameAndIdNot(String brandName, Integer id) {
        return false;
    }

    @Override
    public List<ProductDetailResponse> createProductDetailList(List<ProductDetailRequest> requests) {
        // Tạo một danh sách để lưu các đối tượng ProductDetailResponse
        List<ProductDetailResponse> productDetailResponses = new ArrayList<>();

        // Lặp qua tất cả các request trong danh sách
        for (ProductDetailRequest request : requests) {
            // Kiểm tra xem sản phẩm đã tồn tại trong cơ sở dữ liệu với productId, sizeId, và colorId
            ProductDetail existingProductDetail = productDetailRepository.findByProductIdAndSizeIdAndColorId(
                    request.getProductId(),
                    request.getSizeId(),
                    request.getColorId(),
                    request.getBrandId(),
                    request.getGenderId(),
                    request.getMaterialId(),
                    request.getTypeId(),
                    request.getSoleId()
            );

            if (existingProductDetail != null) {

                System.out.println("ở đoạn trùng---------------------------------------------------");
                // Nếu bản ghi đã tồn tại, cập nhật bản ghi hiện tại
                existingProductDetail.setQuantity(request.getQuantity()+existingProductDetail.getQuantity());
                existingProductDetail.setPrice(request.getPrice());
                existingProductDetail.setWeight(request.getWeight());
                existingProductDetail.setDescrition(request.getDescription());
                existingProductDetail.setStatus(Status.HOAT_DONG);

                // Lưu lại bản ghi đã được cập nhật
                existingProductDetail = productDetailRepository.save(existingProductDetail);

                // Chuyển đổi ProductDetail thành ProductDetailResponse và thêm vào danh sách
                ProductDetailResponse response = ProductDetailResponse.builder()
                        .id(existingProductDetail.getId())
                        .productName(existingProductDetail.getProduct().getProductName())
                        .brandName(existingProductDetail.getBrand().getBrandName())
                        .typeName(existingProductDetail.getType().getTypeName())
                        .colorName(existingProductDetail.getColor().getColorName())
                        .materialName(existingProductDetail.getMaterial().getMaterialName())
                        .sizeName(existingProductDetail.getSize().getSizeName())
                        .soleName(existingProductDetail.getSole().getSoleName())
                        .genderName(existingProductDetail.getGender().getGenderName())
                        .quantity(existingProductDetail.getQuantity())
                        .price(existingProductDetail.getPrice())
                        .weight(existingProductDetail.getWeight())
                        .description(existingProductDetail.getDescrition())
                        .status(existingProductDetail.getStatus())
                        .updateAt(existingProductDetail.getUpdatedAt()) // Giả sử có trường updatedAt trong entity
                        .updateBy(existingProductDetail.getUpdatedBy()) // Giả sử có trường updatedBy trong entity
                        .build();

                productDetailResponses.add(response);
            } else {
                System.out.println("ở đoạn ko trùng---------------------------------------------------");

                // Nếu không tồn tại bản ghi, tạo mới bản ghi ProductDetail
                Product product = productRepository.findById(request.getProductId())
                        .orElseThrow(() -> new IllegalArgumentException("Product id không tồn tại"));
                Brand brand = brandRepository.findById(request.getBrandId())
                        .orElseThrow(() -> new IllegalArgumentException("Brand id không tồn tại"));
                Type type = typeRepository.findById(request.getTypeId())
                        .orElseThrow(() -> new IllegalArgumentException("Type id không tồn tại"));
                Color color = colorRepository.findById(request.getColorId())
                        .orElseThrow(() -> new IllegalArgumentException("Color id không tồn tại"));
                Material material = materialRepository.findById(request.getMaterialId())
                        .orElseThrow(() -> new IllegalArgumentException("Material id không tồn tại"));
                Size size = sizeRepository.findById(request.getSizeId())
                        .orElseThrow(() -> new IllegalArgumentException("Size id không tồn tại"));
                Sole sole = soleRepository.findById(request.getSoleId())
                        .orElseThrow(() -> new IllegalArgumentException("Sole id không tồn tại"));
                Gender gender = genderRepository.findById(request.getGenderId())
                        .orElseThrow(() -> new IllegalArgumentException("Gender id không tồn tại"));

                // Tạo đối tượng ProductDetail
                ProductDetail productDetail = ProductDetail.builder()
                        .product(product)
                        .brand(brand)
                        .type(type)
                        .color(color)
                        .material(material)
                        .size(size)
                        .sole(sole)
                        .gender(gender)
                        .quantity(request.getQuantity())
                        .price(request.getPrice())
                        .weight(request.getWeight())
                        .descrition(request.getDescription())
                        .status(Status.HOAT_DONG)
                        .build();

                // Lưu ProductDetail vào cơ sở dữ liệu
                productDetail = productDetailRepository.save(productDetail);
                log.info(productDetail.toString());

                // Chuyển đổi ProductDetail thành ProductDetailResponse và thêm vào danh sách
                ProductDetailResponse response = ProductDetailResponse.builder()
                        .id(productDetail.getId())
                        .productName(productDetail.getProduct().getProductName())
                        .brandName(productDetail.getBrand().getBrandName())
                        .typeName(productDetail.getType().getTypeName())
                        .colorName(productDetail.getColor().getColorName())
                        .materialName(productDetail.getMaterial().getMaterialName())
                        .sizeName(productDetail.getSize().getSizeName())
                        .soleName(productDetail.getSole().getSoleName())
                        .genderName(productDetail.getGender().getGenderName())
                        .quantity(productDetail.getQuantity())
                        .price(productDetail.getPrice())
                        .weight(productDetail.getWeight())
                        .description(productDetail.getDescrition())
                        .status(productDetail.getStatus())
                        .updateAt(productDetail.getUpdatedAt())  // Giả sử có trường updatedAt trong entity
                        .updateBy(productDetail.getUpdatedBy())  // Giả sử có trường updatedBy trong entity
                        .build();

                productDetailResponses.add(response);
            }
        }

        // Trả về danh sách ProductDetailResponse
        return productDetailResponses;
    }

}
