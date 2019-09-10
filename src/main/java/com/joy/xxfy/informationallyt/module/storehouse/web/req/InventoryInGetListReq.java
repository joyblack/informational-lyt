package com.joy.xxfy.informationallyt.module.storehouse.web.req;

import com.joy.xxfy.informationallyt.module.common.web.req.BaseAddReq;
import com.joy.xxfy.informationallyt.module.common.web.req.BasePageReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
@ToString
public class InventoryInGetListReq extends BasePageReq {

    /**
     * 材料名称
     */
    private String name;

    /**
     * 型号
     */
    private String modelNumber;


    /**
     * 供货人信息，没有提供材料ID的情况下，需要提交供货人信息
     */
    private Long supplierId;


    /**
     * 材料类别ID
     */
    private Long materialCategoryId;


    /**
     * 入库仓库ID
     */
    @NotNull(message = "入库仓库信息不能为空")
    private Long storehouseId;


    /**
     * 入库时间区间
     */
    private Date inDateStart;
    private Date inDateEnd;

    /**
     * 签收人
     */
    private String signPeople;

}
