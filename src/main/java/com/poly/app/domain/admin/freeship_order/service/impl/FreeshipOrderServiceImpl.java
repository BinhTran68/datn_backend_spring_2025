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

    // Lấy danh sách tất cả mức freeship
    @Override
    public List<FreeshipOrderResponse> getAllFreeshipOrders() {
        return freeshipOrderRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    // Lấy mức freeship theo ID
    @Override
    public Optional<FreeshipOrderResponse> getFreeshipOrderById(Integer id) {
        return freeshipOrderRepository.findById(id)
                .map(this::toResponseDto);
    }

    // Thêm hoặc cập nhật mức freeship
    @Override
    @Transactional
    public FreeshipOrderResponse saveOrUpdateFreeshipOrder(FreeshipOrderRequest request) {
        FreeshipOrder freeshipOrder = FreeshipOrder.builder()
                .minOrderValue(request.getMinOrderValue())
                .shippingDiscount(request.getShippingDiscount())
                .build();

        FreeshipOrder savedFreeship = freeshipOrderRepository.save(freeshipOrder);
        return toResponseDto(savedFreeship);
    }

    // Xóa mức freeship theo ID
    @Override
    @Transactional
    public void deleteFreeshipOrder(Integer id) {
        if (!freeshipOrderRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy mức freeship với ID: " + id);
        }
        freeshipOrderRepository.deleteById(id);
    }

    // Lấy mức freeship phù hợp cho đơn hàng
    @Override
    public Optional<FreeshipOrderResponse> getApplicableFreeship(Double totalPrice) {
        return freeshipOrderRepository.findTopByMinOrderValueLessThanEqualOrderByMinOrderValueDesc(totalPrice)
                .map(this::toResponseDto);
    }

    // Chuyển đổi Entity → DTO Response
    private FreeshipOrderResponse toResponseDto(FreeshipOrder entity) {
        return FreeshipOrderResponse.builder()
                .id(entity.getId())
                .minOrderValue(entity.getMinOrderValue())
                .shippingDiscount(entity.getShippingDiscount())
                .build();
    }
}
