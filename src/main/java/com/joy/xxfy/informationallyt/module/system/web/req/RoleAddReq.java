package com.joy.xxfy.informationallyt.module.system.web.req;

import com.joy.xxfy.informationallyt.module.common.web.req.BaseAddReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class RoleAddReq extends BaseAddReq {
    /**
     * 名称
     */
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /**
     * 权限ID串
     */
    private String permissions;
}
