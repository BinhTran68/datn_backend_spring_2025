package com.poly.app.domain.admin.Statistical.Service;

import com.poly.app.domain.admin.Statistical.Repository.StatisticalRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatisticalService {
    @Autowired
    StatisticalRepository statisticalRepository;
//Doanh thu
    private List<NameData> mapToSummaryDTO(List<Object[]> results) {
        return results.stream().map(row -> new NameData(
                row[0].toString(),
                ((Number) row[1]).doubleValue(),
                ((Number) row[2]).intValue(),
                ((Number) row[3]).intValue(),
                ((Number) row[4]).intValue(),
                ((Number) row[5]).intValue(),
                ((Number) row[6]).intValue()
        )).collect(Collectors.toList());
    }

    public List<NameData> getSumDay() {
        return mapToSummaryDTO(statisticalRepository.getSumDay());
    }

    public List<NameData> getSumWeek() {
        return mapToSummaryDTO(statisticalRepository.getSumWeek());
    }

    public List<NameData> getSumMonth() {
        return mapToSummaryDTO(statisticalRepository.getSumMonth());
    }

    public List<NameData> getSumYear() {
        return mapToSummaryDTO(statisticalRepository.getSumYear());
    }

    public List<NameData> getSumByCustomDate(String startDate, String endDate) {
        return mapToSummaryDTO(statisticalRepository.getSumByCustomDate(startDate, endDate));
    }

    //Sản phẩm bán chạy
    public List<Object[]> getBestSellingProduct(int page, int size) {
        int offset = page * size; // Tính toán offset để phân trang
        return statisticalRepository.findBestSellingProduct(offset, size);
    }
 }



