package com.poly.app.domain.admin.bill.request;

import com.poly.app.domain.admin.product.request.productdetail.ProductDetailRequest;
import com.poly.app.domain.model.Customer;
import com.poly.app.domain.model.ProductDetail;
import com.poly.app.infrastructure.constant.BillStatus;
import com.poly.app.infrastructure.constant.TypeBill;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CreateBillRequest {
    Integer customerId;
    Double customerMoney;
    Double cashCustomerMoney;
    Double bankCustomerMoney;
    Boolean isCashAndBank = false;
    Double discountMoney;
    Double shipMoney;
    Double totalMoney;
    Double moneyAfter;
    LocalDateTime completeDate;
    LocalDateTime confirmDate;
    LocalDateTime desiredDateOfReceipt;
    LocalDateTime shipDate;
    Long shippingAddressId;
    String numberPhone;
    String email;
    TypeBill typeBill;
    String notes;
    BillStatus status;
    Integer voucherId;

    List<BillDetailRequest> billDetailRequests;

}


