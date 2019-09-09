package com.joy.xxfy.informationallyt.module.storehouse.web.req;

import com.joy.xxfy.informationallyt.module.common.enums.StatusEnum;
import com.joy.xxfy.informationallyt.module.common.enums.YesEnum;
import com.joy.xxfy.informationallyt.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationallyt.module.common.web.req.BasePageReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class StorehouseInfoGetListReq extends BasePageReq {

    /**
     * 仓库名称
     */
    private String name;

    /**
     * 仓库状态
     */
    private StatusEnum status;

    /**
     * 负责人
     */
    private String responsePeople;

    /**
     * 编号
     */
    private String code;

    /**
     * 管理员
     */
    private String admin;

}
