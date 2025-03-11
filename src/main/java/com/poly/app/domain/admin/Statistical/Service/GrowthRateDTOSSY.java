package com.poly.app.domain.admin.Statistical.Service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GrowthRateDTOSSY {
        private int year;
        private int yearlySold;
        private int lastYearSold;
        private int yearDifference;
        private String yearPercentageChange;

        public GrowthRateDTOSSY(int year, int yearlySold, int lastYearSold, int yearDifference, String yearPercentageChange) {
            this.year = year;
            this.yearlySold = yearlySold;
            this.lastYearSold = lastYearSold;
            this.yearDifference = yearDifference;
            this.yearPercentageChange = yearPercentageChange;
        }

        // Getters và Setters
        public int getYear() { return year; }
        public void setYear(int year) { this.year = year; }

        public int getYearlySold() { return yearlySold; }
        public void setYearlySold(int yearlySold) { this.yearlySold = yearlySold; }

        public int getLastYearSold() { return lastYearSold; }
        public void setLastYearSold(int lastYearSold) { this.lastYearSold = lastYearSold; }

        public int getYearDifference() { return yearDifference; }
        public void setYearDifference(int yearDifference) { this.yearDifference = yearDifference; }

        public String getYearPercentageChange() { return yearPercentageChange; }
        public void setYearPercentageChange(String yearPercentageChange) { this.yearPercentageChange = yearPercentageChange; }


}
