package com.poly.app.domain.admin.voucher.service.impl;

import com.poly.app.domain.admin.voucher.request.voucher.VoucherRequest;
import com.poly.app.domain.admin.voucher.response.VoucherReponse;
import com.poly.app.domain.admin.voucher.response.VoucherResponse;
import com.poly.app.domain.admin.voucher.service.VoucherService;
import com.poly.app.domain.model.StatusVoucher;
import com.poly.app.domain.model.Voucher;
import com.poly.app.domain.repository.VoucherRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class VoucherServiceImpl implements VoucherService {

    VoucherRepository voucherRepository;

    @Override
    public List<VoucherReponse> getAllVoucher() {

//        List<VoucherReponse> voucherReponses = voucherRepository.getAllVou();

        return List.of();
    }

    public StatusVoucher checkVoucherStatus(LocalDateTime startDate, LocalDateTime endDate) {
        LocalDateTime currentDate = LocalDateTime.now(); // Lấy thời gian hiện tại

        if (currentDate.isBefore(startDate)) {
            return StatusVoucher.chua_kich_hoat; // Chưa kích hoạt
        } else if (currentDate.isAfter(endDate)) {
            return StatusVoucher.ngung_kich_hoat; // Đã ngừng kích hoạt
        } else {
            return StatusVoucher.dang_kich_hoat; // Đang kích hoạt
        }
    }

    @Override
    public Voucher createVoucher(VoucherRequest request) {

        StatusVoucher saStatusVoucher = checkVoucherStatus(request.getStartDate(), request.getEndDate());
        // Sinh mã voucher tự động (định nghĩa logic trong createVoucher)
        String generatedVoucherCode = "MGG" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();



        Voucher voucher = Voucher.builder()
                .id(request.getId())
                .voucherCode(generatedVoucherCode)
                .quantity(request.getQuantity())
                .voucherType(request.getVoucherType())
                .discountValue(request.getDiscountValue())
                .discountMaxValue(request.getDiscountMaxValue())
                .billMinValue(request.getBillMinValue())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .statusVoucher(saStatusVoucher)
                .build();
        return voucherRepository.save(voucher);
    }

    @Override
    public VoucherReponse updateVoucher(VoucherRequest request, int id) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
        StatusVoucher saStatusVoucher = checkVoucherStatus(request.getStartDate(), request.getEndDate());

//        voucher.setId(request.getId());
//        voucher.setVoucherCode(request.getVoucherCode());
        voucher.setQuantity(request.getQuantity());
        voucher.setVoucherType(request.getVoucherType());
        voucher.setDiscountValue(request.getDiscountValue());
        voucher.setDiscountMaxValue(request.getDiscountMaxValue());
        voucher.setBillMinValue(request.getBillMinValue());
        voucher.setStartDate(request.getStartDate());
        voucher.setEndDate(request.getEndDate());
        voucher.setStatusVoucher(saStatusVoucher);
        voucherRepository.save(voucher);

        return VoucherReponse.builder()
                .id(voucher.getId())
                .voucherCode(voucher.getVoucherCode())
                .quantity(voucher.getQuantity())
                .voucherType(voucher.getVoucherType())
                .discountValue(voucher.getDiscountValue())
                .discountMaxValue(voucher.getDiscountMaxValue())
                .billMinValue(voucher.getBillMinValue())
                .startDate(voucher.getStartDate())
                .endDate(voucher.getEndDate())
                .statusVoucher(voucher.getStatusVoucher())
                .build();
    }
//    @Override
//    public VoucherReponse updateTt(VoucherRequest request, int id) {
//        Voucher voucher = voucherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
//        voucher.setStatusVoucher(request.getStatusVoucher());
//        voucherRepository.save(voucher);
//        return VoucherReponse.builder()
//                .id(voucher.getId())
//                .voucherCode(voucher.getVoucherCode())
//                .quantity(voucher.getQuantity())
//                .voucherType(voucher.getVoucherType())
//                .discountValue(voucher.getDiscountValue())
//                .discountMaxValue(voucher.getDiscountMaxValue())
//                .billMinValue(voucher.getBillMinValue())
//                .startDate(voucher.getStartDate())
//                .endDate(voucher.getEndDate())
//                .statusVoucher(voucher.getStatusVoucher())
//                .build();
//    }


    @Override
    public String deleteVoucher(int id) {
        if (!voucherRepository.findById(id).isEmpty()) {
            voucherRepository.deleteById(id);
            return "Xoa voucher thanh cong";
        } else {
            return "voucher khong ton tai";
        }
    }

    @Override
    public VoucherReponse getVoucherDetail(int id) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
        return VoucherReponse.builder()
                .id(voucher.getId())
                .voucherCode(voucher.getVoucherCode())
                .quantity(voucher.getQuantity())
                .voucherType(voucher.getVoucherType())
                .discountValue(voucher.getDiscountValue())
                .discountMaxValue(voucher.getDiscountMaxValue())
                .billMinValue(voucher.getBillMinValue())
                .startDate(voucher.getStartDate())
                .endDate(voucher.getEndDate())
                .statusVoucher(voucher.getStatusVoucher())
                .build();
    }
    public Page<VoucherReponse> getAllVoucher(Pageable pageable) {

        return voucherRepository.findAll(pageable).map(voucher ->
                VoucherReponse.builder()
                        .id(voucher.getId())
                        .voucherCode(voucher.getVoucherCode())
                        .quantity(voucher.getQuantity())
                        .voucherType(voucher.getVoucherType())
                        .discountValue(voucher.getDiscountValue())
                        .discountMaxValue(voucher.getDiscountMaxValue())
                        .billMinValue(voucher.getBillMinValue())
                        .startDate(voucher.getStartDate())
                        .endDate(voucher.getEndDate())
                        .statusVoucher(voucher.getStatusVoucher())
                        .build()
        );
    }

    @Override
    public List<VoucherReponse> getAllVouchersWithCustomer(Integer customerId) {
        // Lấy danh sách các voucher public

        // Lấy danh sách voucher của người dùng
        return voucherRepository.findAll().stream()
                .map(voucher -> VoucherReponse.builder()
                        .id(voucher.getId())
                        .voucherCode(voucher.getVoucherCode())
                        .quantity(voucher.getQuantity())
                        .voucherName(voucher.getVoucherName())
                        .voucherType(voucher.getVoucherType())
                        .discountValue(voucher.getDiscountValue())
                        .discountMaxValue(voucher.getDiscountMaxValue())
                        .billMinValue(voucher.getBillMinValue())
                        .startDate(voucher.getStartDate())
                        .endDate(voucher.getEndDate())
                        .statusVoucher(voucher.getStatusVoucher())
                        .build()).toList();
    }
}

