package com.poly.app.domain.repository;

import com.poly.app.domain.admin.voucher.response.VoucherResponse;
import com.poly.app.domain.model.StatusEnum;
import com.poly.app.domain.model.Voucher;
import com.poly.app.infrastructure.constant.VoucherType;
import com.poly.app.domain.admin.voucher.response.VoucherReponse;
import com.poly.app.infrastructure.constant.VoucherType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query(value = """
            (SELECT v.* 
            FROM voucher v
            WHERE v.start_date <= NOW() 
              AND v.end_date >= NOW() 
              AND v.quantity > 0
              AND v.status_voucher = 'dang_kich_hoat'
              AND v.voucher_type = 'PUBLIC')

            UNION

            (SELECT v.* 
            FROM voucher v
            LEFT JOIN customer_voucher cv ON v.id = cv.voucher_id
            WHERE v.start_date <= NOW() 
              AND v.end_date >= NOW() 
              AND v.quantity > 0
              AND v.status_voucher = 'dang_kich_hoat'
              AND (:customerId IS NOT NULL AND cv.customer_id = :customerId))
            """, nativeQuery = true)
    List<Voucher> findValidVouchers(@Param("customerId") String customerId);



    List<Voucher> findByStartDateBeforeAndEndDateAfterAndQuantityGreaterThan(
            LocalDateTime today, LocalDateTime todayAgain, Integer quantity
    );

        List<Voucher> findByVoucherNameContainingIgnoreCase(String voucherName);
    // 🔍 Tìm voucher theo trạng thái
    List<Voucher> findByStatusVoucher(StatusEnum statusVoucher);



    // 🔍 Tìm kiếm theo số lượng voucher (quantity)
    List<Voucher> findByQuantity(Integer quantity);

    // 🔍 Tìm kiếm theo loại voucher (voucherType)
    List<Voucher> findByVoucherType(VoucherType voucherType);

    // 🔍 Tìm kiếm theo khoảng giá trị giảm tối đa (discountMaxValue)
    List<Voucher> findByDiscountMaxValueBetween(Double minDiscount, Double maxDiscount);

    // 🔍 Tìm kiếm theo khoảng giá trị hóa đơn tối thiểu (billMinValue)
    List<Voucher> findByBillMinValueBetween(Double minBill, Double maxBill);


    // 🔍 Tìm kiếm theo khoảng thời gian bắt đầu (startDate) và kết thúc (endDate)
    List<Voucher> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);


    Page<Voucher> findAll(Specification<Voucher> spec, Pageable pageable);
    Voucher findVoucherByVoucherCode(String voucherCode);
}
