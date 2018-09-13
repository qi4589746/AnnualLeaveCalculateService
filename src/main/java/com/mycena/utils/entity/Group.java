package com.mycena.utils.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.mycena.utils.calculator.service.IdGenerator;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@IdGenerator.Config(prefix = "group")
@Entity
@Table(name = Group.TABLE_NAME)
public class Group {
    public static final String TABLE_NAME = "_group";

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    public String id;
    /**
     * 名稱
     */
    @Column(name = "name")
    public String name;
    /**
     * 電話
     */
    @Column(name = "phone")
    public String phone;
    /**
     * 傳真
     */
    @Column(name = "fax")
    public String fax;
    /**
     * 核准設立日期
     */
    @Column(name = "setup_date")
    public Long setupDate;
    /**
     * 統一編號
     */
    @Column(name = "tax_id")
    public String taxId;
    /**
     * 負責人
     */
    @Column(name = "responsible_name")
    public String responsibleName;
    /**
     * 負責人Email
     */
    @Column(name = "responsible_email")
    public String responsibleEmail;
    /**
     * 負責人手機
     */
    @Column(name = "responsible_phone")
    public String responsiblePhone;
    /**
     * 聯絡人
     */
    @Column(name = "contact_name")
    public String contactName;
    /**
     * 聯絡人Email
     */
    @Column(name = "contact_email")
    public String contactEmail;
    /**
     * 聯絡人手機
     */
    @Column(name = "contact_phone")
    public String contactPhone;
    /**
     * 地址
     */
    @Column(name = "address")
    public String address;
    /**
     * 勞保業別代碼
     */
    @Column(name = "labor_insurance_type_code")
    public String laborInsuranceTypeCode;
    /**
     * 勞保業別
     */
    @Column(name = "labor_insurance_type")
    public String laborInsuranceType;
    /**
     * 特休類別
     */
    @Column(name = "annual_leave_type")
    public Integer annualLeaveType;
    /**
     * 付費等級
     */
    @Convert(converter = Level.LevelConverter.class)
    @Column(name = "level")
    public Level level;
    /**
     * 使用到期日
     */
    @Column(name = "expire_date")
    public Long expireDate;
    /**
     * 狀態
     */
    @Convert(converter = GroupState.TypeConverter.class)
    @Column(name = "state")
    public GroupState state;

    public enum Level {
        FREE(0),
        PAID(1);
        @JsonValue
        private final int value;

        Level(int value) {
            this.value = value;
        }

        @JsonCreator
        public static Level fromValue(Integer value) {
            for (Level type : Level.values()) {
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
        public static class LevelConverter implements AttributeConverter<Level, Integer> {
            @Override
            public Integer convertToDatabaseColumn(Level attribute) {
                if (attribute == null) return null;
                return attribute.value;
            }

            @Override
            public Level convertToEntityAttribute(Integer dbData) {
                return Level.fromValue(dbData);
            }
        }
    }

    public enum GroupState {
        OPENED(0),
        CLOSED(1),
        ;
        @JsonValue
        private final int value;

        GroupState(int value) {
            this.value = value;
        }

        @JsonCreator
        public static GroupState fromValue(Integer value) {
            for (GroupState type : GroupState.values()) {
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
        public static class TypeConverter implements AttributeConverter<GroupState, Integer> {
            @Override
            public Integer convertToDatabaseColumn(GroupState attribute) {
                if (attribute == null) return null;
                return attribute.value;
            }

            @Override
            public GroupState convertToEntityAttribute(Integer dbData) {
                return GroupState.fromValue(dbData);
            }
        }
    }
}