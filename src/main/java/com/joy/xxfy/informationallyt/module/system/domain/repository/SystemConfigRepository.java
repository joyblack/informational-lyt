package com.joy.xxfy.informationallyt.module.system.domain.repository;

import com.joy.xxfy.informationallyt.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationallyt.module.system.domain.entity.SystemConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemConfigRepository extends BaseRepository<SystemConfigEntity>, JpaRepository<SystemConfigEntity, Long> {
    /**
     * 根据配置名称获取配置信息
     */
    SystemConfigEntity findFirstByName(String name);
}
