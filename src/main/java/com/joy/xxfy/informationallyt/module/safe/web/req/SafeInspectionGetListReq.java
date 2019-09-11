package com.joy.xxfy.informationallyt.module.safe.web.req;

import com.joy.xxfy.informationallyt.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationallyt.module.common.web.req.BasePageReq;
import com.joy.xxfy.informationallyt.module.safe.domain.enums.ExamineResultEnum;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString(callSuper = true)
public class SafeInspectionGetListReq extends BasePageReq {
    /**
     * 检查日期区间
     */
    private Date checkDateStart;
    private Date checkDateEnd;

    /**
     * 检查单位
     */
    private Long checkDepartmentId;

    /**
     * 隐患内容
     */
    private String problemContent;

    /**
     * 整改负责人
     */
    private String rectificationResponsePeople;

    /**
     * 验收日期区间
     */
    private Date examineDateStart;
    private Date examineDateEnd;

    /**
     * 验收结果
     */
    private ExamineResultEnum examineResult;

}
