package com.joy.xxfy.informationallyt.module.storehouse.web.req;

import com.joy.xxfy.informationallyt.module.common.web.req.BaseUpdateReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class MaterialCategoryUpdateReq extends BaseUpdateReq {
    /**
     * 名称
     */
    @NotBlank(message = "类别名称不能为空")
    private String name;

}
