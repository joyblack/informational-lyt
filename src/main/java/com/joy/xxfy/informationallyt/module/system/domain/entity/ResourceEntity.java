package com.joy.xxfy.informationallyt.module.system.domain.entity;

import com.joy.xxfy.informationallyt.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationallyt.module.system.domain.enums.ResourceType;
import com.joy.xxfy.informationallyt.module.system.domain.enums.UserTypeEnum;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * 资源（权限）
 */
@Entity
@Table(name = "system_resource")
@Data
@ToString(callSuper = true)
public class ResourceEntity extends BaseEntity {

    /**
     * 资源URL
     */
    private String resourceUrl;

    /**
     * 资源类型 => ResourceType 0-菜单 1-按钮
     */
    @Column(nullable = false)
    private ResourceType resourceType;

    /**
     * 限制用户的类型:只允许集团；只允许煤矿平台；亦或是允许所有
     */
    private UserTypeEnum userType;

    /**
     * 资源名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 资源别称
     */
    @Column(nullable = false)
    private String resourceAliasName;

    /**
     * 父节点ID
     */
    @Column(nullable = false)
    private Long parentId;

    /**
     * 路径
     */
    @Lob
    @Column(nullable = false)
    private String path;

    /**
     * 子资源信息
     */
    @Transient
    private List<ResourceEntity> children;

}