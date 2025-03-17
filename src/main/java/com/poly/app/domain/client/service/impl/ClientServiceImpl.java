package com.poly.app.domain.client.service.impl;

import com.poly.app.domain.admin.bill.request.BillDetailRequest;
import com.poly.app.domain.admin.product.response.color.ColorResponse;
import com.poly.app.domain.admin.product.response.img.ImgResponse;
import com.poly.app.domain.admin.product.response.productdetail.ProductDetailResponse;
import com.poly.app.domain.admin.product.response.size.SizeResponse;
import com.poly.app.domain.client.repository.ProductViewRepository;
import com.poly.app.domain.client.request.AddCart;
import com.poly.app.domain.client.request.CreateBillClientRequest;
import com.poly.app.domain.client.response.CartResponse;
import com.poly.app.domain.client.response.ProductViewResponse;
import com.poly.app.domain.client.response.RealPriceResponse;
import com.poly.app.domain.client.response.VoucherBestResponse;
import com.poly.app.domain.client.service.ClientService;
import com.poly.app.domain.client.service.ZaloPayService;
import com.poly.app.domain.model.*;
import com.poly.app.domain.repository.*;
import com.poly.app.infrastructure.constant.*;
import com.poly.app.infrastructure.exception.ApiException;
import com.poly.app.infrastructure.exception.ErrorCode;
import com.poly.app.infrastructure.util.VoucherBest;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
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
            if (address1==null) {
                Address newAddress = Address
                        .builder()
                        .wardId(request.getDetailAddressShipping().getWardId())
                        .specificAddress(request.getDetailAddressShipping().getSpecificAddress())
                        .provinceId(request.getDetailAddressShipping().getProvinceId())
                        .districtId(request.getDetailAddressShipping().getDistrictId())
                        .customer(customer)
                        .build();
                address = addressRepository.save(newAddress);
            }else{
                address = address1;
            }

        }else{
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
                .status(BillStatus.CHO_XAC_NHAN)
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
                .status(BillStatus.CHO_XAC_NHAN)
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
                    Map<String, Object> zaloPayResponse = zaloPayService.createPayment(
                            customer != null ? customer.getId().toString() : "guest",
                            request.getTotalMoney().longValue(),
                            billSave.getId().longValue()
                    );

                    if (zaloPayResponse == null || !zaloPayResponse.containsKey("orderurl")) {
                        throw new ApiException(ErrorCode.INVALID_KEY);
                    }
//                    lưu phương thức thanh toán
//                    tìm keiesm xem thấy phương thức thanh toán zalopay ko
//
                    PaymentMethods paymentMethods = paymentMethodsRepository
                            .findByPaymentMethodsType(PaymentMethodsType.ZALO_PAY)
                            .orElseGet(() -> paymentMethodsRepository.save(PaymentMethods.builder()
                                    .paymentMethod(PaymentMethodEnum.CHUYEN_KHOAN)
                                    .paymentMethodsType(PaymentMethodsType.ZALO_PAY)
                                    .status(Status.HOAT_DONG)
                                    .build()));
//và lưu pttt
                    paymentBillRepository.save(PaymentBill.builder()
                            .bill(billSave)
                            .paymentMethods(paymentMethods)
                            .status(Status.HOAT_DONG)
                            .build());

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
                                .paymentMethod(PaymentMethodEnum.TIEN_MAT)
                                .paymentMethodsType(PaymentMethodsType.COD)
                                .status(Status.HOAT_DONG)
                                .build()));

                paymentBillRepository.save(PaymentBill.builder()
                        .bill(billSave)
                        .paymentMethods(paymentMethods)
                        .status(Status.HOAT_DONG)
                        .build());

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
            cartRepository.save(Cart.builder()
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
                    .cart(cartRepository.getCart(addCart.getCustomerId()))
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


}
