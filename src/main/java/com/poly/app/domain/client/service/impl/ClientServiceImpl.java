package com.poly.app.domain.client.service.impl;

import com.poly.app.domain.admin.address.AddressRequest;
import com.poly.app.domain.admin.bill.request.BillDetailRequest;
import com.poly.app.domain.admin.product.response.color.ColorResponse;
import com.poly.app.domain.admin.product.response.img.ImgResponse;
import com.poly.app.domain.admin.product.response.productdetail.ProductDetailResponse;
import com.poly.app.domain.admin.product.response.size.SizeResponse;
import com.poly.app.domain.client.repository.ProductViewRepository;
import com.poly.app.domain.client.request.AddCart;
import com.poly.app.domain.client.request.CreateBillClientRequest;
import com.poly.app.domain.client.response.*;
import com.poly.app.domain.client.service.ClientService;
import com.poly.app.domain.client.service.ZaloPayService;
import com.poly.app.domain.common.ApiResponse;
import com.poly.app.domain.common.Meta;
import com.poly.app.domain.model.*;
import com.poly.app.domain.repository.*;
import com.poly.app.infrastructure.constant.*;
import com.poly.app.infrastructure.email.Email;
import com.poly.app.infrastructure.email.EmailSender;
import com.poly.app.infrastructure.exception.ApiException;
import com.poly.app.infrastructure.exception.ErrorCode;
import com.poly.app.infrastructure.util.VoucherBest;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ClientServiceImpl implements ClientService {
    ProductViewRepository productViewRepository;
    ImageRepository imageRepository;
    SizeRepository sizeRepository;
    ColorRepository colorRepository;
    ProductRepository productRepository;
    CustomerRepository customerRepository;
    VoucherRepository voucherRepository;
    AddressRepository addressRepository;
    BillRepository billRepository;
    ProductDetailRepository productDetailRepository;
    BillDetailRepository billDetailRepository;
    BillHistoryRepository billHistoryRepository;
    PaymentMethodsRepository paymentMethodsRepository;
    PaymentBillRepository paymentBillRepository;
    ZaloPayService zaloPayService;
    CartDetailRepository cartDetailRepository;
    CartRepository cartRepository;
    EmailSender emailSender;


    @Override
    public Page<ProductViewResponse> getAllProductHadPromotion(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productViewRepository.getAllProductHadPromotion(pageable);
    }

    @Override
    public Page<ProductViewResponse> getAllProductHadSoleDesc(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productViewRepository.getAllProductHadSoleDesc(pageable);
    }

    @Override
    public Page<ProductViewResponse> getAllProductHadCreatedAtDesc(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productViewRepository.getAllProductHadCreatedAtDesc(pageable);
    }

    @Override
    public Page<ProductViewResponse> getAllProductHadViewsDesc(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productViewRepository.getAllProductHadViewsDesc(pageable);
    }

    @Override
    public ProductDetailResponse findProductDetailbyProductIdAndColorIdAndSizeId(int productId, int colorId, int sizeId) {
        try {
            ProductDetailResponse productDetail = productViewRepository.findByProductAndColorAndSize(productId, colorId, sizeId);
            List<ImgResponse> images = imageRepository.findByProductDetailId(productDetail.getId());
            productDetail.setImage(images);
            return productDetail;

        } catch (Exception e) {
            throw new IllegalArgumentException("ko tìm thấy chi tiết sản phẩm này");
        }


    }

    @Override
    public List<SizeResponse> findSizesByProductId(Integer productId) {
        return sizeRepository.findSizesByProductId(productId);
    }

    @Override
    public List<SizeResponse> findSizesByProductIdAndColorId(Integer productId, Integer colorId) {
        return sizeRepository.findSizesByProductIdAndColorId(productId, colorId);
    }

    @Override
    public List<ColorResponse> findColorsByProductId(Integer productId) {
        return colorRepository.findColorsByProductId(productId);
    }

    @Override
    public Integer addViewProduct(int productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ApiException(ErrorCode.SANPHAM_NOT_FOUND));
        int viewCurrent = (product.getViews() != null) ? product.getViews() : 0;
        product.setViews(viewCurrent + 1);
        productRepository.save(product);
        return product.getViews();
    }

    @Override
    @Transactional
    public String createBillClient(CreateBillClientRequest request) {
        Customer customer = null;

        if (request.getCustomerId() != null) {
            customer = customerRepository.findById(request.getCustomerId()).orElse(null);
        }
        Voucher voucher = null;
        if (request.getVoucherId() != null) {
            voucher = voucherRepository.findById(request.getVoucherId()).orElse(null);
        }
        Address address = null;
        if (request.getDetailAddressShipping() != null && customer != null) {
            Address address1 = addressRepository.findByCustomerIdAndProvinceIdAndDistrictIdAndWardIdAndSpecificAddress
                    (customer.getId(),
                            request.getDetailAddressShipping().getProvinceId(),
                            request.getDetailAddressShipping().getDistrictId(),
                            request.getDetailAddressShipping().getWardId(),
                            request.getDetailAddressShipping().getSpecificAddress());
            if (address1 == null) {
                Address newAddress = Address
                        .builder()
                        .wardId(request.getDetailAddressShipping().getWardId())
                        .specificAddress(request.getDetailAddressShipping().getSpecificAddress())
                        .provinceId(request.getDetailAddressShipping().getProvinceId())
                        .districtId(request.getDetailAddressShipping().getDistrictId())
                        .customer(customer)
                        .build();
                address = addressRepository.save(newAddress);
            } else {
                address = address1;
            }

        } else {
            Address newAddress = Address
                    .builder()
                    .wardId(request.getDetailAddressShipping().getWardId())
                    .specificAddress(request.getDetailAddressShipping().getSpecificAddress())
                    .provinceId(request.getDetailAddressShipping().getProvinceId())
                    .districtId(request.getDetailAddressShipping().getDistrictId())
                    .customer(customer)
                    .build();
            address = addressRepository.save(newAddress);
        }

        Bill bill = Bill
                .builder()
                .typeBill(TypeBill.ONLINE)
                .customer(customer)
                .customerName(customer != null ? customer.getFullName() : request.getRecipientName())
                .customerMoney(request.getCustomerMoney())
                .discountMoney(request.getDiscountMoney())
                .moneyAfter(request.getMoneyAfter())
                .shipDate(request.getShipDate())
                .shipMoney(request.getShipMoney())
                .totalMoney(request.getTotalMoney())
                .shippingAddress(address)
                .shipDate(request.getShipDate())
                .shipMoney(request.getShipMoney())
                .voucher(voucher)
                .status(customer != null ? BillStatus.CHO_XAC_NHAN : BillStatus.DANG_XAC_MINH)
                .email(request.getEmail())
                .notes(request.getNotes())
                .numberPhone(request.getRecipientPhoneNumber())
                .build();
        Bill billSave = billRepository.save(bill);

//tao cthd
        for (BillDetailRequest billDetailRequest : request.getBillDetailRequests()) {
            ProductDetail productDetail = productDetailRepository.findById(billDetailRequest.getProductDetailId())
                    .orElseThrow(() -> new ApiException(ErrorCode.SANPHAM_NOT_FOUND));

            if (productDetail != null) {
                BillDetail billDetail = BillDetail
                        .builder()
                        .image(billDetailRequest.getImage())
                        .productDetail(productDetail)
                        .bill(billSave)
                        .price(billDetailRequest.getPrice())
                        .totalMoney(billDetailRequest.getPrice() * billDetailRequest.getQuantity())
                        .quantity(billDetailRequest.getQuantity())
                        .status(Status.HOAT_DONG)
                        .build();
                billDetailRepository.save(billDetail);
            }
        }
//nếu có customer thì xóa sản phẩm đó khỏi giỏ hàng
        if (request.getCustomerId() != null) {
            List<CartResponse> cartResponses = cartDetailRepository.getAllByCustomerIdNoPage(request.getCustomerId());
//            tìm kiếm xem có productDetailId của  mảng request.getBillDetailRequests() có trong cartResponses không nếu có thì delete cartResponse đó
//            for (CartResponse i: cartResponses
//                 ) {
//                for (BillDetailRequest i2: request.getBillDetailRequests()
//                     ) {
//                    if (i2.getProductDetailId() == i.getProductDetailId() ) {
//                        cartDetailRepository.deleteById(i.getCartDetailId());
//                    }
//                }
//
//            }
            cartResponses.forEach(cartItem ->
                    request.getBillDetailRequests().forEach(billItem -> {
                        if (billItem.getProductDetailId().equals(cartItem.getProductDetailId())) {
                            cartDetailRepository.deleteById(cartItem.getCartDetailId());
                        }
                    })
            );

        }

//Lưu lại lịch sử

        billHistoryRepository.save(BillHistory
                .builder()
                .customer(customer)
                .bill(billSave)
                .description(customer != null ? "chờ bên admin xác nhận" : " chờ xác minh danh tính")
                .status(customer != null ? BillStatus.CHO_XAC_NHAN : BillStatus.DANG_XAC_MINH)
                .build());


//        lưu ptthanh toán
        switch (request.getPaymentMethodsType()) {
            case ZALO_PAY -> {
                try {
//                    List<ItemRequest> itemsList = request.getBillDetailRequests()
//                            .stream()
//                            .map(billDetail -> new ItemRequest(
//                                    billDetail.getProductDetailId().toString(),
//                                    "Sản phẩm " + billDetail.getProductDetailId(),
//                                    billDetail.getPrice().longValue(),
//                                    billDetail.getQuantity()
//                            ))
//                            .toList();
//                    lưu phương thức thanh toán
//                    tìm keiesm xem thấy phương thức thanh toán zalopay ko
//
                    PaymentMethods paymentMethods = paymentMethodsRepository
                            .findByPaymentMethodsType(PaymentMethodsType.ZALO_PAY)
                            .orElseGet(() -> paymentMethodsRepository.save(PaymentMethods.builder()
                                    .paymentMethodsType(PaymentMethodsType.ZALO_PAY)
                                    .status(Status.HOAT_DONG)
                                    .build()));
//và lưu pttt
                    paymentBillRepository.save(PaymentBill.builder()
                            .bill(billSave)
                            .paymentMethods(paymentMethods)
                            .payMentBillStatus(PayMentBillStatus.CHUA_THANH_TOAN)
                            .build());

                    if (customer == null) {
                        veritifyBill(billSave.getEmail(), billSave, request.getPaymentMethodsType().toString());
                        return "đang xác minh đơn hàng";
                    }
                    sendMail(request.getEmail(), billSave);
                    Map<String, Object> zaloPayResponse = zaloPayService.createPayment(
                            customer != null ? customer.getId().toString() : "guest",
                            request.getTotalMoney().longValue(),
                            billSave.getId().longValue()
                    );

                    if (zaloPayResponse == null || !zaloPayResponse.containsKey("orderurl")) {
                        throw new ApiException(ErrorCode.INVALID_KEY);
                    }


                    return (String) zaloPayResponse.get("orderurl"); // Trả về URL thanh toán ngay lập tức
                } catch (Exception e) {
                    log.error("Lỗi khi tạo đơn hàng ZaloPay", e);
                    throw new ApiException(ErrorCode.INVALID_KEY);
                }

            }
            case COD -> {
                PaymentMethods paymentMethods = paymentMethodsRepository
                        .findByPaymentMethodsType(PaymentMethodsType.COD)
                        .orElseGet(() -> paymentMethodsRepository.save(PaymentMethods.builder()
                                .paymentMethodsType(PaymentMethodsType.COD)
                                .status(Status.HOAT_DONG)
                                .build()));

                paymentBillRepository.save(PaymentBill.builder()
                        .bill(billSave)
                        .paymentMethods(paymentMethods)
                        .payMentBillStatus(PayMentBillStatus.CHUA_THANH_TOAN)
                        .build());
                if (customer == null) {
                    veritifyBill(billSave.getEmail(), billSave, request.getPaymentMethodsType().toString());
                    return "đang xác minh đơn hàng";
                }
                sendMail(request.getEmail(), billSave);
                return "tạo hóa đơn thành công";
            }
        }

        return "tạo hóa đơn thành công";
    }

    @Override
    public Page<CartResponse> getAllCartCustomerId(Integer customerId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return cartDetailRepository.getAllByCustomerId(customerId, pageable);
    }

    @Override
    public CartResponse addProductToCart(AddCart addCart) {
//        kiểm tra có giỏ hàng chưa
        Cart cart = cartRepository.getCart(addCart.getCustomerId());
        if (cart == null) {
            cart = cartRepository.save(Cart.builder()
                    .customerid(customerRepository.findById(addCart.getCustomerId()).get())
                    .build());
        }

        CartDetail cartDetail;
        cartDetail = cartDetailRepository.findByProductDetailId(addCart.getProductDetailId(), addCart.getCustomerId());
        if (cartDetail != null) {
            cartDetail.setQuantity(cartDetail.getQuantity() + addCart.getQuantityAddCart());
            cartDetailRepository.save(cartDetail);
        } else {
            cartDetail = cartDetailRepository.save(CartDetail.builder()
                    .cart(cart)
                    .price(addCart.getPrice())
                    .productDetailId(productDetailRepository.findById(addCart.getProductDetailId()).orElseThrow(() -> new ApiException(ErrorCode.INVALID_KEY)))
                    .imageUrl(addCart.getImage())
                    .quantity(addCart.getQuantityAddCart())
                    .productName(addCart.getProductName())
                    .build());

        }


        return CartResponse.builder()
                .cartDetailId(cartDetail.getId())
                .quantityAddCart(cartDetail.getQuantity())
                .image(cartDetail.getImageUrl())
                .build();
    }

    @Override
    public String deleteCartById(Integer cartDetailId) {
        cartDetailRepository.findById(cartDetailId).orElseThrow(() -> new ApiException(ErrorCode.INVALID_KEY));
        cartDetailRepository.deleteById(cartDetailId);
        return "xóa thành công";
    }

    @Override
    public List<CartResponse> getAllByCustomserIdNopage(Integer customerId) {
        return cartDetailRepository.getAllByCustomerIdNoPage(customerId);
    }

    @Override
    public VoucherBestResponse voucherBest(String customerId, String billValue) {
        List<Voucher> vouchers = voucherRepository.findValidVouchers(customerId);
        VoucherBest voucherBest = new VoucherBest();
        return voucherBest.recommendBestVoucher(Double.valueOf(billValue), vouchers);
    }

    @Override
    public List<Voucher> findValidVouchers(String customerId) {
        return voucherRepository.findValidVouchers(customerId);
    }

    @Override
    public Integer plus(Integer id) {
        CartDetail cartDetail = cartDetailRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.INVALID_KEY));
        cartDetail.setQuantity(cartDetail.getQuantity() + 1);
        cartDetailRepository.save(cartDetail);

        return cartDetail.getQuantity();
    }

    @Override
    public Integer subtract(Integer id) {
        CartDetail cartDetail = cartDetailRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.INVALID_KEY));
        cartDetail.setQuantity(cartDetail.getQuantity() > 0 ? cartDetail.getQuantity() - 1 : 0);
        cartDetailRepository.save(cartDetail);

        return cartDetail.getQuantity();
    }

    @Override
    public Integer setQuantityCart(Integer id, Integer quantity) {
        CartDetail cartDetail = cartDetailRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.INVALID_KEY));
//        Integer quantityProduct = productDetailRepository.findById(cartDetail.getProductDetailId().getId()
//        ).get().getQuantity();
//        if (quantity > quantityProduct) {
//            cartDetail.setQuantity(quantityProduct);
//            cartDetailRepository.save(cartDetail);
//            throw new ApiException(ErrorCode.INVALID_KEY);
//        }
        cartDetail.setQuantity(quantity);
        cartDetailRepository.save(cartDetail);

        return cartDetail.getQuantity();
    }

    @Override
    public List<RealPriceResponse> getRealPrice(List<AddCart> addCartList) {
        List<RealPriceResponse> realPriceResponses = new ArrayList<>();

        for (AddCart i : addCartList) {
            ProductDetail productDetail = productDetailRepository.findById(i.getProductDetailId())
                    .orElseThrow(() -> new ApiException(ErrorCode.INVALID_KEY));

            if (productDetail != null) {
                RealPriceResponse response = RealPriceResponse.builder()
                        .cartDetailId(null)
                        .quantity(productDetail.getQuantity())
                        .productDetailId(i.getProductDetailId())
                        .productName(productDetail.getProduct().getProductName())
                        .price(productDetail.getPrice())
                        .quantityAddCart(i.getQuantityAddCart())
                        .note(null)
                        .build();

                realPriceResponses.add(response);
            }
        }

        return realPriceResponses;
    }

    @Override
    public Optional<Object> findAdressDefaulCustomerId(Integer customerId) {
        Object o = addressRepository.findByCustomerIdAndIsAddressDefault(customerId, true);
        return Optional.of(o);
    }

    @Override
    public SearchStatusBillResponse searchBill(String billCode) {
        Bill bill = billRepository.findByBillCode(billCode);
        if (bill == null) {
            throw new ApiException(ErrorCode.HOA_DON_NOT_FOUND);
        }
        List<BillDetail> billDetails = billDetailRepository.findByBillId(bill.getId());

        List<BillDetailResponse> productDetails =
                billDetails.stream()
                        .map(item -> new BillDetailResponse(item.getId(), item.getQuantity(), item.getPrice(), item.getImage()))
                        .collect(Collectors.toList());
        PaymentBill paymentBill = paymentBillRepository.findByBillId(bill.getId());
        PaymentMethods paymentMethods = paymentMethodsRepository.findById(paymentBill.getPaymentMethods().getId()).orElse(null);
        String voucherCode = "";
        if (bill.getVoucher() != null) voucherCode = bill.getBillCode();
        SearchStatusBillResponse searchStatusBillResponse = SearchStatusBillResponse.builder()
                .id(bill.getId())
                .billCode(bill.getBillCode())
                .discountMoney(bill.getDiscountMoney())
                .shipMoney(bill.getShipMoney())
                .totalMoney(bill.getTotalMoney())
                .moneyAfter(bill.getMoneyAfter())
                .shippingAddress(bill.getShippingAddress().getId())
                .customerName(bill.getCustomerName())
                .numberPhone(bill.getNumberPhone())
                .email(bill.getEmail())
                .typeBill(bill.getTypeBill())
                .notes(bill.getNotes())
                .status(bill.getStatus())
                .payment(paymentMethods.getPaymentMethodsType().name())
                .voucher(voucherCode)
                .addressRequest(AddressRequest.builder()
                        .provinceId(bill.getShippingAddress().getProvinceId())
                        .districtId(bill.getShippingAddress().getDistrictId())
                        .wardId(bill.getShippingAddress().getWardId())
                        .specificAddress(bill.getShippingAddress().getSpecificAddress())
                        .build())
                .billDetailResponse(productDetails)
                .build();


        return searchStatusBillResponse;
    }

    @Override
    public String veritifyBill(String billCode) {
        Bill bill = billRepository.findByBillCode(billCode);
        if (bill == null) {
            throw new ApiException(ErrorCode.BILL_NOT_EXISTS);
        }
        PaymentBill paymentBill = paymentBillRepository.findByBillId(bill.getId());
        PaymentMethods paymentMethods = paymentMethodsRepository.findById(paymentBill.getPaymentMethods().getId()).orElse(null);

        if (paymentMethods.getPaymentMethodsType().equals(PaymentMethodsType.COD)) {
            bill.setStatus(BillStatus.CHO_XAC_NHAN);
            billRepository.save(bill);
            sendMail(bill.getEmail(), bill);
            billHistoryRepository.save(BillHistory
                    .builder()
                    .customer(null)
                    .bill(bill)
                    .description("xác minh danh tính thành công")
                    .status(BillStatus.CHO_XAC_NHAN)
                    .build());
            return "xác minh thành công";
        } else if (paymentMethods.getPaymentMethodsType().equals(PaymentMethodsType.ZALO_PAY)) {
            bill.setStatus(BillStatus.CHO_XAC_NHAN);
            billRepository.save(bill);
            sendMail(bill.getEmail(), bill);
            try {
                Map<String, Object> zaloPayResponse = zaloPayService.createPayment(
                        bill.getCustomer() != null ? bill.getId().toString() : "guest",
                        bill.getTotalMoney().longValue(),
                        bill.getId().longValue()
                );

                if (zaloPayResponse == null || !zaloPayResponse.containsKey("orderurl")) {
                    throw new ApiException(ErrorCode.INVALID_KEY);
                }
                billHistoryRepository.save(BillHistory
                        .builder()
                        .customer(null)
                        .description("xác minh danh tính thành công")
                        .bill(bill)
                        .status(BillStatus.CHO_XAC_NHAN)
                        .build());
                return (String) zaloPayResponse.get("orderurl"); // Trả về URL thanh toán ngay lập tức
            } catch (Exception e) {
                log.error("Lỗi khi tạo đơn hàng ZaloPay", e);
                throw new ApiException(ErrorCode.INVALID_KEY);
            }
        }
        return null;
    }

    @Override
    public ApiResponse<List<SearchStatusBillResponse>> getAllBillOfCustomerid(Integer customerId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        List<SearchStatusBillResponse> searchStatusBillResponses = new ArrayList<>();
        Page<Bill> page1 = billRepository.findByCustomerId(customerId, pageable);
        if (page1 == null) {
            throw new ApiException(ErrorCode.HOA_DON_NOT_FOUND);
        }

        for (Bill b :
                page1) {
            List<BillDetail> billDetails = billDetailRepository.findByBillId(b.getId());

            List<BillDetailResponse> productDetails =
                    billDetails.stream()
                            .map(item -> new BillDetailResponse(item.getId(), item.getQuantity(), item.getPrice(), item.getImage()))
                            .collect(Collectors.toList());
            PaymentBill paymentBill = paymentBillRepository.findByBillId(b.getId());
            PaymentMethods paymentMethods = paymentMethodsRepository.findById(paymentBill.getPaymentMethods().getId()).orElse(null);
            String voucherCode = "";
            if (b.getVoucher() != null) voucherCode = b.getBillCode();
            SearchStatusBillResponse searchStatusBillResponse = SearchStatusBillResponse.builder()
                    .id(b.getId())
                    .billCode(b.getBillCode())
                    .discountMoney(b.getDiscountMoney())
                    .shipMoney(b.getShipMoney())
                    .totalMoney(b.getTotalMoney())
                    .moneyAfter(b.getMoneyAfter())
                    .shippingAddress(b.getShippingAddress().getId())
                    .customerName(b.getCustomerName())
                    .numberPhone(b.getNumberPhone())
                    .email(b.getEmail())
                    .typeBill(b.getTypeBill())
                    .notes(b.getNotes())
                    .status(b.getStatus())
                    .payment(paymentMethods.getPaymentMethodsType().name())
                    .voucher(voucherCode)
                    .addressRequest(AddressRequest.builder()
                            .provinceId(b.getShippingAddress().getProvinceId())
                            .districtId(b.getShippingAddress().getDistrictId())
                            .wardId(b.getShippingAddress().getWardId())
                            .specificAddress(b.getShippingAddress().getSpecificAddress())
                            .build())
                    .billDetailResponse(productDetails)
                    .build();
            searchStatusBillResponses.add(searchStatusBillResponse);
        }

        return ApiResponse.<List<SearchStatusBillResponse>>builder()
                .message("geta bill customer")
                .meta(Meta.builder()
                        .totalElement(page1.getTotalElements())
                        .currentPage(page1.getNumber() + 1)
                        .totalPages(page1.getTotalPages()).build())
                .data(searchStatusBillResponses)
                .build();
    }

    @Override
    public String cancelBill(Integer id, String description) {
        Bill bill = billRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.INVALID_KEY));
        bill.setStatus(BillStatus.DA_HUY);
        billRepository.save(bill);
        billHistoryRepository.save(BillHistory
                .builder()
                .customer(bill.getCustomer())
                .description("Hủy đơn hàng\n Lý do:" + description)
                .bill(bill)
                .status(BillStatus.DA_HUY)
                .build());

        PaymentBill paymentBill = paymentBillRepository.findByBillId(bill.getId());
        PaymentMethods paymentMethods = paymentMethodsRepository.findById(paymentBill.getPaymentMethods().getId()).orElse(null);

//        if (paymentMethods.getPaymentMethodsType().equals(PaymentMethodsType.COD)) {
//            bill.setStatus(BillStatus.CHO_XAC_NHAN);
//            billRepository.save(bill);
//            sendMail(bill.getEmail(), bill);
//            billHistoryRepository.save(BillHistory
//                    .builder()
//                    .customer(null)
//                    .bill(bill)
//                    .description("xác minh danh tính thành công")
//                    .status(BillStatus.CHO_XAC_NHAN)
//                    .build());
//            return "xác minh thành công";
//        }
cancelBill(bill.getEmail(),bill);

        return "Hủy đơn hàng, lý do:" + description;
    }

    @Override
    public String buyBack(Integer billId, Integer customerId) {
        List<BillDetail> billDetail = billDetailRepository.findByBillId(billId);
        Cart cart = cartRepository.getCart(customerId);

        CartDetail cartDetail;
        for (BillDetail b :
                billDetail) {
            cartDetail = cartDetailRepository.findByProductDetailId(b.getProductDetail().getId(), customerId);
            if (cartDetail != null) {
                cartDetail.setQuantity(cartDetail.getQuantity() + b.getQuantity());
                cartDetailRepository.save(cartDetail);
            } else {
                cartDetail = cartDetailRepository.save(CartDetail.builder()
                        .cart(cart)
                        .price(b.getPrice())
                        .productDetailId(b.getProductDetail())
                        .imageUrl(b.getImage())
                        .quantity(b.getQuantity())
                        .productName(b.getProductDetail().getProduct().getProductName()
                                     + " [ " + b.getProductDetail().getColor().getColorName() + " - "
                                     + b.getProductDetail().getSize().getSizeName() + " ] ")
                        .build());

            }
        }

        return "thêm vào giỏ hàng thành coong";
    }

    private void sendMail(String sendToMail, Bill billCode) {
        Email email = new Email();
        String[] emailSend = {sendToMail};
        email.setToEmail(emailSend);
        email.setSubject("TheHands-Tạo Hóa Đơn Thành Công");
        email.setTitleEmail("");
        email.setBody("<!DOCTYPE html>\n" +
                      "<html lang=\"en\">\n" +
                      "<head>\n" +
                      "    <meta charset=\"UTF-8\">\n" +
                      "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                      "    <title>Hóa đơn TheHands</title>\n" +
                      "</head>\n" +
                      "<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; padding: 50px;\">\n" +
                      "    <div style=\"max-width: 600px; background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); margin: auto;\">\n" +
                      "        <h2 style=\"color: #333;\">🎉 Hóa đơn đã được tạo thành công! 🎉</h2>\n" +
                      "        <p style=\"color: #555;\">Cảm ơn bạn đã mua hàng tại <strong>TheHands</strong>. Dưới đây là thông tin đơn hàng của bạn:</p>\n" +
                      "        <hr style=\"border: none; border-top: 1px solid #ddd; margin: 20px 0;\">\n" +
                      "        <p><strong>📧 Email:</strong> " + sendToMail + "</p>\n" +
                      "        <p><strong>🧾 Mã hóa đơn:</strong> <span style=\"color: #007bff; font-weight: bold;\">" + billCode.getBillCode() + "</span></p>\n" +
                      "        <hr style=\"border: none; border-top: 1px solid #ddd; margin: 20px 0;\">\n" +
                      "        <p style=\"color: #555;\">Bạn có thể kiểm tra hóa đơn của mình bằng cách nhấn vào nút bên dưới:</p>\n" +
                      "        <a href=\"http://localhost:5173/searchbill?billcode=" + billCode.getBillCode() + "\" style=\"display: inline-block; background-color: #007bff; color: #ffffff; padding: 12px 20px; border-radius: 5px; text-decoration: none; font-weight: bold;\">🔍 Xem hóa đơn</a>\n" +
                      "        <p style=\"margin-top: 20px; font-size: 12px; color: #999;\">Nếu bạn không thực hiện giao dịch này, vui lòng bỏ qua email này.</p>\n" +
                      "    </div>\n" +
                      "</body>\n" +
                      "</html>");


        emailSender.sendEmail(email);
    }

    private void veritifyBill(String sendToMail, Bill billCode, String paymentMethod) {
        Email email = new Email();
        String[] emailSend = {sendToMail};
        email.setToEmail(emailSend);
        email.setSubject("TheHands-Xác Minh Đơn Hàng");
        email.setTitleEmail("");
        email.setBody(
                """
                        <!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                            <title>Xác Minh Đơn Hàng TheHands</title>
                        </head>
                        <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; padding: 50px;">
                            <div style="max-width: 600px; background-color: #ffffff; padding: 30px; border-radius: 15px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); margin: auto;">
                                <h2 style="color: #333; font-size: 24px; margin-bottom: 10px;">✅ Xác Minh Đơn Hàng Của Bạn</h2>
                                <p style="color: #666; font-size: 16px; line-height: 1.5;">Cảm ơn bạn đã đặt hàng tại <strong>TheHands</strong>. Vui lòng xác minh đơn hàng của bạn để tiếp tục xử lý.</p>
                                <hr style="border: none; border-top: 1px dashed #ddd; margin: 25px 0;">
                                <p style="color: #555; font-size: 16px;"><strong>📧 Email:</strong> %s</p>
                                <p style="color: #555; font-size: 16px;"><strong>🧾 Mã đơn hàng:</strong> <span style="color: #007bff; font-weight: bold;">%s</span></p>
                                <hr style="border: none; border-top: 1px dashed #ddd; margin: 25px 0;">
                                <p style="color: #666; font-size: 16px; line-height: 1.5;">Để xác minh và xem chi tiết đơn hàng, hãy nhấp vào nút bên dưới:</p>
                                <a href="http://localhost:5173/veritify?billcode=%s&paymentmethod=%s" 
                                   style="display: inline-block; background-color: #007bff; color: #ffffff; padding: 12px 25px; border-radius: 8px; text-decoration: none; font-weight: bold; font-size: 16px; transition: background-color 0.3s;">
                                   ✅ Xác Minh Đơn Hàng
                                </a>
                                <p style="margin-top: 25px; color: #e74c3c; font-size: 14px; font-style: italic;">Lưu ý: Nếu không xác minh trong 24 giờ, đơn hàng có thể bị hủy.</p>
                                <p style="margin-top: 15px; font-size: 12px; color: #999; line-height: 1.4;">Nếu bạn không đặt đơn hàng này, vui lòng bỏ qua email hoặc liên hệ hỗ trợ qua <a href='mailto:support@thehands.com' style='color: #007bff;'>support@thehands.com</a>.</p>
                            </div>
                        </body>
                        </html>
                        """.formatted(sendToMail, billCode.getBillCode(), billCode.getBillCode(), paymentMethod)
        );


        emailSender.sendEmail(email);
    }
    private void cancelBill(String sendToMail, Bill billCode) {
        Email email = new Email();
        String[] emailSend = {sendToMail};
        email.setToEmail(emailSend);
        email.setSubject("TheHands-Thông Báo Hủy Đơn Hàng");
        email.setTitleEmail("");
        email.setBody(
                """
                        <!DOCTYPE html>
                        <html lang="en">
                        <head>
                            <meta charset="UTF-8">
                            <meta name="viewport" content="width=device-width, initial-scale=1.0">
                            <title>Hủy Đơn Hàng Thành Công - TheHands</title>
                        </head>
                        <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; text-align: center; padding: 50px;">
                            <div style="max-width: 600px; background-color: #ffffff; padding: 30px; border-radius: 15px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); margin: auto;">
                                <h2 style="color: #333; font-size: 24px; margin-bottom: 10px;">❌ Hủy Đơn Hàng Thành Công</h2>
                                <p style="color: #666; font-size: 16px; line-height: 1.5;">Chúng tôi xin thông báo rằng đơn hàng của bạn tại <strong>TheHands</strong> đã được hủy thành công theo yêu cầu.</p>
                                <hr style="border: none; border-top: 1px dashed #ddd; margin: 25px 0;">
                                <p style="color: #555; font-size: 16px;"><strong>📧 Email:</strong> %s</p>
                                <p style="color: #555; font-size: 16px;"><strong>🧾 Mã đơn hàng:</strong> <span style="color: #e74c3c; font-weight: bold;">%s</span></p>
                                <hr style="border: none; border-top: 1px dashed #ddd; margin: 25px 0;">
                                <p style="color: #666; font-size: 16px; line-height: 1.5;">Nếu bạn cần hỗ trợ hoặc muốn đặt lại đơn hàng, vui lòng liên hệ với chúng tôi.</p>
                                <a href="mailto:support@thehands.com" 
                                   style="display: inline-block; background-color: #28a745; color: #ffffff; padding: 12px 25px; border-radius: 8px; text-decoration: none; font-weight: bold; font-size: 16px; transition: background-color 0.3s;">
                                   📩 Liên Hệ Hỗ Trợ
                                </a>
                                <p style="margin-top: 25px; font-size: 12px; color: #999; line-height: 1.4;">Cảm ơn bạn đã tin tưởng <strong>TheHands</strong>. Hy vọng sẽ được phục vụ bạn trong tương lai!</p>
                            </div>
                        </body>
                        </html>
                        """.formatted(sendToMail, billCode.getBillCode())
        );

        emailSender.sendEmail(email);
    }
}
