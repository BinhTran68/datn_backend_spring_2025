package com.poly.app.domain.admin.voucher.service;

import com.poly.app.domain.admin.bill.response.BillResponse;
import com.poly.app.domain.admin.voucher.request.voucher.VoucherRequest;
import com.poly.app.domain.admin.voucher.response.VoucherReponse;
import com.poly.app.domain.auth.request.RegisterRequest;
import com.poly.app.domain.model.StatusEnum;
import com.poly.app.domain.model.Voucher;
import com.poly.app.infrastructure.constant.DiscountType;
import com.poly.app.infrastructure.constant.VoucherType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface VoucherService {
    List<VoucherReponse> getAllVoucher();

    Voucher createVoucher(VoucherRequest request);

    VoucherReponse updateVoucher(VoucherRequest request, int id);

    //    VoucherReponse updateTt(VoucherRequest request, int id);
//VoucherReponse updateVoucherStatus(int id, StatusVoucher status);
    String deleteVoucher(int id);

    VoucherReponse getVoucherDetail(int id);

    Page<VoucherReponse> getAllVoucher(Pageable pageable);

    //    Page<VoucherReponse> searchVouchers(String keyword, Pageable pageable);
    Boolean register(RegisterRequest request);

    String switchStatus(Integer id, StatusEnum status);


    List<VoucherReponse> getAllVouchersWithCustomer(Integer customerId);


    // 🔍 Tìm voucher theo tên
    List<VoucherReponse> searchVoucherByName(String voucherName);

    // 🔍 Tìm voucher theo trạng thái
    List<VoucherReponse> searchVoucherByStatus(StatusEnum statusVoucher);

    // 🔍 Tìm voucher theo số lượng
    List<VoucherReponse> searchVoucherByQuantity(Integer quantity);

    // 🔍 Tìm voucher theo loại
    List<VoucherReponse> searchVoucherByType(VoucherType voucherType);

    // 🔍 Tìm voucher theo khoảng giá trị giảm tối đa
    List<VoucherReponse> searchVoucherByDiscountMaxRange(Double minDiscount, Double maxDiscount);

    // 🔍 Tìm voucher theo khoảng giá trị hóa đơn tối thiểu
    List<VoucherReponse> searchVoucherByBillMinRange(Double minBill, Double maxBill);


    // 🔍 Tìm voucher theo khoảng thời gian bắt đầu và kết thúc
    List<VoucherReponse> searchVoucherByStartDateRange(LocalDateTime startDate, LocalDateTime endDate);


    Page<VoucherReponse> getPageVoucher(int size,
                                        int page,
                                        StatusEnum statusVoucher,
                                        String search,
                                        String startDate,
                                        String endDate,
                                        VoucherType voucherType,
                                        DiscountType discountType
    );

}
