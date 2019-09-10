package com.joy.xxfy.informationallyt.module.storehouse.domain.repository;

import com.joy.xxfy.informationallyt.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.InventoryOutEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryOutRepository extends BaseRepository<InventoryOutEntity>, JpaRepository<InventoryOutEntity, Long> {

}
