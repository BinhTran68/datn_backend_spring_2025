package com.poly.app.domain.service.Impl;

import com.poly.app.domain.model.Gender;
import com.poly.app.domain.repository.GenderRepository;
import com.poly.app.domain.request.gender.GenderRequest;
import com.poly.app.domain.response.gender.GenderResponse;
import com.poly.app.domain.service.GenderService;
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

public class GenderServiceImpl implements GenderService {

    GenderRepository genderRepository;


    @Override
    public Gender createGender(GenderRequest request) {
        Gender gender = Gender.builder()
                .genderName(request.getGenderName())
                .status(request.getStatus())
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
    public List<GenderResponse> getAllGender() {
        return genderRepository.findAll().stream()
                .map(gender -> new GenderResponse(gender.getId(), gender.getCode(), gender.getGenderName(), gender.getUpdatedAt(), gender.getStatus())).toList();
    }

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
}
