package com.poly.app.domain.admin.bill.controller;


import com.poly.app.domain.admin.bill.request.BillProductDetailRequest;
import com.poly.app.domain.admin.bill.request.CreateBillRequest;
import com.poly.app.domain.admin.bill.request.RestoreQuantityRequest;
import com.poly.app.domain.admin.bill.request.UpdateQuantityProductRequest;
import com.poly.app.domain.admin.bill.request.UpdateQuantityVoucherRequest;
import com.poly.app.domain.admin.bill.request.UpdateStatusBillRequest;
import com.poly.app.domain.admin.bill.response.UpdateBillRequest;
import com.poly.app.domain.admin.bill.service.BillService;
import com.poly.app.domain.admin.voucher.request.voucher.VoucherRequest;
import com.poly.app.domain.admin.voucher.response.VoucherReponse;
import com.poly.app.domain.common.ApiResponse;
import com.poly.app.domain.common.PageReponse;
import com.poly.app.domain.model.Bill;
import com.poly.app.domain.model.ProductDetail;
import com.poly.app.domain.repository.ProductDetailRepository;
import com.poly.app.infrastructure.constant.BillStatus;
import com.poly.app.infrastructure.constant.TypeBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/bill")
public class BillController {

    // Khi tạo 1 hóa đơn mặc định sẽ là chưa xác nhận

    @Autowired
    BillService billService;
    @Autowired
    private ProductDetailRepository productDetailRepository;

    @GetMapping("/index")
    public PageReponse index(@RequestParam(defaultValue = "10") Integer size,
                             @RequestParam(defaultValue = "0") Integer page,
                             @RequestParam(required = false) TypeBill typeBill,
                             @RequestParam(required = false) String search,
                             @RequestParam(required = false) String startDate,
                             @RequestParam(required = false) String endDate,
                             @RequestParam(required = false) BillStatus statusBill) {
        return new PageReponse(billService.getPageBill(size, page, statusBill, typeBill, search, startDate, endDate));
    }


    @GetMapping("/detail/{billCode}")
    public ApiResponse<?> findBillByBillCode(@PathVariable String billCode) {
        return ApiResponse.builder().data(billService.getBillResponseByBillCode(billCode)).build();
    }


    @PutMapping("/{code}/update")
    public ApiResponse<?> updateBillStatus(@PathVariable String code, @RequestBody UpdateStatusBillRequest request) {
        return ApiResponse.builder().data(billService.updateStatusBill(code, request)).build();
    }


    @PutMapping("/{code}/update-info-bill")
    public ApiResponse<?> updateInfoBillStatus(@PathVariable String code, @RequestBody UpdateBillRequest request) {
        return ApiResponse.builder().data(billService.updateBillInfo(code, request)).build();
    }

    private static final String PUBLIC_DIR = "src/main/resources/static/public/";

    @GetMapping("/print-bill/{billCode}")
    public ResponseEntity<byte[]> printBillById(@PathVariable("billCode") String billCode) throws IOException {
        File pdfFile = billService.printBillById(billCode);
        if (pdfFile != null) {
            byte[] pdfBytes = Files.readAllBytes(pdfFile.toPath());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.inline().filename("HoaDon"+billCode+".pdf").build()); // Đặt tên file
            pdfFile.delete();
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(pdfBytes.length)
                    .body(pdfBytes);
        } else {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/create")
    ResponseEntity<?> createBill(@RequestBody CreateBillRequest request) {
        return ResponseEntity.ok(billService.createBill(request));
    }

    @PostMapping("/change-product-quantity")
    ResponseEntity<?> updateProductQuantity(@RequestBody List<BillProductDetailRequest> request) {
        billService.updateProductQuantity(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restore-quantity")
    public ResponseEntity<?> restoreQuantity(@RequestBody List<RestoreQuantityRequest> requests) {
        try {
            for (RestoreQuantityRequest request : requests) {
                ProductDetail product = productDetailRepository.findById(request.getId())
                        .orElseThrow(() -> new RuntimeException("Product not found"));
                // Cộng lại số lượng
                product.setQuantity(product.getQuantity() + request.getQuantity());
                productDetailRepository.save(product);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error restoring quantity: " + e.getMessage());
        }
    }

    @GetMapping("/vouchers")
    public ResponseEntity<?> getAllVoucher() {
        return ResponseEntity.ok(billService.getAllVoucherResponse());
    }


    @GetMapping("/vouchers/{customerId}")
    public ResponseEntity<?> getAllVoucherByCustomerId(@PathVariable String customerId) {
        return ResponseEntity.ok(billService.getAllVoucherResponse());
    }

    @GetMapping("/quantity-vouchers")
    public ResponseEntity<?> updateQuantityVoucher(@RequestBody UpdateQuantityVoucherRequest request) {
        return ResponseEntity.ok(billService.updateQuantityVoucher(request));
    }


    @GetMapping("/count-by-status")
    public ResponseEntity<List<Map<String, Object>>> getOrderCountByStatus() {
        return ResponseEntity.ok(billService.getBillCountByStatus());
    }

}
