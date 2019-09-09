package com.joy.xxfy.informationallyt.module.common.enums;

public enum YesEnum {
    NO("否"),YES("是");
    private String yes;

    YesEnum(String yes) {
        this.yes = yes;
    }

    public String getYes() {
        return yes;
    }

    public void setYes(String yes) {
        this.yes = yes;
    }
}
