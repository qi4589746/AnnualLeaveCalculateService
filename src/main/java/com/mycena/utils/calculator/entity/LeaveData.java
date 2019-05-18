package com.mycena.utils.calculator.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public class LeaveData {

    long activeDateTime;
    long expireDateTime;

    @JsonIgnore
    int totalMinute;

    @JsonIgnore
    int leaveType;

    float totalDay;

    public LeaveData(long activeDateTime, long expireDateTime, float totalDay, int leaveType) {
        this.activeDateTime = activeDateTime;
        this.expireDateTime = expireDateTime;
        this.totalDay = totalDay;
        this.leaveType = leaveType;
    }

    public LeaveData(long activeDateTime, long expireDateTime, int totalMinute) {
        this.activeDateTime = activeDateTime;
        this.expireDateTime = expireDateTime;
        this.totalMinute = totalMinute;
    }

    public LeaveData() {
    }

    public long getActiveDateTime() {
        return activeDateTime;
    }

    public void setActiveDateTime(long activeDateTime) {
        this.activeDateTime = activeDateTime;
    }

    public long getExpireDateTime() {
        return expireDateTime;
    }

    public void setExpireDateTime(long expireDateTime) {
        this.expireDateTime = expireDateTime;
    }

    public int getTotalMinute() {
        return totalMinute;
    }

    public void setTotalMinute(int totalMinute) {
        this.totalMinute = totalMinute;
    }

    public float getTotalDay() {
//        return totalDay;
        BigDecimal b = new BigDecimal(totalDay);
        return b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public void setTotalDay(float totalDay) {
        this.totalDay = totalDay;
    }

    public int getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(int leaveType) {
        this.leaveType = leaveType;
    }
}
