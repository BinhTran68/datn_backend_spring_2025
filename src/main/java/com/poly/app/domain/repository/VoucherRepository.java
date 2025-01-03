package com.poly.app.domain.repository;

import com.poly.app.domain.model.Voucher;
import com.poly.app.domain.response.VoucherReponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher,Integer> {
    @Query("""
                    SELECT new  com.poly.app.domain.response
                     (vc.id, vc.voucherCode, vc.quantity, vc.voucherType, vc.discountValue,
                     vc.discountMaxValue, vc.billMinValue, vc.startDate, vc.endDate, vc.status)          
                    FROM Voucher vc
            """)
    List<VoucherReponse> getAll();
}
