package com.joy.xxfy.informationallyt.module.safe.web.req;

import com.joy.xxfy.informationallyt.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationallyt.module.common.web.req.BaseUpdateReq;
import com.joy.xxfy.informationallyt.module.safe.domain.enums.ExamineResultEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString(callSuper = true)
public class SafeInspectionUpdateReq extends BaseUpdateReq {
    /**
     * 检查日期
     */
    @NotNull(message = "检查日期不能为空")
    private Date checkDate;


    /**
     * 检查单位
     */
    @NotNull(message = "检查日期不能为空")
    private Long checkDepartmentId;


    /**
     * 隐患内容
     */
    @NotBlank(message = "隐患内容不能为空")
    private String problemContent;

    /**
     * 整改措施
     */
    @NotBlank(message = "整改措施不能为空")
    private String rectificationMethods;

    /**
     * 整改负责人
     */
    @NotBlank(message = "真该负责人不能为空")
    private String rectificationResponsePeople;

    /**
     * 整改期限
     */
    @NotNull(message = "整改期限不能为空")
    private Integer deadDays;

    /**
     * 投入资金
     */
    private BigDecimal investMoney;

    /**
     * 验收日期
     */
    @NotNull(message = "验收日期不能为空")
    private Date examineDate;

    /**
     * 验收结果
     */
    @NotNull(message = "验收结果不能为空")
    private ExamineResultEnum examineResult;

    /**
     * 验收人
     */
    @NotBlank(message = "验收人不能为空")
    private String examinePeople;
}
