package com.joy.xxfy.informationallyt.module.storehouse.web.req;

import com.joy.xxfy.informationallyt.module.common.web.req.BaseAddReq;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 出库Req
 */
@Data
@ToString
public class InventoryOutAddReq extends BaseAddReq {

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
     * 出库数量
     */
    @NotNull(message = "出库数量不能为空")
    @Min(value = 0,message = "出库数量不能为0")
    private Long outNumber;

    /**
     * 出库仓库ID
     */
    @NotNull(message = "出库仓库信息不能为空")
    private Long storehouseId;

    /**
     * 出库后总数
     */
    @NotNull(message = "出库后总数不能为空")
    private Long afterAmount;

    /**
     * 出库时间
     */
    @NotNull(message = "出库时间不能为空")
    private Date outDate;

    /**
     * 领用组
     */
    @NotNull(message = "领用班组不能为空")
    private String usedTeam;

}
