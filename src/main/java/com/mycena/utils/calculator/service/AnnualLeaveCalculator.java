package com.mycena.utils.calculator.service;

import com.mycena.utils.calculator.entity.FormattedDate;
import com.mycena.utils.calculator.entity.LeaveData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.LinkedList;

@Service
public class AnnualLeaveCalculator {
    @Autowired
    AnnualLeaveUtil annualLeaveUtil;

    public LinkedList<LeaveData> getTotalLeaveNum(FormattedDate onBoardDate, FormattedDate calculateDate) {

        LinkedList<LeaveData> leaveDataLinkedList = new LinkedList<>(); //下面case計算用
        float partOneLeaveNum; //下面case計算用
        float partTwoLeaveNum; //下面case計算用

        FormattedDate sixSeniorityDate = onBoardDate.getAfterSixMonthDate(); //取得半年資歷的起始日期，case 3, 7 會用到
        FormattedDate seniority1 = getSeniority(onBoardDate, calculateDate); //到達結算日期所具有的資歷
        int leaveNum1 = annualLeaveUtil.getLeaveDays(seniority1); //到達結算日期所具有資歷的對應特休天數

        FormattedDate calculateDate2 = calculateDate.getEndDateOfOneYear(); //取得下一次結算日期
        FormattedDate seniority2 = getSeniority(onBoardDate, calculateDate2); //到達下一次結算日期所具有的資歷
        int leaveNum2 = annualLeaveUtil.getLeaveDays(seniority2);//到達下一次結算日期所具有資歷的對應特休天數
        int state = leaveNum1 + leaveNum2;
        float partOneWorkRate = getGeneralFirstPartWorkRate(onBoardDate, calculateDate);
        switch (state) {
            case 0: //到結算日期，以及結算日期到下次結算日期前，都未滿半年
//                System.out.println("case 0");
                return leaveDataLinkedList;
            case 3: //到結算日期，以及結算日期到下次結算日期前，僅有半年資歷，到下次結算日期可能未滿一年，等於會有半年的3天特休保留一定的比例到下次才放。
//                System.out.println("case 3");
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
                partTwoLeaveNum = ((sixMonthInterval.month + (sixMonthInterval.day) / denominator) / 6) * 3;
                leaveDataLinkedList.add(new LeaveData(sixSeniorityDate.convertToLongAndSetToTheEndMillisecond(),
                        calculateDate2.getYesterday().convertToLongAndSetToTheEndMillisecond(),
                        annualLeaveUtil.convertFloatToMinute(partTwoLeaveNum)));
//                showDate(leaveDataLinkedList);
//                System.out.println("case 3");
                return leaveDataLinkedList;
            case 7: //到結算日期時，已經滿半年資歷小於半年，但到下次結算日期卻已經滿一年
//                System.out.println("case 7");
                partOneLeaveNum = 3;
//                System.out.println(annualLeaveUtil.convertFloatToMinute(partOneLeaveNum));
                partTwoLeaveNum = (leaveNum2 * (1 - partOneWorkRate));
                leaveDataLinkedList.add(new LeaveData(sixSeniorityDate.convertToLongAndSetToTheBeginMillisecond(),
                        onBoardDate.getNextYearDate().getYesterday().convertToLongAndSetToTheEndMillisecond(), annualLeaveUtil.convertFloatToMinute(partOneLeaveNum)));
                if (annualLeaveUtil.convertFloatToMinute(partTwoLeaveNum) > 0) {
                    leaveDataLinkedList.add(new LeaveData(onBoardDate.getNextYearDate().convertToLongAndSetToTheBeginMillisecond(),
                            calculateDate2.getYesterday().convertToLongAndSetToTheEndMillisecond(), annualLeaveUtil.convertFloatToMinute(partTwoLeaveNum)));
                }
//                showDate(leaveDataLinkedList);
//                System.out.println("case 7");
                return leaveDataLinkedList;
            default:
//                System.out.println(state);
//                System.out.println("case default");
                LinkedList<LeaveData> previousLeaveData = getTotalLeaveNum(onBoardDate, calculateDate.getPreviousYearDate());
                float partOneLeaveNumInPreviousData = previousLeaveData.size() < 2 ? 0.0f : previousLeaveData.getLast().getTotalMinute() / 1440.0f;
                partOneLeaveNum = leaveNum1 - partOneLeaveNumInPreviousData;
                partTwoLeaveNum = (leaveNum2 * (1 - partOneWorkRate));
                if (state == 10 && annualLeaveUtil.convertFloatToMinute(partOneLeaveNumInPreviousData) > 0) {
                    leaveDataLinkedList.add(new LeaveData(sixSeniorityDate.convertToLongAndSetToTheBeginMillisecond(),
                            calculateDate.getYesterday().convertToLongAndSetToTheEndMillisecond(),
                            annualLeaveUtil.convertFloatToMinute(partOneLeaveNumInPreviousData)));
                }

                if (leaveNum1 == leaveNum2) {
                    leaveDataLinkedList.add(new LeaveData(calculateDate.convertToLongAndSetToTheEndMillisecond(),
                            calculateDate2.getYesterday().convertToLongAndSetToTheEndMillisecond(),
                            annualLeaveUtil.convertFloatToMinute(partTwoLeaveNum)));
                } else if (state > 10) {
                    if (annualLeaveUtil.convertFloatToMinute(partOneLeaveNum) > 0) {
                        leaveDataLinkedList.add(new LeaveData(calculateDate.convertToLongAndSetToTheBeginMillisecond(),
                                calculateDate.getNextAimMonthAndDay(onBoardDate).getYesterday().convertToLongAndSetToTheEndMillisecond(),
                                annualLeaveUtil.convertFloatToMinute(partOneLeaveNum)));
                    }

                    if (annualLeaveUtil.convertFloatToMinute(partTwoLeaveNum) > 0) {
                        leaveDataLinkedList.add(new LeaveData(calculateDate.getNextAimMonthAndDay(onBoardDate).convertToLongAndSetToTheBeginMillisecond(),
                                calculateDate2.getYesterday().convertToLongAndSetToTheEndMillisecond(), annualLeaveUtil.convertFloatToMinute(partTwoLeaveNum)));
                    }
                }
//                showDate(leaveDataLinkedList);
//                System.out.println("case default");
                return leaveDataLinkedList;
        }
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
        FormattedDate tempCalculateDate = new FormattedDate(calculateDate);
        int denominator;
        FormattedDate seniority;
        if (tempOnBoardDate.month > tempCalculateDate.month) {
            tempOnBoardDate.year = tempCalculateDate.year;
        } else {
            tempOnBoardDate.year = tempCalculateDate.year + 1;
        }

        seniority = getSeniority(tempCalculateDate, tempOnBoardDate);

        tempCalculateDate.month += seniority.month;
        if (tempCalculateDate.month > 12) {
            tempCalculateDate.year += 1;
            tempCalculateDate.month -= 12;
        }
        denominator = YearMonth.of(tempCalculateDate.year, tempCalculateDate.month).lengthOfMonth();
        if (seniority.year == 1 && seniority.month == 0)
            seniority.month = 12;
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


    private void showDate(LinkedList<LeaveData> leaveDataLinkedList) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("E yyyy/MM/dd");
        System.out.println("*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*");
        for (LeaveData leaveData :
                leaveDataLinkedList) {
            calendar.setTimeInMillis(leaveData.getActiveDateTime());
            System.out.println("S: " + sdf.format(calendar.getTime()));
            calendar.setTimeInMillis(leaveData.getExpireDateTime());
            System.out.println("E: " + sdf.format(calendar.getTime()));
            System.out.println("Total: " + leaveData.getTotalMinute());
            System.out.println("===========================================");
        }
    }

}
