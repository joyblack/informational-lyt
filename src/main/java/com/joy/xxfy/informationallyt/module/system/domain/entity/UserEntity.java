package com.joy.xxfy.informationallyt.module.system.domain.entity;

import com.joy.xxfy.informationallyt.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationallyt.module.common.enums.StatusEnum;
import com.joy.xxfy.informationallyt.module.system.domain.enums.UserTypeEnum;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "system_user")
@Data
@ToString

public class UserEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2015171475599371602L;

    private String idNumber;

    @NotEmpty(message = "登陆名不能为空")
    @Column(nullable = false)
    private String loginName;

    @NotEmpty(message = "电话号码不能为空")
    @Column(nullable = false)
    private String phone;

    private String password;

    @Transient
    private String affirmPassword;

    /**
     * 真实姓名
     */
    private String username;

    /**
     * 用户类型
     */
    private UserTypeEnum userType = UserTypeEnum.CM_ADMIN;

    /**
     * 用户状态
     */
    private StatusEnum status = StatusEnum.START;


    /**
     * 资源列表
     */
    @Lob
    private String permissions;

    /**
     * 部门信息
     */
    @NotNull(message = "部门信息不能为空")
    @JoinColumn(name = "department_id")
    @ManyToOne(cascade = {},fetch = FetchType.EAGER)
    private DepartmentEntity department;

}
