package com.poly.app.domain.service.Impl;

import com.poly.app.domain.model.Material;
import com.poly.app.domain.repository.MaterialRepository;
import com.poly.app.domain.request.material.MaterialRequest;
import com.poly.app.domain.response.material.MaterialResponse;
import com.poly.app.domain.service.MaterialService;
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

public class MaterialServiceImpl implements MaterialService {

    MaterialRepository materialRepository;


    @Override
    public Material createMaterial(MaterialRequest request) {
        Material material = Material.builder()
                .materialName(request.getMaterialName())
                .status(request.getStatus())
                .build();
        return materialRepository.save(material);
    }

    @Override
    public MaterialResponse updateMaterial(MaterialRequest request, int id) {
        Material material = materialRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id ko tồn tại"));

        material.setMaterialName(request.getMaterialName());
        material.setStatus(request.getStatus());

        materialRepository.save(material);

        return MaterialResponse.builder()
                .code(material.getCode())
                .id(material.getId())
                .materialName(material.getMaterialName())
                .updateAt(material.getCreatedAt())
                .status(material.getStatus())
                .build();
    }

    @Override
    public List<MaterialResponse> getAllMaterial() {
        return materialRepository.findAll().stream()
                .map(material -> new MaterialResponse(material.getId(), material.getCode(), material.getMaterialName(), material.getUpdatedAt(), material.getStatus())).toList();
    }

    @Override
    public String deleteMaterial(int id) {
        if ( !materialRepository.findById(id).isEmpty()){
            materialRepository.deleteById(id);
            return "xóa thành công";
        }else{
            return "id ko tồn tại";
        }


    }

    @Override
    public MaterialResponse getMaterial(int id) {
        Material material = materialRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id ko tồn tại"));

        return MaterialResponse.builder()
                .code(material.getCode())
                .id(material.getId())
                .materialName(material.getMaterialName())
                .updateAt(material.getUpdatedAt())
                .status(material.getStatus())
                .build();
    }
}
