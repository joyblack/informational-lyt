package com.joy.xxfy.informationallyt.publish.utils.project;

import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.MaterialCategoryEntity;
import com.joy.xxfy.informationallyt.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationallyt.module.system.domain.entity.ResourceEntity;
import com.joy.xxfy.informationallyt.publish.utils.JoyBeanUtil;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {


    /**
     * 将部门列表转化为部门树结构
     */
    public  static List<DepartmentEntity> getDeptTree(List<DepartmentEntity> list, Long pid){
        List<DepartmentEntity> result =  new ArrayList<>();
        List<DepartmentEntity> temp;
        for(DepartmentEntity entity : list){
            if(entity.getParentId().equals(pid)){
                DepartmentEntity scopeMode = new DepartmentEntity();
                JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(entity,scopeMode);
                temp = getDeptTree(list,entity.getId());
                if(temp.size() > 0){
                    scopeMode.setChildren(temp);
                }
                result.add(scopeMode);
            }
        }
        return result;
    }


    /**
     * 将资源列表转化为树结构
     */
    public  static List<ResourceEntity> getResourcesTree(List<ResourceEntity> list, Long pid){
        List<ResourceEntity> result =  new ArrayList<>();
        List<ResourceEntity> temp;
        for(ResourceEntity entity : list){
            if(entity.getParentId().equals(pid)){
                ResourceEntity scopeMode = new ResourceEntity();
                JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(entity,scopeMode);
                temp = getResourcesTree(list,entity.getId());
                if(temp.size() > 0){
                    scopeMode.setChildren(temp);
                }
                result.add(scopeMode);
            }
        }
        return result;
    }

    /**
     * 获取材料类型树
     */
    public static List<MaterialCategoryEntity> getMaterialCategoryTree(List<MaterialCategoryEntity> list, Long pid) {
        List<MaterialCategoryEntity> result =  new ArrayList<>();
        List<MaterialCategoryEntity> temp;
        for(MaterialCategoryEntity entity : list){
            if(entity.getParentId().equals(pid)){
                MaterialCategoryEntity scopeMode = new MaterialCategoryEntity();
                JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(entity,scopeMode);
                temp = getMaterialCategoryTree(list, entity.getId());
                if(temp.size() > 0){
                    scopeMode.setChildren(temp);
                }
                result.add(scopeMode);
            }
        }
        return result;
    }
}
