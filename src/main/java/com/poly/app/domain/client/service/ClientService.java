package com.poly.app.domain.client.service;

import com.poly.app.domain.admin.product.response.color.ColorResponse;
import com.poly.app.domain.admin.product.response.productdetail.ProductDetailResponse;
import com.poly.app.domain.admin.product.response.size.SizeResponse;
import com.poly.app.domain.client.request.AddCart;
import com.poly.app.domain.client.request.CreateBillClientRequest;
import com.poly.app.domain.client.response.*;
import com.poly.app.domain.common.ApiResponse;
import com.poly.app.domain.model.Voucher;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Page<ProductViewResponse> getAllProductHadPromotion(int page, int size);

    Page<ProductViewResponse> getAllProductHadSoleDesc(int page, int size);

    Page<ProductViewResponse> getAllProductHadCreatedAtDesc(int page, int size);

    Page<ProductViewResponse> getAllProductHadViewsDesc(int page, int size);

//    trang detail từng sản phẩm

    ProductDetailResponse findProductDetailbyProductIdAndColorIdAndSizeId(int productId, int colorId, int sizeId);

    List<SizeResponse> findSizesByProductId(Integer productId);

    List<SizeResponse> findSizesByProductIdAndColorId(Integer productId, Integer colorId);

    List<ColorResponse> findColorsByProductId(Integer productId);

    Integer addViewProduct(int productId);

    String createBillClient(CreateBillClientRequest request);

    Page<CartResponse> getAllCartCustomerId(Integer customerId, Integer page, Integer size);

    CartResponse addProductToCart(AddCart addCart);

    String deleteCartById(Integer cartDetailId);

    List<CartResponse> getAllByCustomserIdNopage(Integer customerId);

    //    tìm voucher valid
    VoucherBestResponse voucherBest(String customerId, String billValue);

    List<Voucher> findValidVouchers(String customerId);

    Integer plus(Integer id);

    Integer subtract(Integer id);

    Integer setQuantityCart(Integer id,Integer quantity);

    List <RealPriceResponse> getRealPrice(List<AddCart> addCartList);

    Optional<Object> findAdressDefaulCustomerId(Integer customerId);

    SearchStatusBillResponse searchBill (String billCode);

    String veritifyBill(String billCode);

    ApiResponse<List<SearchStatusBillResponse>> getAllBillOfCustomerid(Integer customerId, Integer page, Integer size);

    String cancelBill(Integer id, String description);

}
