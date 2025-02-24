package com.poly.app.domain.admin.voucher.controller;


import com.cloudinary.Api;
import com.poly.app.domain.admin.voucher.request.voucher.VoucherRequest;
import com.poly.app.domain.admin.voucher.response.ApiResponse;
import com.poly.app.domain.admin.voucher.response.VoucherResponse;
import com.poly.app.domain.admin.voucher.service.VoucherService;
import com.poly.app.domain.model.StatusVoucher;
import com.poly.app.domain.model.Voucher;
import com.poly.app.domain.repository.VoucherRepository;
import com.poly.app.domain.admin.voucher.response.VoucherReponse;
import com.poly.app.infrastructure.constant.Status;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/voucher")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class VoucherController {
    VoucherRepository voucherRepository;
    VoucherService voucherService;


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
                                                    StatusVoucher status
    ) {
        voucherService.switchStatus(id, status);

        return ApiResponse.<String>builder()
                .message("")
                .data(voucherService.switchStatus(id, status))
                .build();
    }
//    @GetMapping("/search")
//    public ApiResponse<Page<VoucherReponse>> searchVouchers(@RequestParam String keyword,
//                                                            @RequestParam(value = "page", defaultValue = "0") int page,
//                                                            @RequestParam(value = "size", defaultValue = "10") int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        Page<VoucherReponse> searchResults = voucherService.searchVouchers(keyword, pageable);
//        return ApiResponse.<Page<VoucherReponse>>builder()
//                .message("Search results")
//                .data(searchResults)
//                .build();
//    }
//}

}
