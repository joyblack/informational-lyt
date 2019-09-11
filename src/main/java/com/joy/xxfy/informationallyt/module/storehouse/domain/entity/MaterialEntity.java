package com.joy.xxfy.informationallyt.module.storehouse.domain.entity;

import com.joy.xxfy.informationallyt.module.common.domain.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

/**
 * 材料(名称-型号确定材料唯一性)
 */
@Entity
@Table(name = "gm_material",uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "model_number"})})
@Data
@ToString(callSuper = true)
public class MaterialEntity extends BaseEntity {

    /**
     * 名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 型号
     */
    @Column(nullable = false, name = "model_number")
    private String modelNumber;

    /**
     * 分类
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private MaterialCategoryEntity materialCategory;

    /**
     * 库存总量，该数值是所有仓库的同种材料的总和，与其他因素无关。
     */
    @Column(nullable = false)
    private Long amount;

    /**
     * 库存告警值：为空代表不做告警
     */
    private Long warningAmount;

}
