package com.joy.xxfy.informationallyt.module.storehouse.domain.defaults;

import com.joy.xxfy.informationallyt.module.common.domain.entity.BaseEntity;
import com.joy.xxfy.informationallyt.module.common.enums.StatusEnum;
import com.joy.xxfy.informationallyt.module.common.enums.YesEnum;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import java.io.Serializable;

@Data
@ToString(callSuper = true)
public class StorehouseDefault{

    /**
     * 仓库状态
     */
    private StatusEnum status = StatusEnum.START;


    /**
     * 是否是默认仓库
     */
    private YesEnum isDefault = YesEnum.YES;
}
