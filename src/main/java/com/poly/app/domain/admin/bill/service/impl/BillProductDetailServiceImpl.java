package com.poly.app.domain.admin.bill.service.impl;

import com.poly.app.domain.admin.bill.response.BillProductDetailResponse;
import com.poly.app.domain.admin.bill.response.BillProductResponse;
import com.poly.app.domain.admin.bill.service.BillProductDetailService;
import com.poly.app.domain.model.Bill;
import com.poly.app.domain.model.BillDetail;
import com.poly.app.domain.model.Brand;
import com.poly.app.domain.model.Color;
import com.poly.app.domain.model.Gender;
import com.poly.app.domain.model.Image;
import com.poly.app.domain.model.Material;
import com.poly.app.domain.model.Product;
import com.poly.app.domain.model.ProductDetail;
import com.poly.app.domain.model.Size;
import com.poly.app.domain.model.Sole;
import com.poly.app.domain.model.Type;
import com.poly.app.domain.repository.BillDetailRepository;
import com.poly.app.domain.repository.BillRepository;
import com.poly.app.domain.repository.ImageRepository;
import com.poly.app.domain.repository.ProductDetailRepository;
import com.poly.app.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BillProductDetailServiceImpl implements BillProductDetailService {

    @Autowired
    BillRepository billRepository;

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Autowired
    BillDetailRepository billDetailRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ProductRepository productRepository;


    @Override
    public List<BillProductDetailResponse> getBillProductDetailResponse(String billCode) {

        Bill bill = billRepository.findByCode(billCode);

        List<BillDetail> billDetails = billDetailRepository.findByBill(bill);


        List<BillProductDetailResponse> billProductDetailResponseList  =
                billDetails.stream().map(billDetail ->
                        BillProductDetailResponse
                                .builder()
                                .productName(billDetail.getProductDetail().getProduct().getProductName())
                                .color(billDetail.getProductDetail().getColor().getColorName())
                                .size(billDetail.getProductDetail().getSize().getSizeName())
                                .price(billDetail.getProductDetail().getPrice())
                                .quantity(billDetail.getQuantity())
                                .totalPrice(billDetail.getPrice())
                                .urlImage(
                                        Optional.ofNullable(
                                                        imageRepository.getImagesByProductDetailAndIsDefault(billDetail.getProductDetail(), true)
                                                )
                                                .map(Image::getUrl) // Lấy URL nếu không null
                                                .orElse(null)
                                        )
                                .build())
                .collect(Collectors.toList());

        return billProductDetailResponseList;
    }



    @Override
    public Page<BillProductResponse> getBillProductResponsePage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productRepository.findAll(pageable);
        List<BillProductResponse> billProductResponses = productPage.getContent().stream()
                .map(this::convertProductToBillProductResponse)
                .collect(Collectors.toList());

        // 4. Tạo Page<BillProductResponse> từ kết quả đã ánh xạ
        return new PageImpl<>(billProductResponses, pageable, productPage.getTotalElements());
    }

    private BillProductResponse convertProductToBillProductResponse(Product product) {
        List<ProductDetail> productDetails = product.getProductDetails();
        List<Size> sizes = new ArrayList<>();
        List<Color> colors = new ArrayList<>();
        List<Gender> genders = new ArrayList<>();
        List<Material> materials = new ArrayList<>();
        List<Sole> soles = new ArrayList<>();
        List<Image> images = new ArrayList<>();
        List<Type> types = new ArrayList<>();
        List<Brand> brands = new ArrayList<>();
        for (ProductDetail productDetail : productDetails) {
            brands.add(productDetail.getBrand());
            colors.add(productDetail.getColor());
            sizes.add(productDetail.getSize());
            types.add(productDetail.getType());
            materials.add(productDetail.getMaterial());
            soles.add(productDetail.getSole());
        }
        return BillProductResponse.builder()
                .id(product.getId())
                .code(product.getCode())
                .productName(product.getProductName())
                .totalQuantity(product.getProductDetails().stream().mapToInt(ProductDetail::getQuantity).sum())
                .colors(new HashSet<>(colors))
                .genders(genders)
                .materials(materials)
                .sizes(sizes)
                .soles(soles)
                .types(types)
                .brand(!brands.isEmpty() ? brands.get(0) : null)
                .build();
    }




}
