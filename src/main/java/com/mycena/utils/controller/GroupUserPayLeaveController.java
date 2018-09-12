package com.mycena.utils.controller;


import com.mycena.utils.calculator.entity.FormattedDate;
import com.mycena.utils.calculator.service.AnnualLeaveCalculator;
import com.mycena.utils.entity.Group;
import com.mycena.utils.entity.GroupUser;
import com.mycena.utils.entity.GroupUserPayLeave;
import com.mycena.utils.entity.LeaveType;
import com.mycena.utils.repository.GroupUserPayLeaveRepository;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;

@RestController
@CommonsLog
@RequestMapping("/microservice")
public class GroupUserPayLeaveController {

    @Autowired
    AnnualLeaveCalculator annualLeaveCalculator;

    @Autowired
    GroupUserPayLeaveRepository groupUserPayLeaveRepository;

    @PostMapping(value = "/calculateAnnualLeave")
    public ResponseEntity<String> calculateAnnualLeave(@RequestBody Group group, @RequestBody GroupUser groupUser) {
        Calendar calendar = Calendar.getInstance();
        FormattedDate onBoardDate = new FormattedDate(groupUser.arrivalDate);
        FormattedDate calculateDate = new FormattedDate(calendar.get(Calendar.YEAR), group.annualLeaveType, 1);

        if (group.annualLeaveType == 0)
            calculateDate.month = onBoardDate.month;

        int totalLeave = annualLeaveCalculator.getTotalLeaveNum(onBoardDate, calculateDate);

        GroupUserPayLeave groupUserPayLeave = new GroupUserPayLeave(group.id, groupUser.userId, LeaveType.ANNUAL_LEAVE
                , totalLeave, totalLeave, , calculateDate.getEndDateOfOneYear().convertToLong());

        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
