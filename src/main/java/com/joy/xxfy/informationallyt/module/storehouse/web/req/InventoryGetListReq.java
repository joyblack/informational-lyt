package com.joy.xxfy.informationallyt.module.storehouse.web.req;

import com.joy.xxfy.informationallyt.module.common.web.req.BasePageReq;
import lombok.Data;
import lombok.ToString;

import java.util.Date;


@Data
@ToString
public class InventoryGetListReq extends BasePageReq {

    /**
     * 材料名称
     */
    private String name;

    /**
     * 型号
     */
    private String modelNumber;


    /**
     * 材料类别ID
     */
    private Long materialCategoryId;

    /**
     * 仓库ID
     */
    private Long storehouseId;

}
