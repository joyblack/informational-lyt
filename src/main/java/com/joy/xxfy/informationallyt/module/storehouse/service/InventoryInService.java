package com.joy.xxfy.informationallyt.module.storehouse.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.joy.xxfy.informationallyt.module.common.constant.ExcelConstant;
import com.joy.xxfy.informationallyt.module.storehouse.domain.entity.*;
import com.joy.xxfy.informationallyt.module.storehouse.domain.repository.*;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.InventoryInAddReq;
import com.joy.xxfy.informationallyt.module.storehouse.web.req.InventoryInGetListReq;
import com.joy.xxfy.informationallyt.publish.constant.ResultDataConstant;
import com.joy.xxfy.informationallyt.publish.exception.JoyException;
import com.joy.xxfy.informationallyt.publish.result.JoyResult;
import com.joy.xxfy.informationallyt.publish.result.Notice;
import com.joy.xxfy.informationallyt.publish.utils.JoyBeanUtil;
import com.joy.xxfy.informationallyt.publish.utils.JpaPagerUtil;
import com.joy.xxfy.informationallyt.publish.utils.StringUtil;
import com.joy.xxfy.informationallyt.publish.utils.excel.ExportUtil;
import com.joy.xxfy.informationallyt.validated.ValidList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
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
        // 入库前后库存量
        Long amount = 0L;
        Long afterAmount = req.getInNumber();
        MaterialEntity material = materialRepository.findFirstByNameAndModelNumber(req.getName(), req.getModelNumber());
        if(material == null){
            material = new MaterialEntity();
            // 总量即为本次入库总量
            material.setAmount(req.getInNumber());
            material.setMaterialCategory(materialCategory);
            material.setName(req.getName());
            material.setModelNumber(req.getModelNumber());
        }else{
            amount = material.getAmount();
            material.setAmount(material.getAmount() + req.getInNumber());
            afterAmount = material.getAmount();
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
        // 供货商
        log.setSupplier(supplier);
        // 入库前库存量
        log.setAmount(amount);
        // 入库后库存量
        log.setAfterAmount(afterAmount);
        return JoyResult.buildSuccessResultWithData(inventoryInRepository.save(log));
    }

    /**
     * 批量入库
     */
    public JoyResult batchAdd(ValidList<InventoryInAddReq> reqs){
        for (InventoryInAddReq req : reqs) {
            /**
             * 获取本次入库所需的基本数据信息：类别、供货商、仓库
             */
            // 类别信息
            MaterialCategoryEntity materialCategory = materialCategoryRepository.findAllById(req.getMaterialCategoryId());
            if(materialCategory == null){
                throw new JoyException(Notice.MATERIAL_CATEGORY_NOT_EXIST);
            }
            // 供货商信息
            SupplierEntity supplier = supplierRepository.findAllById(req.getSupplierId());
            if(supplier == null){
                throw new JoyException(Notice.SUPPLIER_NOT_EXIST);
            }
            // 仓库
            StorehouseEntity storehouse = storehouseRepository.findAllById(req.getStorehouseId());
            if(storehouse == null){
                throw new JoyException(Notice.STOREHOUSE_NOT_EXIST);
            }
            /**
             * 处理材料的总库存信息
             * 若不存在，新建材料。
             */
            // 入库前后库存量
            Long amount = 0L;
            Long afterAmount = req.getInNumber();
            MaterialEntity material = materialRepository.findFirstByNameAndModelNumber(req.getName(), req.getModelNumber());
            if(material == null){
                material = new MaterialEntity();
                // 总量即为本次入库总量
                material.setAmount(req.getInNumber());
                material.setMaterialCategory(materialCategory);
                material.setName(req.getName());
                material.setModelNumber(req.getModelNumber());
            }else{
                amount = material.getAmount();
                material.setAmount(material.getAmount() + req.getInNumber());
                afterAmount = material.getAmount();
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
            // 供货商
            log.setSupplier(supplier);
            // 入库前库存量
            log.setAmount(amount);
            // 入库后库存量
            log.setAfterAmount(afterAmount);
            inventoryInRepository.save(log);
        }
        return JoyResult.buildSuccessResultWithData(ResultDataConstant.MESSAGE_ADD_SUCCESS);
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
                predicates.add(builder.like(root.get("material").get("name"), "%" + req.getName() +"%"));
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


    public void exportData(InventoryInGetListReq req, HttpServletRequest request, HttpServletResponse response) {
        // 获取查询结果
        List<InventoryInEntity> inventoryIns = (List<InventoryInEntity>)getAllList(req).getData();
        // 日期导出格式
        SimpleDateFormat dateFormat = new SimpleDateFormat(ExcelConstant.DATE_FORMAT);
        List<List<Object>> rows = new ArrayList<>();
        rows.add(CollUtil.newArrayList(ExcelConstant.HEAD_INVENTORY_IN));
        for (int i = 0; i < inventoryIns.size(); i++) {
            InventoryInEntity info = inventoryIns.get(i);
            List<Object> row = CollUtil.newArrayList(
                    // 序号（也可以使用ID?）
                    i + 1,
                    // 材料名称
                    info.getMaterial().getName(),
                    // 型号
                    info.getMaterial().getModelNumber(),
                    // 供货单位/供货人
                    info.getSupplier().getName(),
                    // 材料类别
                    info.getMaterial().getMaterialCategory().getName(),
                    // 库存总数（从材料对象处获得）
                    info.getMaterial().getAmount(),
                    // 入库数量
                    info.getInNumber().toString(),
                    // 入库仓库
                    info.getStorehouse().getName(),
                    // 入库后总数
                    info.getAfterAmount(),
                    // 入库时间
                    dateFormat.format(info.getInDate()),
                    // 签收人
                    info.getSignPeople(),
                    // 备注
                    info.getRemarks()
            );
            rows.add(row);
        }
        String fileName = "入库信息表";
        ExcelWriter writer = ExcelUtil.getWriter();
        writer.write(rows, true);
        ExportUtil.exportData(request, response, fileName, writer);
    }
}
