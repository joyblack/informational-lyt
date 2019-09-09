package com.joy.xxfy.informationallyt.module.system.web.req;

import com.joy.xxfy.informationallyt.module.common.web.req.BasePageReq;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RoleGetListReq extends BasePageReq {
    /**
     * 名称
     */
    private String roleName;

    /**
     * 权限ID
     */
    private String permissions;
}
