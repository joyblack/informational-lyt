package com.joy.xxfy.informationallyt.module.storehouse.service;

import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.StorehouseEntity;
import com.joy.xxfy.informationallyt.module.storehouse.domain.repository.StorehouseRepository;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.StorehouseAddReq;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.StorehouseGetListReq;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.StorehouseUpdateReq;
import com.joy.xxfy.informationallyt.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationallyt.publish.result.JoyResult;
import com.joy.xxfy.informationallyt.publish.result.Notice;
import com.joy.xxfy.informationallyt.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationallyt.publish.utils.StringUtil;
import com.joy.xxfy.informationallyt.publish.utils.JpaPagerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class StorehouseService {

    @Autowired
    private StorehouseRepository storehouseRepository;

    /**
     * 添加
     */
    public JoyResult add(StorehouseAddReq req) {
        // 验证名称是否重复
        StorehouseEntity checkInfo = storehouseRepository.findFirstByName(req.getName());
        if(checkInfo != null){
            return JoyResult.buildFailedResult(Notice.NAME_ALREADY_EXIST);
        }
        // copy properties
        StorehouseEntity info = new StorehouseEntity();
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);
        return JoyResult.buildSuccessResultWithData(storehouseRepository.save(info));
    }

    /**
     * 删除
     */
    public JoyResult delete(Long id) {
        StorehouseEntity info = storehouseRepository.findAllById(id);
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DATA_NOT_EXIST);
        }
        storehouseRepository.deleteById(id);
        return JoyResult.buildSuccessResultWithData(JoyResult.buildSuccessResult(ResultDataConstant.MESSAGE_DELETE_SUCCESS));
    }

    /**
     * 获取数据(id)
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(storehouseRepository.findAllById(id));
    }


    /**
     * 更新
     */
    public JoyResult update(StorehouseUpdateReq req) {
        StorehouseEntity info = storehouseRepository.findAllById(req.getId());
        if(info == null){
            return JoyResult.buildFailedResult(Notice.DATA_NOT_EXIST);
        }
        // 验证名称是否重复
        StorehouseEntity checkInfo = storehouseRepository.findFirstByName(req.getName());
        if(checkInfo != null && !checkInfo.getId().equals(info.getId())){
            return JoyResult.buildFailedResult(Notice.NAME_ALREADY_EXIST);
        }

        // 拷贝信息
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, info);

        // save.
        return JoyResult.buildSuccessResultWithData(storehouseRepository.save(info));
    }

    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(StorehouseGetListReq req) {
        return JoyResult.buildSuccessResultWithData(storehouseRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(StorehouseGetListReq req) {
        return JoyResult.buildSuccessResultWithData(storehouseRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<StorehouseEntity> getPredicates(StorehouseGetListReq req){
        return (Specification<StorehouseEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!StringUtil.isEmpty(req.getName())){
                predicates.add(builder.like(root.get("name"), "%" + req.getName() +"%"));
            }
            if(!StringUtil.isEmpty(req.getAdmin())){
                predicates.add(builder.like(root.get("admin"), "%" + req.getAdmin() +"%"));
            }
            if(!StringUtil.isEmpty(req.getResponsePeople())){
                predicates.add(builder.like(root.get("responsePeople"), "%" + req.getResponsePeople() +"%"));
            }
            if(!StringUtil.isEmpty(req.getCode())){
                predicates.add(builder.like(root.get("code"), "%" + req.getCode() +"%"));
            }

            if(req.getStatus() != null){
                predicates.add(builder.equal(root.get("status"), req.getStatus()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }

}
