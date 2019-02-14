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

    public Long convertToLongAndSetToTheEndMillisecond() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        calendar.set(year, month - 1, day, 23, 59, 59);
        return calendar.getTimeInMillis();
    }

    public Long convertToLongAndSetToTheBeginMillisecond() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        calendar.set(year, month - 1, day, 0, 0, 0);
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
        FormattedDate temp = new FormattedDate(year + 1, month, day);
        return checkDayOfTheMonth(temp);
    }

    public FormattedDate getPreviousYearDate() {
        FormattedDate temp = new FormattedDate(year - 1, month, day);
        return checkDayOfTheMonth(temp);
    }

    private FormattedDate checkDayOfTheMonth(FormattedDate temp) {
        int maxDay = YearMonth.of(temp.year, temp.month).lengthOfMonth();
        if (day > maxDay) {
            temp.month += 1;
            temp.day = day - maxDay;
        }
        return temp;
    }

    public FormattedDate getEndDateOfOneYear()
    {
        int tempYear = year;
        int tempMonth = month;
        int tempDay = day;
        tempYear += 1;

        return checkDayOfTheMonth(new FormattedDate(tempYear, tempMonth, tempDay));
    }

    public FormattedDate getAfterSixMonthDate() {
        int tempYear = year;
        int tempMonth = month;
        int tempDay = day;
        tempMonth += 6;

        if (tempMonth > 12) {
            tempYear += 1;
            tempMonth -= 12;
        }

        YearMonth yearMonthObject = YearMonth.of(tempYear, tempMonth);

        return checkDayOfTheMonth(new FormattedDate(tempYear, tempMonth, tempDay));
    }

    public FormattedDate getNextAimMonthAndDay(FormattedDate aimDate) {
        int tempYear = this.year;
        if ((aimDate.month < this.month) || (aimDate.month == this.month && aimDate.day < this.day))
            tempYear += 1;
        return checkDayOfTheMonth(new FormattedDate(tempYear, aimDate.month, aimDate.day));
    }

    public FormattedDate setYear(int year) {
        this.year = year;
        return this;
    }

    public FormattedDate setMonth(int month) {
        this.month = month;
        return this;
    }

    public FormattedDate setDay(int day) {
        this.day = day;
        return this;
    }

}
