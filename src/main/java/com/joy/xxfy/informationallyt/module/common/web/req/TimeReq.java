package com.joy.xxfy.informationallyt.module.common.web.req;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString
public class TimeReq {
    @NotNull(message = "时间不能为空")
    private Date time;
}
