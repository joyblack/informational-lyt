package com.joy.xxfy.informationallyt.module.storehouse.domain.repository;

import com.joy.xxfy.informationallyt.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.StorehouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StorehouseRepository extends JpaRepository<StorehouseEntity, Long>, BaseRepository<StorehouseEntity> {
    /**
     * 通过名称查询
     */
    StorehouseEntity findFirstByName(String name);
}
