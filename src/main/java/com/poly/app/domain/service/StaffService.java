package com.poly.app.domain.service;

import com.poly.app.domain.model.Staff;
import com.poly.app.domain.request.staff.StaffRequest;
import com.poly.app.domain.response.staff.StaffResponse;

import java.util.List;

public interface StaffService {


    Staff createStaff(StaffRequest request);

    StaffResponse updateStaff(StaffRequest request, int id);

    List<StaffResponse> getAllStaff();

    String deleteStaff(int id);

    StaffResponse getStaff(int id);
}
