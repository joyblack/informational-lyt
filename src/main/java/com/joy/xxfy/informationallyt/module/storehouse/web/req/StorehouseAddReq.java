package com.joy.xxfy.informationallyt.module.storehouse.web.req;

import com.joy.xxfy.informationallyt.module.common.enums.StatusEnum;
import com.joy.xxfy.informationallyt.module.common.enums.YesEnum;
import com.joy.xxfy.informationallyt.module.common.web.req.BaseAddReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class StorehouseAddReq extends BaseAddReq {

    /**
     * 仓库名称
     */
    @NotBlank(message = "仓库名称不能为空")
    private String name;

    /**
     * 负责人
     */
    @NotBlank(message = "负责人不能为空")
    private String responsePeople;

    /**
     * 编号
     */
    @NotBlank(message = "编号不能为空")
    private String code;

    /**
     * 管理员
     */
    @NotBlank(message = "管理员不能为空")
    private String admin;

    /**
     * 是否是默认仓库
     */
    @NotNull(message = "是否为默认仓库不能为空")
    private YesEnum isDefault;

    /**
     * 仓库状态
     */
    @NotNull(message = "仓库状态不能为空")
    private StatusEnum status;
}
