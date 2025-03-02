package com.poly.app.domain.client.controller;

import com.poly.app.domain.admin.product.response.color.ColorResponse;
import com.poly.app.domain.admin.product.response.productdetail.ProductDetailResponse;
import com.poly.app.domain.admin.product.response.size.SizeResponse;
import com.poly.app.domain.client.response.ProductViewResponse;
import com.poly.app.domain.client.service.ClientService;
import com.poly.app.domain.common.ApiResponse;
import com.poly.app.domain.common.Meta;
import com.poly.app.domain.model.ProductDetail;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ClientController {
    ClientService clientService;

    @GetMapping("/getallproducthadpromotion")
    ApiResponse<List<ProductViewResponse>> getAllProductHadPromotion(@RequestParam(value = "page", defaultValue = "0") Integer page
            , @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        Page<ProductViewResponse> page1 = clientService.getAllProductHadPromotion(page - 1, size);

        return ApiResponse.<List<ProductViewResponse>>builder()
                .message("danh sách các sản phẩm đang nằm trong đợt giảm giá")
                .data(page1.getContent())
                .meta(Meta.builder()
                        .totalElement(page1.getTotalElements())
                        .currentPage(page1.getNumber() + 1)
                        .totalPages(page1.getTotalPages()).build())
                .build();
    }

    @GetMapping("/getallproducthadsoledesc")
    ApiResponse<List<ProductViewResponse>> getAllProductHadSoleDesc(@RequestParam(value = "page", defaultValue = "0") Integer page
            , @RequestParam(value = "size", defaultValue = "5") Integer size
    ) {
        Page<ProductViewResponse> page1 = clientService.getAllProductHadSoleDesc(page - 1, size);

        return ApiResponse.<List<ProductViewResponse>>builder()
                .message("danh sách các sản phẩm có lượt bán từ nhiều tới ít")
                .data(page1.getContent())
                .meta(Meta.builder()
                        .totalElement(page1.getTotalElements())
                        .currentPage(page1.getNumber() + 1)
                        .totalPages(page1.getTotalPages()).build())
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
                .message("danh sách các sản phẩm view cao nhất")
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
                .data(clientService.findSizesByProductIdAndColorId(productId,colorId))
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
}
