package com.poly.app.domain.admin.Statistical.Controller;

import com.poly.app.domain.admin.Statistical.Repository.GrowthRateProductRepository;
import com.poly.app.domain.admin.Statistical.Repository.StatisticalRepository;
import com.poly.app.domain.admin.Statistical.Service.*;
import com.poly.app.domain.common.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/admin/statistical")
// 1. Thống kê số lượng hóa đơn đã đặt, đã thanh toán, đã hủy, đã giao hàng
public class StatisticalController {
    @Autowired
    GrowthRateProductRepository repo;

    @Autowired
    StatisticalRepository statisticalRepository;
    @Autowired
    StatisticalService service;
    @Autowired
    BestSaleServie bestSaleService;
    @Autowired
    ChartService chartService;

    @Autowired
    MinProductService minProductService;
    @Autowired
    GrowthRateService growthRateService;

    @GetMapping("/Sum")
    public ApiResponse<Double> SumBill() {
        return ApiResponse.<Double>builder()
                .data(statisticalRepository.SumBill().getTotalMoney())

                .build();
    }

    @GetMapping("/Day")
    public ApiResponse<List<NameData>> getDay() {
        return ApiResponse.<List<NameData>>builder()
                .data(service.getSumDay())
                .build();
    }

    @GetMapping("/Week")
    public ApiResponse<List<NameData>> getWeek() {
        return ApiResponse.<List<NameData>>builder()
                .data(service.getSumWeek())
                .build();
    }

    @GetMapping("/Month")
    public ApiResponse<List<NameData>> getMonth() {
        return ApiResponse.<List<NameData>>builder()
                .data(service.getSumMonth())
                .build();
    }

    @GetMapping("/Year")
    public ApiResponse<List<NameData>> getYear() {
        return ApiResponse.<List<NameData>>builder()
                .data(service.getSumYear())
                .build();
    }

    @GetMapping("/CustomDate")
    public ApiResponse<List<NameData>> getCustom(@RequestParam String startDate, @RequestParam String endDate) {
        return ApiResponse.<List<NameData>>builder()
                .data(service.getSumByCustomDate(startDate, endDate))
                .build();
    }
    //mail doanh thu ngày
    @PostMapping("/send-daily-report-email")
    public ApiResponse<String> sendDailyReportEmail() {
        try {
            service.sendDailyRevenueReportEmail();
            System.out.println("báo cáo ngày đã về rồi đây");
            return ApiResponse.<String>builder()
                    .data("Báo cáo doanh thu ngày đã được gửi thành công")
                    .build();
        } catch (Exception e) {
            return ApiResponse.<String>builder()
                    .build();
        }
    }

    //2.Sản phẩm bán chạy
    @GetMapping("/bestsellers")
    public ApiResponse<List<Object[]>> getBestSellingProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        List<Object[]> result = service.getBestSellingProduct(page, size);

        return ApiResponse.<List<Object[]>>builder()
                .message("Lấy danh sách sản phẩm bán chạy thành công") // Thêm thông báo
                .data(result)
                .build();
    }


    @GetMapping("/bestday")
    public ApiResponse<List<BestSaleDTO>> getBestDay() {
        return ApiResponse.<List<BestSaleDTO>>builder()
                .data(bestSaleService.getTopSellingProductToday())
                .build();
    }

    @GetMapping("/bestweek")
    public ApiResponse<List<BestSaleDTO>> getBestWeek() {
        return ApiResponse.<List<BestSaleDTO>>builder()
                .data(bestSaleService.getTopSellingProductThisWeek())
                .build();
    }

    @GetMapping("/bestmonth")
    public ApiResponse<List<BestSaleDTO>> getBestMonth() {
        return ApiResponse.<List<BestSaleDTO>>builder()
                .data(bestSaleService.getTopSellingProductThisMonth())
                .build();
    }

    // ✅ Lấy top 5 sản phẩm bán chạy nhất trong năm
    @GetMapping("/bestyear")
    public ApiResponse<List<BestSaleDTO>> getBestYear() {
        return ApiResponse.<List<BestSaleDTO>>builder()
                .data(bestSaleService.getTopSellingProductThisYear())
                .build();
    }

    // ✅ Lấy top 5 sản phẩm bán chạy theo khoảng ngày tùy chỉnh
    @GetMapping("/best-custom")
    public ApiResponse<List<BestSaleDTO>> getBestCustom(@RequestParam String startDate, @RequestParam String endDate) {
        return ApiResponse.<List<BestSaleDTO>>builder()
                .data(bestSaleService.getTopSellingProductByCustomDateRange(startDate, endDate))
                .build();
    }
    //Biểu đồ Chart

    @GetMapping("/chartYear")
    public ApiResponse<ChartDTO> getBillStatusThisYear() {
        return ApiResponse.<ChartDTO>builder()
                .data(chartService.getChartThisYear())
                .build();
    }

    @GetMapping("/chartDay")
    public ApiResponse<ChartDTO> getBillStatusThisDay() {
        return ApiResponse.<ChartDTO>builder()
                .data(chartService.getTodayChartData())
                .build();
    }

    @GetMapping("/chartWeek")
    public ApiResponse<ChartDTO> getBillStatusThisWeek() {
        return ApiResponse.<ChartDTO>builder()
                .data(chartService.getChartThisWeek())
                .build();
    }

    @GetMapping("/chartMonth")
    public ApiResponse<ChartDTO> getBillStatusThisMonth() {
        return ApiResponse.<ChartDTO>builder()
                .data(chartService.getChartThisMonth())
                .build();
    }

    @GetMapping("/chartCustom")
    public ApiResponse<ChartDTO> getBillStatusCustom(@RequestParam String startDate, @RequestParam String endDate) {

        ChartDTO chartData = chartService.getChartCustom(startDate, endDate);
        if (chartData == null) {
            chartData = new ChartDTO(0, 0, 0, 0, 0, 0, 0);
        }
        return ApiResponse.<ChartDTO>builder()
                .data(chartData)
                .message("Custom chart data retrieved successfully")
                .build();
    }
    //min so luong
    @GetMapping("/minProduct")
    public ApiResponse<List<MinProductDTO>> getLowStockProducts(@RequestParam(defaultValue = "100") int quantity) {
        List<MinProductDTO> products = minProductService.getProductsWithLowQuantity(quantity);

        return ApiResponse.<List<MinProductDTO>>builder()
                .data(products)
                .build();
    }
//tốc độ phát triển doanh thu
    @GetMapping("/growthRateYear")
    public ApiResponse<List<GrowthRateDTOY>> getGroethRateYear() {
        try {
            List<GrowthRateDTOY> data = growthRateService.getGroethRateYear();
            return ApiResponse.<List<GrowthRateDTOY>>builder()
                    .data(data)
                    .message(data.isEmpty() ? "Không có dữ liệu" : "Lấy dữ liệu thành công")
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<GrowthRateDTOY>>builder()
                    .data(null)
                    .message("Lỗi khi lấy dữ liệu: " + e.getMessage())
                    .build();
        }
    }
    @GetMapping("/growthRateMonth")
    public ApiResponse<List<GrowthRateDTOM>> getGrowthRateMonth() {
        try {
            List<GrowthRateDTOM> data = growthRateService.getGrowthRateMonth();
            return ApiResponse.<List<GrowthRateDTOM>>builder()
                    .data(data)
                    .message(data.isEmpty() ? "Không có dữ liệu" : "Lấy dữ liệu thành công")
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<GrowthRateDTOM>>builder()
                    .data(null)
                    .message("Lỗi khi lấy dữ liệu: " + e.getMessage())
                    .build();
        }
    }

    // sản phẩm
    @GetMapping("/growthRateProductM")
    public ApiResponse<List<GrowthRateDTOSSM>> getSSProductMonth() {
        try {
            List<GrowthRateDTOSSM> data = growthRateService.getSSProductMonth();
            return ApiResponse.<List<GrowthRateDTOSSM>>builder()
                    .data(data)
                    .message(data.isEmpty() ? "Không có dữ liệu" : "Lấy dữ liệu thành công")
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<GrowthRateDTOSSM>>builder()
                    .data(null)
                    .message("Lỗi khi lấy dữ liệu: " + e.getMessage())
                    .build();
        }
    }
    @GetMapping("/growthRateProductY")
    public ApiResponse<List<GrowthRateDTOSSY>> getSSProductYear() {
        try {
            List<GrowthRateDTOSSY> data = growthRateService.getSSProductYear();
            return ApiResponse.<List<GrowthRateDTOSSY>>builder()
                    .data(data)
                    .message(data.isEmpty() ? "Không có dữ liệu" : "Lấy dữ liệu thành công")
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<GrowthRateDTOSSY>>builder()
                    .data(null)
                    .message("Lỗi khi lấy dữ liệu: " + e.getMessage())
                    .build();
        }
    }

}


