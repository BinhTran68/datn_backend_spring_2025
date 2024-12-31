package com.poly.app.domain.service.Impl;

import com.poly.app.domain.model.*;
import com.poly.app.domain.repository.*;
import com.poly.app.domain.request.productdetail.ProductDetailRequest;
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
                .productId(product)
                .brandId(brand)
                .typeId(type)
                .colorId(color)
                .materialId(material)
                .sizeId(size)
                .soleId(sole)
                .genderId(gender)
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


        productDetail.setProductId(product);
        productDetail.setBrandId(brand);
        productDetail.setTypeId(type);
        productDetail.setColorId(color);
        productDetail.setMaterialId(material);
        productDetail.setSizeId(size);
        productDetail.setSoleId(sole);
        productDetail.setGenderId(gender);
        productDetail.setQuantity(request.getQuantity());
        productDetail.setPrice(request.getPrice());
        productDetail.setWeight(request.getWeight());
        productDetail.setDescrition(request.getDescrition());
        productDetail.setStatus(request.getStatus());

        productDetailRepository.save(productDetail);
        return ProductDetailResponse.builder()
                .id(productDetail.getId())
                .code(productDetail.getCode())
                .productName(productDetail.getProductId().getProductName())
                .brandName(productDetail.getBrandId().getBrandName())
                .typeName(productDetail.getTypeId().getTypeName())
                .colorName(productDetail.getColorId().getColorName())
                .materialName(productDetail.getMaterialId().getMaterialName())
                .sizeName(productDetail.getSizeId().getSizeName())
                .soleName(productDetail.getSoleId().getSoleName())
                .genderName(productDetail.getGenderId().getGenderName())
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
                .productName(productDetail.getProductId().getProductName())
                .brandName(productDetail.getBrandId().getBrandName())
                .typeName(productDetail.getTypeId().getTypeName())
                .colorName(productDetail.getColorId().getColorName())
                .materialName(productDetail.getMaterialId().getMaterialName())
                .sizeName(productDetail.getSizeId().getSizeName())
                .soleName(productDetail.getSoleId().getSoleName())
                .genderName(productDetail.getGenderId().getGenderName())
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
}
