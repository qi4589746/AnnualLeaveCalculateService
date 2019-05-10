package com.mycena.utils.controller;


import com.mycena.utils.calculator.entity.FormattedDate;
import com.mycena.utils.calculator.entity.LeaveData;
import com.mycena.utils.calculator.service.AnnualLeaveCalculator;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;

@RestController
@CommonsLog
@RequestMapping("/microservice")
public class GroupUserPayLeaveController {

    @Autowired
    AnnualLeaveCalculator annualLeaveCalculator;

    @PostMapping(value = "/calculateAnnualLeave")
    public ResponseEntity<LinkedList<LeaveData>> calculateAnnualLeave(@RequestBody RequestFormat requestFormat) {

        FormattedDate onBoardDate = new FormattedDate(requestFormat.arrivalDate);
        FormattedDate calculateDate = new FormattedDate(requestFormat.calculateDate);
        LinkedList<LeaveData> leaveDataLinkedList = annualLeaveCalculator.getTotalLeaveNum(onBoardDate, calculateDate);

//        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
//        SimpleDateFormat sdf = new SimpleDateFormat("E yyyy/MM/dd");
//        System.out.println("*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*");
//            for (LeaveData leaveData:
//                 leaveDataLinkedList) {
//                    calendar.setTimeInMillis(leaveData.getActiveDateTime());
//                System.out.println("S: " + sdf.format(calendar.getTime()));
//                    calendar.setTimeInMillis(leaveData.getExpireDateTime());
//                System.out.println("E: " + sdf.format(calendar.getTime()));
//                System.out.println("Total: " + leaveData.getTotalMinute());
//                System.out.println("===========================================");
//            }

        return new ResponseEntity<>(leaveDataLinkedList, HttpStatus.OK);

    }

    public static class RequestFormat {
        Long arrivalDate;
        Long calculateDate;

        public RequestFormat() {
        }

        public RequestFormat(Long arrivalDate, Long calculateDate) {
            this.arrivalDate = arrivalDate;
            this.calculateDate = calculateDate;
        }

        public Long getArrivalDate() {
            return arrivalDate;
        }

        public void setArrivalDate(Long arrivalDate) {
            this.arrivalDate = arrivalDate;
        }

        public Long getCalculateDate() {
            return calculateDate;
        }

        public void setCalculateDate(Long calculateDate) {
            this.calculateDate = calculateDate;
        }
    }

//
//        public static class ResponseFormat {
//        public long beginDate;
//        public long endDate;
//        public long midDate;
//        public int onePartLeave;
//        public int twoPartLeave;
//        public float totalLeaveNum;
//
//        public ResponseFormat(LeaveData leaveData) {
//            this.beginDate = leaveData.beginDate.toMillisecond();
//            this.endDate = leaveData.endDate.toMillisecond();
//            this.midDate = leaveData.midDate == null ? 0 : leaveData.midDate.toMillisecond();
//            this.onePartLeave = leaveData.onePartLeave;
//            this.twoPartLeave = leaveData.twoPartLeave;
//            this.totalLeaveNum = leaveData.totalLeaveNum;
//        }
//    }
}
