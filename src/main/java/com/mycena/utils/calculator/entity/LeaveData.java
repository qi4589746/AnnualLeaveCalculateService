package com.mycena.utils.calculator.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;

public class LeaveData {

    long activeDateTime;
    long expireDateTime;

    @JsonIgnore
    int totalMinute;

    //    @JsonIgnore
    int seniorityMonths;

    float totalDays;

    public LeaveData(long activeDateTime, long expireDateTime, float totalDays, int seniorityMonths) {
        this.activeDateTime = activeDateTime;
        this.expireDateTime = expireDateTime;
        this.totalDays = totalDays;
        this.seniorityMonths = seniorityMonths;
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

    public float getTotalDays() {
//        return totalDay;
        BigDecimal b = new BigDecimal(totalDays);
        return b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public void setTotalDays(float totalDays) {
        this.totalDays = totalDays;
    }

    public int getSeniorityMonths() {
        return seniorityMonths;
    }

    public void setSeniorityMonths(int seniorityMonths) {
        this.seniorityMonths = seniorityMonths;
    }
}
