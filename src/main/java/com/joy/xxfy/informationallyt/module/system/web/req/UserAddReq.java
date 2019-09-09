package com.joy.xxfy.informationallyt.module.system.web.req;

import com.joy.xxfy.informationallyt.module.common.web.req.BaseAddReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class UserAddReq extends BaseAddReq {

    @NotBlank(message = "登陆名不能为空")
    private String loginName;

    @NotBlank(message = "电话号码不能为空")
    private String phone;

    /**
     * 部门信息
     */
    @NotNull(message = "部门信息不能为空")
    private Long departmentId;

    private String password;

    private String affirmPassword;

    /**
     * 真实姓名
     */
    private String username;

    /**
     * 身份证号
     */
    private String idNumber;



}
