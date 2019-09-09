package com.joy.xxfy.informationallyt.module.storehouse.domain.repository;

import com.joy.xxfy.informationallyt.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.StorehouseInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorehouseInfoRepository extends JpaRepository<StorehouseInfoEntity, Long>, BaseRepository<StorehouseInfoEntity> {
    /**
     * 通过名称查询
     */
    StorehouseInfoEntity findFirstByName(String name);
}
