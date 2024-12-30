package com.poly.app.domain.service.Impl;

import com.poly.app.domain.model.Sole;
import com.poly.app.domain.repository.SoleRepository;
import com.poly.app.domain.request.sole.SoleRequest;
import com.poly.app.domain.response.sole.SoleResponse;
import com.poly.app.domain.service.SoleService;
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

public class SoleServiceImpl implements SoleService {

    SoleRepository soleRepository;


    @Override
    public Sole createSole(SoleRequest request) {
        Sole sole = Sole.builder()
                .soleName(request.getSoleName())
                .status(request.getStatus())
                .build();
        return soleRepository.save(sole);
    }

    @Override
    public SoleResponse updateSole(SoleRequest request, int id) {
        Sole sole = soleRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id ko tồn tại"));

        sole.setSoleName(request.getSoleName());
        sole.setStatus(request.getStatus());

        soleRepository.save(sole);

        return SoleResponse.builder()
                .code(sole.getCode())
                .id(sole.getId())
                .soleName(sole.getSoleName())
                .updateAt(sole.getCreatedAt())
                .status(sole.getStatus())
                .build();
    }

    @Override
    public List<SoleResponse> getAllSole() {
        return soleRepository.findAll().stream()
                .map(sole -> new SoleResponse(sole.getId(), sole.getCode(), sole.getSoleName(), sole.getUpdatedAt(), sole.getStatus())).toList();
    }

    @Override
    public String deleteSole(int id) {
        if ( !soleRepository.findById(id).isEmpty()){
            soleRepository.deleteById(id);
            return "xóa thành công";
        }else{
            return "id ko tồn tại";
        }


    }

    @Override
    public SoleResponse getSole(int id) {
        Sole sole = soleRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id ko tồn tại"));

        return SoleResponse.builder()
                .code(sole.getCode())
                .id(sole.getId())
                .soleName(sole.getSoleName())
                .updateAt(sole.getUpdatedAt())
                .status(sole.getStatus())
                .build();
    }
}
