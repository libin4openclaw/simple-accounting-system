package com.simple.accounting.common.enums;

import lombok.Getter;

/**
 * 还款计划状态枚举
 */
@Getter
public enum ScheduleStatus {

    UNPAID(1, "未还"),
    PAID(2, "已还"),
    PARTIAL_PAID(3, "部分还款"),
    OVERDUE(4, "逾期");

    private final Integer code;
    private final String desc;

    ScheduleStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ScheduleStatus getByCode(Integer code) {
        for (ScheduleStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
