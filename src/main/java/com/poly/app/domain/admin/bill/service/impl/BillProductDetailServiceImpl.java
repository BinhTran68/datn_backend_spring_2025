package com.poly.app.domain.admin.bill.service.impl;

import com.poly.app.domain.admin.bill.response.BillProductDetailResponse;
import com.poly.app.domain.admin.bill.service.BillProductDetailService;
import com.poly.app.domain.model.Bill;
import com.poly.app.domain.model.BillDetail;
import com.poly.app.domain.model.Image;
import com.poly.app.domain.model.ProductDetail;
import com.poly.app.domain.repository.BillDetailRepository;
import com.poly.app.domain.repository.BillRepository;
import com.poly.app.domain.repository.ImageRepository;
import com.poly.app.domain.repository.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
