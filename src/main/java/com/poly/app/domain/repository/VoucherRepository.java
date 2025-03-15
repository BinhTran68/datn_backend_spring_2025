package com.poly.app.domain.repository;

import com.poly.app.domain.model.Voucher;
import com.poly.app.infrastructure.constant.VoucherType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
//    @Query("""
//                    SELECT new  com.poly.app.domain.admin.voucher.response.VoucherReponse
//                     (vc.id, vc.voucherCode,vc.voucherName, vc.quantity, vc.voucherType, vc.discountValue,
//                     vc.discountMaxValue, vc.billMinValue, vc.startDate, vc.endDate, vc.statusVoucher,vc.discountValueType)
//                    FROM Voucher vc
//            """)
//    List<VoucherReponse> getAllVou();  /// viết vậy khó sửa lắm


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


    List<Voucher> findByStartDateBeforeAndEndDateAfterAndQuantityGreaterThan(
            LocalDateTime today, LocalDateTime todayAgain, Integer quantity
    );

//    List<Voucher> findByStartDateBeforeAndEndDateAfterAndQuantityGreaterThan(
//            LocalDateTime today, LocalDateTime todayAgain, Integer quantity, Integer customerId
//    );


}
