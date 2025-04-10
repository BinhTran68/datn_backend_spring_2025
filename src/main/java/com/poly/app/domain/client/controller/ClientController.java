package com.poly.app.domain.client.controller;

import com.poly.app.domain.admin.product.response.color.ColorResponse;
import com.poly.app.domain.admin.product.response.productdetail.ProductDetailResponse;
import com.poly.app.domain.admin.product.response.size.SizeResponse;
import com.poly.app.domain.client.request.AddCart;
import com.poly.app.domain.client.request.CreateBillClientRequest;
import com.poly.app.domain.client.response.*;
import com.poly.app.domain.client.service.ClientService;
import com.poly.app.domain.common.ApiResponse;
import com.poly.app.domain.common.Meta;
import com.poly.app.domain.model.Voucher;
import com.poly.app.domain.repository.ProductDetailRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ClientController {
    ClientService clientService;
ProductDetailRepository productDetailRepository;
    @GetMapping("/getallproducthadpromotion")
    ApiResponse<List<ProductViewResponseClass>> getAllProductHadPromotion(@RequestParam(value = "page", defaultValue = "0") Integer page
            , @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        Page<ProductViewResponse> result = clientService.getAllProductHadPromotion(page - 1, size);

        List<ProductViewResponseClass> list = result.getContent().stream()
                .map(i -> ProductViewResponseClass.builder()
                        .productId(i.getProductId())
                        .productName(i.getProductName())
                        .productDetailId(i.getProductDetailId())
                        .price(i.getPrice())
                        .sold(i.getSold())
                        .tag(i.getTag())
                        .colorId(i.getColorId())
                        .sizeId(i.getSizeId())
                        .imageUrl(i.getImageUrl())
                        .createdAt(i.getCreatedAt())
                        .views(i.getViews())
                        .promotionView(clientService.getPromotionView(i.getProductId(), i.getColorId(), i.getGenderId()))
                        .build())
                .collect(Collectors.toList());
        return ApiResponse.<List<ProductViewResponseClass>>builder()
                .message("lọc")
                .data(list)
                .meta(Meta.builder()
                        .totalElement(result.getTotalElements())
                        .currentPage(result.getNumber() + 1)
                        .totalPages(result.getTotalPages()).build())
                .build();
    }

    @GetMapping("/getallproducthadsoledesc")
    ApiResponse<List<ProductViewResponseClass>> getAllProductHadSoleDesc(@RequestParam(value = "page", defaultValue = "0") Integer page
            , @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        Page<ProductViewResponse> result = clientService.getAllProductHadSoleDesc(page - 1, size);

        List<ProductViewResponseClass> list = result.getContent().stream()
                .map(i -> ProductViewResponseClass.builder()
                        .productId(i.getProductId())
                        .productName(i.getProductName())
                        .productDetailId(i.getProductDetailId())
                        .price(i.getPrice())
                        .sold(i.getSold())
                        .tag(i.getTag())
                        .colorId(i.getColorId())
                        .sizeId(i.getSizeId())
                        .imageUrl(i.getImageUrl())
                        .createdAt(i.getCreatedAt())
                        .views(i.getViews())
                        .promotionView(clientService.getPromotionView(i.getProductId(), i.getColorId(), i.getGenderId()))
                        .build())
                .collect(Collectors.toList());
        return ApiResponse.<List<ProductViewResponseClass>>builder()
                .message("lọc")
                .data(list)
                .meta(Meta.builder()
                        .totalElement(result.getTotalElements())
                        .currentPage(result.getNumber() + 1)
                        .totalPages(result.getTotalPages()).build())
                .build();
    }

    @GetMapping("/getallproducthadcreatedatdesc")
    ApiResponse<List<ProductViewResponse>> getAllProductHadCreatedAtDesc(@RequestParam(value = "page", defaultValue = "0") Integer page
            , @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        Page<ProductViewResponse> page1 = clientService.getAllProductHadCreatedAtDesc(page - 1, size);

        return ApiResponse.<List<ProductViewResponse>>builder()
                .message("danh sách các sản phẩm có ngày tạo mới nhất")
                .data(page1.getContent())
                .meta(Meta.builder()
                        .totalElement(page1.getTotalElements())
                        .currentPage(page1.getNumber() + 1)
                        .totalPages(page1.getTotalPages()).build())
                .build();
    }

    @GetMapping("/getallproducthadviewsdesc")
    ApiResponse<List<ProductViewResponse>> getAllProductHadViewsDesc(@RequestParam(value = "page", defaultValue = "0") Integer page
            , @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        Page<ProductViewResponse> page1 = clientService.getAllProductHadViewsDesc(page - 1, size);

        return ApiResponse.<List<ProductViewResponse>>builder()
                .message("danh sách các sản phẩm view cao nhất")
                .data(page1.getContent())
                .meta(Meta.builder()
                        .totalElement(page1.getTotalElements())
                        .currentPage(page1.getNumber() + 1)
                        .totalPages(page1.getTotalPages()).build())
                .build();
    }

    @GetMapping("/getproductdetail")
    ApiResponse<ProductDetailResponse> getProductDetail(@RequestParam(value = "productId", defaultValue = "") Integer productId
            , @RequestParam(value = "colorId", defaultValue = "") Integer colorId,
                                                        @RequestParam(value = "sizeId", defaultValue = "") Integer sizeId
    ) {

        return ApiResponse.<ProductDetailResponse>builder()
                .message("lấy sản phẩm")
                .data(clientService.findProductDetailbyProductIdAndColorIdAndSizeId(productId, colorId, sizeId))
                .build();
    }

    @GetMapping("/findsizebyproductid")
    ApiResponse<List<SizeResponse>> findSizesByProductId(@RequestParam(value = "productId", defaultValue = "") Integer productId
    ) {
        return ApiResponse.<List<SizeResponse>>builder()
                .message("danh sách các size thuộc sản phẩm")
                .data(clientService.findSizesByProductId(productId))
                .build();
    }

    @GetMapping("/findsizebyproductidandcolorid")
    ApiResponse<List<SizeResponse>> findSizesByProductIdAndColorId(@RequestParam(value = "productId", defaultValue = "") Integer productId,
                                                                   @RequestParam(value = "colorId", defaultValue = "") Integer colorId
    ) {
        return ApiResponse.<List<SizeResponse>>builder()
                .message("danh sách các size thuộc sản phẩm và color")
                .data(clientService.findSizesByProductIdAndColorId(productId, colorId))
                .build();
    }

    @GetMapping("/findcolorbyproductid")
    ApiResponse<List<ColorResponse>> findColorsByProductId(@RequestParam(value = "productId", defaultValue = "") Integer productId
    ) {
        return ApiResponse.<List<ColorResponse>>builder()
                .message("danh sách các size thuộc sản phẩm")
                .data(clientService.findColorsByProductId(productId))
                .build();
    }

    @GetMapping("/addviewproduct")
    ApiResponse<Integer> addViewProduct(@RequestParam(value = "productId", defaultValue = "") Integer productId
    ) {
        return ApiResponse.<Integer>builder()
                .message("add view")
                .data(clientService.addViewProduct(productId))
                .build();
    }

    @PostMapping("/createbillclient")
    ApiResponse<String> createBillClient(@RequestBody CreateBillClientRequest request
    ) {
        return ApiResponse.<String>builder()
                .message("tạo hóa đơn client")
                .data(clientService.createBillClient(request))
                .build();
    }

    @GetMapping("/getallcartforcustomerid")
    ApiResponse<List<CartResponse>> getAllCartDetailForCustomerid(
            @RequestParam Integer customerId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        Page<CartResponse> page1 = clientService.getAllCartCustomerId(customerId, page - 1, size);
        return ApiResponse.<List<CartResponse>>builder()
                .message("lấy giỏ hàng của khách hàng")
                .meta(Meta.builder()
                        .totalPages(page1.getTotalPages())
                        .currentPage(page1.getNumber() + 1)
                        .totalElement(page1.getTotalElements())
                        .build())
                .data(page1.getContent())
                .build();
    }

    @GetMapping("/getallcartforcustomeridnopage")
    ApiResponse<List<CartResponse>> getAllCartDetailForCustomeridNopage(
            @RequestParam Integer customerId

    ) {

        return ApiResponse.<List<CartResponse>>builder()
                .message("lấy giỏ hàng của khách hàng no page")

                .data(clientService.getAllByCustomserIdNopage(customerId))
                .build();
    }

    @PostMapping("/addcart")
    ApiResponse<CartResponse> addCart(
            @RequestBody AddCart addCart
    ) {
        return ApiResponse.<CartResponse>builder()
                .message("lấy giỏ hàng của khách hàng")
                .data(clientService.addProductToCart(addCart))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    ApiResponse<String> addCart(
            @PathVariable Integer id
    ) {
        return ApiResponse.<String>builder()
                .message("xóa sp trong giỏ hàng")
                .data(clientService.deleteCartById(id))
                .build();
    }

    @GetMapping("/voucherbest")
    ApiResponse<VoucherBestResponse> voucherBesst(
            @RequestParam(required = false) String customerId,
            @RequestParam String billValue
    ) {
        return ApiResponse.<VoucherBestResponse>builder()
                .message("gợi ý voucher")
                .data(clientService.voucherBest(customerId, billValue))
                .build();
    }

    @GetMapping("/findvalidvouchers")
    ApiResponse<List<Voucher>> findValidVouchers(
            @RequestParam(required = false) String customerId

    ) {
        return ApiResponse.<List<Voucher>>builder()
                .message("lấy voucher hợp lệ")
                .data(clientService.findValidVouchers(customerId))
                .build();
    }

    @GetMapping("/plus")
    ApiResponse<Integer> plus(
            @RequestParam(required = false) Integer customerId

    ) {
        return ApiResponse.<Integer>builder()
                .message("cộng thêm 1")
                .data(clientService.plus(customerId))
                .build();
    }

    @GetMapping("/subtract")
    ApiResponse<Integer> subtract(
            @RequestParam(required = false) Integer customerId

    ) {
        return ApiResponse.<Integer>builder()
                .message("trừ 1")
                .data(clientService.subtract(customerId))
                .build();
    }

    @GetMapping("/setquantitycart")
    ApiResponse<Integer> setQuantityCart(
            @RequestParam(required = false) Integer customerId,
            @RequestParam(required = false) Integer quantity

    ) {
        return ApiResponse.<Integer>builder()
                .message("sửa lại số lượng")
                .data(clientService.setQuantityCart(customerId, quantity))
                .build();
    }

    @PostMapping("/getrealprice")
    ApiResponse<List<RealPriceResponse>> getRealPrice(
            @RequestBody List<AddCart> addCart
    ) {
        return ApiResponse.<List<RealPriceResponse>>builder()
                .message("get real price")
                .data(clientService.getRealPrice(addCart))
                .build();
    }

    @GetMapping("/getadressdefault")
    ApiResponse<Object> getAddressDefault(
            @RequestParam(required = false) Integer customerId

    ) {
        return ApiResponse.<Object>builder()
                .message("sửa get addre")
                .data(clientService.findAdressDefaulCustomerId(customerId))
                .build();
    }

    @GetMapping("/searchbill")
    ApiResponse<SearchStatusBillResponse> searchBill(
            @RequestParam(required = false) String billCode
    ) {
        return ApiResponse.<SearchStatusBillResponse>builder()
                .message("lấy trạng thái hóa đơn")
                .data(clientService.searchBill(billCode))
                .build();
    }
    @GetMapping("/veritifybillcode")
    ApiResponse<String> veritify(
            @RequestParam(required = false) String billCode
    ) {
        return ApiResponse.<String>builder()
                .message("xác minh danh tính")
                .data(clientService.veritifyBill(billCode))
                .build();
    }
    @GetMapping("/getallbillcustomerId")
    ApiResponse<List<SearchStatusBillResponse>> getALlBillCustomerId(
            @RequestParam(value = "customerId", required = false) Integer customerId,

            @RequestParam(value = "page", defaultValue = "1") Integer page
            , @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        return clientService.getAllBillOfCustomerid(customerId,page,size);
    }
    @GetMapping("/cancelbill")
    ApiResponse<String> cacelBill(
            @RequestParam(required = false) Integer billId,
            @RequestParam(required = false) String description

            ) throws Exception {
        return ApiResponse.<String>builder()
                .message("Hủy đơn hàng")
                .data(clientService.cancelBill(billId,description))
                .build();
    }
    @GetMapping("/buyback")
    ApiResponse<String> buyBack(
            @RequestParam Integer billId,
            @RequestParam Integer customerId

    ) {
        return ApiResponse.<String>builder()
                .message("Hủy đơn hàng")
                .data(clientService.buyBack(billId,customerId))
                .build();
    }
    @GetMapping("/refund")
    ApiResponse<String> refund(
            @RequestParam Integer billId,
            @RequestParam Integer moneyRefund,
            @RequestParam String depcription

    ) throws Exception {
        return ApiResponse.<String>builder()
                .message("Hủy đơn hàng")
                .data(clientService.refund(billId,moneyRefund,depcription))
                .build();
    }
    @GetMapping("/filter")
    public ApiResponse<List<ProductViewResponseClass>> filterProducts(
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long productId,
            @RequestParam(required = false) Long genderId,
            @RequestParam(required = false) Long typeId,
            @RequestParam(required = false) Long colorId,
            @RequestParam(required = false) Long materialId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Kiểm tra tham số phân trang


        // Kiểm tra khoảng giá


        Page<ProductViewResponse> result = clientService.findFilteredProducts(
                productId,brandId , genderId, typeId, colorId, materialId, minPrice, maxPrice, page - 1, size);
// Ánh xạ thủ công từ Page<ProductViewResponse> sang List<ProductViewResponseClass>
        List<ProductViewResponseClass> list = result.getContent().stream()
                .map(i -> ProductViewResponseClass.builder()
                        .productId(i.getProductId())
                        .productName(i.getProductName())
                        .productDetailId(i.getProductDetailId())
                        .price(i.getPrice())
                        .sold(i.getSold())
                        .tag(i.getTag())
                        .colorId(i.getColorId())
                        .sizeId(i.getSizeId())
                        .imageUrl(i.getImageUrl())
                        .createdAt(i.getCreatedAt())
                        .views(i.getViews())
                        .promotionView(clientService.getPromotionView(i.getProductId(), i.getColorId(), i.getGenderId()))
                        .build())
                .collect(Collectors.toList());
        return ApiResponse.<List<ProductViewResponseClass>>builder()
                .message("lọc")
                .data(list)
                .meta(Meta.builder()
                        .totalElement(result.getTotalElements())
                        .currentPage(result.getNumber() + 1)
                        .totalPages(result.getTotalPages()).build())
                .build();
    }
    @GetMapping("/product-details/discount")
    public List<ProductDetailDiscountDTO> getDiscountedProductDetails(
            @RequestParam Integer productId,
            @RequestParam Integer colorId,
            @RequestParam Integer genderId) {
        return clientService.getDiscountedProductDetails(productId, colorId, genderId);
    }
    @GetMapping("/has-bought")
    public Boolean hasBought(
            @RequestParam Integer productId,
            @RequestParam Integer customerId
        ) {
        return clientService.hasBought(customerId,productId);
    }
    @GetMapping("/product-full")
    public ApiResponse<List<ProductDetailResponse>> getAllSelectDetail() {
        return ApiResponse.<List<ProductDetailResponse>>builder()
                .message("get all product")
                .data(productDetailRepository.getAllProductDetail())
                .build();
    }
}
