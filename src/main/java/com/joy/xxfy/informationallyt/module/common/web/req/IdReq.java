package com.joy.xxfy.informationallyt.module.common.web.req;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class IdReq {
    @NotNull(message = "ID不能为空")
    private Long id;
}
