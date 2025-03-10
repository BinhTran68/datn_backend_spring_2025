package com.poly.app.domain.admin.Statistical.Repository;

import com.poly.app.domain.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrowthRateRepository extends JpaRepository<Bill, Integer> {
    //tốc độ thoe năm
    @Query(value = """
    WITH revenue_data AS (
        SELECT 
            YEAR(ship_date) AS year,
            COALESCE(SUM(total_money), 0) AS revenue
        FROM bill
        GROUP BY YEAR(ship_date)
    )
    SELECT 
        rd.year AS year,
        rd.revenue AS revenue,
        COALESCE(ld.revenue, 0) AS last_year_revenue,
        rd.revenue - COALESCE(ld.revenue, 0) AS difference,
        CASE 
            WHEN COALESCE(ld.revenue, 0) = 0 THEN '100%'
            ELSE CONCAT(ROUND(((rd.revenue - ld.revenue) / NULLIF(ld.revenue, 0)) * 100, 2), '%')
        END AS percentage_change
    FROM revenue_data rd
    LEFT JOIN revenue_data ld ON rd.year = ld.year + 1
    ORDER BY rd.year DESC
    LIMIT 1;
    """, nativeQuery = true)
    List<Object[]> getGroethRateYear();
    //Tốc độ theo tháng
    @Query(value = """
    WITH revenue_data AS (
        SELECT 
            YEAR(ship_date) AS year,
            MONTH(ship_date) AS month,
            COALESCE(SUM(total_money), 0) AS revenue
        FROM bill
        GROUP BY YEAR(ship_date), MONTH(ship_date)
    )
    SELECT 
        rd.year,
        rd.month,
        rd.revenue,
        COALESCE(ld.revenue, 0) AS last_month_revenue,
        rd.revenue - COALESCE(ld.revenue, 0) AS difference,
        CASE 
            WHEN COALESCE(ld.revenue, 0) = 0 THEN '100%'
            ELSE CONCAT(ROUND(((rd.revenue - ld.revenue) / ld.revenue) * 100, 2), '%')
        END AS percentage_change
    FROM revenue_data rd
    LEFT JOIN revenue_data ld 
        ON (rd.year = ld.year AND rd.month = ld.month + 1)  -- So sánh với tháng trước cùng năm
        OR (rd.year = ld.year + 1 AND rd.month = 1 AND ld.month = 12) -- So sánh tháng 1 với tháng 12 năm trước
    ORDER BY rd.year DESC, rd.month DESC
    LIMIT 1;
    """, nativeQuery = true)
    List<Object[]> getGrowthRateMonth();


    //sản phẩm tháng
    @Query(value = """
    WITH monthly_sales AS (
        SELECT 
            YEAR(ship_date) AS year,
            MONTH(ship_date) AS month,
            SUM(quantity) AS total_sold
        FROM bill
        GROUP BY YEAR(ship_date), MONTH(ship_date)
    )
    SELECT 
        ms.year,
        ms.month,
        ms.total_sold AS monthly_sold,
        COALESCE(prev.total_sold, 0) AS last_month_sold,
        ms.total_sold - COALESCE(prev.total_sold, 0) AS month_difference,
        CASE 
            WHEN COALESCE(prev.total_sold, 0) = 0 THEN '100%'
            ELSE CONCAT(ROUND(((ms.total_sold - prev.total_sold) / NULLIF(prev.total_sold, 0)) * 100, 2), '%')
        END AS month_percentage_change
    FROM monthly_sales ms
    LEFT JOIN monthly_sales prev 
        ON ms.year = prev.year AND ms.month = prev.month + 1
        OR (ms.month = 1 AND prev.month = 12 AND ms.year = prev.year + 1)
    ORDER BY ms.year DESC, ms.month DESC
    LIMIT 12;
    """, nativeQuery = true)
    List<Object[]> getSSProductMonth();

    //sản phẩm nămnăm
    @Query(value = """
    WITH yearly_sales AS (
        SELECT 
            YEAR(ship_date) AS year,
            SUM(quantity) AS total_sold
        FROM bill
        GROUP BY YEAR(ship_date)
    )
    SELECT 
        ys.year,
        ys.total_sold AS yearly_sold,
        COALESCE(prev.total_sold, 0) AS last_year_sold,
        ys.total_sold - COALESCE(prev.total_sold, 0) AS year_difference,
        CASE 
            WHEN COALESCE(prev.total_sold, 0) = 0 THEN '100%'
            ELSE CONCAT(ROUND(((ys.total_sold - prev.total_sold) / NULLIF(prev.total_sold, 0)) * 100, 2), '%')
        END AS year_percentage_change
    FROM yearly_sales ys
    LEFT JOIN yearly_sales prev ON ys.year = prev.year + 1
    ORDER BY ys.year DESC
    LIMIT 5;
    """, nativeQuery = true)
    List<Object[]> getSSProductYear();

}
