package com.poly.app.domain.repository;

import com.poly.app.domain.model.StatusEnum;
import com.poly.app.domain.model.Voucher;
import com.poly.app.infrastructure.constant.VoucherType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
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




}
