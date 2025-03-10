package com.poly.app.domain.admin.Statistical.Repository;

import com.poly.app.domain.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface ChartRepository extends JpaRepository<Bill, Integer> {
    //Ngày
    @Query(value = """
                SELECT 
                    SUM(CASE WHEN status = 'CHO_XAC_NHAN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS choXacNhanPercent,
                    SUM(CASE WHEN status = 'DA_XAC_NHAN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS daXacNhanPercent,
                    SUM(CASE WHEN status = 'CHO_VAN_CHUYEN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS choVanChuyenPercent,
                    SUM(CASE WHEN status = 'DANG_VAN_CHUYEN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS dangVanChuyenPercent,
                    SUM(CASE WHEN status = 'DA_THANH_TOAN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS daThanhToanPercent,
                    SUM(CASE WHEN status = 'DA_HOAN_THANH' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS daHoanThanhPercent,
                    SUM(CASE WHEN status = 'DA_HUY' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS daHuyPercent,
                    SUM(CASE WHEN status = 'TRA_HANG' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS traHangPercent
                FROM bill
                WHERE DATE(FROM_UNIXTIME(created_at / 1000)) = CURDATE()
            """, nativeQuery = true)
    List<Object[]> getChartToday();

    //Tuần
    @Query(value = """
                SELECT 
                    SUM(CASE WHEN status = 'CHO_XAC_NHAN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS choXacNhanPercent,
                    SUM(CASE WHEN status = 'DA_XAC_NHAN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS daXacNhanPercent,
                    SUM(CASE WHEN status = 'CHO_VAN_CHUYEN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS choVanChuyenPercent,
                    SUM(CASE WHEN status = 'DANG_VAN_CHUYEN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS dangVanChuyenPercent,
                    SUM(CASE WHEN status = 'DA_THANH_TOAN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS daThanhToanPercent,
                    SUM(CASE WHEN status = 'DA_HOAN_THANH' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS daHoanThanhPercent,
                    SUM(CASE WHEN status = 'DA_HUY' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS daHuyPercent,
                    SUM(CASE WHEN status = 'TRA_HANG' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS traHangPercent
                FROM bill
                WHERE YEARWEEK(FROM_UNIXTIME(created_at / 1000), 1) = YEARWEEK(CURDATE(), 1)
            """, nativeQuery = true)
    List<Object[]> getChartThisWeek();

    //Tháng
    @Query(value = """
                SELECT 
                    SUM(CASE WHEN status = 'CHO_XAC_NHAN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS choXacNhanPercent,
                    SUM(CASE WHEN status = 'DA_XAC_NHAN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS daXacNhanPercent,
                    SUM(CASE WHEN status = 'CHO_VAN_CHUYEN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS choVanChuyenPercent,
                    SUM(CASE WHEN status = 'DANG_VAN_CHUYEN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS dangVanChuyenPercent,
                    SUM(CASE WHEN status = 'DA_THANH_TOAN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS daThanhToanPercent,
                    SUM(CASE WHEN status = 'DA_HOAN_THANH' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS daHoanThanhPercent,
                    SUM(CASE WHEN status = 'DA_HUY' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS daHuyPercent,
                    SUM(CASE WHEN status = 'TRA_HANG' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS traHangPercent
                FROM bill
                WHERE MONTH(FROM_UNIXTIME(created_at / 1000)) = MONTH(CURDATE()) 
                AND YEAR(FROM_UNIXTIME(created_at / 1000)) = YEAR(CURDATE())
            """, nativeQuery = true)
    List<Object[]> getChartThisMonth();

    //Năm
    @Query(value = """
                SELECT 
                    SUM(CASE WHEN status = 'CHO_XAC_NHAN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS choXacNhanPercent,
                    SUM(CASE WHEN status = 'DA_XAC_NHAN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS daXacNhanPercent,
                    SUM(CASE WHEN status = 'CHO_VAN_CHUYEN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS choVanChuyenPercent,
                    SUM(CASE WHEN status = 'DANG_VAN_CHUYEN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS dangVanChuyenPercent,
                    SUM(CASE WHEN status = 'DA_THANH_TOAN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS daThanhToanPercent,
                    SUM(CASE WHEN status = 'DA_HOAN_THANH' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS daHoanThanhPercent,
                    SUM(CASE WHEN status = 'DA_HUY' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS daHuyPercent,
                    SUM(CASE WHEN status = 'TRA_HANG' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS traHangPercent
                FROM bill
                WHERE YEAR(FROM_UNIXTIME(created_at / 1000)) = YEAR(CURDATE())
            """, nativeQuery = true)
    List<Object[]> getChartThisYear();
//Tùy chỉnh
    @Query(value = """
                SELECT 
                    SUM(CASE WHEN status = 'CHO_XAC_NHAN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS choXacNhanPercent,
                    SUM(CASE WHEN status = 'DA_XAC_NHAN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS daXacNhanPercent,
                    SUM(CASE WHEN status = 'CHO_VAN_CHUYEN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS choVanChuyenPercent,
                    SUM(CASE WHEN status = 'DANG_VAN_CHUYEN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS dangVanChuyenPercent,
                    SUM(CASE WHEN status = 'DA_THANH_TOAN' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS daThanhToanPercent,
                    SUM(CASE WHEN status = 'DA_HOAN_THANH' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS daHoanThanhPercent,
                    SUM(CASE WHEN status = 'DA_HUY' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS daHuyPercent,
                    SUM(CASE WHEN status = 'TRA_HANG' THEN 1 ELSE 0 END) * 100.0 / COUNT(*) AS traHangPercent
                FROM bill
                WHERE DATE(FROM_UNIXTIME(created_at / 1000)) BETWEEN :startDate AND :endDate
            """, nativeQuery = true)
    List<Object[]> getChartCustom(@Param("startDate") String startDate, @Param("endDate") String endDate);


}
