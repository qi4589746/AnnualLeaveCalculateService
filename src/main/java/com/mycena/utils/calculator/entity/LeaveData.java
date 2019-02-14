package com.mycena.utils.calculator.entity;

public class LeaveData {

    long activeDateTime;
    long expireDateTime;
    int totalMinute;

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
}
