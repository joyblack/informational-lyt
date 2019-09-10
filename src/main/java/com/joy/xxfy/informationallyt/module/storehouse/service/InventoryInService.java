package com.joy.xxfy.informationallyt.module.storehouse.service;

import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.*;
import com.joy.xxfy.informationallyt.module.storehouse.domain.repository.*;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.InventoryInAddReq;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.InventoryInGetListReq;
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

@Service
@Transactional
public class InventoryInService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private InventoryInRepository inventoryInRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private MaterialCategoryRepository materialCategoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private StorehouseRepository storehouseRepository;

    /**
     * 入库
     */
    public JoyResult add(InventoryInAddReq req){
        /**
         * 获取本次入库所需的基本数据信息：类别、供货商、仓库
         */
        // 类别信息
        MaterialCategoryEntity materialCategory = materialCategoryRepository.findAllById(req.getMaterialCategoryId());
        if(materialCategory == null){
            return JoyResult.buildFailedResult(Notice.MATERIAL_CATEGORY_NOT_EXIST);
        }
        // 供货商信息
        SupplierEntity supplier = supplierRepository.findAllById(req.getSupplierId());
        if(supplier == null){
            return JoyResult.buildFailedResult(Notice.SUPPLIER_NOT_EXIST);
        }
        // 仓库
        StorehouseEntity storehouse = storehouseRepository.findAllById(req.getStorehouseId());
        if(storehouse == null){
            return JoyResult.buildFailedResult(Notice.STOREHOUSE_NOT_EXIST);
        }
        /**
         * 处理材料的总库存信息
         * 若不存在，新建材料。
         */
        MaterialEntity material = materialRepository.findFirstByNameAndModelNumberAndSupplier(req.getName(), req.getModelNumber(), supplier);
        if(material == null){
            material = new MaterialEntity();
            // 总量即为本次入库总量
            material.setAmount(req.getInNumber());
            material.setMaterialCategory(materialCategory);
            material.setName(req.getName());
            material.setModelNumber(req.getModelNumber());
            material.setSupplier(supplier);
        }else{
            material.setAmount(material.getAmount() + req.getInNumber());
        }
        material = materialRepository.save(material);

        /**
         * 修改库存信息，新增入库数量
         * 若不存在，新建库存信息
         */
        InventoryEntity inventoryEntity = inventoryRepository.findFirstByStorehouseAndMaterial(storehouse, material);
        if(inventoryEntity == null){
            inventoryEntity = new InventoryEntity();
            inventoryEntity.setAmount(req.getInNumber());
            inventoryEntity.setMaterial(material);
            inventoryEntity.setStorehouse(storehouse);
        }else{
            inventoryEntity.setAmount(inventoryEntity.getAmount() + req.getInNumber());
        }
        inventoryRepository.save(inventoryEntity);

        /**
         * 记录入库日志
         */
        InventoryInEntity log = new InventoryInEntity();
        // 一些可直接copy的信息
        JoyBeanUtil.copyPropertiesIgnoreSourceNullProperties(req, log);
        // 材料
        log.setMaterial(material);
        // 仓库
        log.setStorehouse(storehouse);
        return JoyResult.buildSuccessResultWithData(inventoryInRepository.save(log));
    }


    /**
     * 获取数据(id)
     */
    public JoyResult get(Long id) {
        return JoyResult.buildSuccessResultWithData(inventoryInRepository.findAllById(id));
    }

    /**
     * 获取分页数据
     */
    public JoyResult getPagerList(InventoryInGetListReq req) {
        return JoyResult.buildSuccessResultWithData(inventoryInRepository.findAll(getPredicates(req), JpaPagerUtil.getPageable(req)));
    }

    /**
     * 获取全部
     */
    public JoyResult getAllList(InventoryInGetListReq req) {
        return JoyResult.buildSuccessResultWithData(inventoryInRepository.findAll(getPredicates(req)));
    }

    /**
     * 获取分页数据、全部数据的谓词条件
     */
    private Specification<InventoryInEntity> getPredicates(InventoryInGetListReq req){
        return (Specification<InventoryInEntity>) (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            /**
             * 材料名称
             */
            if(!StringUtil.isEmpty(req.getName())){
                predicates.add(builder.like(root.get("name"), "%" + req.getName() +"%"));
            }
            /**
             * 型号
             */
            if(!StringUtil.isEmpty(req.getModelNumber())){
                predicates.add(builder.like(root.get("material").get("modelNumber"), "%" + req.getModelNumber() +"%"));
            }
            /**
             * 签收人
             */
            if(!StringUtil.isEmpty(req.getSignPeople())){
                predicates.add(builder.like(root.get("signPeople"), "%" + req.getSignPeople() +"%"));
            }
            /**
             * 材料类别
             */
            if(req.getMaterialCategoryId() != null){
                predicates.add(builder.equal(root.get("material").get("materialCategory").get("id"), req.getMaterialCategoryId()));
            }
            /**
             * 仓库名称
             */
            if(req.getStorehouseId() != null){
                predicates.add(builder.equal(root.get("storehouse").get("id"), req.getStorehouseId()));
            }

            /**
             * 供应商
             */
            if(req.getSupplierId() != null){
                predicates.add(builder.equal(root.get("material").get("supplier").get("id"), req.getSupplierId()));
            }

            if(req.getSupplierId() != null){
                predicates.add(builder.equal(root.get("material").get("supplier").get("id"), req.getSupplierId()));
            }
            /**
             * 入库时间区间
             */
            if(req.getInDateStart() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("inDate"), req.getInDateStart()));
            }
            if(req.getInDateEnd() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("inDate"), req.getInDateEnd()));
            }
            return builder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }


}
