package com.poly.app.domain.client.service.impl;

import com.poly.app.domain.client.repository.ProductViewRepository;
import com.poly.app.domain.client.response.ProductViewResponse;
import com.poly.app.domain.client.service.ClientService;
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
}
