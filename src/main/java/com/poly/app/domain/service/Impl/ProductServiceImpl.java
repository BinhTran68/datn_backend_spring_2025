package com.poly.app.domain.service.Impl;

import com.poly.app.domain.model.Product;
import com.poly.app.domain.repository.ProductRepository;
import com.poly.app.domain.request.product.ProductRequest;
import com.poly.app.domain.response.product.ProductResponse;
import com.poly.app.domain.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
        Product product = Product.builder()
                .productName(request.getProductName())
                .status(request.getStatus())
                .build();
        return productRepository.save(product);
    }

    @Override
    public ProductResponse updateProduct(ProductRequest request, int id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id ko tồn tại"));

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
    public List<ProductResponse> getAllProduct() {
        return productRepository.findAll().stream()
                .map(product -> new ProductResponse(product.getId(), product.getCode(),
                        product.getProductName(), product.getUpdatedAt(),
                        product.getStatus())).toList();
    }

    @Override
    public String deleteProduct(int id) {
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
}
