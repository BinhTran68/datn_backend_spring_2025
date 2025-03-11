package com.poly.app.domain.admin.Statistical.Service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrowthRateDTOSSM {
        private int year;
        private int month;
        private int monthlySold;
        private int lastMonthSold;
        private int monthDifference;
        private String monthPercentageChange;

        public GrowthRateDTOSSM(int year, int month, int monthlySold, int lastMonthSold, int monthDifference, String monthPercentageChange) {
            this.year = year;
            this.month = month;
            this.monthlySold = monthlySold;
            this.lastMonthSold = lastMonthSold;
            this.monthDifference = monthDifference;
            this.monthPercentageChange = monthPercentageChange;
        }

        // Getters và Setters
        public int getYear() { return year; }
        public void setYear(int year) { this.year = year; }

        public int getMonth() { return month; }
        public void setMonth(int month) { this.month = month; }

        public int getMonthlySold() { return monthlySold; }
        public void setMonthlySold(int monthlySold) { this.monthlySold = monthlySold; }

        public int getLastMonthSold() { return lastMonthSold; }
        public void setLastMonthSold(int lastMonthSold) { this.lastMonthSold = lastMonthSold; }

        public int getMonthDifference() { return monthDifference; }
        public void setMonthDifference(int monthDifference) { this.monthDifference = monthDifference; }

        public String getMonthPercentageChange() { return monthPercentageChange; }
        public void setMonthPercentageChange(String monthPercentageChange) { this.monthPercentageChange = monthPercentageChange; }
    }


