package com.joy.xxfy.informationallyt.module.system.service;

import com.joy.xxfy.informationallyt.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationallyt.module.system.domain.entity.UserEntity;
import com.joy.xxfy.informationallyt.module.system.domain.enums.DepartmentTypeEnum;
import com.joy.xxfy.informationallyt.module.system.domain.enums.UserTypeEnum;
import com.joy.xxfy.informationallyt.module.system.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationallyt.module.system.web.req.DepartmentAddReq;
import com.joy.xxfy.informationallyt.module.system.web.req.DepartmentUpdateReq;
import com.joy.xxfy.informationallyt.publish.constant.DepartmentConstant;
import com.joy.xxfy.informationallyt.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationallyt.publish.constant.SystemConstant;
import com.joy.xxfy.informationallyt.publish.result.JoyResult;
import com.joy.xxfy.informationallyt.publish.result.Notice;
import com.joy.xxfy.informationallyt.publish.utils.LogUtil;
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
public class DepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * 新增
     */
    public JoyResult add(DepartmentAddReq req) {
        // check parent dept info.
        DepartmentEntity parent = departmentRepository.findAllById(req.getParentId());
        if(!req.getParentId().equals(DepartmentConstant.COMPANY_PARENT_NODE_ID) && parent == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_PARENT_NOT_EXIST);
        }
        // check same name on common level.
        DepartmentEntity checkDepartment = departmentRepository.findAllByParentIdAndName(req.getParentId(), req.getDepartmentName());
        if(checkDepartment != null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NAME_ALREADY_EXIST);
        }
        // entity
        DepartmentEntity department = new DepartmentEntity();
        // 名称
        department.setName(req.getDepartmentName());
        // 电话
        department.setPhone(req.getPhone());
        // 负责人
        department.setResponseUser(req.getResponseUser());
        // 编码
        department.setCode(req.getCode());
        // 备注
        department.setRemarks(req.getRemarks());
        // 父部门信息
        department.setParentId(req.getParentId());
        department.setPath("");
        DepartmentEntity save = departmentRepository.save(department);
        // 通过ID回写路径信息
        String path = getSuitPath(parent, department);
        save.setPath(path);
        LogUtil.info("Joy: Repeat update the department [path]:", path);
        return JoyResult.buildSuccessResultWithData(departmentRepository.save(save));
    }

    /**
     * 更新
     */
    public JoyResult update(DepartmentUpdateReq req) {
        // 获取部门信息
        DepartmentEntity departmentInfo = departmentRepository.findAllById(req.getId());
        if(departmentInfo == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        // check parent dept info.
        DepartmentEntity parent = departmentRepository.findAllById(req.getParentId());
        if(!req.getParentId().equals(DepartmentConstant.COMPANY_PARENT_NODE_ID) && parent == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_PARENT_NOT_EXIST);
        }
        // 检查是否有重名部门
        DepartmentEntity checkDepartment = departmentRepository.findAllByParentIdAndNameAndIdNot(req.getParentId(), req.getDepartmentName(),req.getId());
        if(checkDepartment != null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NAME_ALREADY_EXIST);
        }

        if(req.getDepartmentName() != null){
            departmentInfo.setName(req.getDepartmentName());
        }
        if(req.getPhone() != null){
            departmentInfo.setPhone(req.getPhone());
        }
        // 负责人
        if(req.getResponseUser() != null){
            departmentInfo.setResponseUser(req.getResponseUser());
        }
        // 编码
        if(req.getCode() != null){
            departmentInfo.setCode(req.getCode());
        }
        // 备注
        if(req.getRemarks() != null){
            departmentInfo.setRemarks(req.getRemarks());
        }
        return JoyResult.buildSuccessResultWithData(departmentInfo);
    }

    /**
     * 获取部门信息
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(departmentRepository.findAllById(id));
    }

    /**
     * 删除部门信息
     */
    public JoyResult delete(Long id) {
        DepartmentEntity oldDept = departmentRepository.findAllById(id);
        if(oldDept == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        try{
            departmentRepository.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return JoyResult.buildFailedResult(Notice.DATA_IN_USED_CANT_BE_DELETE);
        }
        return JoyResult.buildSuccessResultWithData(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取部门子节点，一层
     */
    public JoyResult getChildren(Long parentId) {
        return JoyResult.buildSuccessResultWithData(departmentRepository.findAllByParentId(parentId));
    }

    // 获取部门树
    public JoyResult getTree(Long parentId) {
        DepartmentEntity department = departmentRepository.findAllById(parentId);
        String path = department == null? SystemConstant.EMPTY_VALUE : department.getPath();
        List<DepartmentEntity> children = departmentRepository.findAllByPathStartingWith(path);
        return JoyResult.buildSuccessResultWithData(TreeUtil.getDeptTree(children, department == null ? 0 : department.getId()));
    }



    /*
     * 拼接Path 1- 1-2-(if 2 is 1 child.)
     */
    private String getSuitPath(DepartmentEntity parent, DepartmentEntity child){
        if(parent == null){
            return child.getId() + DepartmentConstant.DEPARTMENT_PATH_SEPARATOR;
        }else{
            return parent.getPath()  + child.getId() +  DepartmentConstant.DEPARTMENT_PATH_SEPARATOR;
        }
    }


    // 获取父部门遍历树（包含自身）
    public JoyResult getParentNodes(Long id) {
        List<DepartmentEntity> departments = new ArrayList<>();
        while(id != 0){
            DepartmentEntity department = departmentRepository.findAllById(id);
            if(department == null){
                break;
            }
            departments.add(department);
            id = department.getParentId();
        }
        // 移除顶层节点
        departments.remove(departments.size() - 1);
        // 导入插入结果
        Collections.reverse(departments);

        return JoyResult.buildSuccessResultWithData(departments);
    }

    // 获取父部门遍历树字符串（包含自身）
    public JoyResult getParentNodesJustIds(Long id) {
        List<DepartmentEntity> departments = (List<DepartmentEntity>)getParentNodes(id).getData();
        return JoyResult.buildSuccessResultWithData(departments.stream().map(d -> d.getId()).collect(Collectors.toList()));
    }
}
