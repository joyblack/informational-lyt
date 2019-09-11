package com.joy.xxfy.informationallyt.module.storehouse.domain.repository;

import com.joy.xxfy.informationallyt.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.MaterialCategoryEntity;
import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.MaterialEntity;
import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaterialRepository extends BaseRepository<MaterialEntity>, JpaRepository<MaterialEntity, Long> {
    /**
     * 根据唯一性约束(名称、型号)查找
     */
    MaterialEntity findFirstByNameAndModelNumber(String name,String modelNumber);

}
