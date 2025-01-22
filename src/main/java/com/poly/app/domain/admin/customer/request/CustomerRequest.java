//package com.poly.app.domain.admin.customer.request;
//
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class CustomerRequest {
//    private String code;
//    private String fullName;
//    private LocalDateTime dateBirth;
//    private String CitizenId;
//    private String phoneNumber;
//    private String email;
//    private Boolean gender;
//    private String password;
//    private List<AddressRequest> addresses;
//}





//package com.poly.app.domain.admin.customer.request;
//
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class CustomerRequest {
//    private String code;
//    private String fullName;
//    private LocalDateTime dateBirth;
//    private String CitizenId;
//    private String phoneNumber;
//    private String email;
//    private Boolean gender;
//    private String password;
//
//    // Đảm bảo địa chỉ không phải null, khởi tạo giá trị mặc định nếu cần
//    private List<AddressRequest> addresses = new ArrayList<>();  // Khởi tạo danh sách rỗng
//}




package com.poly.app.domain.admin.customer.request;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CustomerRequest {
    private String fullName;
    private String CitizenId;
    private String email;
    private Boolean gender;
    private String phoneNumber;
    private LocalDateTime dateBirth;
    private String password;
    private String avatar;
    private Integer status;
    private List<AddressRequest> addresses = new ArrayList<>();

    // Constructor
    public CustomerRequest() {
        this.addresses = new ArrayList<>(); // Ensure addresses is never null
    }
}
