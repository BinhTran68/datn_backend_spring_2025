package com.poly.app.domain.admin.voucher.controller;


import com.poly.app.domain.admin.voucher.request.voucher.VoucherRequest;
import com.poly.app.domain.admin.voucher.response.ApiResponse;
import com.poly.app.domain.admin.voucher.service.VoucherService;
import com.poly.app.domain.common.PageReponse;
import com.poly.app.domain.model.StatusEnum;
import com.poly.app.domain.model.Voucher;
import com.poly.app.domain.repository.VoucherRepository;
import com.poly.app.domain.admin.voucher.response.VoucherReponse;
import com.poly.app.infrastructure.constant.BillStatus;
import com.poly.app.infrastructure.constant.DiscountType;
import com.poly.app.infrastructure.constant.TypeBill;
import com.poly.app.infrastructure.constant.VoucherType;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/voucher")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VoucherController {
    VoucherRepository voucherRepository;
    VoucherService voucherService;


    @GetMapping("/index")
    public PageReponse index(@RequestParam(defaultValue = "10") Integer size,
                             @RequestParam(defaultValue = "0") Integer page,
                             @RequestParam(required = false) StatusEnum statusVoucher,
                             @RequestParam(required = false) String search,
                             @RequestParam(required = false) String startDate,
                             @RequestParam(required = false) String endDate,
                             @RequestParam(required = false) VoucherType voucherType,
                             @RequestParam(required = false)DiscountType discountType
    )
    {
        log.info("startDate {}, endDate {}" , startDate,  endDate);
        return new PageReponse(voucherService.getPageVoucher(
                size,
                page,
                statusVoucher,
                search, startDate, endDate, voucherType, discountType

        ));
    }


    @GetMapping("/with-customer")
    public ApiResponse<List<VoucherReponse>> getAllVouchersWithCustomer(@RequestParam(required = false) Integer customerId) {
        return ApiResponse.<List<VoucherReponse>>builder()
                .message("list voucher")
                .data(voucherService.getAllVouchersWithCustomer(customerId))
                .build();
    }

    @GetMapping("/hien")
    public ApiResponse<List<VoucherReponse>> getAllVoucher() {
        return ApiResponse.<List<VoucherReponse>>builder()
                .message("list voucher")
                .data(voucherService.getAllVoucher())
                .build();
    }


    @PostMapping("/add")
    public ApiResponse<Voucher> create(@RequestBody VoucherRequest request) {
        return ApiResponse.<Voucher>builder()
                .message("create voucher")
                .data(voucherService.createVoucher(request))
                .build();
    }

    @PutMapping("/update/{id}")
    public ApiResponse<VoucherReponse> update(@RequestBody VoucherRequest request, @PathVariable int id) {
        return ApiResponse.<VoucherReponse>builder()
                .message("update voucher")
                .data(voucherService.updateVoucher(request, id))
                .build();
    }
//    @PutMapping("/update_tt/{id}")
//    public ApiResponse<VoucherReponse> updateTt(@RequestBody VoucherRequest request, @PathVariable int id) {
//        return ApiResponse.<VoucherReponse>builder()
//                .message("Cập nhật trạng thái voucher")
//                .data(voucherService.updateVoucher(request, id))
//                .build();
//    }


    @DeleteMapping("/delete/{id}")
    public ApiResponse<String> delete(@PathVariable int id) {
        return ApiResponse.<String>builder()
                .message("delete id voucher")
                .data(voucherService.deleteVoucher(id))
                .build();
    }

    @GetMapping("/detail/{id}")
    public ApiResponse<VoucherReponse> getVoucherDetail(@PathVariable int id) {
        return ApiResponse.<VoucherReponse>builder()
                .message("detail by id")
                .data(voucherService.getVoucherDetail(id))
                .build();
    }


    @GetMapping("/page")
    public ApiResponse<Page<VoucherReponse>> phanTrang(@RequestParam(value = "page") Integer page,
                                                       @RequestParam(value = "size") Integer size
    ) {


        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        Page<VoucherReponse> list = voucherService.getAllVoucher(pageable);
        return ApiResponse.<Page<VoucherReponse>>builder()
                .message("")
                .data(list)
                .build();
    }

    @GetMapping("/switchStatus")
    public ApiResponse<String> switchStatus(@RequestParam(value = "id") Integer id,
                                            @RequestParam(value = "status")
                                                    StatusEnum status
    ) {
        voucherService.switchStatus(id, status);

        return ApiResponse.<String>builder()
                .message("")
                .data(voucherService.switchStatus(id, status))
                .build();
    }


//     🔍 Tìm kiếm voucher theo tên
    @GetMapping("/search/byName")
    public ApiResponse<List<VoucherReponse>> searchVoucherByName(@RequestParam String voucherName) {
        return ApiResponse.<List<VoucherReponse>>builder()
                .message("Search results by name")
                .data(voucherService.searchVoucherByName(voucherName))
                .build();
    }

    // 🔍 Tìm kiếm voucher theo trạng thái
    @GetMapping("/search/status")
    public ApiResponse<List<VoucherReponse>> searchByStatus(@RequestParam StatusEnum statusVoucher) {
        return ApiResponse.<List<VoucherReponse>>builder()
                .message("Search results by status")
                .data(voucherService.searchVoucherByStatus(statusVoucher))
                .build();
    }



    // 🔍 Tìm kiếm voucher theo số lượng
    @GetMapping("/search/byQuantity")
    public ApiResponse<List<VoucherReponse>> searchVoucherByQuantity(@RequestParam Integer quantity) {
        return ApiResponse.<List<VoucherReponse>>builder()
                .message("Search results by quantity")
                .data(voucherService.searchVoucherByQuantity(quantity))
                .build();
    }

    // 🔍 Tìm kiếm voucher theo loại
    @GetMapping("/search/byType")
    public ApiResponse<List<VoucherReponse>> searchVoucherByType(@RequestParam VoucherType voucherType) {
        return ApiResponse.<List<VoucherReponse>>builder()
                .message("Search results by voucher type")
                .data(voucherService.searchVoucherByType(voucherType))
                .build();
    }

    // 🔍 Tìm kiếm voucher theo khoảng giá trị giảm tối đa
    @GetMapping("/search/byDiscountMaxRange")
    public ApiResponse<List<VoucherReponse>> searchVoucherByDiscountMaxRange(
            @RequestParam Double minDiscount,
            @RequestParam Double maxDiscount) {
        return ApiResponse.<List<VoucherReponse>>builder()
                .message("Search results by discount max value range")
                .data(voucherService.searchVoucherByDiscountMaxRange(minDiscount, maxDiscount))
                .build();
    }

    // 🔍 Tìm kiếm voucher theo khoảng giá trị hóa đơn tối thiểu
    @GetMapping("/search/byBillMinRange")
    public ApiResponse<List<VoucherReponse>> searchVoucherByBillMinRange(
            @RequestParam Double minBill,
            @RequestParam Double maxBill) {
        return ApiResponse.<List<VoucherReponse>>builder()
                .message("Search results by bill min value range")
                .data(voucherService.searchVoucherByBillMinRange(minBill, maxBill))
                .build();
    }


    // 🔍 Tìm kiếm voucher theo khoảng thời gian bắt đầu và kết thúc
    @GetMapping("/search/byDateRange")
    public ApiResponse<List<VoucherReponse>> searchVoucherByDateRange(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return ApiResponse.<List<VoucherReponse>>builder()
                .message("Search results by start date range")
                .data(voucherService.searchVoucherByStartDateRange(startDate, endDate))
                .build();
    }
}
