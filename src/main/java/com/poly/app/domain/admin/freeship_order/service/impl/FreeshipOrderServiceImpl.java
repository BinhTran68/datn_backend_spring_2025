package com.poly.app.domain.admin.freeship_order.service.impl;
import com.poly.app.domain.admin.freeship_order.request.FreeshipOrderRequest;
import com.poly.app.domain.admin.freeship_order.response.FreeshipOrderResponse;
import com.poly.app.domain.admin.freeship_order.service.FreeshipOrderService;
import com.poly.app.domain.model.FreeshipOrder;
import com.poly.app.domain.repository.FreeshipOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FreeshipOrderServiceImpl implements FreeshipOrderService {

    private final FreeshipOrderRepository freeshipOrderRepository;

    @Override
    public FreeshipOrder setMinOrderValue(Double minOrderValue) {
        if (minOrderValue == null || minOrderValue < 0) {
            throw new IllegalArgumentException("Giá trị đơn hàng tối thiểu phải lớn hơn hoặc bằng 0");
        }
        // Giả sử chỉ có một bản ghi cấu hình freeship duy nhất
        FreeshipOrder freeshipOrder = freeshipOrderRepository.findTopByOrderByIdDesc();
        freeshipOrder.setMinOrderValue(minOrderValue);
        return freeshipOrderRepository.save(freeshipOrder);
    }


}
