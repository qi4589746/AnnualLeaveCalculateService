package com.mycena.utils.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = GroupUser.TABLE_NAME, uniqueConstraints = {@UniqueConstraint(columnNames = {"group_id", "no"})})
@IdClass(GroupUser.CompositeId.class)
public class GroupUser {
    public static final String TABLE_NAME = "group_user";
    @Id
    @Column(name = "group_id", nullable = false, updatable = false)
    public String groupId;
    @Id
    @Column(name = "user_id", nullable = false, updatable = false)
    public String userId;
    /**
     * 名稱
     */
    @Column(name = "name")
    public String name;
    /**
     * 編號
     */
    @Column(name = "no")
    public Integer no;
    /**
     * 身分證字號
     */
    @Column(name = "id_card_num")
    public String idCardNum;
    /**
     * 性別
     */
    @Column(name = "gender")
    public String gender;
    /**
     * 國籍
     */
    @Column(name = "country")
    public String country;
    /**
     * 生日
     */
    @Column(name = "birthDate")
    public Long birthDate;
    /**
     * 電話
     */
    @Column(name = "phone")
    public String phone;
    /**
     * 住家電話
     */
    @Column(name = "home_phone")
    public String homePhone;
    /**
     * 聯絡地址
     */
    @Column(name = "address")
    public String address;
    /**
     * 戶籍地址
     */
    @Column(name = "household_address")
    public String householdAddress;
    /**
     * 轉帳銀行代碼
     */
    @Column(name = "transfer_bank_code")
    public String transferBankCode;
    /**
     * 轉帳銀行帳號
     */
    @Column(name = "transfer_bank_account")
    public String transferBankAccount;
    /**
     * 緊急聯絡人
     */
    @Column(name = "emergency_contact")
    public String emergencyContact;
    /**
     * 緊急聯絡人關係
     */
    @Column(name = "emergency_contact_relationship")
    public String emergencyContactRelationship;
    /**
     * 緊急聯絡人電話
     */
    @Column(name = "emergency_contact_phone")
    public String emergencyContactPhone;
    /**
     * 到職日
     */
    @Column(name = "arrival_date")
    public Long arrivalDate;
    /**
     * 離職日
     */
    @Column(name = "resignation_date")
    public Long resignationDate;
    /**
     * 職稱
     */
    @Column(name = "title")
    public String title;
    /**
     * 備註
     */
    @Column(name = "remark")
    public String remark;
    /**
     * 狀態
     */
    @Convert(converter = GroupUserState.TypeConverter.class)
    @Column(name = "state")
    public GroupUserState state;

    @EqualsAndHashCode
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CompositeId implements Serializable {
        public String groupId;
        public String userId;
    }

    public enum GroupUserState {
        WORKED(0),
        RESIGNED(1),
        ;
        @JsonValue
        private final int value;

        GroupUserState(int value) {
            this.value = value;
        }

        @JsonCreator
        public static GroupUserState fromValue(Integer value) {
            for (GroupUserState type : GroupUserState.values()) {
                if (Objects.equals(type.value, value)) {
                    return type;
                }
            }
            return null;
        }

        public int getValue() {
            return value;
        }

        @Converter
        public static class TypeConverter implements AttributeConverter<GroupUserState, Integer> {
            @Override
            public Integer convertToDatabaseColumn(GroupUserState attribute) {
                if (attribute == null) return null;
                return attribute.value;
            }

            @Override
            public GroupUserState convertToEntityAttribute(Integer dbData) {
                return GroupUserState.fromValue(dbData);
            }
        }
    }
}