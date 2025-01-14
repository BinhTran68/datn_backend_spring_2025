package com.poly.app.domain.admin.product.service.Impl;

import com.poly.app.domain.admin.product.response.material.MaterialResponse;
import com.poly.app.domain.model.Product;
import com.poly.app.domain.repository.ProductRepository;
import com.poly.app.domain.admin.product.request.product.ProductRequest;
import com.poly.app.domain.admin.product.response.product.ProductResponse;
import com.poly.app.domain.admin.product.service.ProductService;
import com.poly.app.infrastructure.exception.ApiException;
import com.poly.app.infrastructure.exception.ErrorCode;
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

public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;


    @Override
    public Product createProduct(ProductRequest request) {
        if (productRepository.existsByProductName(request.getProductName())) {
            throw new ApiException(ErrorCode.BRAND_EXISTS );
        }
        Product product = Product.builder()
                .productName(request.getProductName())
                .status(request.getStatus())
                .build();
        return productRepository.save(product);
    }

    @Override
    public ProductResponse updateProduct(ProductRequest request, int id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id ko tồn tại"));

        if (productRepository.existsByProductNameAndIdNot(request.getProductName(),id)) {
            throw new ApiException(ErrorCode.BRAND_EXISTS );
        }
        product.setProductName(request.getProductName());
        product.setStatus(request.getStatus());

        productRepository.save(product);

        return ProductResponse.builder()
                .code(product.getCode())
                .id(product.getId())
                .productName(product.getProductName())
                .updateAt(product.getCreatedAt())
                .status(product.getStatus())
                .build();
    }


    @Override
    public Page<ProductResponse> getAllProduct(int page, int product) {
        Pageable pageable = PageRequest.of(page, product);
        Page<ProductResponse> response= productRepository.getAll(pageable);
        return response ;    }

    @Override
    public Page<ProductResponse> fillbyProductName(int page, int product, String name) {
        Pageable pageable = PageRequest.of(page, product);


        Page<ProductResponse> productPage = productRepository.fillbyname(String.format("%%%s%%", name), pageable);
        log.info(name);
        // Chuyển đổi từ Page<Product> sang Page<ProductResponse>
        return productPage;
    }


    @Override
    public String delete(int id) {
        if (!productRepository.findById(id).isEmpty()) {
            productRepository.deleteById(id);
            return "xóa thành công";
        } else {
            return "id ko tồn tại";
        }


    }

    @Override
    public ProductResponse getProduct(int id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id ko tồn tại"));

        return ProductResponse.builder()
                .code(product.getCode())
                .id(product.getId())
                .productName(product.getProductName())
                .updateAt(product.getUpdatedAt())
                .status(product.getStatus())
                .build();
    }

    @Override
    public boolean existsByProductName(String productName) {
        if (productRepository.existsByProductName(productName)) return true;
        return false;
    }

    @Override
    public boolean existsByProductNameAndIdNot(String productName, Integer id) {
        if (productRepository.existsByProductNameAndIdNot(productName, id)) return true;
        return false;
    }
}
