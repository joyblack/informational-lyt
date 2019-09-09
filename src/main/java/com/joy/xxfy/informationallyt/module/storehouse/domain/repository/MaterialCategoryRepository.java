package com.joy.xxfy.informationallyt.module.storehouse.domain.repository;

import com.joy.xxfy.informationallyt.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.MaterialCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MaterialCategoryRepository extends BaseRepository<MaterialCategoryEntity>, JpaRepository<MaterialCategoryEntity, Long> {

    /**
     * 通过（父ID、类型名称）获取信息
     */
    MaterialCategoryEntity findFirstByParentIdAndName(Long parentId, String name);

    /**
     * 通过（父ID）获取下一级子列表
     */
    List<MaterialCategoryEntity> findAllByParentId(Long parentId);

    /**
     * 根据父路径查询所有子节点
     */
    List<MaterialCategoryEntity> findAllByPathStartingWith(String path);

    /**
     * 根据父节点查询一个子节点信息
     */
    MaterialCategoryEntity findFirstByParentId(Long parentId);
}
