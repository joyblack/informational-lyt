package com.joy.xxfy.informationallyt.module.safe.domain.entity;

import com.joy.xxfy.informationallyt.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationallyt.module.safe.domain.enums.ExamineResultEnum;
import com.joy.xxfy.informationallyt.module.system.domain.entity.DepartmentEntity;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 整改
 * inspection: 检查
 * rectification：整改
 */
@Entity
@Table(name = "safe_inspection")
@Data
@ToString(callSuper = true)
public class SafeInspectionEntity extends BaseEntity {
    /**
     * 检查日期
     */
    @Column(nullable = false)
    private Date checkDate;


    /**
     * 检查单位
     */
    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private DepartmentEntity checkDepartment;


    /**
     * 隐患内容
     */
    @Lob
    @Column(nullable = false)
    private String problemContent;

    /**
     * 整改措施
     */
    @Lob
    @Column(nullable = false)
    private String rectificationMethods;

    /**
     * 整改负责人
     */
    @Column(nullable = false)
    private String rectificationResponsePeople;

    /**
     * 整改期限
     */
    @Column(nullable = false)
    private Integer deadDays;

    /**
     * 投入资金
     */
    private BigDecimal investMoney;

    /**
     * 验收日期
     */
    @Column(nullable = false)
    private Date examineDate;

    /**
     * 验收结果
     */
    @Column(nullable = false)
    private ExamineResultEnum examineResult;

    /**
     * 验收人
     */
    @Column(nullable = false)
    private String examinePeople;
}
