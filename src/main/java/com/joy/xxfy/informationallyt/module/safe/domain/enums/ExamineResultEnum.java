package com.joy.xxfy.informationallyt.module.safe.domain.enums;

public enum ExamineResultEnum {

    EXAMINE_RESULT_PASS("合格"),
    EXAMINE_RESULT_ING("整改中"),
    EXAMINE_RESULT_NOT_PASS("不合格");

    private String result;

    ExamineResultEnum(String result){
        this.result = result;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
