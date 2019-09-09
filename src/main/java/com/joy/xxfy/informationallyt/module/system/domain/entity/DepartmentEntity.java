package com.joy.xxfy.informationallyt.module.system.domain.entity;

import com.joy.xxfy.informationallyt.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationallyt.module.system.domain.enums.DepartmentTypeEnum;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "system_department")
@Data
@ToString(callSuper = true)

public class DepartmentEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 4751065577213406424L;
    /**
     * 部门名称
     */
    @NotEmpty(message = "部门名称不能为空")
    private String name;

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
     * 部门路径
     */
    @Column(nullable = false)
    private String path;

    /**
     * 子部门
     */
    @Transient
    private List<DepartmentEntity> children = new ArrayList<>();

}
