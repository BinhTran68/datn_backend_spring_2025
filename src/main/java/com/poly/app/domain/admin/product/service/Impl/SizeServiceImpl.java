package com.poly.app.domain.admin.product.service.Impl;

import com.poly.app.domain.admin.product.response.material.MaterialResponse;
import com.poly.app.domain.model.Size;
import com.poly.app.domain.repository.SizeRepository;
import com.poly.app.domain.admin.product.request.size.SizeRequest;
import com.poly.app.domain.admin.product.response.size.SizeResponse;
import com.poly.app.domain.admin.product.service.SizeService;
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

public class SizeServiceImpl implements SizeService {

    SizeRepository sizeRepository;


    @Override
    public Size createSize(SizeRequest request) {
        if (sizeRepository.existsBySizeName(request.getSizeName())) {
            throw new ApiException(ErrorCode.BRAND_EXISTS );
        }
        Size size = Size.builder()
                .sizeName(request.getSizeName())
                .status(request.getStatus())
                .build();
        return sizeRepository.save(size);
    }

    @Override
    public SizeResponse updateSize(SizeRequest request, int id) {
        Size size = sizeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id ko tồn tại"));

        if (sizeRepository.existsBySizeNameAndIdNot(request.getSizeName(),id)) {
            throw new ApiException(ErrorCode.BRAND_EXISTS );
        }
        size.setSizeName(request.getSizeName());
        size.setStatus(request.getStatus());

        sizeRepository.save(size);

        return SizeResponse.builder()
                .code(size.getCode())
                .id(size.getId())
                .sizeName(size.getSizeName())
                .updateAt(size.getCreatedAt())
                .status(size.getStatus())
                .build();
    }


    @Override
    public Page<SizeResponse> getAllSize(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SizeResponse> response= sizeRepository.getAll(pageable);
        return response ;    }

    @Override
    public Page<SizeResponse> fillbySizeName(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);


        Page<SizeResponse> sizePage = sizeRepository.fillbyname(String.format("%%%s%%", name), pageable);
        log.info(name);
        // Chuyển đổi từ Page<Size> sang Page<SizeResponse>
        return sizePage;
    }


    @Override
    public String delete(int id) {
        if (!sizeRepository.findById(id).isEmpty()) {
            sizeRepository.deleteById(id);
            return "xóa thành công";
        } else {
            return "id ko tồn tại";
        }


    }

    @Override
    public SizeResponse getSize(int id) {
        Size size = sizeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id ko tồn tại"));

        return SizeResponse.builder()
                .code(size.getCode())
                .id(size.getId())
                .sizeName(size.getSizeName())
                .updateAt(size.getUpdatedAt())
                .status(size.getStatus())
                .build();
    }

    @Override
    public boolean existsBySizeName(String sizeName) {
        if (sizeRepository.existsBySizeName(sizeName)) return true;
        return false;
    }

    @Override
    public boolean existsBySizeNameAndIdNot(String sizeName, Integer id) {
        if (sizeRepository.existsBySizeNameAndIdNot(sizeName, id)) return true;
        return false;
    }
}
