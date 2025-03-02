package com.poly.app.domain.client.request;

import com.poly.app.domain.admin.address.AddressRequest;
import com.poly.app.domain.admin.bill.request.BillDetailRequest;
import com.poly.app.infrastructure.constant.BillStatus;
import com.poly.app.infrastructure.constant.TypeBill;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateBillClientRequest {
    private Integer customerId;
    private Double customerMoney;
    private Double discountMoney;
    private Double shipMoney;
    private Double totalMoney;
    private Double moneyAfter;
    private LocalDateTime desiredDateOfReceipt;
    private LocalDateTime shipDate;
    private Integer shippingAddressId;
    private String email;
    private String notes;
    private Integer voucherId;
    private String recipientName;
    private String recipientPhoneNumber;
    private AddressRequest address;
    private List<BillDetailRequest> billDetailRequests;
}
