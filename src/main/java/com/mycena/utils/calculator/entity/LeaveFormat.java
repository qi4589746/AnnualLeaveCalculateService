package com.mycena.utils.calculator.entity;

public class LeaveFormat {
    public int day;
    public int hour;
    public int minute;

    public LeaveFormat(int day, int hour, int minute) {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
    }

    @Override
    public String toString() {
        return day + "天 " + hour + " 時" + minute + " 分";
    }
}
