package com.joy.xxfy.informationallyt.module.system.web.req;

import com.joy.xxfy.informationallyt.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationallyt.module.system.domain.enums.DepartmentTypeEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class DepartmentAddReq extends BaseAddReq {
    /**
     * 部门名称
     */
    @NotBlank(message = "部门名称不能为空")
    private String departmentName;

    /**
     * 编码
     */
    private String code;

    /**
     * 父节点部门信息
     */
    @NotNull(message = "父部门不能为空")
    private Long parentId;

    /**
     * 负责人
     */
    private String responseUser;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 部门类型:默认为煤矿平台所属部门
     */
    private DepartmentTypeEnum departmentType;
}
