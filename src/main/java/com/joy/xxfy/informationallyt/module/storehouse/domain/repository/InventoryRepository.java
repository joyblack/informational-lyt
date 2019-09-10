package com.joy.xxfy.informationallyt.module.storehouse.domain.repository;

import com.joy.xxfy.informationallyt.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.InventoryEntity;
import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.MaterialCategoryEntity;
import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.MaterialEntity;
import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.StorehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends BaseRepository<InventoryEntity>, JpaRepository<InventoryEntity, Long> {
    /**
     * 根据仓库信息、材料信息获取库存信息
     */
    InventoryEntity findFirstByStorehouseAndMaterial(StorehouseEntity storehouse, MaterialEntity material);
}
