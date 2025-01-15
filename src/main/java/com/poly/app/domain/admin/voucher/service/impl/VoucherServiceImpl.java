package com.poly.app.domain.admin.voucher.service.impl;

import com.poly.app.domain.admin.voucher.request.voucher.VoucherRequest;
import com.poly.app.domain.admin.voucher.response.VoucherReponse;
import com.poly.app.domain.admin.voucher.response.VoucherResponse;
import com.poly.app.domain.admin.voucher.service.VoucherService;
import com.poly.app.domain.model.Voucher;
import com.poly.app.domain.repository.VoucherRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class VoucherServiceImpl implements VoucherService {

    VoucherRepository voucherRepository;

    @Override
    public List<VoucherReponse> getAllVoucher() {

//        List<VoucherReponse> voucherReponses = voucherRepository.getAllVou();
        
        return voucherRepository.findAll().stream()
                .map(voucher -> new VoucherReponse(voucher.getId(), voucher.getVoucherCode(),
                        voucher.getQuantity(), voucher.getVoucherType(), voucher.getDiscountValue(),
                        voucher.getDiscountMaxValue(), voucher.getBillMinValue(), voucher.getStartDate(),
                        voucher.getEndDate(), voucher.getStatus())).toList();
    }

    @Override
    public Voucher createVoucher(VoucherRequest request) {
        Voucher voucher = Voucher.builder()
                .id(request.getId())
                .voucherCode(request.getVoucherCode())
                .quantity(request.getQuantity())
                .voucherType(request.getVoucherType())
                .discountValue(request.getDiscountValue())
                .discountMaxValue(request.getDiscountMaxValue())
                .billMinValue(request.getBillMinValue())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .status(request.getStatus())
                .build();
        return voucherRepository.save(voucher);
    }

    @Override
    public VoucherReponse updateVoucher(VoucherRequest request, int id) {
        Voucher voucher = voucherRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id khong ton tai"));
//        voucher.setId(request.getId());
        voucher.setVoucherCode(request.getVoucherCode());
        voucher.setQuantity(request.getQuantity());
        voucher.setVoucherType(request.getVoucherType());
        voucher.setDiscountValue(request.getDiscountValue());
        voucher.setDiscountMaxValue(request.getDiscountMaxValue());
        voucher.setBillMinValue(request.getBillMinValue());
        voucher.setStartDate(request.getStartDate());
        voucher.setEndDate(request.getEndDate());
        voucher.setStatus(request.getStatus());
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
                .status(voucher.getStatus())
                .build();
    }

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
                .status(voucher.getStatus())
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
                        .status(voucher.getStatus())
                        .build()
        );
    }
}

