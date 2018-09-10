package com.mycena.utils.calculator.entity;

import com.mycena.util.AnnualLeaveUtil;

public class LeaveData {
    public FormattedDate beginDate;
    public FormattedDate endDate;
    public FormattedDate midDate;
    public LeaveFormat onePartLeave;
    public LeaveFormat twoPartLeave;
    public float totalLeaveNum;


    public LeaveData(FormattedDate beginDate, FormattedDate endDate, FormattedDate midDate, LeaveFormat onePartLeave, LeaveFormat twoPartLeave, float totalLeaveNum) {
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
                    "\n" + AnnualLeaveUtil.convertFloatToLeaveFormat(totalLeaveNum).toString();
        } else {
            out = "總共: " + totalLeaveNum + "\n" +
                    "\n\n" + beginDate.toString() + " ~ " + midDate.toString() +
                    "\n" + onePartLeave.toString() +
                    "\n\n" + midDate.getTomorrow().toString() + " ~ " + endDate.toString() +
                    "\n" + twoPartLeave.toString()
            ;

        }

        return out;
    }
}
