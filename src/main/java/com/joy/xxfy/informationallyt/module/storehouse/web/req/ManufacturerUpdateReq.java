package com.joy.xxfy.informationallyt.module.storehouse.web.req;

import com.joy.xxfy.informationallyt.module.common.enums.StatusEnum;
import com.joy.xxfy.informationallyt.module.common.enums.YesEnum;
import com.joy.xxfy.informationallyt.module.common.web.req.BaseUpdateReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class ManufacturerUpdateReq extends BaseUpdateReq {

    /**
     * 名称
     */
    @NotBlank(message = "厂家名称不能为空")
    private String name;


    /**
     * 编号
     */
    @NotBlank(message = "厂家编号不能为空")
    private String code;

    /**
     * 业务
     */
    private String business;

    /**
     * 联系人
     */
    private String contactPeople;

    /**
     * 联系电话
     */
    private String contactPhone;
}
