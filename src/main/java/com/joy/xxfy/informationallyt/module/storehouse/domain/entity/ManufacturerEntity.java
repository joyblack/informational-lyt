package com.joy.xxfy.informationallyt.module.storehouse.domain.entity;

import com.joy.xxfy.informationallyt.module.common.domain.entity.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 厂家
 */
@Data
@ToString(callSuper = true)
@Entity
@Table(name = "storehouse_manufacturer")
public class ManufacturerEntity extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 6497537507608581615L;

    /**
     * 名称
     */
    @Column(nullable = false)
    private String name;


    /**
     * 编号
     */
    @Column(nullable = false)
    private String code;

    /**
     * 业务
     */
    private String business;

    /**
     * 联系人
     */
    private String contactPeople;

    /**
     * 联系电话
     */
    private String contactPhone;


}
