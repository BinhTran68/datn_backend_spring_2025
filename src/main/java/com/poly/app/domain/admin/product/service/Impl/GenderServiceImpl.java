package com.poly.app.domain.admin.product.service.Impl;

import com.poly.app.domain.admin.product.response.gender.GenderResponse;
import com.poly.app.domain.admin.product.response.gender.GenderResponseSelect;
import com.poly.app.domain.model.Gender;
import com.poly.app.domain.repository.GenderRepository;
import com.poly.app.domain.admin.product.request.gender.GenderRequest;
import com.poly.app.domain.admin.product.response.gender.GenderResponse;
import com.poly.app.domain.admin.product.service.GenderService;
import com.poly.app.infrastructure.constant.Status;
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

public class GenderServiceImpl implements GenderService {

    GenderRepository genderRepository;


    @Override
    public Gender createGender(GenderRequest request) {
        Gender gender = Gender.builder()
                .genderName(request.getGenderName())
                .status(Status.HOAT_DONG)
                .build();
        return genderRepository.save(gender);
    }

    @Override
    public GenderResponse updateGender(GenderRequest request, int id) {

        Gender gender = genderRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id ko tồn tại"));

        gender.setGenderName(request.getGenderName());
        gender.setStatus(request.getStatus());

        genderRepository.save(gender);

        return GenderResponse.builder()
                .code(gender.getCode())
                .id(gender.getId())
                .genderName(gender.getGenderName())
                .updateAt(gender.getCreatedAt())
                .status(gender.getStatus())
                .build();
    }

    @Override
    public Page<GenderResponse> getAllGender(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GenderResponse> response= genderRepository.getAll(pageable);
        return response ;    }

    @Override
    public String deleteGender(int id) {
        if ( !genderRepository.findById(id).isEmpty()){
            genderRepository.deleteById(id);
            return "xóa thành công";
        }else{
            return "id ko tồn tại";
        }


    }

    @Override
    public GenderResponse getGender(int id) {
        Gender gender = genderRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id ko tồn tại"));

        return GenderResponse.builder()
                .code(gender.getCode())
                .id(gender.getId())
                .genderName(gender.getGenderName())
                .updateAt(gender.getUpdatedAt())
                .status(gender.getStatus())
                .build();
    }

    @Override
    public Page<GenderResponse> fillbyName(int page, int size, String name) {
        Pageable pageable = PageRequest.of(page, size);


        Page<GenderResponse> gender = genderRepository.fillbyname(String.format("%%%s%%", name), pageable);
        log.info(name);

        return gender;
    }


    @Override
    public boolean existsByGenderName(String brandName) {
        return false;
    }

    @Override
    public boolean existsByGenderNameAndIdNot(String brandName, Integer id) {
        return false;
    }

    @Override
    public List<GenderResponseSelect> getAll() {
        return genderRepository.dataSelect();
    }
}
