package com.joy.xxfy.informationallyt.module.storehouse.domain.repository;

import com.joy.xxfy.informationallyt.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.InventoryInEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryInRepository extends BaseRepository<InventoryInEntity>, JpaRepository<InventoryInEntity, Long> {

}
