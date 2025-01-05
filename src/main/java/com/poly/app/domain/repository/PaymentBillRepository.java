package com.poly.app.domain.repository;

import com.poly.app.domain.admin.bill.response.PaymentBillResponse;
import com.poly.app.domain.model.Bill;
import com.poly.app.domain.model.PaymentBill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentBillRepository extends JpaRepository<PaymentBill,Integer> {


    List<PaymentBill> findByBill(Bill bill);

    @Query(value = "SELECT " +
            "pm.paymentMethodsType AS paymentMethodsType, " +
            "pm.paymentMethod AS paymentMethod, " +
            "pm.notes AS notes, " +
            "pm.totalMoney AS totalMoney, " +
            "pm.createdAt AS createdAt, " +
            "pm.transactionCode AS transactionCode " +
            "FROM PaymentBill pb " +
            "LEFT JOIN PaymentMethods pm ON pm.id = pb.paymentMethods.id " +
            "LEFT JOIN Bill b ON b.id = pb.bill.id " +
            "WHERE b.billCode = :billCode")
    List<PaymentBillResponse> findPaymentBillByBillCode(@Param("billCode") String billCode);

}
