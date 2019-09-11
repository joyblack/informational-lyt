package com.joy.xxfy.informationallyt.module.storehouse.domain.entity;

import com.joy.xxfy.informationallyt.module.common.domain.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * 出库信息
 */
@Data
@ToString(callSuper = true)
@Entity
@Table(name = "gm_inventory_out")
public class InventoryOutEntity extends BaseEntity {
    /**
     * 材料
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "material_id", nullable = false)
    private MaterialEntity material;

    /**
     * 仓库
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "storehouse_id", nullable = false)
    private StorehouseEntity storehouse;


    /**
     * 库存总数
     */
    @Column(nullable = false)
    private Long amount;

    /**
     * 出库数量
     */
    @Column(nullable = false)
    private Long outNumber;

    /**
     * 出库后总数
     */
    @Column(nullable = false)
    private Long afterAmount;

    /**
     * 出库时间
     */
    @Column(nullable = false)
    private Date outDate;

    /**
     * 领用班组
     */
    @Column(nullable = false)
    private String usedTeam;
}
