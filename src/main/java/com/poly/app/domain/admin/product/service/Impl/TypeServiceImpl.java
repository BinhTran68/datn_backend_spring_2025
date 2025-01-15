package com.poly.app.domain.admin.product.service.Impl;

import com.poly.app.domain.model.Type;
import com.poly.app.domain.repository.TypeRepository;
import com.poly.app.domain.admin.product.request.type.TypeRequest;
import com.poly.app.domain.admin.product.response.type.TypeResponse;
import com.poly.app.domain.admin.product.service.TypeService;
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

public class TypeServiceImpl implements TypeService {

    TypeRepository typeRepository;


    @Override
    public Type createType(TypeRequest request) {
        Type type = Type.builder()
                .typeName(request.getTypeName())
                .status(request.getStatus())
                .build();
        return typeRepository.save(type);
    }

    @Override
    public TypeResponse updateType(TypeRequest request, int id) {
        Type type = typeRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id ko tồn tại"));

        type.setTypeName(request.getTypeName());
        type.setStatus(request.getStatus());

        typeRepository.save(type);

        return TypeResponse.builder()
                .code(type.getCode())
                .id(type.getId())
                .typeName(type.getTypeName())
                .updateAt(type.getCreatedAt())
                .status(type.getStatus())
                .build();
    }

    @Override
    public List<TypeResponse> getAllType() {
        return typeRepository.findAll().stream()
                .map(type -> new TypeResponse(type.getId(), type.getCode(), type.getTypeName(), type.getUpdatedAt(), type.getStatus())).toList();
    }

    @Override
    public String deleteType(int id) {
        if ( !typeRepository.findById(id).isEmpty()){
            typeRepository.deleteById(id);
            return "xóa thành công";
        }else{
            return "id ko tồn tại";
        }


    }

    @Override
    public TypeResponse getType(int id) {
        Type type = typeRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id ko tồn tại"));

        return TypeResponse.builder()
                .code(type.getCode())
                .id(type.getId())
                .typeName(type.getTypeName())
                .updateAt(type.getUpdatedAt())
                .status(type.getStatus())
                .build();
    }
}
