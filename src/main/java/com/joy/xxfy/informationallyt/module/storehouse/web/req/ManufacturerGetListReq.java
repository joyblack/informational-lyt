package com.joy.xxfy.informationallyt.module.storehouse.web.req;

import com.joy.xxfy.informationallyt.module.common.enums.StatusEnum;
import com.joy.xxfy.informationallyt.module.common.web.req.BasePageReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class ManufacturerGetListReq extends BasePageReq {

    /**
     * 名称
     */
    private String name;


    /**
     * 编号
     */
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
