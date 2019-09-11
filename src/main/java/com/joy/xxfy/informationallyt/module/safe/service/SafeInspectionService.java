package com.joy.xxfy.informationallyt.module.safe.service;

import com.joy.xxfy.informationallyt.module.safe.domain.entity.SafeInspectionEntity;
import com.joy.xxfy.informationallyt.module.safe.domain.repository.SafeInspectionRepository;
import com.joy.xxfy.informationallyt.module.safe.web.req.SafeInspectionAddReq;
import com.joy.xxfy.informationallyt.module.safe.web.req.SafeInspectionGetListReq;
import com.joy.xxfy.informationallyt.module.safe.web.req.SafeInspectionUpdateReq;
import com.joy.xxfy.informationallyt.module.system.domain.entity.DepartmentEntity;
import com.joy.xxfy.informationallyt.module.system.domain.repository.DepartmentRepository;
import com.joy.xxfy.informationallyt.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationallyt.publish.result.JoyResult;
import com.joy.xxfy.informationallyt.publish.result.Notice;
import com.joy.xxfy.informationallyt.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationallyt.publish.utils.JpaPagerUtil;
import com.joy.xxfy.informationallyt.publish.utils.StringUtil;
import com.joy.xxfy.informationallyt.validated.ValidList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class SafeInspectionService {
    @Autowired
    private SafeInspectionRepository safeInspectionRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * 添加
     */
    public JoyResult add(SafeInspectionAddReq req) {
        // 验证检查部门是否存在
        DepartmentEntity department = departmentRepository.findAllById(req.getCheckDepartmentId());
        if(department == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        // 装配数据
        SafeInspectionEntity info = new SafeInspectionEntity();
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);
        info.setCheckDepartment(department);
        return JoyResult.buildSuccessResultWithData(safeInspectionRepository.save(info));
    }

    /**
     * 批量添加
     */
    public JoyResult add(ValidList<SafeInspectionAddReq> reqs) {
        List<SafeInspectionEntity> infos = new ArrayList<>();
        for (SafeInspectionAddReq req : reqs) {
            DepartmentEntity department = departmentRepository.findAllById(req.getCheckDepartmentId());
            if(department == null){
                return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
            }
            // 装配数据
            SafeInspectionEntity info = new SafeInspectionEntity();
            JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);
            info.setCheckDepartment(department);
            infos.add(info);
        }
        if(infos.size() > 0){
            safeInspectionRepository.saveAll(infos);
        }
        return JoyResult.buildSuccessResultWithData(ResultDataConstant.MESSAGE_ADD_SUCCESS);
    }

    /**
     * 改
     */
    public JoyResult update(SafeInspectionUpdateReq req) {
        // 巡检信息是否存在
        SafeInspectionEntity safeInspectionEntity = safeInspectionRepository.findAllById(req.getId());
        if(safeInspectionEntity == null){
            return JoyResult.buildFailedResult(Notice.DATA_NOT_EXIST);
        }
        // 验证巡检部门是否存在
        DepartmentEntity department = departmentRepository.findAllById(req.getCheckDepartmentId());
        if(department == null){
            return JoyResult.buildFailedResult(Notice.DEPARTMENT_NOT_EXIST);
        }
        // 装配数据
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, safeInspectionEntity);
        safeInspectionEntity.setCheckDepartment(department);
        return JoyResult.buildSuccessResultWithData(safeInspectionRepository.save(safeInspectionEntity));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        // 巡检信息是否存在
        SafeInspectionEntity safeInspectionEntity = safeInspectionRepository.findAllById(id);
        if(safeInspectionEntity == null){
            return JoyResult.buildFailedResult(Notice.DATA_NOT_EXIST);
        }
        safeInspectionRepository.deleteById(id);
        return JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS);
    }

    /**
     * 获取数据
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(safeInspectionRepository.findAllById(id));
    }

    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(SafeInspectionGetListReq req) {
        return JoyResult.buildSuccessResultWithData(safeInspectionRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(SafeInspectionGetListReq req) {
        return JoyResult.buildSuccessResultWithData(safeInspectionRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<SafeInspectionEntity> getPredicates(SafeInspectionGetListReq req){
        return (Specification<SafeInspectionEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            // check time between
            if(req.getCheckDateStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("checkTime"), req.getCheckDateStart()));
            }
            if(req.getCheckDateEnd() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("checkTime"), req.getCheckDateEnd()));
            }
            // examine time between
            if(req.getExamineDateStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("examineDate"), req.getExamineDateStart()));
            }
            if(req.getExamineDateEnd() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("examineDate"), req.getExamineDateEnd()));
            }
            // check department
            if(req.getCheckDepartmentId() != null){
                predicates.add(builder.equal(root.get("checkDepartment").get("id"), req.getCheckDepartmentId()));
            }
            // problemContent
            if(!StringUtil.isEmpty(req.getProblemContent())){
                predicates.add(builder.like(root.get("problemContent"), "%" + req.getProblemContent() + "%"));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}
