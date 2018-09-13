package com.mycena.utils;

import com.mycena.utils.repository.GroupRepository;
import com.mycena.utils.repository.GroupUserPayLeaveRepository;
import com.mycena.utils.repository.GroupUserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UtilsApplicationTests {
    @Autowired
    GroupUserRepository groupUserRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    GroupUserPayLeaveRepository groupUserPayLeaveRepository;


    @Test
    public void contextLoads() {
//
//        GroupUser groupUser = new GroupUser();
//        groupUserRepository.save(groupUser);



    }

}
