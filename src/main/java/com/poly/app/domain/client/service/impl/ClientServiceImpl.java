package com.poly.app.domain.client.service.impl;

import com.poly.app.domain.admin.product.response.color.ColorResponse;
import com.poly.app.domain.admin.product.response.img.ImgResponse;
import com.poly.app.domain.admin.product.response.productdetail.ProductDetailResponse;
import com.poly.app.domain.admin.product.response.size.SizeResponse;
import com.poly.app.domain.client.repository.ProductViewRepository;
import com.poly.app.domain.client.response.ProductViewResponse;
import com.poly.app.domain.client.service.ClientService;
import com.poly.app.domain.model.Product;
import com.poly.app.domain.model.ProductDetail;
import com.poly.app.domain.repository.ColorRepository;
import com.poly.app.domain.repository.ImageRepository;
import com.poly.app.domain.repository.ProductRepository;
import com.poly.app.domain.repository.SizeRepository;
import com.poly.app.infrastructure.exception.ApiException;
import com.poly.app.infrastructure.exception.ErrorCode;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ClientServiceImpl implements ClientService {
    ProductViewRepository productViewRepository;
    ImageRepository imageRepository;
    SizeRepository sizeRepository;
    ColorRepository colorRepository;
    ProductRepository productRepository;

    @Override
    public Page<ProductViewResponse> getAllProductHadPromotion(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productViewRepository.getAllProductHadPromotion(pageable);
    }

    @Override
    public Page<ProductViewResponse> getAllProductHadSoleDesc(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productViewRepository.getAllProductHadSoleDesc(pageable);
    }

    @Override
    public Page<ProductViewResponse> getAllProductHadCreatedAtDesc(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productViewRepository.getAllProductHadCreatedAtDesc(pageable);
    }

    @Override
    public Page<ProductViewResponse> getAllProductHadViewsDesc(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productViewRepository.getAllProductHadViewsDesc(pageable);
    }

    @Override
    public ProductDetailResponse findProductDetailbyProductIdAndColorIdAndSizeId(int productId, int colorId, int sizeId) {
        try {
            ProductDetailResponse productDetail = productViewRepository.findByProductAndColorAndSize(productId, colorId, sizeId);
            List<ImgResponse> images = imageRepository.findByProductDetailId(productDetail.getId());
            productDetail.setImage(images);
            return productDetail;

        } catch (Exception e) {
            throw new IllegalArgumentException("ko tìm thấy chi tiết sản phẩm này");
        }


    }

    @Override
    public List<SizeResponse> findSizesByProductId(Integer productId) {
        return sizeRepository.findSizesByProductId(productId);
    }

    @Override
    public List<SizeResponse> findSizesByProductIdAndColorId(Integer productId, Integer colorId) {
        return sizeRepository.findSizesByProductIdAndColorId(productId, colorId);
    }

    @Override
    public List<ColorResponse> findColorsByProductId(Integer productId) {
        return colorRepository.findColorsByProductId(productId);
    }

    @Override
    public Integer addViewProduct(int productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ApiException(ErrorCode.SANPHAM_NOT_FOUND));
        int viewCurrent = (product.getViews() != null) ? product.getViews() : 0;
        product.setViews(viewCurrent + 1);
        productRepository.save(product);
        return product.getViews();
    }
}
