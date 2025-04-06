package com.poly.app.domain.admin.freeship_order.service;

import com.poly.app.domain.admin.freeship_order.request.FreeshipOrderRequest;
import com.poly.app.domain.admin.freeship_order.response.FreeshipOrderResponse;
import com.poly.app.domain.model.FreeshipOrder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface FreeshipOrderService {

    // Lấy danh sách tất cả mức freeship
    FreeshipOrder setMinOrderValue(Double minOrderValue);
}
