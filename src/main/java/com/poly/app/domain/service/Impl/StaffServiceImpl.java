package com.poly.app.domain.service.Impl;

import com.poly.app.domain.model.Staff;
import com.poly.app.domain.repository.StaffRepository;
import com.poly.app.domain.request.staff.StaffRequest;
import com.poly.app.domain.response.staff.StaffResponse;
import com.poly.app.domain.service.StaffService;
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
public class StaffServiceImpl implements StaffService {
    StaffRepository staffRepository;


   @Override
    public Staff createStaff(StaffRequest request) {
        Staff staff = Staff.builder()
                .fullName(request.getFullName())
                .dateBirth(request.getDateBirth())
                .CitizenId(request.getCitizenId())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .gender(request.getGender())
                .password(request.getPassword())
                .avatar(request.getAvatar())
                .build();
        return staffRepository.save(staff);
    }

    @Override
    public StaffResponse updateStaff(StaffRequest request, int id) {
        Staff staff = staffRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id khong ton tai"));

        staff.setFullName(request.getFullName());
        staff.setDateBirth(request.getDateBirth());
        staff.setCitizenId(request.getCitizenId());
        staff.setPhoneNumber(request.getPhoneNumber());
        staff.setEmail(request.getEmail());
        staff.setGender(request.getGender());
        staff.setPassword(request.getPassword());
        staff.setAvatar(request.getAvatar());

        staffRepository.save(staff);

        return StaffResponse.builder()
                .fullName(request.getFullName())
                .dateBirth(request.getDateBirth())
                .CitizenId(request.getCitizenId())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .gender(request.getGender())
                .password(request.getPassword())
                .avatar(request.getAvatar())
                .build();
    }

    @Override
    public List<StaffResponse> getAllStaff() {
        return staffRepository.findAll().stream()
                .map(staff -> new StaffResponse(staff.getCode(), staff.getFullName(),
                        staff.getDateBirth(), staff.getCitizenId(), staff.getPhoneNumber(), staff.getEmail(),
                        staff.getGender(), staff.getPassword(), staff.getAvatar())).toList();
    }


    @Override
    public String deleteStaff(int id) {
        if (!staffRepository.findById(id).isEmpty()) {
            staffRepository.deleteById(id);
            return "xoa thanh cong";
        } else {
            return "id khong ton tai";
        }
        
    }

    @Override
    public StaffResponse getStaff(int id) {
        Staff staff = staffRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id khong ton tai!"));

        return StaffResponse.builder()
                .code(staff.getCode())
                .fullName(staff.getFullName())
                .dateBirth(staff.getDateBirth())
                .CitizenId(staff.getCitizenId())
                .phoneNumber(staff.getPhoneNumber())
                .email(staff.getEmail())
                .gender(staff.getGender())
                .password(staff.getPassword())
                .avatar(staff.getAvatar())
                .build();
    }

}
