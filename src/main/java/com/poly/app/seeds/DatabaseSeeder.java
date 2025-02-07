package com.poly.app.seeds;


import com.poly.app.domain.model.Address;
import com.poly.app.domain.model.Announcement;
import com.poly.app.domain.model.Bill;
import com.poly.app.domain.model.BillDetail;
import com.poly.app.domain.model.BillHistory;
import com.poly.app.domain.model.Brand;
import com.poly.app.domain.model.Color;
import com.poly.app.domain.model.Customer;
import com.poly.app.domain.model.Gender;
import com.poly.app.domain.model.Material;
import com.poly.app.domain.model.PaymentBill;
import com.poly.app.domain.model.PaymentMethods;
import com.poly.app.domain.model.Product;
import com.poly.app.domain.model.ProductDetail;
import com.poly.app.domain.model.Role;
import com.poly.app.domain.model.Size;
import com.poly.app.domain.model.Sole;
import com.poly.app.domain.model.Staff;
import com.poly.app.domain.model.Type;
import com.poly.app.domain.model.Voucher;
import com.poly.app.infrastructure.constant.AccountStatus;
import com.poly.app.infrastructure.constant.PaymentMethodEnum;
import com.poly.app.infrastructure.constant.PaymentMethodsType;
import com.poly.app.infrastructure.constant.Status;
import com.poly.app.infrastructure.constant.BillStatus;
import com.poly.app.infrastructure.constant.TypeBill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import com.poly.app.domain.repository.*;

import java.time.LocalDateTime;


public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    GenderRepository genderRepository;

    @Autowired
    AnnouncementRepository announcementRepository;

    @Autowired
    SizeRepository sizeRepository;

    @Autowired
    SoleRepository soleRepository;

    @Autowired
    TypeRepository typeRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    ColorRepository colorRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartDetailRepository cartDetailRepository;

    @Autowired
    BillRepository billRepository;

    @Autowired
    BillDetailRepository billDetailRepository;

    @Autowired
    BillHistoryRepository billHistoryRepository;

    @Autowired
    PaymentMethodsRepository paymentMethodsRepository;

    @Autowired
    PaymentBillRepository paymentBillRepository;

    @Autowired
    PromotionRepository promotionRepository;

    @Autowired
    PromotionDetailRepository promotionDetailRepository;

    @Autowired
    VoucherRepository voucherRepository;

    @Autowired
    CustomerVoucherRepository customerVoucherRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    PasswordEncoder passwordEncoder;




    @Override
    public void run(String... args) throws Exception {
        Role role = Role.builder().roleName("ROLE_ADMIN").build();
        roleRepository.save(role);

        Role role1 = Role.builder().roleName("ROLE_STAFF").build();
        roleRepository.save(role1);

        Role role2 = Role.builder().roleName("ROLE_MANAGER").build();
        roleRepository.save(role2);


        //


        //

        // Seed Gender data
        Gender genderMale = Gender.builder()
                .genderCode("M")
                .genderName("Nam")
                .status(Status.HOAT_DONG) // Status 1 có thể đại diện cho "active"
                .build();
        genderRepository.save(genderMale);

        Gender genderFemale = Gender.builder()
                .genderCode("F")
                .genderName("Nữ")
                .status(Status.HOAT_DONG)
                .build();
        genderRepository.save(genderFemale);

        Gender genderOther = Gender.builder()
                .genderCode("O")
                .genderName("Khác")
                .status(Status.HOAT_DONG)
                .build();
        genderRepository.save(genderOther);

        Customer customer1 = Customer.builder()
                .code("CUST001")
                .fullName("Nguyễn Văn A")
                .dateBirth(LocalDateTime.now()) // Ngày sinh (dạng timestamp), ví dụ: 01/01/1990
                .CitizenId("0123456789")
                .phoneNumber("0912345678")
                .email("nguyenvana@example.com")
                .gender(true) // true = Nam, false = Nữ
                .password(passwordEncoder.encode("123")) // Nên mã hóa mật khẩu khi lưu thực tế
                .avatar("avatar1.jpg")
                .status(AccountStatus.HOAT_DONG) // Đã kích hoạt tài khoản
                .build();
        customerRepository.save(customer1);

        Customer customer2 = Customer.builder()
                .code("CUST002")
                .fullName("Trần Thị B")
                .dateBirth(LocalDateTime.now()) // Ngày sinh: 01/01/2000
                .CitizenId("9876543210")
                .phoneNumber("0987654321")
                .email("tranthib@example.com")
                .gender(false)
                .password(passwordEncoder.encode("123"))
                .avatar("avatar2.jpg")
                .status(AccountStatus.CHUA_KICH_HOAT) // Tài khoản chưa kích hoạt
                .build();
        customerRepository.save(customer2);


        ///

        Customer customerExits = customerRepository.findById(1).orElseThrow(() ->
                new RuntimeException("Customer with ID 1 not found"));

        Announcement announcement1 = Announcement.builder()
                .customer(customerExits)
                .announcementCode("ANN001")
                .announcementContent("Thông báo khuyến mãi 50% cho khách hàng VIP!")
                .build();
        announcementRepository.save(announcement1);

        Announcement announcement2 = Announcement.builder()
                .customer(customerExits)
                .announcementCode("ANN002")
                .announcementContent("Chúc mừng bạn đã nhận được voucher giảm giá 100k!")
                .build();
        announcementRepository.save(announcement2);


        //

        Size size1 = Size.builder().sizeName("Small").status(Status.HOAT_DONG).build();
        Size size2 = Size.builder().sizeName("Medium").status(Status.HOAT_DONG).build();
        Size size3 = Size.builder().sizeName("Large").status(Status.HOAT_DONG).build();
        Size size4 = Size.builder().sizeName("Extra Large").status(Status.HOAT_DONG).build();

        // Save data to the database
        sizeRepository.save(size1);
        sizeRepository.save(size2);
        sizeRepository.save(size3);
        sizeRepository.save(size4);


        Sole sole1 = Sole.builder().soleName("Rubber Sole").status(Status.HOAT_DONG).build();
        Sole sole2 = Sole.builder().soleName("PU Sole").status(Status.HOAT_DONG).build();
        Sole sole3 = Sole.builder().soleName("PVC Sole").status(Status.HOAT_DONG).build();
        Sole sole4 = Sole.builder().soleName("Leather Sole").status(Status.HOAT_DONG).build();
        Sole sole5 = Sole.builder().soleName("Foam Sole").status(Status.HOAT_DONG).build();

        // Save data to the database
        soleRepository.save(sole1);
        soleRepository.save(sole2);
        soleRepository.save(sole3);
        soleRepository.save(sole4);
        soleRepository.save(sole5);


        Type type1 = Type.builder().typeName("Sports Shoes").status(Status.HOAT_DONG).build();
        Type type2 = Type.builder().typeName("Casual Shoes").status(Status.HOAT_DONG).build();
        Type type3 = Type.builder().typeName("Formal Shoes").status(Status.HOAT_DONG).build();
        Type type4 = Type.builder().typeName("Boots").status(Status.HOAT_DONG).build();
        Type type5 = Type.builder().typeName("Slippers").status(Status.HOAT_DONG).build();

        // Save data to the database
        typeRepository.save(type1);
        typeRepository.save(type2);
        typeRepository.save(type3);
        typeRepository.save(type4);
        typeRepository.save(type5);


        // Add seed data
        Brand brand1 = Brand.builder().brandName("Nike").status(Status.HOAT_DONG).build();
        Brand brand2 = Brand.builder().brandName("Adidas").status(Status.HOAT_DONG).build();
        Brand brand3 = Brand.builder().brandName("Puma").status(Status.HOAT_DONG).build();
        Brand brand4 = Brand.builder().brandName("Reebok").status(Status.HOAT_DONG).build();
        Brand brand5 = Brand.builder().brandName("New Balance").status(Status.HOAT_DONG).build();

        // Save data to the database
        brandRepository.save(brand1);
        brandRepository.save(brand2);
        brandRepository.save(brand3);
        brandRepository.save(brand4);
        brandRepository.save(brand5);


        // Add seed data
        Material material1 = Material.builder().materialName("Leather").status(Status.HOAT_DONG).build();
        Material material2 = Material.builder().materialName("Canvas").status(Status.HOAT_DONG).build();
        Material material3 = Material.builder().materialName("Suede").status(Status.HOAT_DONG).build();
        Material material4 = Material.builder().materialName("Nylon").status(Status.HOAT_DONG).build();
        Material material5 = Material.builder().materialName("Mesh").status(Status.HOAT_DONG).build();

        // Save data to the database
        materialRepository.save(material1);
        materialRepository.save(material2);
        materialRepository.save(material3);
        materialRepository.save(material4);
        materialRepository.save(material5);


        Color color1 = Color.builder().colorName("Red").status(Status.HOAT_DONG).build();
        Color color2 = Color.builder().colorName("Blue").status(Status.HOAT_DONG).build();
        Color color3 = Color.builder().colorName("Black").status(Status.HOAT_DONG).build();
        Color color4 = Color.builder().colorName("White").status(Status.HOAT_DONG).build();
        Color color5 = Color.builder().colorName("Green").status(Status.HOAT_DONG).build();
        Color color6 = Color.builder().colorName("Yellow").status(Status.HOAT_DONG).build();
        Color color7 = Color.builder().colorName("Gray").status(Status.HOAT_DONG).build();

        // Save data to the database
        colorRepository.save(color1);
        colorRepository.save(color2);
        colorRepository.save(color3);
        colorRepository.save(color4);
        colorRepository.save(color5);
        colorRepository.save(color6);
        colorRepository.save(color7);


        Product product1 = Product.builder().productName("Nike Air Max").status(Status.HOAT_DONG).build();
        Product product2 = Product.builder().productName("Adidas UltraBoost").status(Status.HOAT_DONG).build();
        Product product3 = Product.builder().productName("Puma RS-X3").status(Status.HOAT_DONG).build();
        Product product4 = Product.builder().productName("Reebok Classic Leather").status(Status.HOAT_DONG).build();
        Product product5 = Product.builder().productName("New Balance 574").status(Status.HOAT_DONG).build();

        // Save data to the database
        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
        productRepository.save(product4);
        productRepository.save(product5);


        Product product = productRepository.findById(1).orElse(null); // Assuming there is a product with id 1
        Brand brand = brandRepository.findById(1).orElse(null); // Assuming there is a brand with id 1
        Type type = typeRepository.findById(1).orElse(null); // Assuming there is a type with id 1
        Color color = colorRepository.findById(1).orElse(null); // Assuming there is a color with id 1
        Material material = materialRepository.findById(1).orElse(null); // Assuming there is a material with id 1
        Size size = sizeRepository.findById(1).orElse(null); // Assuming there is a size with id 1
        Sole sole = soleRepository.findById(1).orElse(null); // Assuming there is a sole with id 1
        Gender gender = genderRepository.findById(1).orElse(null); // Assuming there is a gender with id 1

        // Add seed data for ProductDetail
        ProductDetail productDetail1 = ProductDetail.builder()
                .product(product)
                .brand(brand)
                .type(type)
                .color(color)
                .material(material)
                .size(size)
                .sole(sole)
                .gender(gender)
                .productDetailCode("PD001")
                .quantity(100)
                .price(99.99)
                .weight(0.5)
                .descrition("Nike Air Max 2023, High comfort and design.")
                .status(Status.HOAT_DONG)
                .build();

        ProductDetail productDetail2 = ProductDetail.builder()
                .product(product)
                .brand(brand)
                .type(type)
                .color(color)
                .material(material)
                .size(size)
                .sole(sole)
                .gender(gender)
                .productDetailCode("PD002")
                .quantity(200)
                .price(89.99)
                .weight(0.6)
                .descrition("Adidas UltraBoost 2023, Advanced cushioning.")
                .status(Status.HOAT_DONG)
                .build();

        // Save product details to the database
        productDetailRepository.save(productDetail1);
        productDetailRepository.save(productDetail2);


        Role adminRole = roleRepository.findByRoleName("ROLE_ADMIN");
        Role userRole = roleRepository.findByRoleName("ROLE_STAFF");

        // Nếu chưa có role, tạo mới
        if (adminRole == null) {
            adminRole = roleRepository.save(new Role("ADMIN",Status.HOAT_DONG));
        }
        if (userRole == null) {
            userRole = roleRepository.save(new Role("USER", Status.HOAT_DONG));
        }

        // Tạo và lưu Staff
        Staff staff1 = Staff.builder()
                .code("STF001")
                .fullName("Nguyễn Văn A")
                .dateBirth(LocalDateTime.now()) // Ngày sinh (dạng timestamp)
                .CitizenId("1234567890")
                .phoneNumber("0901234567")
                .email("nguyenvana@example.com")
                .gender(true) // Nam
                .password(passwordEncoder.encode("123456")) // Mã hóa mật khẩu
                .avatar("avatar1.jpg")
                .status(AccountStatus.HOAT_DONG) // Đã kích hoạt tài khoản
                .role(adminRole) // Vai trò ADMIN
                .build();

        staffRepository.save(staff1);

        Staff staff2 = Staff.builder()
                .code("STF002")
                .fullName("Trần Thị B")
                .dateBirth(LocalDateTime.now()) // Ngày sinh (dạng timestamp)
                .CitizenId("0987654321")
                .phoneNumber("0987654321")
                .email("tranthib@example.com")
                .gender(false) // Nữ
                .password(passwordEncoder.encode("123456"))
                .avatar("avatar2.jpg")
                .status(AccountStatus.CHUA_KICH_HOAT) // Tài khoản chưa kích hoạt
                .role(userRole) // Vai trò USER
                .build();

        staffRepository.save(staff2);


        Customer customerBill1 = customerRepository.findById(1).orElse(null); // Giả sử customer có id = 1
        Customer customerBill2 = customerRepository.findById(2).orElse(null); // Giả sử customer có id = 2
        Staff staffBill1 = staffRepository.findById(1).orElse(null); // Giả sử staff có id = 1
        Staff staffBill2 = staffRepository.findById(2).orElse(null); // Giả sử staff có id = 2

        if (customerBill1 != null && staffBill1 != null) {
            Bill bill1 = Bill.builder()
                    .customer(customerBill1)
                    .staff(staffBill1)

                    .customerMoney(500000.0)
                    .discountMoney(50000.0)
                    .shipMoney(30000.0)
                    .totalMoney(480000.0)
                    .typeBill(TypeBill.ONLINE)
                    .completeDate(LocalDateTime.now()) // Ngày hoàn thành
                    .confirmDate(LocalDateTime.now()) // Ngày xác nhận
                    .desiredDateOfReceipt(LocalDateTime.now()) // Ngày nhận hàng mong muốn
                    .shipDate(LocalDateTime.now()) // Ngày giao hàng

                    .numberPhone("0912345678")
                    .email("nguyenvana@example.com")
                    .status(BillStatus.DA_THANH_TOAN) // Trạng thái hóa đơn
                    .build();
            billRepository.save(bill1);


            Bill bill3 = Bill.builder()
                    .customer(customerBill1)
                    .staff(staffBill1)

                    .customerMoney(500000.0)
                    .discountMoney(50000.0)
                    .shipMoney(30000.0)
                    .totalMoney(480000.0)
                    .typeBill(TypeBill.ONLINE)
                    .completeDate(LocalDateTime.now()) // Ngày hoàn thành
                    .confirmDate(LocalDateTime.now()) // Ngày xác nhận
                    .desiredDateOfReceipt(LocalDateTime.now()) // Ngày nhận hàng mong muốn
                    .shipDate(LocalDateTime.now()) // Ngày giao hàng
                    .numberPhone("0912345678")
                    .email("nguyenvana@example.com")
                    .status(BillStatus.DA_THANH_TOAN) // Trạng thái hóa đơn
                    .build();
            billRepository.save(bill1);
            billRepository.save(bill3);


        }



        if (customerBill2 != null && staffBill2 != null) {
            Bill bill2 = Bill.builder()
                    .customer(customerBill2)
                    .staff(staffBill2)
                    .billCode("BILL002")
                    .customerMoney(300000.0)
                    .discountMoney(20000.0)
                    .shipMoney(25000.0)
                    .totalMoney(305000.0)
                    .typeBill(TypeBill.OFFLINE)
                    .completeDate(LocalDateTime.now()) // Ngày hoàn thành
                    .confirmDate(LocalDateTime.now()) // Ngày xác nhận
                    .desiredDateOfReceipt(LocalDateTime.now()) // Ngày nhận hàng mong muốn
                    .shipDate(LocalDateTime.now()) // Ngày giao hàng

                    .numberPhone("0987654321")
                    .email("tranthib@example.com")
                    .status(BillStatus.CHO_XAC_NHAN) // Trạng thái hóa đơn
                    .build();
            billRepository.save(bill2);

        }


        Bill bill1 = billRepository.findById(1).orElse(null); // Giả sử bill có id = 1
        Bill bill2 = billRepository.findById(2).orElse(null); // Giả sử bill có id = 2
        ProductDetail productDetailBill1 = productDetailRepository.findById(1).orElse(null); // Giả sử productDetail có id = 1
        ProductDetail productDetailBill2 = productDetailRepository.findById(2).orElse(null); // Giả sử productDetail có id = 2

        BillHistory billHistory = BillHistory
                .builder().bill(bill2).customer(customerBill1).staff(staffBill1).status(BillStatus.CHO_XAC_NHAN)
                .build();
        BillHistory billHistory1 = BillHistory
                .builder().bill(bill1).customer(customerBill1).staff(staffBill1).status(BillStatus.CHO_VAN_CHUYEN)
                .build();

        billHistoryRepository.save(billHistory);

        billHistoryRepository.save(billHistory1);

        if (bill1 != null && productDetailBill1 != null) {
            BillDetail billDetail1 = BillDetail.builder()
                    .bill(bill1)
                    .productDetail(productDetail1)
                    .price(100000.0) // Giá của sản phẩm
                    .quantity(2) // Số lượng sản phẩm
                    .totalMoney(200000.0) // Tổng tiền (price * quantity)
                    .status(Status.HOAT_DONG) // Trạng thái hóa đơn chi tiết
                    .build();
            billDetailRepository.save(billDetail1);
        }

        if (bill2 != null && productDetailBill2 != null) {
            BillDetail billDetail2 = BillDetail.builder()
                    .bill(bill2)
                    .productDetail(productDetail2)
                    .price(150000.0) // Giá của sản phẩm
                    .quantity(1) // Số lượng sản phẩm
                    .totalMoney(150000.0) // Tổng tiền (price * quantity)
                    .status(Status.HOAT_DONG) // Trạng thái hóa đơn chi tiết
                    .build();
            billDetailRepository.save(billDetail2);
        }


        PaymentMethods paymentMethod1 = PaymentMethods.builder()
                .paymentMethod(PaymentMethodEnum.TIEN_MAT)// Tên phương thức thanh toán
                .totalMoney(1000000.0) // Tổng tiền
                .paymentMethodsType(PaymentMethodsType.THANH_TOAN_TRUOC)
                .notes("Thanh toán qua thẻ Visa hoặc MasterCard") // Ghi chú
                .transactionCode("TXN001") // Mã giao dịch
                .status(Status.HOAT_DONG) // Trạng thái (1: Hoạt động, 0: Không hoạt động)
                .build();


        paymentMethodsRepository.save(paymentMethod1);

        PaymentMethods paymentMethod2 = PaymentMethods.builder()

                .paymentMethod(PaymentMethodEnum.TIEN_MAT)
                .totalMoney(2000000.0)
                .paymentMethodsType(PaymentMethodsType.THANH_TOAN_TRUOC)
                .notes("Thanh toán qua chuyển khoản vào tài khoản ngân hàng")
                .transactionCode("TXN002")
                .status(Status.HOAT_DONG)
                .build();
        paymentMethodsRepository.save(paymentMethod2);

        PaymentMethods paymentMethod3 = PaymentMethods.builder()
                .paymentMethod(PaymentMethodEnum.CHUYEN_KHOAN)
                .totalMoney(500000.0)
                .paymentMethodsType(PaymentMethodsType.THANH_TOAN_KHI_NHAN_HANG)
                .notes("Thanh toán khi nhận sản phẩm tại nhà")
                .transactionCode("TXN003")
                .status(Status.HOAT_DONG) // Trạng thái không hoạt động
                .build();
        paymentMethodsRepository.save(paymentMethod3);


        Bill billPaymentMethods1 = billRepository.findById(1).orElse(null); // Giả sử Bill có ID là 1
        PaymentMethods paymentMethodBill1 = paymentMethodsRepository.findById(1).orElse(null); // Giả sử phương thức thanh toán có ID là 1

        Bill billPaymentMethods2 = billRepository.findById(2).orElse(null); // Giả sử Bill có ID là 2
        PaymentMethods paymentMethodBill2 = paymentMethodsRepository.findById(2).orElse(null); // Giả sử phương thức thanh toán có ID là 2

        if (billPaymentMethods1 != null && paymentMethodBill1 != null) {
            // Tạo PaymentBill cho bill1 và paymentMethod1
            PaymentBill paymentBill1 = PaymentBill.builder()
                    .bill(bill1)
                    .paymentMethods(paymentMethod1)
                    .status(Status.HOAT_DONG) // Trạng thái thanh toán thành công
                    .build();
            paymentBillRepository.save(paymentBill1);
        }

        if (billPaymentMethods2 != null && paymentMethodBill2 != null) {
            // Tạo PaymentBill cho bill2 và paymentMethod2
            PaymentBill paymentBill2 = PaymentBill.builder()
                    .bill(bill2)
                    .paymentMethods(paymentMethod2)
                    .status(Status.HOAT_DONG) // Trạng thái thanh toán chưa hoàn thành
                    .build();
            paymentBillRepository.save(paymentBill2);
        }


        Voucher voucher1 = Voucher.builder()
                .voucherCode("VOUCHER10")

                .discountValue(10.0) // Giảm 10%
                .voucherType("PERCENT") // Loại giảm theo phần trăm
                .discountMaxValue(50.0) // Giảm tối đa 50.000đ
                .billMinValue(200.0) // Giá trị hóa đơn tối thiểu 200.000đ
                .startDate(LocalDateTime.now()) // Ngày bắt đầu là ngày hiện tại
                .endDate(LocalDateTime.now()) // Ngày kết thúc là Status.HOAT_DONG ngày sau
                .build();

        Voucher voucher2 = Voucher.builder()
                .voucherCode("VOUCHER200")

                .discountValue(200.0) // Giảm 200.000đ
                .voucherType("MONEY") // Loại giảm theo số tiền
                .discountMaxValue(200.0)
                .billMinValue(500.0) // Hóa đơn tối thiểu 500.000đ
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now()) // Status.HOAT_DONG ngày sau
                .build();

        // Lưu các phiếu giảm giá vào cơ sở dữ liệu
        voucherRepository.save(voucher1);
        voucherRepository.save(voucher2);
    }

}
