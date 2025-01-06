package com.poly.app.domain.admin.product.service.Impl;

import com.poly.app.domain.model.Size;
import com.poly.app.domain.repository.SizeRepository;
import com.poly.app.domain.admin.product.request.size.SizeRequest;
import com.poly.app.domain.admin.product.response.size.SizeResponse;
import com.poly.app.domain.admin.product.service.SizeService;
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

public class SizeServiceImpl implements SizeService {

    SizeRepository sizeRepository;


    @Override
    public Size createSize(SizeRequest request) {
        Size size = Size.builder()
                .sizeName(request.getSizeName())
                .status(request.getStatus())
                .build();
        return sizeRepository.save(size);
    }

    @Override
    public SizeResponse updateSize(SizeRequest request, int id) {
        Size size = sizeRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id ko tồn tại"));

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
    public List<SizeResponse> getAllSize() {
        return sizeRepository.findAll().stream()
                .map(size -> new SizeResponse(size.getId(), size.getCode(), size.getSizeName(), size.getUpdatedAt(), size.getStatus())).toList();
    }

    @Override
    public String deleteSize(int id) {
        if ( !sizeRepository.findById(id).isEmpty()){
            sizeRepository.deleteById(id);
            return "xóa thành công";
        }else{
            return "id ko tồn tại";
        }


    }

    @Override
    public SizeResponse getSize(int id) {
        Size size = sizeRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id ko tồn tại"));

        return SizeResponse.builder()
                .code(size.getCode())
                .id(size.getId())
                .sizeName(size.getSizeName())
                .updateAt(size.getUpdatedAt())
                .status(size.getStatus())
                .build();
    }
}
