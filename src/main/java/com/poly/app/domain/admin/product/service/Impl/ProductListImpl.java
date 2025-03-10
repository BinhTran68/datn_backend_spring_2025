package com.poly.app.domain.admin.product.service.Impl;

import com.poly.app.domain.admin.product.response.product.ProductResponse;
import com.poly.app.domain.admin.product.service.ProductListService;
import com.poly.app.domain.admin.product.service.ProductService;
import com.poly.app.domain.admin.voucher.response.VoucherReponse;
import com.poly.app.domain.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductListImpl implements ProductListService {
    @Autowired
    ProductRepository productRepository;
    @Override
    public List<ProductResponse> getAllIn() {
        return productRepository.findAll().stream()
                .map(product -> ProductResponse.fromEntity(product)).toList();
    }

    public Page<ProductResponse> getAllIn(Pageable pageable) {

        return productRepository.findAll(pageable).map(ProductResponse::fromEntity


        );
    }
    }


