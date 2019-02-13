package com.mycena.utils.controller;


import com.mycena.utils.calculator.entity.FormattedDate;
import com.mycena.utils.calculator.entity.LeaveData;
import com.mycena.utils.calculator.service.AnnualLeaveCalculator;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.TimeZone;

//import com.mycena.utils.entity.GroupUserPayLeave;
//import com.mycena.utils.entity.LeaveType;
//import com.mycena.utils.repository.GroupUserPayLeaveRepository;

@RestController
@CommonsLog
@RequestMapping("/microservice")
public class GroupUserPayLeaveController {

    @Autowired
    AnnualLeaveCalculator annualLeaveCalculator;

//    @Autowired
//    GroupUserPayLeaveRepository groupUserPayLeaveRepository;

//    @PostMapping(value = "/calculateAnnualLeave")
//    public ResponseEntity<String> calculateAnnualLeave(@RequestBody Group group, @RequestBody GroupUser groupUser) {
//
//        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
//        FormattedDate onBoardDate = new FormattedDate(groupUser.arrivalDate);
//        FormattedDate calculateDate = new FormattedDate(calendar.get(Calendar.YEAR), group.annualLeaveType, 1);
//
//        if (group.annualLeaveType == 0)
//            calculateDate.month = onBoardDate.month;
//
//        LeaveData leaveData = annualLeaveCalculator.getTotalLeaveNum(onBoardDate, calculateDate);
//        GroupUserPayLeave groupUserPayLeave = null;
//
//        if (leaveData.onePartLeave != 0) {
//            groupUserPayLeave = new GroupUserPayLeave(group.id, groupUser.userId, LeaveType.ANNUAL_LEAVE
//                    , leaveData.onePartLeave, leaveData.onePartLeave, leaveData.beginDate.convertToLong(), leaveData.midDate.convertToLong());
//        }
//        if (groupUserPayLeave != null) {
//            groupUserPayLeaveRepository.save(groupUserPayLeave);
//            groupUserPayLeave = null;
//        }
//
//        if (leaveData.twoPartLeave != 0) {
//            groupUserPayLeave = new GroupUserPayLeave(group.id, groupUser.userId, LeaveType.ANNUAL_LEAVE
//                    , leaveData.twoPartLeave, leaveData.twoPartLeave, leaveData.midDate.getTomorrow().convertToLong(), leaveData.endDate.convertToLong());
//        }
//        if (groupUserPayLeave != null)
//            groupUserPayLeaveRepository.save(groupUserPayLeave);
//
//
//        return new ResponseEntity<String>(HttpStatus.OK);
//    }

    @PostMapping(value = "/calculateAnnualLeave")
    public ResponseEntity<ResponseFormat> calculateAnnualLeave(@RequestParam Long arrivalDate, @RequestParam int annualLeaveType) {

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        FormattedDate onBoardDate = new FormattedDate(arrivalDate);
        FormattedDate calculateDate = new FormattedDate(calendar.get(Calendar.YEAR), annualLeaveType, 1); //每月第一天
        if (annualLeaveType == 0)
            calculateDate.month = onBoardDate.month;
        LeaveData leaveData = annualLeaveCalculator.getTotalLeaveNum(onBoardDate, calculateDate);
        return new ResponseEntity<>(new ResponseFormat(leaveData), HttpStatus.OK);

    }

    public static class ResponseFormat {
        public long beginDate;
        public long endDate;
        public long midDate;
        public int onePartLeave;
        public int twoPartLeave;
        public float totalLeaveNum;

        public ResponseFormat(LeaveData leaveData) {
            this.beginDate = leaveData.beginDate.toMillisecond();
            this.endDate = leaveData.endDate.toMillisecond();
            this.midDate = leaveData.midDate == null ? 0 : leaveData.midDate.toMillisecond();
            this.onePartLeave = leaveData.onePartLeave;
            this.twoPartLeave = leaveData.twoPartLeave;
            this.totalLeaveNum = leaveData.totalLeaveNum;
        }
    }
}
