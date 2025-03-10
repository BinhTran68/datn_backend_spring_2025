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
import com.poly.app.domain.client.service.ClientService;
import com.poly.app.domain.client.service.ZaloPayService;
import com.poly.app.domain.model.*;
import com.poly.app.domain.repository.*;
import com.poly.app.infrastructure.constant.*;
import com.poly.app.infrastructure.exception.ApiException;
import com.poly.app.infrastructure.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        if (request.getShippingAddressId() != null) {
            address = addressRepository.findById(request.getShippingAddressId()).orElseThrow(()
                    -> new ApiException(ErrorCode.HOA_DON_NOT_FOUND));
        }
        if (request.getAddress() != null && address == null) {
            Address newAddress = Address
                    .builder()
                    .wardId(request.getAddress().getWardId())
                    .specificAddress(request.getAddress().getSpecificAddress())
                    .provinceId(request.getAddress().getProvinceId())
                    .districtId(request.getAddress().getDistrictId())
                    .customer(customer)
                    .build();
            address = addressRepository.save(newAddress);
        }

        Bill bill = Bill
                .builder()
                .typeBill(TypeBill.ONLINE)
                .customer(customer)
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


}
