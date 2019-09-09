package com.joy.xxfy.informationallyt.module.system.domain.repository;

import com.joy.xxfy.informationallyt.module.common.domain.repository.BaseRepository;
import com.joy.xxfy.informationallyt.module.system.domain.entity.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResourceRepository extends BaseRepository<ResourceEntity>, JpaRepository<ResourceEntity, Long> {

    /**
     * 根据parentId&name获取信息
     */
    ResourceEntity findAllByParentIdAndName(Long parentId, String name);

    /**
     * 根据parentId&name&!id 获取部门信息
     */
    ResourceEntity findAllByParentIdAndNameAndIdNot(Long parentId, String name, Long id);

    /**
     * 根据父节点ID获取子节点信息
     */
    List<ResourceEntity> findAllByParentId(Long parentId);

    /**
     * 根据父路径查询所有子节点
     */
    List<ResourceEntity> findAllByPathStartingWith(String path);

    /**
     * 根据Url获取资源信息
     */
    ResourceEntity findFirstByResourceUrl(String resourceUrl);



}
