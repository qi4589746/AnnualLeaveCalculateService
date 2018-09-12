package com.mycena.utils.calculator.entity;


import com.mycena.utils.calculator.service.AnnualLeaveUtil;
import org.springframework.beans.factory.annotation.Autowired;

public class LeaveData {
    @Autowired
    AnnualLeaveUtil annualLeaveUtil;

    public FormattedDate beginDate;
    public FormattedDate endDate;
    public FormattedDate midDate;
    public int onePartLeave;
    public int twoPartLeave;
    public float totalLeaveNum;


    public LeaveData(FormattedDate beginDate, FormattedDate endDate, FormattedDate midDate, int onePartLeave, int twoPartLeave, float totalLeaveNum) {
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.midDate = midDate;
        this.onePartLeave = onePartLeave;
        this.twoPartLeave = twoPartLeave;
        this.totalLeaveNum = totalLeaveNum;
    }

    @Override
    public String toString() {

        String out;
        if (midDate == null) {
            out = "總共: " + totalLeaveNum + "\n" +
                    beginDate.toString() + " ~ " + endDate.toString() +
                    "\n" + annualLeaveUtil.convertFloatToLeaveFormat(totalLeaveNum).toString();
        } else {
            out = "總共: " + totalLeaveNum + "\n" +
                    "\n\n" + beginDate.toString() + " ~ " + midDate.toString() +
                    "\n" + onePartLeave +
                    "\n\n" + midDate.getTomorrow().toString() + " ~ " + endDate.toString() +
                    "\n" + twoPartLeave
            ;

        }

        return out;
    }
}
