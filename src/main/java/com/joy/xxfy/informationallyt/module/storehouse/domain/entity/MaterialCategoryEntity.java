package com.joy.xxfy.informationallyt.module.storehouse.domain.entity;

import com.joy.xxfy.informationallyt.module.common.domain.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * 材料类别
 */
@Entity
@Table(name = "gm_material_category")
@Data
@ToString(callSuper = true)
public class MaterialCategoryEntity extends BaseEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 父节点
     */
    @Column(nullable = false)
    private Long parentId;


    /**
     * 路径信息
     */
    @Lob
    @Column(nullable = false)
    private String path;


    @Transient
    private List<MaterialCategoryEntity> children;

}
