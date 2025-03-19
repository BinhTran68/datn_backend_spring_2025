package com.poly.app.domain.admin.promotion.controller;

import com.poly.app.domain.admin.promotion.request.PromotionRequest;
import com.poly.app.domain.admin.promotion.response.ApiResponse;
import com.poly.app.domain.admin.promotion.response.PromotionResponse;
import com.poly.app.domain.admin.promotion.service.PromotionService;
import com.poly.app.domain.model.Promotion;
import com.poly.app.domain.model.StatusEnum;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/promotion")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PromotionController {
//    PromotionRepository promotionRepository;
    PromotionService promotionService;

    @GetMapping("/hien")
    public ApiResponse<List<PromotionResponse>> getAllPromotion() {
        return ApiResponse.<List<PromotionResponse>>builder()
                .message("list promotion")
                .data(promotionService.getAllPromotion())
                .build();
    }

    @PostMapping("/add")
    public ApiResponse<Promotion> create(@RequestBody PromotionRequest request) {
        return ApiResponse.<Promotion>builder()
                .message("create promotion")
                .data(promotionService.createPromotion(request))
                .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<PromotionResponse> update(@RequestBody PromotionRequest request, @PathVariable int id) {
        return ApiResponse.<PromotionResponse>builder()
                .message("update promotion")
                .data(promotionService.updatePromotion(request, id))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> delete(@PathVariable int id) {
        return ApiResponse.<String>builder()
                .message("delete id promotion")
                .data(promotionService.deletePromotion(id))
                .build();
    }

    @GetMapping("/detail/{id}")
    public ApiResponse<PromotionResponse> getPromotionDetail(@PathVariable int id) {
        return ApiResponse.<PromotionResponse>builder()
                .message("detail by id")
                .data(promotionService.getPromotionDetail(id))
                .build();
    }

    @GetMapping("/page")
    public ApiResponse<Page<PromotionResponse>> phanTrang(@RequestParam(value = "page") Integer page,
                                                                       @RequestParam(value = "size") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        Page<PromotionResponse> list = promotionService.getAllPromotion(pageable);
        return ApiResponse.<Page<PromotionResponse>>builder()
                .message("")
                .data(list)
                .build();
    }

    @GetMapping("/switchStatus")
    public com.poly.app.domain.admin.voucher.response.ApiResponse<String> switchStatus(@RequestParam(value = "id") Integer id,
                                                                                       @RequestParam(value = "status")
                                                                                               StatusEnum status
    ) {
        promotionService.switchStatus(id, status);

        return com.poly.app.domain.admin.voucher.response.ApiResponse.<String>builder()
                .message("")
                .data(promotionService.switchStatus(id, status))
                .build();
    }


    // 🔍 Tìm kiếm theo tên chương trình khuyến mãi
    @GetMapping("/search/byName")
    public ApiResponse<List<PromotionResponse>> searchPromotionByName(@RequestParam String promotionName) {
        return ApiResponse.<List<PromotionResponse>>builder()
                .message("Search results by promotion name")
                .data(promotionService.searchPromotionByName(promotionName))
                .build();
    }

    // 🔍 Tìm kiếm theo khoảng giá trị giảm giá
    @GetMapping("/search/byDiscountRange")
    public ApiResponse<List<PromotionResponse>> searchPromotionByDiscountRange(
            @RequestParam Double minDiscount,
            @RequestParam Double maxDiscount) {
        return ApiResponse.<List<PromotionResponse>>builder()
                .message("Search results by discount value range")
                .data(promotionService.searchPromotionByDiscountRange(minDiscount, maxDiscount))
                .build();
    }

    // 🔍 Tìm kiếm theo trạng thái khuyến mãi
    @GetMapping("/search/byStatus")
    public ApiResponse<List<PromotionResponse>> searchPromotionByStatus(@RequestParam StatusEnum statusPromotion) {
        return ApiResponse.<List<PromotionResponse>>builder()
                .message("Search results by status")
                .data(promotionService.searchPromotionByStatus(statusPromotion))
                .build();
    }

    // 🔍 Tìm kiếm theo khoảng thời gian bắt đầu và kết thúc
    @GetMapping("/search/byEndDateRange")
    public ApiResponse<List<PromotionResponse>> searchPromotionByEndDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return ApiResponse.<List<PromotionResponse>>builder()
                .message("Search results by end date range")
                .data(promotionService.searchPromotionByEndDateRange(startDate, endDate))
                .build();
    }
}
