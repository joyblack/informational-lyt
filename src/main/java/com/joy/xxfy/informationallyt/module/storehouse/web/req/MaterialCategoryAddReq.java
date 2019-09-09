package com.joy.xxfy.informationallyt.module.storehouse.web.req;

import com.joy.xxfy.informationallyt.module.common.web.req.BaseAddReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class MaterialCategoryAddReq extends BaseAddReq {
    /**
     * 名称
     */
    @NotBlank(message = "类别名称不能为空")
    private String name;

    /**
     * 父节点
     */
    @NotNull(message = "父节点信息不能为空")
    private Long parentId;
}
