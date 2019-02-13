package com.mycena.utils.calculator.entity;

import java.time.YearMonth;
import java.util.Calendar;
import java.util.TimeZone;

public class FormattedDate {
    public int year;
    public int month;
    public int day;

    public FormattedDate(Long time) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        calendar.setTimeInMillis(time);

        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONDAY) + 1;
        this.day = calendar.get(Calendar.DATE);
    }

    public FormattedDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public FormattedDate(FormattedDate formattedDate) {
        this.year = formattedDate.year;
        this.month = formattedDate.month;
        this.day = formattedDate.day;
    }

    public Long convertToLong() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        calendar.set(year, month, day, 23, 0);
        return calendar.getTimeInMillis();
    }

    public FormattedDate getYesterday() {
        int tempYear = year;
        int tempMonth = month;
        int tempDay = day;
        tempDay -= 1;

        if (tempDay == 0) {
            tempMonth -= 1;
            if (tempMonth == 0) {
                tempYear -= 1;
                tempMonth = 12;
            }
            tempDay = YearMonth.of(tempYear, tempMonth).lengthOfMonth();
        }

        return new FormattedDate(tempYear, tempMonth, tempDay);
    }

    public FormattedDate getTomorrow() {
        int tempYear = year;
        int tempMonth = month;
        int tempDay = day;
        tempDay += 1;

        if (tempDay > YearMonth.of(tempYear, tempMonth).lengthOfMonth()) {
            tempMonth += 1;
            if (tempMonth > 12) {
                tempYear += 1;
                tempMonth = 1;
            }
        }

        return new FormattedDate(tempYear, tempMonth, tempDay);
    }

    @Override
    public String toString() {
        return year + "/" + month + "/" + day;
    }

    public FormattedDate getNextYearDate() {
        return new FormattedDate(year + 1, month,day);
    }

    public FormattedDate getEndDateOfOneYear()
    {
        int tempYear = year;
        int tempMonth = month;
        int tempDay = day;
        tempYear += 1;
        tempDay -= 1;

        if(tempDay == 0)
        {
            tempMonth -= 1;
            if(tempMonth == 0)
            {
                tempYear -= 1;
                tempMonth = 12;
                tempDay = 31;
                return new FormattedDate(tempYear, tempMonth, tempDay);
            }
            YearMonth yearMonthObject = YearMonth.of(tempYear, tempMonth);
            tempDay = yearMonthObject.lengthOfMonth();
        }

        return new FormattedDate(tempYear, tempMonth, tempDay);
    }

    public FormattedDate getAfterSixMonthDate() {
        int tempYear = year;
        int tempMonth = month;
        int tempDay = day;
        tempMonth += 6;
        tempDay -= 1;

        if (tempDay == 0)
        {
            tempMonth -= 1;
            if(tempMonth==0)
                tempYear -= 1;
        }

        if (tempMonth > 12) {
            tempYear += 1;
            tempMonth -= 12;
        }

        YearMonth yearMonthObject = YearMonth.of(tempYear, tempMonth);

        if (yearMonthObject.lengthOfMonth() < tempDay) {
            tempDay = 1;
            tempMonth += 1;
        }

        return new FormattedDate(tempYear, tempMonth, tempDay);
    }

    public long toMillisecond() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        cal.set(year, month, day);
        return cal.getTimeInMillis();
    }

}
