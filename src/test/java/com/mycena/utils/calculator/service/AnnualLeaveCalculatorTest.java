package com.mycena.utils.calculator.service;

import com.mycena.utils.calculator.entity.FormattedDate;
import com.mycena.utils.calculator.entity.LeaveData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AnnualLeaveCalculatorTest {
    private AnnualLeaveCalculator annualLeaveCalculator;
    private ZoneId zoneId;

    @Before
    public void setUp() throws Exception {
        annualLeaveCalculator = new AnnualLeaveCalculator();
        annualLeaveCalculator.annualLeaveUtil = new AnnualLeaveUtil();

        zoneId = ZoneId.of("Asia/Taipei");
    }

    // 同月同日
    // onBoardLocalDate: 2019/05/01
    // calculateLocalDate: 2019/05/01
    @Test
    public void test1() {
        LocalDate onBoardLocalDate = LocalDate.of(2019, 5, 1);
        LocalDate calculateLocalDate = LocalDate.of(2019, 5, 1);
        List<LeaveData> list = wrapForGetTotalLeaveNum(onBoardLocalDate, calculateLocalDate);
        print(list);

        Assert.assertEquals(1, list.size());

        LeaveData leaveData1 = list.get(0);
        Assert.assertEquals(Duration.ofDays(3).toMinutes(), leaveData1.getTotalMinute());
        Assert.assertEquals(LocalDate.of(2019, 11, 1).atTime(LocalTime.MIN).atZone(zoneId).toInstant().toEpochMilli(), leaveData1.getActiveDateTime());
        Assert.assertEquals(LocalDate.of(2020, 4, 30).atTime(LocalTime.MAX).atZone(zoneId).toInstant().toEpochMilli(), leaveData1.getExpireDateTime());
    }

    // 早四個月
    // onBoardLocalDate: 2019/05/01
    // calculateLocalDate: 2019/01/01
    @Test
    public void test2() {
        LocalDate onBoardLocalDate = LocalDate.of(2019, 5, 1);
        LocalDate calculateLocalDate = LocalDate.of(2019, 1, 1);
        List<LeaveData> list = wrapForGetTotalLeaveNum(onBoardLocalDate, calculateLocalDate);
        print(list);

        Assert.assertEquals(1, list.size());
        //TODO more assert
    }

    // 早四個月，隔年計算
    // onBoardLocalDate: 2019/05/01
    // calculateLocalDate: 2020/01/01
    @Test
    public void test3() {
        LocalDate onBoardLocalDate = LocalDate.of(2019, 5, 1);
        LocalDate calculateLocalDate = LocalDate.of(2020, 1, 1);
        List<LeaveData> list = wrapForGetTotalLeaveNum(onBoardLocalDate, calculateLocalDate);
        print(list);

        Assert.assertEquals(2, list.size());
        //TODO more assert
    }

    // 晚一個月
    // onBoardLocalDate: 2019/04/01
    // calculateLocalDate: 2019/05/01
    @Test
    public void test4() {
        LocalDate onBoardLocalDate = LocalDate.of(2019, 4, 1);
        LocalDate calculateLocalDate = LocalDate.of(2019, 5, 1);
        List<LeaveData> list = wrapForGetTotalLeaveNum(onBoardLocalDate, calculateLocalDate);
        print(list);

        Assert.assertEquals(2, list.size());

        LeaveData leaveData1 = list.get(0);
        Assert.assertEquals(Duration.ofDays(3).toMinutes(), leaveData1.getTotalMinute());
        Assert.assertEquals(LocalDate.of(2019, 10, 1).atTime(LocalTime.MIN).atZone(zoneId).toInstant().toEpochMilli(), leaveData1.getActiveDateTime());
        Assert.assertEquals(LocalDate.of(2020, 3, 31).atTime(LocalTime.MAX).atZone(zoneId).toInstant().toEpochMilli(), leaveData1.getExpireDateTime());

        LeaveData leaveData2 = list.get(1);
        Assert.assertEquals(LocalDate.of(2020, 4, 1).atTime(LocalTime.MIN).atZone(zoneId).toInstant().toEpochMilli(), leaveData2.getActiveDateTime());
        Assert.assertEquals(LocalDate.of(2020, 4, 30).atTime(LocalTime.MAX).atZone(zoneId).toInstant().toEpochMilli(), leaveData2.getExpireDateTime());
        //TODO more assert
    }

    // 晚一個月又六天
    // onBoardLocalDate: 2019/04/01
    // calculateLocalDate: 2019/05/07
    @Test
    public void test5() {
        LocalDate onBoardLocalDate = LocalDate.of(2019, 4, 1);
        LocalDate calculateLocalDate = LocalDate.of(2019, 5, 7);
        List<LeaveData> list = wrapForGetTotalLeaveNum(onBoardLocalDate, calculateLocalDate);
        print(list);

        Assert.assertEquals(2, list.size());

        LeaveData leaveData1 = list.get(0);
        Assert.assertEquals(Duration.ofDays(3).toMinutes(), leaveData1.getTotalMinute());
        Assert.assertEquals(LocalDate.of(2019, 10, 1).atTime(LocalTime.MIN).atZone(zoneId).toInstant().toEpochMilli(), leaveData1.getActiveDateTime());
        Assert.assertEquals(LocalDate.of(2020, 3, 31).atTime(LocalTime.MAX).atZone(zoneId).toInstant().toEpochMilli(), leaveData1.getExpireDateTime());

        LeaveData leaveData2 = list.get(1);
        Assert.assertEquals(LocalDate.of(2020, 4, 1).atTime(LocalTime.MIN).atZone(zoneId).toInstant().toEpochMilli(), leaveData2.getActiveDateTime());
        Assert.assertEquals(LocalDate.of(2020, 5, 6).atTime(LocalTime.MAX).atZone(zoneId).toInstant().toEpochMilli(), leaveData2.getExpireDateTime());
        //TODO more assert
    }

    // 早三天
    // onBoardLocalDate: 2019/05/01
    // calculateLocalDate: 2019/04/28
    @Test
    public void test6() {
        LocalDate onBoardLocalDate = LocalDate.of(2019, 5, 1);
        LocalDate calculateLocalDate = LocalDate.of(2019, 4, 28);
        List<LeaveData> list = wrapForGetTotalLeaveNum(onBoardLocalDate, calculateLocalDate);
        print(list);

        Assert.assertEquals(1, list.size());

        LeaveData leaveData1 = list.get(0);
        Assert.assertEquals(LocalDate.of(2019, 11, 1).atTime(LocalTime.MIN).atZone(zoneId).toInstant().toEpochMilli(), leaveData1.getActiveDateTime());
        Assert.assertEquals(LocalDate.of(2020, 4, 27).atTime(LocalTime.MAX).atZone(zoneId).toInstant().toEpochMilli(), leaveData1.getExpireDateTime());

        //TODO more assert
    }

    // 晚三天
    // onBoardLocalDate: 2019/05/01
    // calculateLocalDate: 2019/05/04
    @Test
    public void test7() {
        LocalDate onBoardLocalDate = LocalDate.of(2019, 5, 1);
        LocalDate calculateLocalDate = LocalDate.of(2019, 5, 4);
        List<LeaveData> list = wrapForGetTotalLeaveNum(onBoardLocalDate, calculateLocalDate);
        print(list);

        Assert.assertEquals(2, list.size());


        LeaveData leaveData1 = list.get(0);
        Assert.assertEquals(Duration.ofDays(3).toMinutes(), leaveData1.getTotalMinute());
        Assert.assertEquals(LocalDate.of(2019, 11, 1).atTime(LocalTime.MIN).atZone(zoneId).toInstant().toEpochMilli(), leaveData1.getActiveDateTime());
        Assert.assertEquals(LocalDate.of(2020, 4, 30).atTime(LocalTime.MAX).atZone(zoneId).toInstant().toEpochMilli(), leaveData1.getExpireDateTime());

        LeaveData leaveData2 = list.get(1);
        Assert.assertEquals(LocalDate.of(2020, 5, 1).atTime(LocalTime.MIN).atZone(zoneId).toInstant().toEpochMilli(), leaveData2.getActiveDateTime());
        Assert.assertEquals(LocalDate.of(2020, 5, 3).atTime(LocalTime.MAX).atZone(zoneId).toInstant().toEpochMilli(), leaveData2.getExpireDateTime());
        //TODO more assert
    }

    private List<LeaveData> wrapForGetTotalLeaveNum(LocalDate onBoardLocalDate, LocalDate calculateLocalDate) {
        FormattedDate onBoardDate = new FormattedDate(onBoardLocalDate.atStartOfDay(zoneId).toInstant().toEpochMilli());
        FormattedDate calculateDate = new FormattedDate(calculateLocalDate.atStartOfDay(zoneId).toInstant().toEpochMilli());

        return annualLeaveCalculator.getTotalLeaveNum(onBoardDate, calculateDate);
    }


    // 同月同日，隔年計算
    // onBoardLocalDate: 2019/05/01
    // calculateLocalDate: 2020/05/01
    @Test
    public void test8() {
        LocalDate onBoardLocalDate = LocalDate.of(2019, 5, 1);
        LocalDate calculateLocalDate = LocalDate.of(2020, 5, 1);
        List<LeaveData> list = wrapForGetTotalLeaveNum(onBoardLocalDate, calculateLocalDate);
        print(list);

        Assert.assertEquals(1, list.size());

        LeaveData leaveData1 = list.get(0);
        Assert.assertEquals(Duration.ofDays(7).toMinutes(), leaveData1.getTotalMinute());
        Assert.assertEquals(LocalDate.of(2020, 5, 1).atTime(LocalTime.MIN).atZone(zoneId).toInstant().toEpochMilli(), leaveData1.getActiveDateTime());
        Assert.assertEquals(LocalDate.of(2021, 4, 30).atTime(LocalTime.MAX).atZone(zoneId).toInstant().toEpochMilli(), leaveData1.getExpireDateTime());
    }

    private void print(List<LeaveData> list) {
        AtomicInteger count = new AtomicInteger();
        list.forEach(leaveData -> {
            System.out.println("--- part " + count.incrementAndGet() + " ---");
            System.out.println("activeDateTime = " + Instant.ofEpochMilli(leaveData.getActiveDateTime()).atZone(zoneId));
            System.out.println("expireDateTime = " + Instant.ofEpochMilli(leaveData.getExpireDateTime()).atZone(zoneId));
            System.out.println("totalMinutes = " + Duration.ofMinutes(leaveData.getTotalMinute()));
        });

        System.out.println("=== total " + count + " ===");
    }
}
