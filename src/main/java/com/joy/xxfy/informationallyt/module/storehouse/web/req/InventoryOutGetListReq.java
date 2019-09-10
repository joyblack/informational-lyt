package com.joy.xxfy.informationallyt.module.storehouse.web.req;

import com.joy.xxfy.informationallyt.module.common.web.req.BasePageReq;
import lombok.Data;
import lombok.ToString;

import java.util.Date;


@Data
@ToString
public class InventoryOutGetListReq extends BasePageReq {

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
     * 出库仓库ID
     */
    private Long storehouseId;


    /**
     * 出库时间区间
     */
    private Date outDateStart;
    private Date outDateEnd;

    /**
     * 领用班组
     */
    private String usedTeam;

}
