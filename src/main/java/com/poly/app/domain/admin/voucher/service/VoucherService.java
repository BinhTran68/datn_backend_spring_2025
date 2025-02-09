package com.poly.app.domain.admin.voucher.service;

import com.poly.app.domain.admin.voucher.request.voucher.VoucherRequest;
import com.poly.app.domain.admin.voucher.response.VoucherReponse;
import com.poly.app.domain.model.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface  VoucherService {
    List<VoucherReponse> getAllVoucher();
    Voucher createVoucher(VoucherRequest request);
    VoucherReponse updateVoucher(VoucherRequest request, int id);
//    VoucherReponse updateTt(VoucherRequest request, int id);

    String deleteVoucher(int id);
    VoucherReponse getVoucherDetail(int id);
    Page<VoucherReponse> getAllVoucher (Pageable pageable);
//    Page<VoucherReponse> searchVouchers(String keyword, Pageable pageable);

    List<VoucherReponse> getAllVouchersWithCustomer(Integer customerId);

}
