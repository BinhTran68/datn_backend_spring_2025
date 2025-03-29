package com.poly.app.domain.admin.freeship_order.service;

import com.poly.app.domain.admin.freeship_order.request.FreeshipOrderRequest;
import com.poly.app.domain.admin.freeship_order.response.FreeshipOrderResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface FreeshipOrderService {

    // Lấy danh sách tất cả mức freeship
    List<FreeshipOrderResponse> getAllFreeshipOrders();

    // Lấy mức freeship theo ID
    Optional<FreeshipOrderResponse> getFreeshipOrderById(Integer id);

    // Thêm mới hoặc cập nhật mức freeship
    FreeshipOrderResponse saveOrUpdateFreeshipOrder(FreeshipOrderRequest request);

    // Xóa mức freeship theo ID
    void deleteFreeshipOrder(Integer id);

    // Lấy mức freeship phù hợp cho đơn hàng
    Optional<FreeshipOrderResponse> getApplicableFreeship(Double totalPrice);
}
