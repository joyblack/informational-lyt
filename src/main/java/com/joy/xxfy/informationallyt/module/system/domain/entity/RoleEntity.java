package com.joy.xxfy.informationallyt.module.system.domain.entity;

import com.joy.xxfy.informationallyt.module.common.domain.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色
 */
@Entity
@Table(name = "system_role")
@Data
@ToString

public class RoleEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -873520780965494950L;

    /**
     * 角色名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 资源ID列表
     */
    @Lob
    private String permissions;
}
