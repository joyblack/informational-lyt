package com.joy.xxfy.informationallyt.module.common.enums;

public enum ShiftsEnum {
    MORNING("早班"),
    NOON("中班"),
    EVENING("晚班"),
    OTHER("其他")
    ;
    private String shifts;

    ShiftsEnum(String shifts) {
        this.shifts = shifts;
    }

    public String getShifts() {
        return shifts;
    }

    public void setShifts(String shifts) {
        this.shifts = shifts;
    }
}
