package com.joy.xxfy.informationallyt.module.storehouse.domain.entity;

import com.joy.xxfy.informationallyt.module.common.domain.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * 库存：材料-仓库关联(仓库-材料唯一性)
 */
@Data
@ToString(callSuper = true)
@Entity
@Table(name = "gm_inventory",uniqueConstraints = {@UniqueConstraint(columnNames = {"material_id", "storehouse_id"})})
public class InventoryEntity extends BaseEntity {
    /**
     * 材料
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "material_id", nullable = false)
    private MaterialEntity material;

    /**
     * 所属仓库
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "storehouse_id", nullable = false)
    private StorehouseEntity storehouse;

    /**
     * 数量
     */
    @Column(nullable = false)
    private Long amount;

}
