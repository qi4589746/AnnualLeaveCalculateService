package com.mycena.utils.calculator.service;

import com.mycena.utils.calculator.entity.FormattedDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;

@Service
public class AnnualLeaveCalculator {
    @Autowired
    AnnualLeaveUtil annualLeaveUtil;

    public int getTotalLeaveNum(FormattedDate onBoardDate, FormattedDate calculateDate) {
        float totalLeaveDay = 0.0f;

        FormattedDate seniority1 = getSeniority(onBoardDate, calculateDate);
        float leaveNum1 = annualLeaveUtil.getLeaveDays(seniority1);


        FormattedDate calculateDate2 = calculateDate.getEndDateOfOneYear();
        FormattedDate seniority2 = getSeniority(onBoardDate, calculateDate2);
        float leaveNum2 = annualLeaveUtil.getLeaveDays(seniority2);

        //處理6個月例外
        if (leaveNum1 == 0) {
            FormattedDate sixSeniorityDate = onBoardDate.getAfterSixMonthDate();
            if (leaveNum2 == 7) {
                float partOneWorkRate = getGeneralFirstPartWorkRate(onBoardDate, calculateDate);

                totalLeaveDay = 3 + (leaveNum2 * (1 - partOneWorkRate));

            } else if (leaveNum2 == 3) {
                float denominator;
                FormattedDate sixMonthInterval = getSeniority(sixSeniorityDate, calculateDate2);

                if (sixSeniorityDate.day < calculateDate2.day)
                    denominator = YearMonth.of(calculateDate2.year, calculateDate2.month).lengthOfMonth();
                else {
                    if (calculateDate2.month - 1 == 0)
                        denominator = YearMonth.of(calculateDate2.year - 1, 12).lengthOfMonth();
                    else
                        denominator = YearMonth.of(calculateDate2.year, calculateDate2.month - 1).lengthOfMonth();
                }

                totalLeaveDay = ((sixMonthInterval.month + (sixMonthInterval.day) / denominator) / 6) * 3;
            }
        } else {
            float partOneWorkRate = getGeneralFirstPartWorkRate(onBoardDate, calculateDate);
            totalLeaveDay = (partOneWorkRate * leaveNum1) + (leaveNum2 * (1 - partOneWorkRate));
        }

        return annualLeaveUtil.convertFloatToMinute(totalLeaveDay);

    }

    private FormattedDate getSeniority(FormattedDate onBoardDate, FormattedDate calculateDate) {
        int year = getDiffYears(onBoardDate.year, calculateDate.year);
        int month = getDiffMonths(onBoardDate.month, calculateDate.month);
        int day = getDiffDays(onBoardDate.day, calculateDate.day);
        int usedYear = calculateDate.year;
        int usedMonth = calculateDate.month;

        if (day < 0) {

            if (usedMonth - 1 == 0) {
                usedMonth = 13;
                usedYear -= 1;
            }

            YearMonth yearMonthObject = YearMonth.of(usedYear, usedMonth - 1);
            day = yearMonthObject.lengthOfMonth() + day;
            month -= 1;
        }

        if (month < 0) {
            month = month + 12;
            year -= 1;
        }

        return new FormattedDate(year, month, day);
    }

    private float getGeneralFirstPartWorkRate(FormattedDate onBoardDate, FormattedDate calculateDate) {
        FormattedDate tempOnBoardDate = new FormattedDate(onBoardDate);
        int denominator;
        FormattedDate seniority;
        if (tempOnBoardDate.month > calculateDate.month) {
            tempOnBoardDate.year = calculateDate.year;
        } else {
            tempOnBoardDate.year = calculateDate.year + 1;
        }

        seniority = getSeniority(calculateDate, tempOnBoardDate);

        calculateDate.month += seniority.month;
        if (calculateDate.month > 12) {
            calculateDate.year += 1;
            calculateDate.month -= 12;
        }

        denominator = YearMonth.of(calculateDate.year, calculateDate.month).lengthOfMonth();
        return ((float) seniority.month + ((float) seniority.day / (float) denominator)) / 12;
    }

    private int getDiffYears(int startYear, int endYear) {
        return endYear - startYear;
    }

    private int getDiffMonths(int startMonth, int endMonth) {
        return endMonth - startMonth;
    }

    private int getDiffDays(int startDay, int endDay) {
        return endDay - startDay;
    }


}
