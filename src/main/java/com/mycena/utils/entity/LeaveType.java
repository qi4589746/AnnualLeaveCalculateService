package com.mycena.utils.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

public enum LeaveType {
    /**
     * 事假
     */
    PERSONAL_LEAVE(1),
    /**
     * 病假
     */
    SICK_LEAVE(2),
    /**
     * 特休
     */
    ANNUAL_LEAVE(3),
    /**
     * 公假
     */
    OFFICIAL_LEAVE(4),
    /**
     * 婚假
     */
    MARRIAGE_LEAVE(5),
    /**
     * 喪假
     */
    FUNERAL_LEAVE(6),
    /**
     * 產假
     */
    MATERNITY_LEAVE(7),
    /**
     * 陪產假
     */
    PATERNITY_LEAVE(8),
    /**
     * 生理假
     */
    MENSTRUATION_LEAVE(9),
    /**
     * 補休
     */
    COMPENSATORY_LEAVE(10),
    /**
     * 其他
     */
    OTHER(99),
    ;
    @JsonValue
    private final int value;

    LeaveType(int value) {
        this.value = value;
    }

    @JsonCreator
    public static LeaveType fromValue(Integer value) {
        for (LeaveType type : LeaveType.values()) {
            if (Objects.equals(type.value, value)) {
                return type;
            }
        }
        return null;
    }

    @Converter
    public static class TypeConverter implements AttributeConverter<LeaveType, Integer> {
        @Override
        public Integer convertToDatabaseColumn(LeaveType attribute) {
            if (attribute == null) return null;
            return attribute.value;
        }

        @Override
        public LeaveType convertToEntityAttribute(Integer dbData) {
            return LeaveType.fromValue(dbData);
        }
    }
}
