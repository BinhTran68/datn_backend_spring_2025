package com.poly.app.domain.admin.Statistical.Service;

import com.poly.app.domain.admin.Statistical.Repository.GrowthRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GrowthRateService {
    @Autowired
    private GrowthRateRepository growthRateRepository;

    public List<GrowthRateDTOY> getGroethRateYear() {
        List<Object[]> rawData = growthRateRepository.getGroethRateYear();
        List<GrowthRateDTOY> resulty = new ArrayList<>();

        for (Object[] row : rawData) {
            int year = (row[0] != null) ? ((Number) row[0]).intValue() : 0;
            double revenue = (row[1] != null) ? ((Number) row[1]).doubleValue() : 0.0;
            double lastYearRevenue = (row[2] != null) ? ((Number) row[2]).doubleValue() : 0.0;
            double difference = (row[3] != null) ? ((Number) row[3]).doubleValue() : 0.0;
            String percentageChange = (row[4] != null) ? row[4].toString() : "";

            resulty.add(new GrowthRateDTOY(year, revenue, lastYearRevenue, difference, percentageChange));
        }
        return resulty;
    }


    public List<GrowthRateDTOM> getGrowthRateMonth() {
        List<Object[]> rawData = growthRateRepository.getGrowthRateMonth();
        List<GrowthRateDTOM> result = new ArrayList<>();

        for (Object[] row : rawData) {
            int year = (row[0] != null) ? ((Number) row[0]).intValue() : 0;
            int month = (row[1] != null) ? ((Number) row[1]).intValue() : 0;
            double revenue = (row[2] != null) ? ((Number) row[2]).doubleValue() : 0.0;
            double lastMonthRevenue = (row[3] != null) ? ((Number) row[3]).doubleValue() : 0.0;
            double difference = (row[4] != null) ? ((Number) row[4]).doubleValue() : 0.0;
            String percentageChange = (row[5] != null) ? row[5].toString() : "";

            result.add(new GrowthRateDTOM(year, month, revenue, lastMonthRevenue, difference, percentageChange));
        }
        return result;
    }



}
