package com.joy.xxfy.informationallyt.module.system.domain.enums;


public enum UserTypeEnum {
    CM_ADMIN("煤矿平台管理员"),
    CP_ADMIN("集团管理员"),
    ALL("所有");

    private String describes;

    UserTypeEnum(String describes) {
        this.describes = describes;
    }


    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describe) {
        this.describes = describes;
    }
}
