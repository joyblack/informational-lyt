package com.joy.xxfy.informationallyt.module.safe.domain.repository;

import com.joy.xxfy.informationallyt.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationallyt.module.safe.domain.entity.SafeInspectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SafeInspectionRepository extends BaseRepository<SafeInspectionEntity>, JpaRepository<SafeInspectionEntity, Long> {

}
