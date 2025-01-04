package com.poly.app.domain.service.Impl;

import com.poly.app.domain.model.*;
import com.poly.app.domain.repository.*;
import com.poly.app.domain.request.productdetail.FilterRequest;
import com.poly.app.domain.request.productdetail.ProductDetailRequest;
import com.poly.app.domain.response.productdetail.FilterProductDetailResponse;
import com.poly.app.domain.response.productdetail.ProductDetailResponse;
import com.poly.app.domain.service.ProductDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
                .descrition(request.getDescrition())
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
        productDetail.setDescrition(request.getDescrition());
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
                .descrition(productDetail.getDescrition())
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
                .descrition(productDetail.getDescrition())
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
        log.info("tổng element"+Integer.toString(totalElement));
        return totalElement;
    }
}
