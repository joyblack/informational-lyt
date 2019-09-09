package com.joy.xxfy.informationallyt.module.common.enums;

public enum StatusEnum {
    STOP("停用"),START("启用");
    private String status;

    StatusEnum(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
