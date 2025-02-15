package com.poly.app.domain.repository;

import com.poly.app.domain.admin.voucher.response.VoucherResponse;
import com.poly.app.domain.model.Voucher;
import com.poly.app.domain.admin.voucher.response.VoucherReponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
    @Query("""
                    SELECT new  com.poly.app.domain.admin.voucher.response.VoucherReponse
                     (vc.id, vc.voucherCode,vc.voucherName, vc.quantity, vc.voucherType, vc.discountValue,
                     vc.discountMaxValue, vc.billMinValue, vc.startDate, vc.endDate, vc.statusVoucher,vc.discountValueType)
                    FROM Voucher vc 
            """)
    List<VoucherReponse> getAllVou();

//    @Query("""
//                SELECT new com.poly.app.domain.admin.voucher.response.VoucherReponse
//                 (vc.id, vc.voucherCode, vc.quantity, vc.voucherType, vc.discountValue,
//                 vc.discountMaxValue, vc.billMinValue, vc.startDate, vc.endDate, vc.status)
//                FROM Voucher vc
//                 WHERE (:voucherCode IS NULL OR vc.voucherCode LIKE %:promotionCode%)
//            AND (:voucherType IS NULL OR vc.voucherType LIKE %:promotionName%)
//            AND (:promotionType IS NULL OR vc.promotionType LIKE %:promotionType%)
//            AND (:status IS NULL OR vc.status = :status)
//            """)
//    Page<VoucherReponse> searchVouchers(@Param("keyword") String keyword, Pageable pageable);
}
