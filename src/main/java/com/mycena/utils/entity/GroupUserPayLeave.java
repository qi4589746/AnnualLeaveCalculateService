package com.mycena.utils.entity;

import com.mycena.utils.calculator.service.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = GroupUserPayLeave.TABLE_NAME, uniqueConstraints = {@UniqueConstraint(columnNames = {"group_id", "no"})})
@IdGenerator.Config(prefix = "group")
public class GroupUserPayLeave {
    public static final String TABLE_NAME = "group_user_pay_leave";

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    public String id;

    @Column(name = "group_id", nullable = false, updatable = false)
    public String groupId;

    @Column(name = "user_id", nullable = false, updatable = false)
    public String userId;

    @Column(name = "type")
    public LeaveType type;

    @Column(name = "totalMinute")
    public int totalMinute;

    @Column(name = "availableMinute")
    public int availableMinute;

    @Column(name = "activeDateTime")
    public Long activeDateTime;

    @Column(name = "expireDateTime")
    public Long expireDateTime;


}
