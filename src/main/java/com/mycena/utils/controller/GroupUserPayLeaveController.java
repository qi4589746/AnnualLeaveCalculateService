package com.mycena.utils.controller;


import com.mycena.utils.entity.Group;
import com.mycena.utils.entity.GroupUser;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CommonsLog
@RequestMapping("/microservice")
public class GroupUserPayLeaveController {

    @PostMapping(value = "/calculateAnnualLeave")
    public ResponseEntity<String> calculateAnnualLeave(@RequestBody Group group, @RequestBody GroupUser groupUser) {


        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
