package com.joy.xxfy.informationallyt.module.storehouse.domain.entity;

import com.joy.xxfy.informationallyt.module.common.domain.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * 入库日志信息
 */
@Data
@ToString(callSuper = true)
@Entity
@Table(name = "gm_inventory_in")
public class InventoryInEntity extends BaseEntity {
    /**
     * 材料信息
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
     * 入库数量
     */
    @Column(nullable = false)
    private Long inNumber;

    /**
     * 入库后总数
     */
    @Column(nullable = false)
    private Long afterAmount;

    /**
     * 入库时间
     */
    @Column(nullable = false)
    private Date inDate;

    /**
     * 签收人
     */
    @Column(nullable = false)
    private String signPeople;
}
