package com.mycena.utils.calculator.service;


import com.mycena.utils.calculator.entity.FormattedDate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AnnualLeaveUtil {

    static final int minuteOfDay = 1440;

    public int getLeaveDays(FormattedDate seniority) {
        if(seniority.year == 0 && seniority.month >= 6)
            return 3;

        if(seniority.year >= 10){
//            if(seniority.month == 0 && seniority.day == 0)
//                return 15f;
//            float leaveNum = 15 + seniority.year - 10 + seniority.month / 12.0f + seniority.day / 30f;
            int leaveNum = 15 + seniority.year - 10;
            if (leaveNum > 30)
                return 30;
            return leaveNum;
        }

        if(seniority.year >=5)
            return 15;

        if(seniority.year >=3)
            return 14;

        switch (seniority.year) {
            case 1:
                return 7;
            case 2:
                return 10;
            case 0:
                if(seniority.month >= 6)
                    return 3;
        }
        return 0;
    }

    public int convertFloatToMinute(float totalLeaveDay) {
        BigDecimal bigDecimal = new BigDecimal(totalLeaveDay);
        totalLeaveDay = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
        return (int) (totalLeaveDay * minuteOfDay);
    }

}
