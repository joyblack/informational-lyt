package com.joy.xxfy.informationallyt.module.storehouse.service;

import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.SupplierEntity;
import com.joy.xxfy.informationallyt.module.storehouse.domain.repository.SupplierRepository;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.ManufacturerAddReq;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.ManufacturerGetListReq;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.ManufacturerUpdateReq;
import com.joy.xxfy.informationallyt.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationallyt.publish.result.JoyResult;
import com.joy.xxfy.informationallyt.publish.result.Notice;
import com.joy.xxfy.informationallyt.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationallyt.publish.utils.JpaPagerUtil;
import com.joy.xxfy.informationallyt.publish.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    /**
     * 添加
     */
    public JoyResult add(ManufacturerAddReq req) {
        // 验证名称是否重复
        SupplierEntity checkInfo = supplierRepository.findFirstByName(req.getName());
        if(checkInfo != null){
            return JoyResult.buildFailedResult(Notice.NAME_ALREADY_EXIST);
        }
        // copy properties
        SupplierEntity info = new SupplierEntity();
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);
        return JoyResult.buildSuccessResultWithData(supplierRepository.save(info));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        SupplierEntity info = supplierRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DATA_NOT_EXIST);
        }
        supplierRepository.deleteById(id);
        return JoyResult.buildSuccessResultWithData(JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS));
    }

    /**
     * 获取数据(id)
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(supplierRepository.findAllById(id));
    }


    /**
     * 更新
     */
    public JoyResult update(ManufacturerUpdateReq req) {
        SupplierEntity info = supplierRepository.findAllById(req.getId());
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DATA_NOT_EXIST);
        }
        // 验证名称是否重复
        SupplierEntity checkInfo = supplierRepository.findFirstByName(req.getName());
        if(checkInfo != null && !checkInfo.getId().equals(info.getId())){
            return JoyResult.buildFailedResult(Notice.NAME_ALREADY_EXIST);
        }

        // 拷贝信息
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);

        // save.
        return JoyResult.buildSuccessResultWithData(supplierRepository.save(info));
    }

    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(ManufacturerGetListReq req) {
        return JoyResult.buildSuccessResultWithData(supplierRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(ManufacturerGetListReq req) {
        return JoyResult.buildSuccessResultWithData(supplierRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<SupplierEntity> getPredicates(ManufacturerGetListReq req){
        return (Specification<SupplierEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!StringUtil.isEmpty(req.getName())){
                predicates.add(builder.like(root.get("name"), "%" + req.getName() +"%"));
            }
            if(!StringUtil.isEmpty(req.getBusiness())){
                predicates.add(builder.like(root.get("business"), "%" + req.getBusiness() +"%"));
            }
            if(!StringUtil.isEmpty(req.getContactPeople())){
                predicates.add(builder.like(root.get("contactPeople"), "%" + req.getContactPeople() +"%"));
            }
            if(!StringUtil.isEmpty(req.getContactPhone())){
                predicates.add(builder.like(root.get("contactPhone"), "%" + req.getContactPhone() +"%"));
            }
            if(!StringUtil.isEmpty(req.getCode())){
                predicates.add(builder.like(root.get("code"), "%" + req.getCode() +"%"));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}
