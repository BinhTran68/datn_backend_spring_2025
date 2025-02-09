package com.poly.app.domain.admin.staff.service.Impl;

import com.poly.app.domain.admin.address.AddressRequest;
import com.poly.app.domain.admin.customer.response.AddressResponse;
import com.poly.app.domain.admin.staff.request.StaffRequest;
import com.poly.app.domain.admin.staff.response.StaffReponse;
import com.poly.app.domain.admin.staff.service.StaffService;
import com.poly.app.domain.model.Address;
import com.poly.app.domain.model.Staff;
import com.poly.app.domain.repository.AddressRepository;
import com.poly.app.domain.repository.StaffRepository;
import com.poly.app.infrastructure.email.Email;
import com.poly.app.infrastructure.email.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StaffServiceImpl implements StaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private EmailSender emailSender;

    @Override

    public StaffReponse createStaff(StaffRequest staffRequest) {
        Staff staff = new Staff();
        staff.setFullName(staffRequest.getFullName());
        staff.setEmail(staffRequest.getEmail());
        staff.setPhoneNumber(staffRequest.getPhoneNumber());
        staff.setDateBirth(staffRequest.getDateBirth());
        staff.setPassword(staffRequest.getPassword());
        staff.setCitizenId(staffRequest.getCitizenId());
        staff.setGender(staffRequest.getGender());
        staff.setAvatar(staffRequest.getAvatar());

        staffRepository.save(staff);
        Address address = Address.builder()
                .provinceId(staffRequest.getProvinceId())
                .districtId(staffRequest.getDistrictId())
                .wardId(staffRequest.getWardId())
                .specificAddress(staffRequest.getSpecificAddress())
                .build();
        address.setStaff(staff);
        address.setIsAddressDefault(true);
        addressRepository.save(address);

        Staff staffFromDB = staffRepository.findById(staff.getId()).orElse(null);
        assert staffFromDB != null;
        Email email = new Email();
        String[] emailSend = {staffRequest.getEmail()};
        email.setToEmail(emailSend);
        email.setSubject("Tạo tài khoản thành công");
        email.setTitleEmail("");
        email.setBody("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; margin: 50px;\">\n" +
                "\n" +
                "    <div class=\"success-message\" style=\"background-color: #FFFFF; color: black; padding: 20px; border-radius: 10px; margin-top: 50px;\">\n" +
                "        <h2 style=\"color: #333;\">Tài khoản đã được tạo thành công!</h2>\n" +
                "        <p style=\"color: #555;\">Cảm ơn bạn đã đăng ký tại TheHands. Dưới đây là thông tin đăng nhập của bạn:</p>\n" +
                "        <p><strong>Email:</strong> " + staffRequest.getEmail() + "</p>\n" +
                "        <p><strong>Mật khẩu:</strong> " + staffRequest.getPassword() + "</p>\n" +
                "        <p style=\"color: #555;\">Đăng nhập ngay để trải nghiệm!</p>\n" +
                "    </div>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n");


        emailSender.sendEmail(email);
        return new StaffReponse(staffFromDB);
    }


    @Override
    public StaffReponse updateStaff(Integer id, StaffRequest staffRequest) {
        Optional<Staff> optionalStaff = staffRepository.findById(id);
        if (optionalStaff.isPresent()) {
            Staff staff = optionalStaff.get();
            staff.setFullName(staffRequest.getFullName());
            staff.setEmail(staffRequest.getEmail());
            staff.setPhoneNumber(staffRequest.getPhoneNumber());
            staff.setDateBirth(staffRequest.getDateBirth());
            staff.setPassword(staffRequest.getPassword());
            staff.setCitizenId(staffRequest.getCitizenId());
            staff.setGender(staffRequest.getGender());
            staff.setAvatar(staffRequest.getAvatar());
            staff.getAddresses().clear();
            staff.setStatus(staffRequest.getStatus());
            Staff updatedStaff = staffRepository.save(staff);
            return new StaffReponse(updatedStaff);
        } else {
            throw new RuntimeException("Customer not found");
        }
    }

    @Override
    public void deleteStaff(Integer id) {
        staffRepository.deleteById(id);
    }

    @Override
    public StaffReponse getStaffById(Integer id) {
        Optional<Staff> optionalStaff = staffRepository.findById(id);
        return optionalStaff.map(StaffReponse::new).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public List<StaffReponse> getAllStaff() {
        List<Staff> staff = staffRepository.findAll();
        Collections.reverse(staff);
        return staff.stream().map(StaffReponse::new).collect(Collectors.toList());
    }

    @Override
    public StaffReponse getStaffByEmail(String email) {
        Optional<Staff> optionalStaff = Optional.ofNullable(staffRepository.findByEmail(email));
        return optionalStaff.map(StaffReponse::new).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    @Override
    public AddressResponse updateAddress(Integer addressId, AddressRequest addressRequest) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            address.setProvinceId(addressRequest.getProvinceId());
            address.setDistrictId(addressRequest.getDistrictId());
            address.setWardId(addressRequest.getWardId());
            address.setSpecificAddress(addressRequest.getSpecificAddress());
            address.setIsAddressDefault(addressRequest.getIsAddressDefault());
            Address updatedAddress = addressRepository.save(address);
            return new AddressResponse(updatedAddress);
        } else {
            throw new RuntimeException("Address not found");
        }
    }

    @Override
    public void deleteAddress(Integer addressId) {
        addressRepository.deleteById(addressId);
    }

    @Override
    public AddressResponse addAddress(Integer staffId, AddressRequest addressRequest) {
        Optional<Staff> optionalStaff = staffRepository.findById(staffId);
        if (optionalStaff.isPresent()) {
            Staff staff = optionalStaff.get();
            Address address = Address.builder()
                    .provinceId(addressRequest.getProvinceId())
                    .districtId(addressRequest.getDistrictId())
                    .wardId(addressRequest.getWardId())
                    .specificAddress(addressRequest.getSpecificAddress())
                    .isAddressDefault(addressRequest.getIsAddressDefault())
                    .build();
            address.setStaff(staff);
            Address savedAddress = addressRepository.save(address);
            return new AddressResponse(savedAddress);
        } else {
            throw new RuntimeException("Customer not found");
        }
    }

    @Override
    public void setDefaultAddress(Integer addressId) {
        Optional<Address> optionalAddress = addressRepository.findById(addressId);
        if (optionalAddress.isPresent()) {
            Address address = optionalAddress.get();
            List<Address> addresses = addressRepository.findByCustomerId(address.getCustomer().getId());
            addresses.forEach(addr -> {
                if (addr.getIsAddressDefault()) {
                    addr.setIsAddressDefault(false);
                    addressRepository.save(addr);
                }
            });
            address.setIsAddressDefault(true);
            addressRepository.save(address);
        } else {
            throw new RuntimeException("Address not found");
        }
    }

    @Override
    public List<StaffReponse> filterStaff(String searchText, String status, String dobFrom, String dobTo, Integer ageFrom, Integer ageTo) {
        List<Staff> staffList = staffRepository.findAll();
        staffList = staffList.stream()
                .filter(staff -> searchText == null || searchText.isEmpty() ||
                        staff.getFullName().toLowerCase().contains(searchText.toLowerCase()) ||
                        staff.getPhoneNumber().contains(searchText))
                .filter(staff -> status == null || status.equals("Tất cả") ||
                        (status.equals("Kích hoạt") && staff.getStatus() == 0) ||
                        (status.equals("Khóa") && staff.getStatus() == 1))
                .filter(staff -> {
                    if (dobFrom != null && dobTo != null) {
                        LocalDateTime dobFromDateTime = LocalDateTime.parse(dobFrom);
                        LocalDateTime dobToDateTime = LocalDateTime.parse(dobTo);
                        return !staff.getDateBirth().isBefore(dobFromDateTime) && !staff.getDateBirth().isAfter(dobToDateTime);
                    }
                    return true;
                })
                .filter(staff -> {
                    if (ageFrom != null && ageTo != null) {
                        int age = LocalDateTime.now().getYear() - staff.getDateBirth().getYear();
                        return age >= ageFrom && age <= ageTo;
                    }
                    return true;
                })
                .collect(Collectors.toList());

        return staffList.stream().map(StaffReponse::new).collect(Collectors.toList());
    }
}