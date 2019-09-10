package com.joy.xxfy.informationallyt.module.storehouse.domain.entity;

import com.joy.xxfy.informationallyt.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationallyt.module.common.enums.StatusEnum;
import com.joy.xxfy.informationallyt.module.common.enums.YesEnum;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 仓库
 */
@Data
@ToString(callSuper = true)
@Entity
@Table(name = "gm_storehouse")
public class StorehouseEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -854264746744904798L;

    /**
     * 仓库名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 仓库状态
     */
    @Column(nullable = false)
    private StatusEnum status;

    /**
     * 负责人
     */
    @Column(nullable = false)
    private String responsePeople;

    /**
     * 编号
     */
    @Column(nullable = false)
    private String code;

    /**
     * 管理员
     */
    @Column(nullable = false)
    private String admin;

    /**
     * 是否是默认仓库
     */
    @Column(nullable = false)
    private YesEnum isDefault;

}
