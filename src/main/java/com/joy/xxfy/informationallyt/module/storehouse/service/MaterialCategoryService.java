package com.joy.xxfy.informationallyt.module.storehouse.service;

import com.joy.xxfy.informationallyt.module.common.constant.CommonConstant;
import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.MaterialCategoryEntity;
import com.joy.xxfy.informationallyt.module.storehouse.domain.repository.MaterialCategoryRepository;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.MaterialCategoryAddReq;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.MaterialCategoryUpdateReq;
import com.joy.xxfy.informationallyt.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationallyt.publish.result.JoyResult;
import com.joy.xxfy.informationallyt.publish.result.Notice;
import com.joy.xxfy.informationallyt.publish.utils.DateUtil;
import com.joy.xxfy.informationallyt.publish.utils.project.TreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MaterialCategoryService {

    @Autowired
    private MaterialCategoryRepository materialCategoryRepository;

    /**
     * 新增
     */
    public JoyResult add(MaterialCategoryAddReq req) {
        // 检测父类型信息
        MaterialCategoryEntity parent = materialCategoryRepository.findAllById(req.getParentId());
        if(!req.getParentId().equals(CommonConstant.TOP_NODE_ID) && parent == null){
            return JoyResult.buildFailedResult(Notice.PARENT_DATA_NOT_EXIST);
        }

        // 检测名字是否重复
        MaterialCategoryEntity checkRepeat = materialCategoryRepository.findFirstByParentIdAndName(req.getParentId(), req.getName());
        if(checkRepeat != null){
            return JoyResult.buildFailedResult(Notice.NAME_ALREADY_EXIST);
        }

        // 装配类型数据
        MaterialCategoryEntity category = new MaterialCategoryEntity();
        category.setParentId(req.getParentId());
        category.setName(req.getName());
        category.setRemarks(req.getRemarks());
        category.setPath("");
        MaterialCategoryEntity save = materialCategoryRepository.save(category);
        // 通过ID回写路径信息
        String path = getSuitPath(parent, save);
        save.setPath(path);
        return JoyResult.buildSuccessResultWithData(materialCategoryRepository.save(save));
    }

    /**
     * 更新
     */
    public JoyResult update(MaterialCategoryUpdateReq req) {
        // 获取信息
        MaterialCategoryEntity info = materialCategoryRepository.findAllById(req.getId());
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DATA_NOT_EXIST);
        }
        // 检测名字是否重复
        MaterialCategoryEntity checkRepeat = materialCategoryRepository.findFirstByParentIdAndName(info.getParentId(), req.getName());
        if(checkRepeat != null && !checkRepeat.getId().equals(info.getId())){
            return JoyResult.buildFailedResult(Notice.NAME_ALREADY_EXIST);
        }
        info.setName(req.getName());
        info.setUpdateTime(DateUtil.now());
        info.setRemarks(req.getRemarks());
        return JoyResult.buildSuccessResultWithData(materialCategoryRepository.save(info));
    }

    /**
     * 获取信息
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(materialCategoryRepository.findAllById(id));
    }

    /**
     * 删除信息
     */
    public JoyResult delete(Long id) {
        MaterialCategoryEntity info = materialCategoryRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DATA_NOT_EXIST);
        }
        materialCategoryRepository.deleteById(id);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取子节点，一层
     */
    public JoyResult getChildren(Long parentId) {
        return JoyResult.buildSuccessResultWithData(materialCategoryRepository.findAllByParentId(parentId));
    }

    /**
     * 获取树
     */
    public JoyResult getTree(Long parentId) {
        MaterialCategoryEntity parent = materialCategoryRepository.findAllById(parentId);
        String path = parent == null? CommonConstant.VALUE_EMPTY_STRING : parent.getPath();
        List<MaterialCategoryEntity> children = materialCategoryRepository.findAllByPathStartingWith(path);
        return JoyResult.buildSuccessResultWithData(TreeUtil.getMaterialCategoryTree(children, parent == null ? 0 : parent.getId()));
    }

    /*
     * 拼接Path 1- 1-2-(if 2 is 1 child.)
     */
    private String getSuitPath(MaterialCategoryEntity parent, MaterialCategoryEntity child){
        if(parent == null){
            return child.getId() + CommonConstant.PATH_SEPARATOR;
        }else{
            return parent.getPath()  + child.getId() +  CommonConstant.PATH_SEPARATOR;
        }
    }


    // 获取父部门遍历树（包含自身）
    public JoyResult getParentNodes(Long id) {
        List<MaterialCategoryEntity> cs = new ArrayList<>();
        while(id != 0){
            MaterialCategoryEntity c = materialCategoryRepository.findAllById(id);
            if(c == null){
                break;
            }
            cs.add(c);
            id = c.getParentId();
        }
        // 翻转
        Collections.reverse(cs);
        return JoyResult.buildSuccessResultWithData(cs);
    }

    // 获取父部门遍历树字符串（包含自身）
    public JoyResult getParentNodesJustIds(Long id) {
        List<MaterialCategoryEntity> cs = (List<MaterialCategoryEntity>) getParentNodes(id).getData();
        return JoyResult.buildSuccessResultWithData(cs.stream().map(d -> d.getId()).collect(Collectors.toList()));
    }

}
