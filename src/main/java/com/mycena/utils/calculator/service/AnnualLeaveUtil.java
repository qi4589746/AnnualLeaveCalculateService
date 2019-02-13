package com.mycena.utils.calculator.service;


import com.mycena.utils.calculator.entity.FormattedDate;
import com.mycena.utils.calculator.entity.LeaveFormat;
import org.springframework.stereotype.Service;

@Service
public class AnnualLeaveUtil {

    static final int minuteOfDay = 1440;
    static final int minuteOfHour = 60;

    public float getLeaveDays(FormattedDate seniority) {
        if(seniority.year == 0 && seniority.month >= 6)
            return 3;

        if(seniority.year >= 10){
//            System.out.println("seniority: " + seniority);
            if(seniority.month == 0 && seniority.day == 0)
                return 15f;
            float leaveNum = 15 + seniority.year - 10 + seniority.month / 12.0f + seniority.day / 30f;
            if (leaveNum > 30)
                return 30f;
            return leaveNum;
        }

        if(seniority.year >=5)
            return 15f;

        if(seniority.year >=3)
            return 14f;

        switch (seniority.year) {
            case 1:
                return 7f;
            case 2:
                return 10f;
            case 0:
                if(seniority.month >= 6)
                    return 3f;
        }
        return 0f;
    }

    public int convertFloatToMinute(float totalLeaveDay) {
        return (int) (totalLeaveDay * minuteOfDay);
    }

    public LeaveFormat convertFloatToLeaveFormat(float leaveNum) {
        int day = (int) leaveNum;

        float minute = (leaveNum - day) * minuteOfDay;

        int hour = (int) (minute / 60);

        minute = minute - (hour * 60);
        return new LeaveFormat(day, hour, (int) minute);

    }

}
