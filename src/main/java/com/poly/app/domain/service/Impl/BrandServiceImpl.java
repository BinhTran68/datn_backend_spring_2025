package com.poly.app.domain.service.Impl;

import com.poly.app.domain.model.Brand;
import com.poly.app.domain.repository.BrandRepository;
import com.poly.app.domain.request.brand.BrandRequest;
import com.poly.app.domain.response.brand.BrandResponse;
import com.poly.app.domain.service.BrandService;
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

public class BrandServiceImpl implements BrandService {

    BrandRepository brandRepository;


    @Override
    public Brand createBrand(BrandRequest request) {
        Brand brand = Brand.builder()
                .brandName(request.getBrandName())
                .status(request.getStatus())
                .build();
        return brandRepository.save(brand);
    }

    @Override
    public BrandResponse updateBrand(BrandRequest request, int id) {
        Brand brand = brandRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id ko tồn tại"));

        brand.setBrandName(request.getBrandName());
        brand.setStatus(request.getStatus());

        brandRepository.save(brand);

        return BrandResponse.builder()
                .code(brand.getCode())
                .id(brand.getId())
                .brandName(brand.getBrandName())
                .updateAt(brand.getCreatedAt())
                .status(brand.getStatus())
                .build();
    }

    @Override
    public List<BrandResponse> getAllBrand() {
        return brandRepository.findAll().stream()
                .map(brand -> new BrandResponse(brand.getId(), brand.getCode(), brand.getBrandName(), brand.getUpdatedAt(), brand.getStatus())).toList();
    }

    @Override
    public String delete(int id) {
        if ( !brandRepository.findById(id).isEmpty()){
            brandRepository.deleteById(id);
            return "xóa thành công";
        }else{
            return "id ko tồn tại";
        }


    }

    @Override
    public BrandResponse getBrand(int id) {
        Brand brand = brandRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id ko tồn tại"));

        return BrandResponse.builder()
                .code(brand.getCode())
                .id(brand.getId())
                .brandName(brand.getBrandName())
                .updateAt(brand.getUpdatedAt())
                .status(brand.getStatus())
                .build();
    }
}
