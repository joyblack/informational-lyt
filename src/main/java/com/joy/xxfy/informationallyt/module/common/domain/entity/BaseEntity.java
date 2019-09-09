package com.joy.xxfy.informationallyt.module.common.domain.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@MappedSuperclass
@Data
@ToString
public class BaseEntity {
    public BaseEntity() {
    }

    private static final long serialVersionUID = -1L;

    /**
     * 自增id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "create_time")
    @NotNull(message = "创建日期不能为空")
    private Date createTime = new Date();

    /**
     * 修改时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, name = "update_time")
    @NotNull(message = "修改日期不能为空")
    private Date updateTime = new Date();

    /**
     * 备注
     **/
    @Lob
    private String remarks;
}
