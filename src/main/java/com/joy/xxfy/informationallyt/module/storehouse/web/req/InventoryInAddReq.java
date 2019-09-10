package com.joy.xxfy.informationallyt.module.storehouse.web.req;

import com.joy.xxfy.informationallyt.module.common.web.req.BaseAddReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 入库Req
 */
@Data
@ToString
public class InventoryInAddReq extends BaseAddReq {

    /**
     * 材料名称
     */
    @NotBlank(message = "材料名称不能为空")
    private String name;

    /**
     * 型号
     */
    @NotNull(message = "型号不能为空")
    private String modelNumber;


    /**
     * 供货人信息，没有提供材料ID的情况下，需要提交供货人信息
     */
    @NotNull(message = "供货商不能为空")
    private Long supplierId;


    /**
     * 材料类别ID
     */
    @NotNull(message = "材料类别不能为空")
    private Long materialCategoryId;



    /**
     * 库存总数
     */
    @NotNull(message = "库存总数不能为空")
    private Long amount;

    /**
     * 入库数量
     */
    @NotNull(message = "入库数量不能为空")
    @Min(value = 0,message = "入库数量不能为0")
    private Long inNumber;

    /**
     * 入库仓库ID
     */
    @NotNull(message = "入库仓库信息不能为空")
    private Long storehouseId;

    /**
     * 入库后总数
     */
    @NotNull(message = "入库后总数不能为空")
    private Long afterAmount;

    /**
     * 入库时间
     */
    @NotNull(message = "入库时间不能为空")
    private Date inDate;

    /**
     * 签收人
     */
    @NotNull(message = "签收人不能为空")
    private String signPeople;

}
