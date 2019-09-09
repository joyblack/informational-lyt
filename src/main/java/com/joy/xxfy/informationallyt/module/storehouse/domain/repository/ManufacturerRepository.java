package com.joy.xxfy.informationallyt.module.storehouse.domain.repository;

import com.joy.xxfy.informationallyt.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.ManufacturerEntity;
import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.StorehouseInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<ManufacturerEntity, Long>, BaseRepository<ManufacturerEntity> {
    /**
     * 通过名称查询
     */
    ManufacturerEntity findFirstByName(String name);
}
